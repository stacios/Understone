package Model;

import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class SQLTesting {

    SQLiteDataSource ds = new SQLiteDataSource();

    public SQLTesting() {
        initializeDatabase();
    }

    public void initializeDatabase() {

        try {
            ds.setUrl("jdbc:sqlite:UDB.db");
        } catch ( Exception e ) {
            System.err.println("Failed to set up the data source.");
            e.printStackTrace();
            System.exit(0);
        }

        System.out.println("Opened database successfully");

        String createTableQuery = "CREATE TABLE IF NOT EXISTS UDB (" +
                "NUMBER INTEGER)";
        executeUpdate(createTableQuery, "Updated Understone Database Table");
    }

    private void executeUpdate(String theQuery, String theMsg) {
        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement()) {
            int rv = stmt.executeUpdate(theQuery);
            System.out.println(theMsg + ", executeUpdate() returned " + rv);
        } catch (SQLException e) {
            System.err.println("Error executing update: " + theQuery);
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void insertNumber(int theNum) {
        String insertNumberQuery = "INSERT INTO UDB (NUMBER) VALUES (?)";
        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertNumberQuery)) {
            pstmt.setInt(1, theNum);
            int rv = pstmt.executeUpdate();
            System.out.println("Inserted number " + theNum + " into the database, executeUpdate() returned " + rv);
        } catch (SQLException e) {
            System.err.println("Error inserting number into the database.");
            e.printStackTrace();
            System.exit(0);
        }
    }
}

package Model.DB;

import java.sql.*;

public class DwarfDB {

    // TODO: Create values/methods for all 4 dwarves, as well as ways to access each table.
    public static void initializeDB() {
        initializeDefaults();
        insertDefaults();
    }

    public static void initializeDefaults() {
        String createTable = "CREATE TABLE IF NOT EXISTS DwarfDefaults (" +
                "setting TEXT PRIMARY KEY, " +
                "value REAL);";

        try (Connection conn = SQLiteConnection.getDataSource().getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTable);
            System.out.println("DwarfDefaults table created.");
        } catch (SQLException e) {
            System.err.println("Error creating DwarfDefaults table: " + e.getMessage());
        }
    }


    public static void insertDefaults() {
        String insertDefaults = "INSERT OR REPLACE INTO DwarfDefaults (setting, value) VALUES " +
                "('health', 100), " +
                "('maxhealth', 130), " +
                "('width', 1), " +
                "('height', 1), " +
                "('moveSpeed', 2.0), " +
                "('damage', 10);";

        try (Connection conn = SQLiteConnection.getDataSource().getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(insertDefaults);
            System.out.println("Default Dwarf data inserted.");
        } catch (SQLException e) {
            System.err.println("Error inserting values into DwarfDefaults table: " + e.getMessage());
        }
    }

    public static int getDefaultValue(String theQuery) {
        String query = "SELECT value FROM DwarfDefaults WHERE setting = ?";

        try (Connection conn = SQLiteConnection.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, theQuery);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("value");
            }
        } catch (SQLException e) {
            System.err.println("Error getting value for " + theQuery + ": " + e.getMessage());
        }

        System.err.println("Warning! Default value not found for query: " + theQuery + ". Inserting default value of 0.");

        return 0;
    }

}

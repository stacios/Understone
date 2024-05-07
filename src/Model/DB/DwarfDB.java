package Model.DB;

import java.sql.*;

public class DwarfDB {

    public static void intitializeDwarfDB() {
        initializeDwarf();
    }

    public static void initializeDwarf() {
        String dwarfTable = "CREATE TABLE IF NOT EXISTS Dwarf (" +
                "name TEXT NOT NULL, " +
                "health INTEGER, " +
                "maxHealth INTEGER, " +
                "width INTEGER, " +
                "height INTEGER, " +
                "moveSpeed REAL);";

        try (Connection conn = SQLiteConnection.getDataSource().getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(dwarfTable);
            System.out.println("Dwarf table created.");
        } catch (SQLException e) {
            System.err.println("Dwarf table error creation: " + e.getMessage());
        }
    }

    public static void updateDwarf(String theName, int theHealth, int maxHealth, double theWidth, double theHeight, double theMoveSpeed) {
        String updateDwarf = "REPLACE INTO Dwarf (name, health, maxHealth, width, height, moveSpeed) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = SQLiteConnection.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateDwarf)) {
            pstmt.setString(1, theName);
            pstmt.setInt(2, theHealth);
            pstmt.setInt(3, maxHealth);
            pstmt.setDouble(4, theWidth);
            pstmt.setDouble(5, theHeight);
            pstmt.setDouble(6, theMoveSpeed);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Dwarf updated successfully.");
            } else {
                System.out.println("No changes were made to the database.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating dwarf: " + e.getMessage());
        }
    }

}

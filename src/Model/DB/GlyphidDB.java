package Model.DB;

import java.sql.*;

public class GlyphidDB {

    public static void intitializeDwarfDB() {

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
                "('maxhealth', 130)" +
                "('width', 1), " +
                "('height', 1), " +
                "('moveSpeed', 2.0), " +
                "('damage', 10)";

        try (Connection conn = SQLiteConnection.getDataSource().getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(insertDefaults);
            System.out.println("Default Dwarf data inserted.");
        } catch (SQLException e) {
            System.err.println("Error inserting values into DwarfDefaults table: " + e.getMessage());
        }
    }



}

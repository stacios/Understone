package Model.DB;

import java.sql.*;

public class GlyphidDB {

    public static void initializeDB() {
        initializeDefaults();
        insertDefaults();
    }

    public static void initializeDefaults() {
        String createTable = "CREATE TABLE IF NOT EXISTS GlyphidDefaults (" +
                "setting TEXT PRIMARY KEY, " +
                "value REAL);";

        try (Connection conn = SQLiteConnection.getDataSource().getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTable);
            System.out.println("GlyphidDefaults table created.");
        } catch (SQLException e) {
            System.err.println("Error creating GlyphidDefaults table: " + e.getMessage());
        }
    }

    public static void insertDefaults() {
        String insertDefaults = "INSERT OR REPLACE INTO GlyphidDefaults (setting, value) VALUES " +
                "('health', 50), " +
                "('width', 1), " +
                "('height', 1), " +
                "('moveSpeed', 1.5), " +
                "('firetimer', 5), " +
                "('damage', 5);";

        try (Connection conn = SQLiteConnection.getDataSource().getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(insertDefaults);
            System.out.println("Default Glyphid data inserted.");
        } catch (SQLException e) {
            System.err.println("Error inserting values into GlyphidDefaults table: " + e.getMessage());
        }
    }

    public static int getDefaultValue(String theQuery) {
        String query = "SELECT value FROM GlyphidDefaults WHERE setting = ?";

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

package Model.DB;

import java.sql.*;

import static Model.CharacterTypes.*;

public class GlyphidDB {

    /**
     * Initializes Glyphid Tables. Then, inserts default values for each Glyphid.
     */
    public static void initializeDB() {
        initializeGlyphidTables(ACID_SPIITER);
        initializeGlyphidTables(GLYPHID);
        initializeGlyphidTables(GRUNT);
        initializeGlyphidTables(MACTERA);
        initializeGlyphidTables(PRAETORIAN);
        initializeGlyphidTables(ROCK);
        initializeGlyphidTables(SWARMER);
        insertAcidSpitterDefaults();
        insertGlyphidDefaults();
        insertGruntDefaults();
        insertMacteraDefaults();
        insertPraetorianDefaults();
        insertRockDefaults();
        insertSwarmerDefaults();
    }

    /**
     * Initializes Glyphid Tables based on Glyphid Type.
     *
     * @param theGlyphidType is the specified Glyphid Type to be created.
     */
    private static void initializeGlyphidTables(String theGlyphidType) {
        String createTable = String.format(
                "CREATE TABLE IF NOT EXISTS %sDefaults (" +
                        "setting TEXT PRIMARY KEY, " +
                        "value REAL);", theGlyphidType);

        try (Connection conn = SQLiteConnection.getDataSource().getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTable);
            //System.out.println(theGlyphidType + "Defaults table created.");
        } catch (SQLException e) {
            System.err.println("Error creating " + theGlyphidType + "Defaults table: " + e.getMessage());
        }
    }

    /**
     * Inserts default values for Acid Spitter.
     */
    public static void insertAcidSpitterDefaults() {
        String sql = "INSERT OR REPLACE INTO AcidSpitterDefaults (setting, value) VALUES " +
                "('health', 80), " +
                "('maxhealth', 100), " +
                "('width', 1), " +
                "('height', 1), " +
                "('movespeed', 2.5), " +
                "('damage', 12), " +
                "('firetimer', 5);";

        makeSQLConnection(sql, ACID_SPIITER);
    }

    /**
     * Inserts default values for Glyphid.
     */
    public static void insertGlyphidDefaults() {
        String sql = "INSERT OR REPLACE INTO GlyphidDefaults (setting, value) VALUES " +
                "('health', 100), " +
                "('maxhealth', 120), " +
                "('width', 1), " +
                "('height', 1), " +
                "('movespeed', 2.0), " +
                "('damage', 10), " +
                "('firetimer', 5);";

        makeSQLConnection(sql, GLYPHID);
    }

    /**
     * Inserts default values for Grunt.
     */
    public static void insertGruntDefaults() {
        String sql = "INSERT OR REPLACE INTO GruntDefaults (setting, value) VALUES " +
                "('health', 90), " +
                "('maxhealth', 110), " +
                "('width', 1), " +
                "('height', 1), " +
                "('movespeed', 2.2), " +
                "('damage', 9), " +
                "('firetimer', 5);";

        makeSQLConnection(sql, GRUNT);
    }

    /**
     * Inserts default value for
     */
    public static void insertMacteraDefaults() {
        String sql = "INSERT OR REPLACE INTO MacteraDefaults (setting, value) VALUES " +
                "('health', 85), " +
                "('maxhealth', 105), " +
                "('width', 1), " +
                "('height', 1), " +
                "('movespeed', 2.3), " +
                "('damage', 11), " +
                "('firetimer', 5);";

        makeSQLConnection(sql, MACTERA);
    }

    public static void insertPraetorianDefaults() {
        String sql = "INSERT OR REPLACE INTO PraetorianDefaults (setting, value) VALUES " +
                "('health', 150), " +
                "('maxhealth', 170), " +
                "('width', 1), " +
                "('height', 1), " +
                "('movespeed', 1.8), " +
                "('damage', 15), " +
                "('firetimer', 5);";

        makeSQLConnection(sql, PRAETORIAN);
    }

    public static void insertRockDefaults() {
        String sql = "INSERT OR REPLACE INTO RockDefaults (setting, value) VALUES " +
                "('health', 200), " +
                "('maxhealth', 220), " +
                "('width', 1), " +
                "('height', 1), " +
                "('movespeed', 1.0), " +
                "('damage', 20), " +
                "('firetimer', 5);";

        makeSQLConnection(sql, ROCK);
    }

    public static void insertSwarmerDefaults() {
        String sql = "INSERT OR REPLACE INTO SwarmerDefaults (setting, value) VALUES " +
                "('health', 50), " +
                "('maxhealth', 70), " +
                "('width', 1), " +
                "('height', 1), " +
                "('movespeed', 3.0), " +
                "('damage', 5), " +
                "('firetimer', 5);";

        makeSQLConnection(sql, SWARMER);
    }

    private static void makeSQLConnection(String theSQL, String theGlyphidType) {
        try (Connection conn = SQLiteConnection.getDataSource().getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(theSQL);
            //System.out.println("Default " + theGlyphidType + " data inserted.");
        } catch (SQLException e) {
            System.err.println("Error inserting " + theGlyphidType + " values into " + theGlyphidType + " table: " + e.getMessage());
        }
    }

    public static int getDefaultValue(String glyphidType, String setting) {
        String query = String.format(
                "SELECT value FROM %sDefaults WHERE setting = ?", glyphidType);

        try (Connection conn = SQLiteConnection.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, setting);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int value = rs.getInt("value");
                //System.out.println(value);
                return rs.getInt("value");
            }
        } catch (SQLException e) {
            System.err.println("Error getting value for column " + setting + " in " + glyphidType + "Defaults: " + e.getMessage());
        }

        System.err.println("Warning! Default value not found for column: "
                + setting + " in " + glyphidType + "Defaults. Returning default value of 0.");
        return 0;
    }
}

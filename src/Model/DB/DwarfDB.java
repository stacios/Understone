package Model.DB;

import java.sql.*;

import static Model.CharacterTypes.*;

public class DwarfDB {

    /**
     * Initializes Dwarf Tables. Then, inserts default values for each Dwarf.
     */
    public static void initializeDB() {
        initializeDwarfTables(DRILLER);
        initializeDwarfTables(GUNNER);
        initializeDwarfTables(ENGINEER);
        initializeDwarfTables(SCOUT);
        insertDrillerDefaults();
        insertEngineerDefaults();
        insertGunnerDefaults();
        insertScoutDefaults();
    }

    /**
     * Initializes Dwarf Tables based on Dwarf Type.
     *
     * @param theDwarfType is the specfiied Dwarf Type to be created.
     */
    private static void initializeDwarfTables(String theDwarfType) {
        String createTable = String.format(
                "CREATE TABLE IF NOT EXISTS %sDefaults (" +
                        "setting TEXT PRIMARY KEY, " +
                        "value REAL);", theDwarfType);

        try (Connection conn = SQLiteConnection.getDataSource().getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTable);
            //System.out.println(theDwarfType + "Defaults table created.");
        } catch (SQLException e) {
            System.err.println("Error creating " + theDwarfType + "Defaults table: " + e.getMessage());
        }
    }

    /**
     * Inserts default values for Driller.
     */
    public static void insertDrillerDefaults() {
        String sql = "INSERT OR REPLACE INTO DrillerDefaults (setting, value) VALUES " +
                "('health', 110), " +
                "('maxhealth', 130), " +
                "('width', 1), " +
                "('height', 1), " +
                "('movespeed', 2.0), " +
                "('damage', 10);";

        makeSQLConnection(sql, DRILLER);
    }

    /**
     * Inserts default values for Engineer.
     */
    public static void insertEngineerDefaults() {
        String sql = "INSERT OR REPLACE INTO EngineerDefaults (setting, value) VALUES " +
                "('health', 90), " +
                "('maxhealth', 130), " +
                "('width', 1), " +
                "('height', 1), " +
                "('movespeed', 2.0), " +
                "('damage', 8);";

        makeSQLConnection(sql, ENGINEER);
    }

    public static void insertGunnerDefaults() {
        String sql = "INSERT OR REPLACE INTO GunnerDefaults (setting, value) VALUES " +
                "('health', 110), " +
                "('maxhealth', 130), " +
                "('width', 1), " +
                "('height', 1), " +
                "('movespeed', 1.5), " +
                "('damage', 10);";

        makeSQLConnection(sql, GUNNER);
    }

    public static void insertScoutDefaults() {
        String sql = "INSERT OR REPLACE INTO ScoutDefaults (setting, value) VALUES " +
                "('health', 100), " +
                "('maxhealth', 130), " +
                "('width', 1), " +
                "('height', 1), " +
                "('movespeed', 2.5), " +
                "('damage', 10);";

        makeSQLConnection(sql, SCOUT);
    }

    private static void makeSQLConnection(String theSQL, String theDwarfType) {
        try (Connection conn = SQLiteConnection.getDataSource().getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(theSQL);
            //System.out.println("Default " + theDwarfType + " data inserted.");
        } catch (SQLException e) {
            System.err.println("Error inserting " + theDwarfType + " values into " + theDwarfType + "table: " + e.getMessage());
        }
    }

    public static int getDefaultValue(String dwarfType, String setting) {
        String query = String.format(
                "SELECT value FROM %sDefaults WHERE setting = ?", dwarfType);

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
            System.err.println("Error getting value for column " + setting + " in " + dwarfType + "Defaults: " + e.getMessage());
        }

        System.err.println("Warning! Default value not found for "
                + setting + " in " + dwarfType + "Defaults. Returning default value of 0.");
        return 0;
    }
}

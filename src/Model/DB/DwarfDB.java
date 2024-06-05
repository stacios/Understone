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
        final String createTable = String.format(
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
        final String sql = "INSERT OR REPLACE INTO DrillerDefaults (setting, value) VALUES " +
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
        final String sql = "REPLACE INTO EngineerDefaults (setting, value) VALUES " +
                "('health', 90), " +
                "('maxhealth', 130), " +
                "('width', 1), " +
                "('height', 1), " +
                "('movespeed', 2.25), " +
                "('damage', 8);";

        makeSQLConnection(sql, ENGINEER);
    }

    /**
     * Inserts default values for Gunner.
     */
    public static void insertGunnerDefaults() {
        final String sql = "INSERT OR REPLACE INTO GunnerDefaults (setting, value) VALUES " +
                "('health', 110), " +
                "('maxhealth', 130), " +
                "('width', 1), " +
                "('height', 1), " +
                "('movespeed', 1.5), " +
                "('damage', 10);";

        makeSQLConnection(sql, GUNNER);
    }

    /**
     * Inserts default values for Scout.
     */
    public static void insertScoutDefaults() {
        final String sql = "INSERT OR REPLACE INTO ScoutDefaults (setting, value) VALUES " +
                "('health', 100), " +
                "('maxhealth', 130), " +
                "('width', 1), " +
                "('height', 1), " +
                "('movespeed', 2.5), " +
                "('damage', 10);";

        makeSQLConnection(sql, SCOUT);
    }

    /**
     * Makes SQL Connection and executes SQl statement to Database.
     *
     * @param theSQL is the passed SQL statement.
     * @param theDwarfType is the specified Dwarf type to be inserted.
     */
    private static void makeSQLConnection(String theSQL, String theDwarfType) {
        try (Connection conn = SQLiteConnection.getDataSource().getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(theSQL);
            //System.out.println("Default " + theDwarfType + " data inserted.");
        } catch (SQLException theEx) {
            System.err.println("Error inserting " + theDwarfType + " values into " + theDwarfType + "table: " + theEx.getMessage());
        }
    }

    /**
     * Gets default value from specified Dwarf Type from specified setting in the table.
     *
     * @param theDwarfType is the specified Dwarf Type.
     * @param theSetting is the value from the table.
     * @return value of selected column.
     */
    public static int getDefaultValue(String theDwarfType, String theSetting) {
        if (theDwarfType == "karl") return 0;
        final String query = String.format(
                "SELECT value FROM %sDefaults WHERE setting = ?", theDwarfType);

        try (Connection conn = SQLiteConnection.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, theSetting);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // TODO return value as double since we're using double values, then possibly cast to int in CharacterFactory
                int value = rs.getInt("value");
                //System.out.println(value);
                return rs.getInt("value");
            }
        } catch (SQLException theEx) {
            System.err.println("Error getting value for column " +
                    theSetting + " in " + theDwarfType + "Defaults: " + theEx.getMessage());
        }

        System.err.println("Warning! Default value not found for "
                + theSetting + " in " + theDwarfType + "Defaults. Returning default value of 0.");
        return 0;
    }
}

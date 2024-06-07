package Model.DB;

import org.sqlite.SQLiteDataSource;

/**
 * Singleton class for managing connections to SQLite database.
 * Ensures that only one SQLiteDataSource is created and utilized.
 */
public class SQLiteConnection {

    /**
     * Singleton instance of SQLiteDataSource.
     */
    private static SQLiteDataSource myDataSourceInstance = null;

    /**
     * Gets single instance of SQLITEDataSource.
     * Initializes database(UD.db) if it has not been created yet, and accesses
     * through one static instance.
     *
     * @return SQLiteDataSource instance.
     * @throws RuntimeException if data setup fails.
     */
    public static SQLiteDataSource getDataSource() {

        if (myDataSourceInstance == null) {
            try {
                myDataSourceInstance = new SQLiteDataSource();
                myDataSourceInstance.setUrl("jdbc:sqlite:UDB.db");
                System.out.println("Successfully set up SQLite Data Source.");
            } catch (Exception theEx) {
                theEx.printStackTrace();
                throw new RuntimeException("Failed to set up the data source instance.");
            }
        }

        return myDataSourceInstance;
    }
}

package Model.DB;

import org.sqlite.SQLiteDataSource;

public class SQLiteConnection {

    private static SQLiteDataSource myDataSource = null;

    public static SQLiteDataSource getDataSource() {

        if (myDataSource == null) {
            try {
                myDataSource = new SQLiteDataSource();
                myDataSource.setUrl("jdbc:sqlite:UDB.db");
                System.out.println("Successfully set up data source.");
            } catch (Exception e) {
                System.err.println("Failed to set up the data source.");
                e.printStackTrace();
                System.exit(0);
            }
        }

        return myDataSource;
    }
}

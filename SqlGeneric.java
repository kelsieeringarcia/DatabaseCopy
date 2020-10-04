package flights;

import java.sql.*;
import java.sql.Array;

public class SqlGeneric {
    private static final String databaseURL =
            "jdbc:derby:FlightsDB;create=true";

    /**
     * Creates new tables for the database
     */
    static void dropTables() {
        try (Connection connection = DriverManager.getConnection(databaseURL);
             Statement statement = connection.createStatement()) {

            // Remove and recreate tables
            String[] commands = new String[]{
                    SqlFlight.dropTable(),
                    SqlAirline.dropTable(),
                    SqlAirport.dropTable(),
                    SqlStatus.dropTable(),
                    SqlGate.dropTable()
            };
            for (String c: commands) {
                statement.execute(c);
            }
        }
        catch (SQLException e) {
            System.err.println("A problem occurred dropping the database tables.");
            e.printStackTrace();
        }
    }


    /**
     * Creates new tables for the database
     */
    static void createTables() {
        try (Connection connection = DriverManager.getConnection(databaseURL);
             Statement statement = connection.createStatement()) {

            String[] commands = new String[]{
                    SqlGate.createTable(),
                    SqlStatus.createTable(),
                    SqlAirport.createTable(),
                    SqlAirline.createTable(),
                    SqlFlight.createTable()
            };
            for (String c: commands) {
                statement.execute(c);
            }
        }
        catch (SQLException e) {
            System.err.println("A problem occurred creating the database tables.");
            e.printStackTrace();
        }
    }

    /**
     * Removes all data from all tables and inserts initial data
     */
    static void resetTables() {
        try (Connection connection = DriverManager.getConnection(databaseURL);
             Statement statement = connection.createStatement()) {

            // Create tables and insert data
            String[] commands = new String[]{
                    SqlFlight.removeData(),
                    SqlAirline.removeData(),
                    SqlAirport.removeData(),
                    SqlStatus.removeData(),
                    SqlGate.removeData(),

                    SqlGate.insertData(),
                    SqlStatus.insertData(),
                    SqlAirport.insertData(),
                    SqlAirline.insertData(),
                    SqlFlight.insertData()
            };
            for (String c: commands) {
                statement.execute(c);
            }
        }
        catch (SQLException e) {
            System.err.println("A problem occurred resetting the database tables.");
            e.printStackTrace();
        }
    }
    
    

}

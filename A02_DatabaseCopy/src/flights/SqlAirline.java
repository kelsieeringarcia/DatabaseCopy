package flights;



public class SqlAirline {

    public static String dropTable() {
        return "DROP TABLE Airline";
    }

    public static String createTable() {
        return
                "CREATE TABLE Airline ("
                + "Id varchar(255) not null primary key, "
                + "Name varchar(255))";
    }

    public static String removeData() {
        return
                "DELETE FROM Airline";
    }

    public static String insertData() {
        return
                "INSERT INTO Airline (Id, Name) "
                + "VALUES ('AA', 'American'), "
                + "('AF', 'Air France'), "
                + "('AS', 'Alaska'), "
                + "('BA', 'British'), "
                + "('DL', 'Delta'), "
                + "('JL', 'Japan'), "
                + "('KL', 'Royal Dutch'), "
                + "('NH', 'All Nippon'), "
                + "('UA', 'United'), "
                + "('WN', 'Southwest')";
    }

    public static String getAll() {
        return
                "SELECT * "
                + "FROM Airline";
    }
    
    
}

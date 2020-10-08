package flights;

public class SqlGate {

    public static String dropTable() {
        return "DROP TABLE Gate";
    }

    public static String createTable() {
        return
                "CREATE TABLE Gate ("
                + "Id varchar(255) not null primary key)";
    }

    public static String removeData() {
        return
                "DELETE FROM Gate";
    }

    public static String insertData() {
        return
                "INSERT INTO Gate(Id) "
                + "VALUES ('A01'), "
                + "('A02'), "
                + "('A03'), "
                + "('A04'), "
                + "('A05'), "
                + "('B01'), "
                + "('B02'), "
                + "('B03'), "
                + "('B04'), "
                + "('B05'), "
                + "('C01'), "
                + "('C02'), "
                + "('C03'), "
                + "('C04'), "
                + "('C05')";
    }

    public static String getAll() {
        return
                "SELECT * "
                + "FROM Gate";
    }
}

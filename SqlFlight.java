package flights;

public class SqlFlight {

    public static String dropTable() {
        return
                "DROP TABLE Flight";
    }

    public static String createTable() {
        return
                "CREATE TABLE Flight ("
                + "Id int NOT NULL PRIMARY KEY "
                + "  GENERATED ALWAYS AS IDENTITY "
                + "  (START WITH 1000, INCREMENT BY 1), "
                + "Airline varchar(255), "
                + "Number int, "
                + "Destination varchar(255), "
                + "Status int, "
                + "Gate varchar(255), "
                + "Date varchar(255), "
                + "Time varchar(255), "
                + "Duration int, "
                + "FOREIGN KEY(Airline) REFERENCES Airline(Id), "
                + "FOREIGN KEY(Destination) REFERENCES Airport(Id), "
                + "FOREIGN KEY(Status) REFERENCES Status(Id), "
                + "FOREIGN KEY(Gate) REFERENCES Gate(Id))";
    }

    public static String removeData() {
        return
                "DELETE FROM Flight";
    }

    public static String insertData() {
        return
                "INSERT INTO Flight (Airline, Number, Destination, Status, Gate, Date, Time, Duration) "
                + "VALUES ('AA', 1300, 'KSEA', 0, 'A05', 'Sep 14', '03:00', 30), "
                + "('AA', 0148, 'KLAX', 0, 'C04', 'Oct 12', '18:15', 60), "
                + "('AA', 6711, 'KDFW', 1, 'C02', 'Sep 29', '24:30', 120), "
                + "('AA', 7099, 'KORD', 0, 'A02', 'Oct 11', '05:30', 40), "
                + "('AF', 7028, 'LFPG', 0, 'B03', 'Oct 06', '21:15', 45), "
                + "('AF', 8207, 'EGLL', 0, 'B03', 'Sep 06', '05:00', 500), "
                + "('AS', 0194, 'KSEA', 1, 'A02', 'Oct 30', '15:00', 90), "
                + "('AS', 4493, 'KSLC', 0, 'B04', 'Oct 12', '13:00', 80), "
                + "('AS', 9189, 'KPDX', 2, 'C03', 'Oct 09', '20:30', 75), "
                + "('BA', 1640, 'EGLL', 0, 'C04', 'Oct 21', '16:15', 120), "
                + "('BA', 1759, 'RJAA', 0, 'B02', 'Sep 18', '04:15', 600), "
                + "('DL', 2116, 'KSEA', 0, 'B05', 'Oct 08', '05:30', 200), "
                + "('DL', 4879, 'KATL', 0, 'A05', 'Oct 04', '16:00', 240), "
                + "('DL', 9482, 'KSLC', 1, 'B05', 'Oct 19', '18:45', 30), "
                + "('DL', 9690, 'KPHX', 0, 'A02', 'Oct 09', '16:45', 60), "
                + "('JL', 5790, 'EGLL', 0, 'B05', 'Sep 09', '17:15', 550), "
                + "('JL', 7029, 'KJFK', 2, 'C03', 'Sep 08', '20:15', 340), "
                + "('JL', 7077, 'RJAA', 0, 'A05', 'Oct 17', '17:00', 540), "
                + "('KL', 4720, 'EHAM', 0, 'A01', 'Sep 12', '17:00', 60), "
                + "('KL', 8230, 'VHHH', 0, 'A03', 'Sep 20', '05:30', 240), "
                + "('KL', 9966, 'ZSPD', 2, 'A04', 'Sep 01', '00:00', 210), "
                + "('NH', 3135, 'RJAA', 0, 'A02', 'Oct 10', '03:45', 60), "
                + "('NH', 4299, 'RJTT', 0, 'C05', 'Oct 04', '24:45', 330), "
                + "('UA', 3451, 'KDFW', 3, 'C01', 'Sep 22', '12:15', 340), "
                + "('UA', 5360, 'KBOS', 3, 'C05', 'Sep 12', '12:45', 280), "
                + "('UA', 5440, 'KDEN', 2, 'C01', 'Oct 05', '15:30', 170), "
                + "('WN', 5494, 'KLAX', 0, 'A03', 'Oct 19', '06:15', 45), "
                + "('WN', 5637, 'KSLC', 0, 'C04', 'Oct 03', '14:45', 120), "
                + "('WN', 6513, 'KLAS', 1, 'C02', 'Sep 13', '20:00', 150), "
                + "('WN', 9018, 'KPHX', 0, 'A05', 'Sep 03', '03:45', 160)";
    }

    public static String insertValue(
            String airlineId, int number, String airportId, int status,
            String gate, String date, String time, int duration) {

        return
                "INSERT INTO Flight (Airline, Number, Destination, Status, Gate, Date, Time, Duration) "
                + "VALUES ('" + airlineId + "', " + number + ", '" + airportId + "', " + status
                + ", '" + gate + "', '" + date + "', '" + time + "', " + duration +")";
    }

    public static String getAll() {
        return
                "SELECT * "
                + "FROM Flight";
    }
    /**
     * This query will remove the specific flight from the selected table.
     * @param number
     * @return A DELETE query
     */
    public static String removeFlightWhere(int number) {
    	return "DELETE "
    			+"FROM Flight "
    			+ "WHERE Number = " + number;
    }
    /**
     * This query will update the selected row in the database.
     */
    public static String updateFlight(
            String airlineId, int number, String airportId, int status,
            String gate, String date, String time, int duration) {

        return
                "UPDATE Flight "
                + "SET AirlineId = '" + airlineId + "', Destination = '" + airportId 
                + "', Status = '" + status + "', Gate = '" + gate + "', Date = '" + date + "', Time = '" 
                + time + "', Duration = '" + duration  
                +"WHERE Number = '" + number +"' ";
    }

    /**
     * Find all flights and sort them by a specified column
     * @param column An enum that matches a column name of the flight table
     * @return A Sql String that returns a sorted table of flights
     */
    public static String getAllSorted(SqlColumn column) {
        return
                "SELECT * "
                + "FROM Flight "
                + "ORDER BY " + column.getColumn() + ", Airline, Number, Destination, Status";
    }

    /**
     * Find all flights and sort them by a specified column
     * Replaces AirportId, AirlineId, and Status Code with descriptive names
     *
     * @param column a Sql Table column name
     * @return A Sql String that returns a sorted table of flights
     */
    public static String getAllSortedWithNames(SqlColumn column) {
        return
                "SELECT "
                + "Airline.Name, Flight.Number, Airport.City, "
                + "Status.Description, Flight.Gate, "
                + "Flight.Date, Flight.Time, Flight.Duration "
                + "FROM Flight "
                + "INNER JOIN Airport ON Flight.Destination=Airport.Id "
                + "INNER JOIN Status ON Flight.Status=Status.Id "
                + "INNER JOIN Airline ON Flight.Airline=Airline.Id "
                + "ORDER BY " + column.getColumn() + ", Flight.Airline, Flight.Number";
    }

    /**
     * Find and return all flights that match a search query
     * in a specified column
     *
     * @param column a SQL table column
     * @param searchQuery a string to search for
     * @return a SQL string that returns a filtered table of flights
     */
    public static String getAllMatchingColumn(SqlColumn column ,String searchQuery) {
        return
                "SELECT * "
                + "FROM Flight "
                + "WHERE " + column.getColumn() + " = '" + searchQuery + "'";
    }

    /**
     * Find and return all flights, then
     * replace airline identifiers with airline names
     * @return a SQL string that returns a table of flights with airline names
     */
    public static String getAllWithAirlineNames() {
        return
                "SELECT Airline.Name, Flight.Number "
                + "FROM Flight "
                + "INNER JOIN Airline ON Flight.Airline=Airline.Id";

    }
}

package flights;

@SuppressWarnings("unused")
public enum SqlColumn {
    FLIGHT_ID("Flight.Id"),
    FLIGHT_AIRLINE("Flight.Airline"),
    FLIGHT_NUMBER("Flight.Number"),
    FLIGHT_DESTINATION("Flight.Destination"),
    FLIGHT_STATUS("Flight.Status"),
    FLIGHT_GATE("Flight.Gate"),
    FLIGHT_DATE("Flight.Date"),
    FLIGHT_TIME("Flight.Time"),
    FLIGHT_DURATION("Flight.Duration"),
    AIRLINE_ID("Airline.Id"),
    AIRLINE_NAME("Airline.Name"),
    AIRPORT_ID("Airport.Id"),
    AIRPORT_NAME("Airport.Name"),
    AIRPORT_CITY("Airport.City"),
    AIRPORT_COUNTRY("Airport.Country");

    private final String column;

    SqlColumn(String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }

}

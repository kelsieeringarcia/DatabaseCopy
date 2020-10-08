package flights;

public class Airline {
	private String airlineId;
	private String airlineName;
	
	public Airline(String airlineId, String airlineName) {
		this.airlineId = airlineId;
		this.airlineName = airlineName;
	}

	public String getAirlineId() {
		return airlineId;
	}

	public void setAirlineId(String airlineId) {
		this.airlineId = airlineId;
	}

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

}

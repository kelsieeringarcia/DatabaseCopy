package flights;

public class Airport {
	private String airportId;
	private String airportName;
	private String airportCity;
	private String airportCountry;

	public Airport(String airportId, String airportName, String airportCity, String airportCountry) {
		this.airportId = airportId;
		this.airportName = airportName;
		this.airportCity = airportCity;
		this.airportCountry = airportCountry;
	}
	public String getAirportId() {
		return airportId;
	}

	public void setAirportId(String airportId) {
		this.airportId = airportId;
	}

	public String getAirportName() {
		return airportName;
	}

	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}

	public String getAirportCity() {
		return airportCity;
	}

	public void setAirportCity(String airportCity) {
		this.airportCity = airportCity;
	}

	public String getAirportCountry() {
		return airportCountry;
	}

	public void setAirportCountry(String airportCountry) {
		this.airportCountry = airportCountry;
	}


}

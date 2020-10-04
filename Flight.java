package flights;

public class Flight {
	private int id;
	private String airline;
	private int number;
	private String destination;
	private int status;
	private String gate;
	private String date;
	private String time;
	private int duration;
	
	public Flight(int id, String airline, int number, String destination, int status,
				  String gate, String date, String time, int duration) {
		this.id = id;
		this.airline = airline;
		this.number = number;
		this.destination = destination;
		this.status = status;
		this.gate = gate;
		this.date = date;
		this.time = time;
		this.duration = duration;
	}

	public int getDepartureId() {
		return id;
	}


	public String getAirlineId() {
		return airline;
	}

	public int getFlightNumber() {
		return number;
	}

	public void setFlightNumber(int number) {
		this.number = number;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getGate() {
		return gate;
	}

	public void setGate(String gate) {
		this.gate = gate;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

}

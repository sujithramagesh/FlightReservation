package l3;

import java.util.HashMap;
import java.util.List;

class Flight {
int FlightNumber;
HashMap<String,List<Seats>> seatMap;
float totalFare;



public float getTotalFare() {
	return totalFare;
}
public void setTotalFare(float totalFare) {
	this.totalFare = totalFare;
}
public Flight(int flightNumber, HashMap<String, List<Seats>> seatMap) {
	super();
	FlightNumber = flightNumber;
	this.seatMap = seatMap;
}
public Flight() {
	// TODO Auto-generated constructor stub
}
public int getFlightNumber() {
	return FlightNumber;
}
public void setFlightNumber(int flightNumber) {
	FlightNumber = flightNumber;
}
public HashMap<String, List<Seats>> getSeatMap() {
	return seatMap;
}
public void setSeatMap(HashMap<String, List<Seats>> seatMap) {
	this.seatMap = seatMap;
}

}



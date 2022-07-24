package l3;

import java.util.ArrayList;

public class BookingClass {
int bookingId;
int flightNo;
int noOfPassengers;
String seatType;
float totalTicketCost;
ArrayList<Integer> seatNo;
boolean isMeal=false;
boolean isSenior=false;

public boolean isMeal() {
	return isMeal;
}
public void setMeal(boolean isMeal) {
	this.isMeal = isMeal;
}
public boolean isSenior() {
	return isSenior;
}
public void setSenior(boolean isSenior) {
	this.isSenior = isSenior;
}
public ArrayList<Integer> getSeatNo() {
	return seatNo;
}
public void setSeatNo(ArrayList<Integer> seatNo) {
	this.seatNo = seatNo;
}
public int getBookingId() {
	return bookingId;
}
public void setBookingId(int bookingId) {
	this.bookingId = bookingId;
}
public int getFlightNo() {
	return flightNo;
}
public void setFlightNo(int flightNo) {
	this.flightNo = flightNo;
}
public int getNoOfPassengers() {
	return noOfPassengers;
}
public void setNoOfPassengers(int noOfPassengers) {
	this.noOfPassengers = noOfPassengers;
}
public String getSeatType() {
	return seatType;
}
public void setSeatType(String seatType) {
	this.seatType = seatType;
}
public float getTotalTicketCost() {
	return totalTicketCost;
}
public void setTotalTicketCost(float totalTicketCost) {
	this.totalTicketCost = totalTicketCost;
}


}

package l3;

import java.util.HashMap;

class Seats {
	String bookingType;
	String seatType;
	int seatNo;
	boolean status;
	double fare;

	public String getBookingType() {
		return bookingType;
	}

	public void setBookingType(String bookingType) {
		this.bookingType = bookingType;
	}

	public Seats(String bookingType, int seatNo, boolean status) {
		super();
		this.bookingType = bookingType;

		this.seatNo = seatNo;
		this.status = status;

	}

	public String getSeatType() {
		return seatType;
	}

	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}

	public int getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(int seatNo) {
		this.seatNo = seatNo;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public double getFare() {
		return fare;
	}

	public void setFare(double fare) {
		this.fare = fare;
	}

}

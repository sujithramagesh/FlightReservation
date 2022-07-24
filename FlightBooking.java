package l3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FlightBooking {
	static ArrayList<Flight> flightList = new ArrayList<Flight>();

	public static void main(String[] args) {
		loadFlight();
		getUserMenus();

	}

	private static void getUserMenus() {
		Scanner sc = new Scanner(System.in);
		int choice;
		do {
			System.out.println("Please Enter the option to perform:");
			System.out.println(
					"1.Booking\n2.Cancellation\n3.Print available seats\n4.Show meal ordered SeatNumber\n5.Flight Summary\n6.Exit");
			choice = sc.nextInt();
			switch (choice) {
			case 1:
				booking();
				break;
			case 2:
				cancelTicket();
				break;
			case 3:
				printAvailableSeats();
				break;
			case 4:
				getMealOrderedSeats();
				break;
			case 5:
				printFlightSummary();
			}
		} while (choice != 6);

	}

	private static void printFlightSummary() {
		for (Flight flight : flightList) {
			System.out.print("Flight -" + flight.getFlightNumber() + "Total amount in Account" + flight.getTotalFare());
		}

	}

	private static void getMealOrderedSeats() {
		BookingRepository bRepo = BookingRepository.getInstance();
		String bSeats = "";
		String eSeats = "";
		for (Flight flight : flightList) {
			bSeats = "";
			eSeats = "";
			System.out.print("Flight:" + flight.getFlightNumber() + ":");
			for (BookingClass bc : bRepo.bookingList) {
				if (bc.getSeatType().equals("B") && bc.flightNo == flight.getFlightNumber()) {
					if (bc.isMeal == true) {
						for (int i : bc.getSeatNo()) {
							bSeats += i + ",";
						}
					}
				} else if (bc.getSeatType().equals("E") && bc.flightNo == flight.getFlightNumber()) {
					if (bc.isMeal == true) {
						for (int i : bc.getSeatNo()) {
							eSeats += i + ",";
						}
					}
				}

			}
			System.out.println("B-" + bSeats + " E-" + eSeats);
		}
		System.out.println();
	}

	private static void cancelTicket() {
		int cancelFee = 200;
		BookingRepository bRepo = BookingRepository.getInstance();
		Scanner sc = new Scanner(System.in);
		boolean isValidId = false;

		System.out.println("Enter the booking Id");
		int bookingId = sc.nextInt();
		BookingClass bClass = bRepo.get(bookingId);
		if (bClass != null) {
			int totalCancelFee = cancelFee * bClass.getSeatNo().size();
			float refund = bClass.getTotalTicketCost() - totalCancelFee;
			Flight flight = getFlight(bClass.getFlightNo());
			flight.setTotalFare(flight.getTotalFare() - refund);
			changeSeatStatus(flight, bClass.seatNo);
			System.out.println("Cancellation Fees:" + totalCancelFee);
			System.out.println("Refundable Amount:" + refund);
			bRepo.delete(bClass);
		} else {
			System.out.println("Booking Cancelled or not available");
		}

	}

	private static void changeSeatStatus(Flight flight, ArrayList<Integer> seatNo) {
		for (Map.Entry<String, List<Seats>> seatMap : flight.getSeatMap().entrySet()) {
			for (Seats seat : seatMap.getValue()) {
				for (int i : seatNo) {
					if (seat.getSeatNo() == i) {
						seat.setStatus(false);
					}
				}
			}
		}

	}

	private static void booking() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the Flights");
		printFlights();
		System.out.println(":");
		int flightNumber = sc.nextInt();
		boolean isAvailable = false;
		Flight flight = new Flight();
		flight = getFlight(flightNumber);
		System.out.println("Select category(G-General,T-Tatkal):");
		String category = sc.next();
		System.out.println(
				"Seats availability:Business-" + getNoOfSeats("B", flight) + ",Economy-" + getNoOfSeats("E", flight));
		System.out.println("Seat Type(B-Business(2000 INR),E-Economy(1000 INR)):");
		String seatType = sc.next();
		int noOfSeats = 0;

		System.out.println("No of seats required:");
		noOfSeats = sc.nextInt();
		isAvailable = isSeatAvailable(noOfSeats, seatType, flight);
		if (isAvailable) {
			boolean isSeniorCitizenbool = false;
			boolean isMealIncludedbool = false;
			System.out.println("Senior Citizen(Y-Yes,N-No):");
			char isSeniorCitizen = sc.next().charAt(0);
			if (isSeniorCitizen == 'Y')
				isSeniorCitizenbool = true;

			System.out.println("Meal(Y-Yes,N-No):");
			char isMealIncluded = sc.next().charAt(0);
			if (isMealIncluded == 'Y')
				isMealIncludedbool = true;
			BookingRepository bRepo = BookingRepository.getInstance();
			BookingClass booking = new BookingClass();
			booking.setSenior(isSeniorCitizenbool);
			booking.setMeal(isMealIncludedbool);
			booking.setFlightNo(flightNumber);
			booking.setSeatType(seatType);
			booking.setNoOfPassengers(noOfSeats);
			booking.setSeatNo(bookTicket(noOfSeats, seatType, flight));
			int baseFare = 0;
			if (seatType.equals("B")) {
				baseFare = 2000;
			} else if (seatType.equals("E")) {
				baseFare = 1000;
			}
			int mealFare = 0;
			if (isMealIncluded == 'Y'||isMealIncluded == 'y') {
				mealFare = 200;
			}

			float totalCost, seniorFare = 0;
			totalCost = noOfSeats * baseFare;

			if (isSeniorCitizen == 'Y'||isSeniorCitizen == 'y') {
				totalCost = totalCost - (float) (0.1 * totalCost);
			}

			totalCost += mealFare * noOfSeats + seniorFare;
			System.out.println("Total Cost is:" + totalCost);
			flight.setTotalFare(flight.getTotalFare() + totalCost);
			;
			booking.setTotalTicketCost(totalCost);
			System.out.println("Ticket Booked Successfully");
			System.out.println("Booking id:" + bRepo.add(booking));
		} else {
			System.out.println("Booking seats are more than available");
		}

	}

	private static ArrayList<Integer> bookTicket(int noOfSeats, String seatType, Flight flight) {
		ArrayList<Integer> seatNo = new ArrayList<Integer>();
		for (Map.Entry<String, List<Seats>> seatMap : flight.getSeatMap().entrySet()) {
			if (seatMap.getKey().equals(seatType)) {
				for (int i = 0; i < noOfSeats; i++) {
					for (Seats seat : seatMap.getValue()) {
						if (!seat.isStatus()) {
							seat.setStatus(true);
							seatNo.add(seat.getSeatNo());
							break;
						}
					}

				}
			}
		}
		return seatNo;

	}

	private static void updateFlightClassDetails(Flight flight, int noOfSeats, String seatType) {

		for (Map.Entry<String, List<Seats>> seatMap : flight.getSeatMap().entrySet()) {

		}
	}

	private static boolean isSeatAvailable(int noOfSeats, String seatType, Flight flight) {
		int totcount = getNoOfSeats(seatType, flight);
		if (noOfSeats <= totcount) {
			return true;
		}
		return false;
	}

	private static int getNoOfSeats(String string, Flight flight) {
		int count = 0;
		for (Map.Entry<String, List<Seats>> mapEntry : flight.getSeatMap().entrySet()) {
			if (mapEntry.getKey().equals(string)) {
				for (Seats seat : mapEntry.getValue()) {
					if (!seat.isStatus()) {
						count++;
					}
				}
			}
		}
		return count;
	}

	private static Flight getFlight(int flightNumber) {
		for (Flight flight : flightList) {
			if (flightNumber == flight.getFlightNumber()) {
				return flight;
			}
		}
		return null;
	}

	private static void printFlights() {
		System.out.print("(");
		for (Flight flights : flightList) {
			System.out.print(flights.getFlightNumber() + ",");
		}
		System.out.print(")");
	}

	private static void printAvailableSeats() {
		for (Flight flight : flightList) {
			System.out.print("Flight " + flight.getFlightNumber() + ": ");
			for (Map.Entry<String, List<Seats>> mapEntry : flight.getSeatMap().entrySet()) {
				System.out.print(mapEntry.getKey() + "- ");
				if (mapEntry.getKey().equals("B")) {
					for (Seats seat : mapEntry.getValue()) {
						if (!seat.isStatus())
							System.out.print(seat.getSeatNo() + ",");
					}
				} else {
					for (Seats seat : mapEntry.getValue()) {
						if (!seat.isStatus())
							System.out.print(seat.getSeatNo() + ",");
					}
				}

				System.out.println();
			}

		}

	}

	private static void loadFlight() {
		flightList.add(new Flight(101, loadSeat()));
		flightList.add(new Flight(102, loadSeat()));
		flightList.add(new Flight(103, loadSeat()));
	}

	private static HashMap<String, List<Seats>> loadSeat() {
		int tatkalBusinessCount = 2;
		int tatkalEconomyCount = 4;
		int normalBusinessCount = 6;
		int normalEconomyCount = 12;
		int seatNo = 1;

		List<Seats> seatList = new ArrayList<Seats>();
		HashMap<String, List<Seats>> seatMap = new HashMap<String, List<Seats>>();
		for (int i = 0; i < normalBusinessCount; i++) {
			seatList.add(new Seats("G", seatNo, false));
			seatNo++;
		}
		/*
		 * for(int i=0;i<tatkalBusinessCount;i++) { seatList.add(new Seats("T", seatNo,
		 * false)); seatNo++; }
		 */
		/*
		 * for(int i=0;i<tatkalEconomyCount;i++) { seatList.add(new Seats("T", seatNo,
		 * false)); seatNo++; }
		 */
		seatMap.put("B", seatList);
		seatList = new ArrayList<Seats>();
		for (int i = 0; i < normalEconomyCount; i++) {
			seatList.add(new Seats("G", seatNo, false));
			seatNo++;
		}
		seatMap.put("E", seatList);

		return seatMap;
	}
}

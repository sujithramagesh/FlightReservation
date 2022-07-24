package l3;

import java.util.ArrayList;

public class BookingRepository {
	public static BookingRepository bRepo=null;
	ArrayList<BookingClass> bookingList=new ArrayList<BookingClass>();
	private BookingRepository()
	{

	}
	public static BookingRepository getInstance()
	{
		if(bRepo==null)
		{
			bRepo=new BookingRepository();
		}
		return bRepo;
	}
	public int add(BookingClass booking) {
		
		int bookingId=bookingList.size()+1;	
		booking.setBookingId(bookingId);
		bookingList.add(booking);
		return bookingId;

	}
	public BookingClass get(int bookingId)
	{
		for(BookingClass bClass:bookingList)
		{
			if(bClass.bookingId==bookingId)
			{
				return bClass;
			}
		}
		return null;
	}
	public void delete(BookingClass bClass) {
		bookingList.remove(bookingList.indexOf(bClass));
		
	}
	
}

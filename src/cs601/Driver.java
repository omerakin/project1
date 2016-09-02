package cs601;

import cs601.hotelapp.Address;
import cs601.hotelapp.Hotel;
import cs601.hotelapp.HotelData;
import java.nio.file.Paths;

public class Driver {
	public static void main(String[] args) {
		
		HotelData data = new HotelData();
		
		// Load hotel info from hotels200.json
		data.loadHotelInfo("input/hotels200.json");
		
		// Traverse input/reviews directory recursively, 
		// find all the json files and load reviews
		data.loadReviews(Paths.get("input/reviews")); 
		data.printToFile(Paths.get("outputFile"));
		
		/*
		Address a1 = new Address("a", "b", "c", 1, 1);
		Address a2 = new Address("aa", "bb", "cc", 1, 1);
		
		Hotel h1 = new Hotel("11111", "omer", a1);
		Hotel h2 = new Hotel("22222", "omer", a2); 
		
		System.out.println(h1.compareTo(h2));
		*/
	}
}

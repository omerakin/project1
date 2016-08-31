package cs601;

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
		
	}
	jksdjfkkkk;
}

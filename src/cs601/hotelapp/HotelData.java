package cs601.hotelapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Class HotelData - a data structure that stores information about hotels and
 * hotel reviews. Allows to quickly lookup a hotel given the hotel id. 
 * Allows to easily find hotel reviews for a given hotel, given the hotelID. 
 * Reviews for a given hotel id are sorted by the date and user nickname.
 *
 */
public class HotelData {

	// FILL IN CODE - declare data structures to store hotel data
	private Map<String, Hotel> hotelsGivenByHotelId;
	private Map<String, TreeSet<Review>> reviewsGivenByHotelId;
	private Hotel hotel;
	private Address address;
	private Review reviews;
	private Boolean isSuccessful;
	int count = 0;

	/**
	 * Default constructor.
	 */
	public HotelData() {
		// FILL IN CODE
		// Initialise all data structures
		hotelsGivenByHotelId = new HashMap<String,Hotel>();
		reviewsGivenByHotelId = new HashMap<String,TreeSet<Review>>();

	}

	/**
	 * Create a Hotel given the parameters, and add it to the appropriate data
	 * structure(s).
	 * 
	 * @param hotelId
	 *            - the id of the hotel
	 * @param hotelName
	 *            - the name of the hotel
	 * @param city
	 *            - the city where the hotel is located
	 * @param state
	 *            - the state where the hotel is located.
	 * @param streetAddress
	 *            - the building number and the street
	 * @param latitude
	 * @param longitude
	 */
	public void addHotel(String hotelId, String hotelName, String city, String state, String streetAddress, double lat,
			double lon) {
		// FILL IN CODE
		//Set the values to the address and hotel object.
		address = new Address(streetAddress, city, state, lat, lon);
		hotel = new Hotel(hotelId, hotelName, address);
		//Add to the hotelsGivenByHotelId TreeMap.
		hotelsGivenByHotelId.put(hotelId, hotel);
	}

	/**
	 * Add a new review.
	 * 
	 * @param hotelId
	 *            - the id of the hotel reviewed
	 * @param reviewId
	 *            - the id of the review
	 * @param rating
	 *            - integer rating 1-5.
	 * @param reviewTitle
	 *            - the title of the review
	 * @param review
	 *            - text of the review
	 * @param isRecommended
	 *            - whether the user recommends it or not
	 * @param date
	 *            - date of the review in the format yyyy-MM-dd, e.g.
	 *            2016-08-29.
	 * @param username
	 *            - the nickname of the user writing the review.
	 * @return true if successful, false if unsuccessful because of invalid date
	 *         or rating. Needs to catch and handle ParseException if the date is invalid.
	 *         Needs to check whether the rating is in the correct range
	 */
	public boolean addReview(String hotelId, String reviewId, int rating, String reviewTitle, String review,
			boolean isRecom, String date, String username) {
		
		/* isRecom not used yet ??
		 * 
		 */

		// FILL IN CODE
		//Initialise it to default value.
		isSuccessful = false;
		//Check the rating is in the correct range or not.
		if(1> rating) {
			// set the false.
			isSuccessful = false;
		} else if (5 < rating) {
			// set the false.
			isSuccessful = false;
		} else {
			//Check the date is correct format or not.
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date date1 = sdf.parse(date);
				// set the true.
				isSuccessful = true;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				System.out.println("Date is invalid!");
			}
		}
		// If successful is true add it.
		if(isSuccessful && hotelsGivenByHotelId.containsKey(hotelId)) {
			//Set the values to the reviews object.
			reviews = new Review(reviewId, hotelId, reviewTitle, review, username, date, rating);			
			//Check that if hotel id already exist or not
			if(!(reviewsGivenByHotelId.containsKey(hotelId))) {
				TreeSet<Review> newReviewSet = new TreeSet<Review>();
				newReviewSet.add(reviews);
				//Add to the reviewsGivenByHotelId TreeMap.
				reviewsGivenByHotelId.put(hotelId, newReviewSet);
			} else {
				TreeSet<Review> existingReviewSet;
				existingReviewSet = reviewsGivenByHotelId.get(hotelId);			
				existingReviewSet.add(reviews);	
				//Add to the reviewsGivenByHotelId TreeMap.
				reviewsGivenByHotelId.put(hotelId, existingReviewSet);
			}
		}
		
		return isSuccessful; // don't forget to change it
	}

	/**
	 * Return an alphabetized list of the ids of all hotels
	 * 
	 * @return
	 */
	public List<String> getHotels() {
		// FILL IN CODE
		//Initialise an ArrayList to store hotelIds
		List<String> hotelIdList = new ArrayList<>();
			
		//Add hotelId to ArrayList
		for (String hotelId: hotelsGivenByHotelId.keySet()){
			hotelIdList.add(hotelId);
		}
		//Sort hotelIds
		Collections.sort(hotelIdList);
		//return it.
		return hotelIdList; // don't forget to change it
	}

	/**
	 * Read the json file with information about the hotels (id, name, address,
	 * etc) and load it into the appropriate data structure(s). Note: This
	 * method does not load reviews
	 * 
	 * @param filename
	 *            the name of the json file that contains information about the
	 *            hotels
	 */
	public void loadHotelInfo(String jsonFilename) {

		// Hint: Use JSONParser from JSONSimple library
		// FILL IN CODE
		
		//Get the file directory and find the path
		Path jsonFileNameDirectory = Paths.get(jsonFilename);
		String jsonFilenameString = jsonFileNameDirectory.toAbsolutePath().toString();
		
		
		JSONParser parser = new JSONParser(); 
		try {
			Object object = parser.parse(new FileReader(jsonFilenameString));
			JSONObject jsonObject = (JSONObject) object;
			
			String nameq = (String) jsonObject.get("q");
			//System.out.println("qqqqqq" + nameq);
			
			String namerid = (String) jsonObject.get("rid");
			//System.out.println("rrriiiddd" + namerid);
			
			String namerc = (String) jsonObject.get("rc");
			//System.out.println("rrrccc" + namerc);
			
			JSONArray listOfHotel = (JSONArray) jsonObject.get("sr");
			JSONObject jsonObjectHotel;
			
			for (int i=0; i<listOfHotel.size();i++) {
				jsonObjectHotel = (JSONObject) listOfHotel.get(i);
				
				// Get hotelId.
				String hotelId = (String) jsonObjectHotel.get("id");
				//System.out.println(hotelId);
				
				// Get hotelName.
				String hotelName = (String) jsonObjectHotel.get("f");
				//System.out.println(hotelName);
				
				// Get hotelCity.
				String hotelCity = (String) jsonObjectHotel.get("ci");
				//System.out.println(hotelCity);
				
				// Get hotelState
				String hotelState = (String) jsonObjectHotel.get("pr");
				//System.out.println(hotelState);
				
				// Get hotelStreetAddress
				String hotelStreetAddress = (String) jsonObjectHotel.get("ad");
				//System.out.println(hotelStreetAddress);
				
				//Create jsonObjectHotelLL to get Lat and Lng
				JSONObject jsonObjectHotelLL = (JSONObject) jsonObjectHotel.get("ll");
				
				// Get hotelLat
				double hotelLat = Double.parseDouble((String) jsonObjectHotelLL.get("lat"));
				//System.out.println(hotelLat);
				
				// Get hotelLon
				double hotelLon = Double.parseDouble((String) jsonObjectHotelLL.get("lng"));
				//System.out.println(hotelLon);
				
				// Add to the hotelsGivenByHotelId
				addHotel(hotelId, hotelName, hotelCity, hotelState, hotelStreetAddress, hotelLat, hotelLon);
				
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("FileNotFoundException occured.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("IOException occured.");
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("org.json.simple.parser.ParseException occured.");
		}
	
	}

	/**
	 * Load reviews for all the hotels into the appropriate data structure(s).
	 * Traverse a given directory recursively to find all the json files with
	 * reviews and load reviews from each json. Note: this method must be
	 * recursive and use DirectoryStream as discussed in class.
	 * 
	 * @param path
	 *            the path to the directory that contains json files with
	 *            reviews Note that the directory can contain json files, as
	 *            well as subfolders (of subfolders etc..) with more json files
	 */
	public void loadReviews(Path path) {
		// FILL IN CODE

		// Hint: first, write a separate method to read a single json file with
		// reviews
		// using JSONSimple library
		// Call this method from this one as you traverse directories and find
		// json files

		try {
			DirectoryStream<Path> pathsList = Files.newDirectoryStream(path);
			for(Path p : pathsList){
				// check that file is directory or not.
				if(!Files.isDirectory(p)){
					// If not, then this is a json. Add it as review
					
					//System.out.println("Directory degil " + p.toString());
					
					JSONParser jsonParser = new JSONParser();
					try {
						JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(p.toAbsolutePath().toString()));
						
						//reviewDetails Object
						JSONObject reviewDetails = (JSONObject) jsonObject.get("reviewDetails");
						//reviewCollection Object
						JSONObject reviewCollection = (JSONObject) reviewDetails.get("reviewCollection");
						//review Array
						JSONArray review = (JSONArray) reviewCollection.get("review");
						JSONObject reviewObject;
						for(int i=0; i<review.size();i++){
							reviewObject = (JSONObject) review.get(i);
							
							String hotelId = (String) reviewObject.get("hotelId");
							//System.out.println(hotelId);
							
							String reviewId = (String) reviewObject.get("reviewId");
							//System.out.println(reviewId);
							
							long ratingLong = (long) reviewObject.get("ratingOverall");
							int rating = (int) ratingLong;
							//System.out.println(rating);
							
							String reviewTitle = (String) reviewObject.get("title");
							//System.out.println(reviewTitle);
							
							String reviewText = (String) reviewObject.get("reviewText");
							//System.out.println(reviewText);
							
							boolean isRecom = ("YES" == (String) reviewObject.get("isRecommended"));
							//System.out.println(isRecom);
							
							String date = (String) reviewObject.get("reviewSubmissionTime");
							//System.out.println(date);
							
							/*
							JSONArray managementResponses = (JSONArray) reviewObject.get("managementResponses");
							String date = "";
							if (managementResponses.size()>0) {
								JSONObject managementResponsesObject = (JSONObject) managementResponses.get(0);
								date = (String) managementResponsesObject.get("date");
							}
							*/
							
							String username = (String) reviewObject.get("userNickname");
							if(username.equals("")){
								username = "anonymous";
							}
							//System.out.println(username);
							
							//Add review
							addReview(hotelId, reviewId, rating, reviewTitle, reviewText, isRecom, date, username);
						}
					
					} catch (org.json.simple.parser.ParseException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						System.out.println("org.json.simple.parser.ParseException occured.");
					}
					
					
					//System.out.println("Directory degil " + p.toString());
				} else if (Files.isDirectory(p)) {
					// If it is, check the subfolders.
					// this method get the paremeter path, and in it, check sub directories.
					loadReviews(p);
					//System.out.println("Directory " + p.toString());
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("IOException occurred");
		}
	

	}


	/**
	 * Returns a string representing information about the hotel with the given
	 * id, including all the reviews for this hotel separated by
	 * -------------------- Format of the string: HoteName: hotelId
	 * streetAddress city, state -------------------- Review by username: rating
	 * ReviewTitle ReviewText -------------------- Review by username: rating
	 * ReviewTitle ReviewText ...
	 * 
	 * @param hotel
	 *            id
	 * @return - output string.
	 */
	public String toString(String hotelId) {

		// FILL IN CODE
		String result = "";
		
		for (String hotel_id_hotels: hotelsGivenByHotelId.keySet()){
			if(hotel_id_hotels.equals(hotelId)){
				result = result + hotelsGivenByHotelId.get(hotel_id_hotels).getHotel_name() + ": ";
				result = result + hotelsGivenByHotelId.get(hotel_id_hotels).getHotel_id() + "\n";
				result = result + hotelsGivenByHotelId.get(hotel_id_hotels).getAddress().getStreet_address() + "\n";
				result = result + hotelsGivenByHotelId.get(hotel_id_hotels).getAddress().getCity() + ", ";
				result = result + hotelsGivenByHotelId.get(hotel_id_hotels).getAddress().getState() + "\n";		
			}
		}
		
		for (String hotel_id_review: reviewsGivenByHotelId.keySet()){
			if(hotel_id_review.equals(hotelId)){
				for(Review hotelIdReview : reviewsGivenByHotelId.get(hotel_id_review)){
					result = result + "--------------------\n";
					result = result + "Review by " + hotelIdReview.getUsername() + ": ";
					result = result + hotelIdReview.getRating() + "\n";
					result = result + hotelIdReview.getReview_title() + "\n";
					result = result + hotelIdReview.getReview_text() + "\n";
				}
				
				/*
				for(int i=0; i<reviewsGivenByHotelId.get(hotel_id_review).size(); i++){
					result = result + "--------------------\n";
					result = result + "Review by " + reviewsGivenByHotelId.get(hotel_id_review).headSet(i) + ": ";
					result = result + reviewsGivenByHotelId.get(hotel_id_review).getRating() + "\n";
					result = result + reviewsGivenByHotelId.get(hotel_id_review).getReview_title() + "\n";
					result = result + reviewsGivenByHotelId.get(hotel_id_review).getReview_text() + "\n";
				}
				*/
			}
		}

		return result; // don't forget to change to the correct string
	}

	/**
	 * Save the string representation of the hotel data to the file specified by
	 * filename in the following format: 
	 * an empty line 
	 * A line of 20 asterisks ******************** on the next line 
	 * information for each hotel, printed in the format described in the toString method of this class.
	 * 
	 * @param filename
	 *            - Path specifying where to save the output.
	 */
	public void printToFile(Path filename) {
		// FILL IN CODE

		//System.out.println(filename.toString());
		try {
			PrintWriter printWriter = new PrintWriter(new FileWriter(filename.toString()));
			
			for(String hotelid_info: getHotels()){
				printWriter.println("\n********************");
				printWriter.print(toString(hotelid_info));
			}
			printWriter.flush();			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

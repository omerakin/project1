package cs601.hotelapp;

import java.nio.file.Path;
import java.util.*;

/**
 * Class HotelData - a data structure that stores information about hotels and
 * hotel reviews. Allows to quickly lookup a hotel given the hotel id. 
 * Allows to easily find hotel reviews for a given hotel, given the hotelID. 
 * Reviews for a given hotel id are sorted by the date and user nickname.
 *
 */
public class HotelData {

	// FILL IN CODE - declare data structures to store hotel data

	/**
	 * Default constructor.
	 */
	public HotelData() {
		// Initialize all data structures
		// FILL IN CODE

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

		// FILL IN CODE
		return false; // don't forget to change it
	}

	/**
	 * Return an alphabetized list of the ids of all hotels
	 * 
	 * @return
	 */
	public List<String> getHotels() {
		// FILL IN CODE

		return null; // don't forget to change it
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

		return ""; // don't forget to change to the correct string
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

	}

}

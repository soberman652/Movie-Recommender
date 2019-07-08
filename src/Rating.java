

public class Rating implements Comparable<Rating> {

	private int userID;
	private int movieID;
	private double rating;
	private int timestamp;
	
	// Add other fields for handling ratings, links, and tags in some way
	
	
	
	// CONSTRUCTOR
	public Rating(int userID, int movieID, double rating, int timestamp) {
		this.userID = userID;
		this.movieID = movieID;
		this.rating = rating;
		this.timestamp = timestamp;
		
		// debug
		//System.out.println("MovieID: "+movieID+"  Param rating: "+rating+" Field rating: "+this.rating);

		
	}
	
	public Rating(int userID, int movieID, double rating) {
		this.userID = userID;
		this.movieID = movieID;
		this.rating = rating;
	}
	
	
	public Rating(int movieID) {
		this.movieID = movieID;
		this.rating = 0.0;
	}
	
	public Rating(double rating) {
		this.rating = rating;
	}
	
	public int getMovieID() {
		return movieID;
	}
	
	public int getUserID() {
		return userID;
	}
	
	public double getRating() {
		return rating;
	}
	
	public int getTimestamp() {
		return timestamp;
	}
	

	
	// TOSTRING
	public String toString() {
		String out = "USERID: " + userID;
		out += "\nMOVIEID: " + movieID;
		out += "\nRATING: " + rating;
		out += "\nTIMESTAMP: " + timestamp;
		
		return out;
	}

	
	@Override
	public int compareTo(Rating o) {
	if (movieID > o.movieID) {
		return 1;
	}
	
	else if (movieID < o.movieID) {
		return -1;
	}
	else
		return 0;
	
	} 
	
	
	
}

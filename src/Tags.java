

public class Tags {

	private int userID;
	private int movieID;
	private String tag;
	private int timestamp;
	
	// Add other fields for handling ratings, links, and tags in some way
	
	
	
	// CONSTRUCTOR
	public Tags(int userID, int movieID, String tag, int timestamp) {
		this.userID = userID;
		this.movieID = movieID;
		this.tag = tag;
		this.timestamp = timestamp;
	}
	
	public int getMovieID() {
		return movieID;
	}
	
	// TOSTRING
	public String toString() {
		String out = "USERID: " + userID;
		out += "\nMOVIEID: " + movieID;
		out += "\nTAG: " + tag;
		out += "\nTIMESTAMP: " + timestamp;
		
		return out;
	}
	
	
}

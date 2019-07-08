

public class Links implements Comparable<Links> {

	private int movieID;
	private String imdbID;
	private String tmdbID;

	
	// Add other fields for handling ratings, links, and tags in some way
	
	
	
	// CONSTRUCTOR
	public Links(int movieID, String imdbID, String tmdbID) {
		this.movieID = movieID;
		this.imdbID = imdbID;
		this.tmdbID = tmdbID;
	}
	
	public Links(int movieID) {
		this.movieID = movieID;
	}
	
	public int getID() {
		return movieID;
	}
	

	public String getimdbID() {
		return imdbID;
	}
	
	
	
	// TOSTRING
	public String toString() {
		String out = "movieID: " + movieID;
		out += "\nIMDBID: " + imdbID;
		out += "\nTMDBID: " + tmdbID;
		
		return out;
	}

	@Override
	public int compareTo(Links o) {
		// TODO Auto-generated method stub
		return movieID - o.movieID;
	}
	
	
}

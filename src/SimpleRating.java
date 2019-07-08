

public class SimpleRating implements Comparable<SimpleRating> {
	
	private int movieID;
	private double rating;

	
	// Add other fields for handling ratings, links, and tags in some way
	
	
	
	// CONSTRUCTOR
	public SimpleRating(int movieID, double rating) {
	
		this.movieID = movieID;
		this.rating = rating;
	
		
	}

	
	public SimpleRating(int movieID) {
		this.movieID = movieID;
		this.rating = 0.0;
	}
	
	public SimpleRating(double rating) {
		this.rating = rating;
	}
	
	public int getMovieID() {
		return movieID;
	}
	
	
	
	public double getRating() {
		return rating;
	}
	

	
	// TOSTRING
	public String toString() {
		String out = "\nMOVIEID: " + movieID;
		out += "\nRATING: " + rating;
		
		return out;
	}


	
	
	@Override
	public int compareTo(SimpleRating r) {
	if (this.rating < r.rating) {
		return 1;
	}
	
	else if (this.rating > r.rating) {
		return -1;
	}
	else
		return 0;
	
	}
	
}

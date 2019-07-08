import java.util.ArrayList;
import java.util.Arrays;

public class Movie implements Comparable<Movie> {

	private int ID;
	private String title;
	private int year;
	private String[] genres;
	private ArrayList<Rating> ratings;
	private String imdbID;

	
	// Add other fields for handling ratings, links, and tags in some way
	
	
	
	// CONSTRUCTOR
	public Movie(int id, int year, String title, String[] genres) {
		this.ID = id;
		this.year = year;
		this.title = title;
		this.genres = genres;
		ratings = new ArrayList<Rating>();
	}
	
	public Movie(int id) {
		ID = id;
	}
	
	
	
	
	public String getTitle() {
		return title;
	}

	
	public void setimdbID (String s) {
		imdbID = s;
	}
	
	public String getimdbID () {
		return imdbID;
	}
		
	public void addRating(Rating r) {
		
		if (r.getMovieID() == ID)
			ratings.add(r);
		
	}
	

	
	
	public int getID() {
		return ID;
	}
	
	// TOSTRING
	public String toString() {
		String out = "ID: " + ID;
		out += "\nYEAR: " + year;
		out += "\nTITLE: " + title;
		out += "\nGENRES: " + Arrays.toString(genres);
		
		return out;
	}

	@Override
	public int compareTo(Movie o) {
		// TODO Auto-generated method stub
		/*if (ID > o.ID) {
			return 1;
			
		}
		
		else if (ID < o.ID) {
			return -1;
		}
		else
			return 0;
		*/
		return ID - o.ID;
		
	}
	
	
}

import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;

public class User implements Comparable<User> {

	
	private int userID;
	private ArrayList<Rating> ratings;
	private double avgRating;
	
	
	public User(Rating r) {
		userID = r.getUserID();
		ratings = new ArrayList<Rating>();
		ratings.add(r);
	}
	
	public User(int userID) {
		this.userID = userID;
	}
	
	public void addRating(Rating r) {
	
		ratings.add(r);
		
	}
	
	public double getRating(int movieID) {
		/*
		for (Rating r : ratings) {
			if (movieID == r.getMovieID())
				return r.getRating();
			
		}
		return -1;
		*/
		
		Rating ra = new Rating(movieID);
		int i = Collections.binarySearch(ratings, ra);
		
		if (i >= 0) {
			Rating m = ratings.get(i);
			return m.getRating();
		}
		return -1;
		
	}
	
	public double calcUserAvg() {
		
		double numRatings = 0;
		double sum = 0;
		
		for (Rating r : ratings) {
			double rating = r.getRating();
			sum += rating;
			numRatings++;
		}
		avgRating = (sum/numRatings);
		return avgRating;
		
	}
	
	public double accessUserAvg() {
		return avgRating;
	}
	
	public ArrayList<Integer> getFavMovies() {
		
		ArrayList<Integer> favMovies = new ArrayList<Integer>();
		
		// return list of movies with rating > 3
		
		for (Rating r : ratings) {
			
			if (r.getRating() > 3)
				favMovies.add(r.getMovieID());
		}
		
		return favMovies;
	}
	
	public ArrayList<Integer> getUserMovies() {
		
		ArrayList<Integer> userMovies = new ArrayList<Integer>();
				
		for (Rating r : ratings) {			
			userMovies.add(r.getMovieID());
		}
		
		return userMovies;
	}
		
	
	public boolean lovedMovie(int movieID) {
		
		for (Rating r : ratings) {
			
			if ((r.getMovieID() == movieID) && (r.getRating() > 4))
				return true;
		}
		return false;
		
	}
	
	
	
	public int getUserID() {
		return userID;
	}
	
	public String toString() {
		String out = "USERID: " + userID;
		
		return out;
	}

	@Override
	public int compareTo(User o) {
		// TODO Auto-generated method stub
		return userID - o.userID;
	}
	
	
	
}

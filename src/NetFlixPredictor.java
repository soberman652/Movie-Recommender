import java.util.ArrayList;
import java.util.Collections;


public class NetFlixPredictor {


	// Add fields to represent your database.
	
	private ArrayList<Movie> movieDB;
	private ArrayList<Tags> tagsDB;
	private ArrayList<Links> linksDB;
	private ArrayList<User> userDB;
	
	

	private void setLinkforMovie(Links l) {
		
		Movie mo = findMovie(l.getID());
		int i = Collections.binarySearch(movieDB, mo);
		mo.setimdbID(l.getimdbID());
		movieDB.set(i, mo);
	}
	
	public Movie findMovie(int movieID) {

		
		Movie mo = new Movie(movieID);
		int i = Collections.binarySearch(movieDB, mo);
		
		if (i >= 0) {
			Movie m = movieDB.get(i);
			return m;
		}
		else
			return null;
		
		
	}
	
	private User findUser(int userID) {

		User mo = new User(userID);
		int i = Collections.binarySearch(userDB, mo);
		
		if (i >= 0) {
			User u = userDB.get(i);
			return u;
		}
		else
			return null;
		
	}
	
	
	private double calcAvgRating(ArrayList<User> similarUsers, int movieID) {
		// Form average of ratings in similarUsers for movieID

		double numUsers = 0;
		double sum = 0;
		
		if (similarUsers.isEmpty())
			return 3;
		
		for (User u : similarUsers) {
			double rating = u.getRating(movieID);
			sum += rating;
			numUsers++;
		}
		double avgRating = sum/numUsers;
		return avgRating;
		
	}
	
	
	
	private ArrayList<User> findMovieWatchers(int movieID) {
		// Return list of users who have seen movieID

		ArrayList<User> movieWatchers = new ArrayList<User>();
		for (User u : userDB) {
			if (u.getRating(movieID) > 0) {
				movieWatchers.add(u);
			}
		}
		return movieWatchers;
		
		

	}

	private ArrayList<User> findSimilarUsers(ArrayList<Integer> favMovies, ArrayList<User> movieWatchers) {
	// Return list of users from movieWatchers who have seen > 2 of the favMovies and rated each of them > 3
	
		ArrayList<User> similarUsers = new ArrayList<User>();
		int count = 0;
		
		for (User u : movieWatchers) {
			for (int i : u.getFavMovies()) {
				for (int j : favMovies) 	{ 
					if (i == j)
						count++;
				}
			}
			if (count > 2)
				similarUsers.add(u);
			count = 0;
		}
		return similarUsers;
	}
	
	public String getimdbID(int movieID) {
		
		Links l = new Links(movieID);
		int i = Collections.binarySearch(linksDB, l);
		
		if (i >= 0) {
			Links u = linksDB.get(i);
			return u.getimdbID();
		}
		else
			return null;
		
		
		
		
	}
	
	
	
	/**
	 * 
	 * Use the file names to read all data into some local structures. 
	 * 
	 * @param movieFilePath The full path to the movies database.
	 * @param ratingFilePath The full path to the ratings database.
	 * @param tagFilePath The full path to the tags database.
	 * @param linkFilePath The full path to the links database.
	 */
	public NetFlixPredictor(String movieFilePath, String ratingFilePath, String tagFilePath, String linkFilePath) {

		movieDB = new ArrayList<Movie>();
		tagsDB = new ArrayList<Tags>();
		linksDB = new ArrayList<Links>();
		userDB = new ArrayList<User>();
		
		
		ArrayList<String> movieStrings = FileIO.readFile(movieFilePath);
		ArrayList<String> ratingStrings = FileIO.readFile(ratingFilePath);
		ArrayList<String> tagStrings = FileIO.readFile(tagFilePath);
		ArrayList<String> linkStrings = FileIO.readFile(linkFilePath);
		
			
		MovieLensCSVTranslator translator = new MovieLensCSVTranslator();
						
		for (int i = 1; i < movieStrings.size(); i++) {
			Movie m = translator.parseMovie(movieStrings.get(i));
			movieDB.add(m);
		}
		
		Collections.sort(movieDB);
		
		for (int i = 1; i < tagStrings.size(); i++) {
			Tags m = translator.parseTags(tagStrings.get(i));
			tagsDB.add(m);
		}
		
		for (int i = 1; i < linkStrings.size(); i++) {
			Links l = translator.parseLinks(linkStrings.get(i));
			setLinkforMovie(l);
			linksDB.add(l);
		}
				
		
		for (int i = 1; i < ratingStrings.size(); i++) {
			Rating r = translator.parseRatings(ratingStrings.get(i));
			
			// add rating to appropriate movie arraylist
			
			Movie m = findMovie(r.getMovieID());
			m.addRating(r);
			
			
			// add rating to appropriate user arraylist
			
			User u = findUser(r.getUserID());
			if (u == null) {
				u = new User(r);
				userDB.add(u);
			}
			else {
				u.addRating(r);
			}

		}
		
	}
		

	/**
	 * If userNumber has rated movieNumber, return the rating. Otherwise, return -1.
	 * 
	 * @param userNumber The ID of the user.
	 * @param movieNumber The ID of the movie.
	 * @return The rating that userNumber gave movieNumber, or -1 if the user does not exist in the database, the movie does not exist, or the movie has not been rated by this user.
	 */
	public double getRating(int userID, int movieID) {
		
		for (User u : userDB) {
			if (u.getUserID() == userID)
				return u.getRating(movieID);
		}
				
		return -1;
	}

		
	/**
	 * If userNumber has rated movieNumber, return the rating. Otherwise, use other available data to guess what this user would rate the movie.
	 * 
	 * @param userNumber The ID of the user.
	 * @param movieNumber The ID of the movie.
	 * @return The rating that userNumber gave movieNumber, or the best guess if the movie has not been rated by this user.
	 * @pre A user with id userID and a movie with id movieID exist in the database.
	 */
	public double guessRating(int userID, int movieID) {

			
			double r = getRating(userID, movieID);
			
			if (r != -1) {
				return r;
			}
			else {
			
			
				// List of users who have seen movieID
				ArrayList<User> movieWatchers = findMovieWatchers(movieID);
				

				User main = findUser(userID);
				
				double userAvg = main.calcUserAvg();
				
				double numerator = 0;
				double mainDenominator = 0;
				double otherDenominator = 0;
				double mainRating = 0;
				double otherRating = 0;
				double similarity = 0;
				double movieNumerator = 0;
				double movieDenominator = 0;
				double predictedRating;
				
				ArrayList<Integer> userMovies = main.getUserMovies();
				
				// compute cosine similarity of user vs. other moviewatchers
				//    A * B
				// -----------
				// ||A|| ||B||
				// 
				for (User other : movieWatchers) {
					double otherAvg = other.calcUserAvg();
					numerator = 0;
					mainDenominator = 0;
					otherDenominator = 0;
					for (int i : userMovies) {
						mainRating = main.getRating(i);
						otherRating = other.getRating(i);
						
						if (otherRating > 0) {
							double a = mainRating - userAvg;
							double b = otherRating - otherAvg;
							
							numerator += a*b;
							mainDenominator += a*a;
							otherDenominator += b*b;
						}
					} // all movies
					
					if ((mainDenominator == 0) || (otherDenominator == 0)) {
						similarity = 0;
					}
					else {
						similarity = numerator / (Math.sqrt(mainDenominator) * Math.sqrt(otherDenominator));
					}		
					 // computing similarity of main vs. other(i)

					
					movieNumerator += similarity * (other.getRating(movieID) - otherAvg);
					movieDenominator += Math.abs(similarity);
				} // all users
				
				
				if (movieDenominator == 0) {
					predictedRating = userAvg;
				}
				else
					predictedRating = userAvg + movieNumerator/movieDenominator;
				/*
				if (predictedRating > 5)
					predictedRating = 5;
				if (predictedRating < 0)
					predictedRating = 0;
				 */
				
				return predictedRating;

			}
		
	}
	
	/**
	 * Recommend a movie that you think this user would enjoy (but they have not currently rated it). 
	 * 
	 * @param userNumber The ID of the user.
	 * @return The ID of a movie that data suggests this user would rate highly (but they haven't rated it currently).
	 * @pre A user with id userID exists in the database.
	 */
	//public int recommendMovie(int userID) {
	public ArrayList<Integer> recommendMovie(int userID) {
		User u = findUser(userID);
		
		ArrayList<SimpleRating> guessedRatings = new ArrayList<SimpleRating>();
		
		for (Movie m : movieDB) {
			int mID = m.getID();
			double r = u.getRating(mID);

			if (r < 0) {

				double guessedRating = guessRating(userID, mID);

				SimpleRating rating = new SimpleRating(mID, guessedRating);
				guessedRatings.add(rating);
			}
		}

		Collections.sort(guessedRatings);
		
		ArrayList<Integer> highestRatedMovies = new ArrayList<Integer>();
		
		System.out.println("");
		
		for (int i=0; i < 3 && i < guessedRatings.size(); i++) {
			highestRatedMovies.add(guessedRatings.get(i).getMovieID());
		}
		
		
		return highestRatedMovies;
			
	}
	
	public ArrayList<Movie> getMovies(){
		return movieDB;
	}
	
	public ArrayList<Integer> getRatedMovies(int userID) {
		User u = findUser(userID);
		return u.getFavMovies();
	}
	
		
}

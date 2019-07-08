import java.util.ArrayList;

public class MovieLensCSVTranslator {

	
	private ArrayList<String> getLinePieces(String line) {
		ArrayList<String> pieces = new ArrayList<String>();  // Holds comma separated pieces of the line
		boolean quoted = false;  // Keeps track of whether the current character is inside of quotes or not
		int start = 0;
		for (int i = 0; i < line.length(); i++) {  // For each character...
			char thisChar = line.charAt(i);
			if (thisChar == '"') {  // If we see a quote symbol
				quoted = !quoted;   // Then we're inside of quotes
			} else if (thisChar == ',' && !quoted) {  // If we see a comma and we're not inside of quotes
				pieces.add(line.substring(start,i));  // Add this chunk of data to the pieces list
				start = i+1;
			}
		}
		
		pieces.add(line.substring(start));
		
		return pieces;
	}
	
	
	public Movie parseMovie(String line) {
		ArrayList<String> pieces = getLinePieces(line);  // Get all the sections of the line separated by commas (but not in quotes)
		
		int id = Integer.parseInt(pieces.get(0));  // ID is the first piece of data
		
		String title = pieces.get(1);  // title is the first piece of data
		
		int yearStart;
		int year = 0;
		
		if (title.contains("(")) {
			yearStart = title.lastIndexOf("(");
			year = Integer.parseInt(title.substring(yearStart+1, yearStart+5));  // Extract the year from inside parenthesis
			title = title.substring(0, title.indexOf("("));
		}
		
		
		
		String[] genrePieces = null;
		String smallpieces[] = new String[1]; 
		
		if (line.contains("|")) {
			genrePieces = pieces.get(2).split("\\|"); // Split up the genres by looking for |
		}
		else {
			String genrePiece = pieces.get(2);
			smallpieces[0] = genrePiece;
			genrePieces = smallpieces;
		}
		
		Movie m = new Movie(id, year, title, genrePieces);
		return m;
	}

	public Links parseLinks(String line) {
		
		ArrayList<String> pieces = getLinePieces(line);

		int movieID = Integer.parseInt(pieces.get(0));
		String imdbID = pieces.get(1);
		String tmdbID = "0";
		
		
		if (pieces.size() > 2)
			tmdbID = pieces.get(2);
		
		Links l = new Links(movieID, imdbID, tmdbID);
		return l;
		
	}
	
	
	public Rating parseRatings(String line) {
		
		
		String[] splits = line.split(",");

		int userID = Integer.parseInt(splits[0]);
		int movieID = Integer.parseInt(splits[1]);
		double rating = Double.parseDouble(splits[2]);
		int timestamp = Integer.parseInt(splits[3]);
		
		Rating r = new Rating(userID, movieID, rating, timestamp);
		return r;

	}
	
	public Tags parseTags(String line) {
		
		ArrayList<String> pieces = getLinePieces(line);
		
		
		int userID = Integer.parseInt(pieces.get(0));
		int movieID = Integer.parseInt(pieces.get(1));
		
		String tag = pieces.get(2);
		
		int timestamp = Integer.parseInt(pieces.get(3));
		
		Tags t = new Tags(userID, movieID, tag, timestamp);
		return t;
		
	}
	
	
	
	

	
	
}

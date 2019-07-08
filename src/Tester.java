import java.util.ArrayList;

public class Tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//ArrayList<String> movieStrings = FileIO.readFile("dataset" + FileIO.fileSeparator + "movies_orig.csv");
		ArrayList<String> movieStrings = FileIO.readFile("dataset" + FileIO.fileSeparator + "links.csv");
		/*for (String s : movieStrings) {
			System.out.println(s);
		}*/
	
		//ArrayList<Movie> movieData = new ArrayList<Movie>();
		ArrayList<Links> movieData = new ArrayList<Links>();
		MovieLensCSVTranslator translator = new MovieLensCSVTranslator();
				
		for (int i = 1; i < movieStrings.size(); i++) {
			//Movie m = translator.parseMovie(movieStrings.get(i));
			Links m = translator.parseLinks(movieStrings.get(i));
			movieData.add(m);
		}
		
		/*for (Movie m : movieData) {
			System.out.println(m);
		}*/
		
		for (Links m : movieData) {
			System.out.println(m);
		}
	}

}

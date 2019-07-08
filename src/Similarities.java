import java.util.ArrayList;

public class Similarities implements Comparable<Similarities> {

	private User user;
	private double similarity;

	public Similarities(User user, double similarity) {
		this.user = user;
		this.similarity = similarity;
	}

	public double getSimilarity() {
		return similarity;
	}
	
	public User getUser() {
		return user;
	}
	
	
	@Override
	public int compareTo(Similarities o) {
		// TODO Auto-generated method stub
		if (similarity > o.similarity) {
			return 1;
		}
		else if (similarity < o.similarity) {
			return -1;
		}
		else
			return 0;
	}	
	
}

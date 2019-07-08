

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


import processing.core.PApplet;
import processing.core.PImage;

public class DrawingMovie {

	private Movie movie;
	private PImage coverArt;
	
	public DrawingMovie(Movie m) {
		this.movie = m;
		coverArt = null;
	}
	
	public void draw(PApplet drawer, float x, float y, float width, float height) {
		if (movie != null) {
			if (coverArt != null) {
				drawer.image(coverArt, x, y,width,height);
			}
			
			String title = movie.getTitle();
			drawer.text(title, x, y);
			
			
		}
		drawer.stroke(0);
		drawer.noFill();
		drawer.rect(x, y, width, height);
	}
	

	public void downloadArt(PApplet drawer) {
		
		Thread downloader = new Thread(new Runnable() {

			@Override
			public void run() {
				

				// Find the cover art using IMDB links
				// Initialize coverArt
				
				
				
				Scanner scan = null;
				try {
				
					String link = movie.getimdbID();
					
					//System.out.println("Movie: "+movie.getTitle()+ "  IMDB: "+link);
					
					String pageURLString = "http://www.imdb.com/title/tt"+link+"/";
				
					
					URL pageURL = new URL(pageURLString);
					InputStream is = pageURL.openStream();
					scan = new Scanner(is);

			
				
					String fileData = "";
					while(scan.hasNextLine()) {
						String line = scan.nextLine();
						fileData += line + FileIO.lineSeparator;
	
				}
										
					// look for <div class="poster">
					// look for src="
					// look for

				
					String ImgRegex = "(?s)<div class=\"poster\">.*?src=\"([^\"]+)";

					String group=" ";
					Pattern pattern = Pattern.compile(ImgRegex);
					Matcher matcher = pattern.matcher(fileData);
					while (matcher.find()) {
						group = matcher.group(1);
					}
				
					String imageURL = group;
					coverArt = drawer.loadImage(imageURL);
				
					
				} catch (IOException e) {
				e.printStackTrace();
				} finally {
				if (scan != null)
					scan.close();
				}
				
			}
			
		});
		
		downloader.start();

	}

	
}

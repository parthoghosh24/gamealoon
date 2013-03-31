package com.gamealoon.tests;


import java.util.ArrayList;
import models.Game;
import models.Platform;
import org.junit.Test;
import com.google.code.morphia.Datastore;
import utility.GloonDAO;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

/**
 * Testing class for Db and various Models/Entities of gamealoon over here. All
 * Models and db related tests should be written over here
 * 
 * @author Partho
 * 
 */
public class ModelsTest {

	/**
	 * We are testing the db and various entites over here....
	 * 
	 */
	@Test
	public void testDb() {
		running(fakeApplication(), new Runnable() {

			@Override
			public void run() {

				GloonDAO daoInstance = GloonDAO.instantiateDAO();
				Datastore gloonDatastore = daoInstance.initDatastore();

				if (gloonDatastore.getCount(Platform.class) < 1) {
					System.out.println("Data getting created.............");
					Platform playstation3 = new Platform();
					playstation3.setTitle("Playstation 3");
					playstation3
							.setDescription("The cell powered third generation beast");
					playstation3.setManufacturer("Sony Inc.");

					gloonDatastore.save(playstation3);

					ArrayList<Platform> platforms = new ArrayList<>();
					platforms.add(playstation3);

					Game maxPayne3 = new Game();
					maxPayne3.setTitle("Max Payne 3");
					maxPayne3
							.setDescription("The third and final chapter in the Max Payne Trilogy. Max goes for final kill");
					maxPayne3.setDeveloper("Rockstar Games");
					maxPayne3.setPublisher("Rockstar Games");
					maxPayne3.setGenere("Third Person Action");
					maxPayne3.setPrice("60$");
					maxPayne3.setPlatforms(platforms);
					maxPayne3.setRating("Mature");
					maxPayne3.setReleaseDate("2012-03-24");
					maxPayne3.setScore(8.5f);

					gloonDatastore.save(maxPayne3);

				} else {
					System.out.println("Fetching data.............");
					Game fetchedMaxPayne = gloonDatastore.find(Game.class,
							"title", "Max Payne 3").get();

					if (fetchedMaxPayne != null) {
						System.out.println("Game Title: "
								+ fetchedMaxPayne.getTitle());
						System.out.println("Game Description: "
								+ fetchedMaxPayne.getDescription());
						System.out.println("Game Developer: "
								+ fetchedMaxPayne.getDeveloper());
						System.out.println("Game Publisher: "
								+ fetchedMaxPayne.getPublisher());
						System.out.println("Game Price: "
								+ fetchedMaxPayne.getPrice());
						System.out.println("Game Rating: "
								+ fetchedMaxPayne.getRating());
						System.out.println("Game Release Date: "
								+ fetchedMaxPayne.getReleaseDate());
						System.out.println("Game Score(out of 10): "
								+ fetchedMaxPayne.getScore());
						System.out.println("Game Genere: "
								+ fetchedMaxPayne.getGenere());
						System.out.println("Game Platforms: "
								+ fetchedMaxPayne.getPlatforms());
					}
				}
				
				/*MongoClient client;
				try {
					client = new MongoClient();
					client.dropDatabase(GloonDataInterface.DB_NAME);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				
			}
		});
	}

}

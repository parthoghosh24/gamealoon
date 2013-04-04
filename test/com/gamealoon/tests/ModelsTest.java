package com.gamealoon.tests;


import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;
import org.junit.Test;

import com.gamealoon.database.GloonDAO;
import com.gamealoon.database.GloonDataInterface;
import com.gamealoon.models.Achievement;
import com.gamealoon.models.Game;
import com.gamealoon.models.Platform;
import com.gamealoon.models.User;
import com.gamealoon.utility.Utility;
import com.google.code.morphia.Datastore;
import com.mongodb.MongoClient;


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
					
					
					User partho = new User();
					partho.setEmail("defjam24@gmail.com");
					partho.setPassword("secret");
					partho.setFirstName("partho");
					partho.setMiddleName("");
					partho.setLastName("ghosh");
					partho.setUsername("loonatic86");
					partho.setDay(24);
					partho.setMonth(3);
					partho.setYear(1986);
					partho.setAvatarPath("myAvatarPath");
					partho.setInsertTime(new Date());
					partho.setGameBio("I love gaming. Started my gaming adventure from 1999 and still going strong...");					
					partho.setUserId(Utility.generateUserId(partho.getFirstName(),partho.getMiddleName(),partho.getLastName()
							,partho.getDay(),partho.getMonth(),partho.getYear()));
					
					Achievement newGloonie = new Achievement();
					newGloonie.setTitle("New Gloonie!");
					newGloonie.setDescription("Welcome to gamealoon!!!");
					newGloonie.setImagePath("newGloonieImagePath");
					
					Achievement videoGlooniac= new Achievement();
					videoGlooniac.setTitle("Video Glooniac!");
					videoGlooniac.setDescription("Uploaded 500 videos!!!");
					videoGlooniac.setImagePath("videoGlooniacPath");
					
					Achievement  glooniacWriter = new Achievement();
					glooniacWriter.setTitle("Glooniac Writer!!!");
					glooniacWriter.setDescription("Wrote 1000 articles in total!!!");
					glooniacWriter.setImagePath("glooniacWriterPath");
					
					Achievement gloonyAboutgames = new Achievement();
					gloonyAboutgames.setTitle("Gloony about games!");
					gloonyAboutgames.setDescription("Played 1000 times in 1 week!!!");
					gloonyAboutgames.setImagePath("gloonyAboutGamesPath");
					
					Achievement gloonyAboutVideos = new Achievement();
					gloonyAboutVideos.setTitle("Gloony about videos!");
					gloonyAboutVideos.setDescription("Seen 1000 videos in 1 week!!!");					
					gloonyAboutVideos.setImagePath("gloonyAboutVideosPath");
					
					Set<Achievement> parthoAchievements = new HashSet<>();
					parthoAchievements.add(newGloonie);
					parthoAchievements.add(glooniacWriter);
					parthoAchievements.add(gloonyAboutgames);
					parthoAchievements.add(videoGlooniac);
					
					partho.setAchievements(parthoAchievements);
					
					
					
					partho.setAchievementsBasedScore(parthoAchievements.size()*10);
					partho.setVideoUploadBasedScore(50);
					partho.setUserFollowScore(10);
					partho.setArticleBasedScore(600);

					                   
                    
					
					
                    User swati = new User();
                    swati.setEmail("swati.hindu@gmail.com");
                    swati.setPassword("secret");
                    swati.setFirstName("swati");
                    swati.setMiddleName("");
                    swati.setLastName("mittal");
                    swati.setUsername("loonatic87");
                    swati.setDay(7);
                    swati.setMonth(3);
                    swati.setYear(1987);
                    swati.setAvatarPath("myAvatarPath");
                    swati.setInsertTime(new Date());
                    swati.setGameBio("I love racing and puzzle games a lot. Hosted many gaming contests in my college days...");					
                    swati.setUserId(Utility.generateUserId(swati.getFirstName(),swati.getMiddleName(),swati.getLastName()
							,swati.getDay(),swati.getMonth(),swati.getYear()));
                    
                    Set<Achievement> swatiAchievements = new HashSet<>();
                    swatiAchievements.add(newGloonie);
                    swatiAchievements.add(gloonyAboutVideos);
                    swati.setAchievements(parthoAchievements);
					
                    swati.setAchievementsBasedScore(parthoAchievements.size()*10);
                    swati.setVideoUploadBasedScore(70);
                    swati.setUserFollowScore(0);
                    swati.setArticleBasedScore(200);
                    
                    Set<User> parthoFollowers = new HashSet<>();
					parthoFollowers.add(swati);
					gloonDatastore.save(swati);
					
					partho.setFollowedBy(parthoFollowers);
					gloonDatastore.save(partho);
					
					Set<User> swatiFollowers = new HashSet<>();
					swatiFollowers.add(partho);
					swati.setUserFollowScore(10);
					swati.setFollowedBy(swatiFollowers);
					gloonDatastore.save(swati);
					

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
					System.out.println("-----------------------------------------------------<< >>-------------------------------------------");				  
				  List<User> allUsers = gloonDatastore.find(User.class).asList();
				  
				  for(User user:allUsers)
				  {
					  if(user!=null)
					  {
						  System.out.println("User name: "+user.getUsername());
						  System.out.println("User Id: "+user.getUserId());
						  System.out.println("Email: "+user.getEmail());
						  System.out.println("Age: "+(2013-user.getYear()));
						  System.out.println("Achievements: "+user.getAchievements());
						  System.out.println("Followed by: "+user.getFollowedBy());
						  System.out.println("Total Score: "+(user.getAchievementsBasedScore()+
								  user.getVideoUploadBasedScore()+
								              user.getArticleBasedScore()+
								              user.getUserFollowScore()));
					  }  
					  System.out.println("<<<<<<<<<<<<<<<   >>>>>>>>>>>>>>>>>>>");
				  }
				  
				}
				
			}
		});
	}

}

package com.gamealoon.tests;



import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.junit.Test;

import com.gamealoon.database.GloonDAO;
import com.gamealoon.models.Achievement;
import com.gamealoon.models.Article;
import com.gamealoon.models.Category;
import com.gamealoon.models.Game;
import com.gamealoon.models.Platform;
import com.gamealoon.models.User;
import com.gamealoon.utility.Utility;
import com.google.code.morphia.Datastore;



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

	final GloonDAO daoInstance = GloonDAO.instantiateDAO();
	final Datastore gloonDatastore = daoInstance.initDatastore();
	/**
	 * We are testing the db and various entites over here....
	 * 
	 */
	@Test
	public void testDb() {
		running(fakeApplication(), new Runnable() {

			@Override
			public void run() {

				

				if (gloonDatastore.getCount(Platform.class) < 1) {
					System.out.println("Data getting created.............");
					
					createPlatformsAndGames();
					createUsersAndAchievements();
					createArticles();
					

				} else {
					System.out.println("Fetching data.............");
				    findAllGames();
				    findAllUsers();
				}
				
			}
			
			private void createUsersAndAchievements()
			{
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
				
				Achievement newGloonie = new Achievement();
				newGloonie.setTitle("New Gloonie!");
				newGloonie.setDescription("Welcome to gamealoon!!!");
				newGloonie.setImagePath("newGloonieImagePath");
				gloonDatastore.save(newGloonie);
				
				Achievement videoGlooniac= new Achievement();
				videoGlooniac.setTitle("Video Glooniac!");
				videoGlooniac.setDescription("Uploaded 500 videos!!!");
				videoGlooniac.setImagePath("videoGlooniacPath");
				gloonDatastore.save(videoGlooniac);
				
				Achievement  glooniacWriter = new Achievement();
				glooniacWriter.setTitle("Glooniac Writer!!!");
				glooniacWriter.setDescription("Wrote 1000 articles in total!!!");
				glooniacWriter.setImagePath("glooniacWriterPath");
				gloonDatastore.save(glooniacWriter);
				
				Achievement gloonyAboutgames = new Achievement();
				gloonyAboutgames.setTitle("Gloony about games!");
				gloonyAboutgames.setDescription("Played 1000 times in 1 week!!!");
				gloonyAboutgames.setImagePath("gloonyAboutGamesPath");
				gloonDatastore.save(gloonyAboutgames);
				
				Achievement gloonyAboutVideos = new Achievement();
				gloonyAboutVideos.setTitle("Gloony about videos!");
				gloonyAboutVideos.setDescription("Seen 1000 videos in 1 week!!!");					
				gloonyAboutVideos.setImagePath("gloonyAboutVideosPath");
				gloonDatastore.save(gloonyAboutVideos);
				
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
				partho.setTotalScore(partho.getAchievementsBasedScore()+partho.getVideoUploadBasedScore()+partho.getUserFollowScore()+partho.getArticleBasedScore());

	
				
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
				swati.setTotalScore(swati.getAchievementsBasedScore()+swati.getVideoUploadBasedScore()+swati.getUserFollowScore()+swati.getArticleBasedScore());
				swati.setFollowedBy(swatiFollowers);
				gloonDatastore.save(swati);
			}
			
			private void createPlatformsAndGames()
			{
				Platform playstation3 = new Platform();
				playstation3.setTitle("Playstation 3");
				playstation3
						.setDescription("The cell powered third generation beast by Sony");
				playstation3.setManufacturer("Sony Inc.");

				gloonDatastore.save(playstation3);
				
				Platform xbox360 = new Platform();
				xbox360.setTitle("Xbox 360");
				xbox360.setDescription("Microsoft's 2nd generation beast");
				xbox360.setManufacturer("Microsoft Inc.");
				gloonDatastore.save(xbox360);
				
				Platform pc = new Platform();
				pc.setTitle("PC");
				pc.setDescription("The world is owned by Microsoft, Apple and Linux");
				pc.setManufacturer("Many");
				gloonDatastore.save(pc);
                   
				ArrayList<Platform> platforms = new ArrayList<>();
				platforms.add(playstation3);
				platforms.add(xbox360);
				platforms.add(pc);

				Game maxPayne3 = new Game();
				maxPayne3.setTitle("Max Payne 3");
				maxPayne3.setDescription("The third and final chapter in the Max Payne Trilogy. Max goes for final kill");
				maxPayne3.setDeveloper("Rockstar Games");
				maxPayne3.setPublisher("Rockstar Games");
				maxPayne3.setGenere("Third Person Action");
				maxPayne3.setPrice("60$");
				maxPayne3.setPlatforms(platforms);
				maxPayne3.setRating("Mature");
				maxPayne3.setReleaseDate("2012-03-24");
				maxPayne3.setScore(8.5f);

				gloonDatastore.save(maxPayne3);
				
				Game farCry3 = new Game();
				farCry3.setTitle("Far Cry 3");
				farCry3.setDescription("With Far Cry 3, players step into the shoes of Jason Brody, a man alone at the edge of the world, stranded on a mysterious tropical island. In this savage paradise where lawlessness and violence are the only sure thing, players dictate how the story unfolds, from the battles they choose to fight to the allies or enemies they make along the way. As Jason Brody, players will slash, sneak, detonate and shoot their way across the island in a world that has lost all sense of right and wrong.");
				farCry3.setDeveloper("Ubisoft Montreal");
				farCry3.setPublisher("Ubisoft");
				farCry3.setGenere("First Person Shooter");
				farCry3.setPrice("60$");
				farCry3.setPlatforms(platforms);
				farCry3.setRating("Mature");
				farCry3.setReleaseDate("2012-12-04");
				farCry3.setScore(9.0f);
				
				gloonDatastore.save(farCry3);
			}
			
			private void createArticles()
			{
				Article maxPayne3Review = new Article();
				maxPayne3Review.setTitle("max payne 3 review");
				User author = gloonDatastore.find(User.class, "username", "loonatic86").get();						
				maxPayne3Review.setAuthor(author);
				maxPayne3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
				maxPayne3Review.setCategory(Category.Review);
				maxPayne3Review.setCreationDate(Utility.convertDateToString(new Date()));
				Game game = gloonDatastore.find(Game.class, "title", "Max Payne 3").get();
				maxPayne3Review.setGame(game);
				maxPayne3Review.setInsertTime(new Date());
				maxPayne3Review.setScore(0.56);
				gloonDatastore.save(maxPayne3Review);
			}
			
			//Query test
			
			private void findAllGames()
			{
			  List<Game> games = gloonDatastore.find(Game.class).asList();
			  
			  if(games.size()>0)
			  {
				  System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				  for(Game game: games)
				  {
					  if(game!=null)
					  {
						  System.out.println("Game name: "+game.getTitle());
						  System.out.println("Game platforms: "+game.getPlatforms());
						  System.out.println("Game score(Out of 10): "+game.getScore());
						  System.out.println("Game genere: "+game.getGenere());
						  System.out.println("--------------->><<----------------------");
					  }
				  }
				  System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			  }
			}
			
			private void findAllUsers()
			{
				List<User> users = gloonDatastore.find(User.class).asList();
				  
				  if(users.size()>0)
				  {
					  System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
					  for(User user: users)
					  {
						  if(user!=null)
						  {
							  System.out.println("User username: "+user.getUsername());
							  System.out.println("User gamebio: "+user.getGameBio());
							  System.out.println("User score: "+user.getTotalScore());
							  System.out.println("User followers: "+user.getFollowedBy());
							  System.out.println("--------------->><<----------------------");
						  }
					  }
					  System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				  }
			}
			
		});
	}

}

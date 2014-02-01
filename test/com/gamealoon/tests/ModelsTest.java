package com.gamealoon.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;
import play.api.Play;

import com.gamealoon.algorithm.Gamification;
import com.gamealoon.algorithm.RankAlgorithm;
import com.gamealoon.algorithm.SecurePassword;
import com.gamealoon.database.daos.ArticleDAO;
import com.gamealoon.database.daos.GameDAO;
import com.gamealoon.database.daos.UserDAO;
import com.gamealoon.models.Category;
import com.gamealoon.models.Game;
import com.gamealoon.models.Genre;
import com.gamealoon.utility.AppConstants;
import com.gamealoon.utility.Utility;



import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

/**
 * Testing class for Db and various Models/Entities of gamealoon over here. All
 * Models and db related tests should be written over here.
 * 
 * @author Partho
 * 
 */
public class ModelsTest {

	final ArticleDAO articleDaoInstance = ArticleDAO.instantiateDAO();
	final GameDAO gameDaoInstance = GameDAO.instantiateDAO();
	final UserDAO userDaoInstance = UserDAO.instantiateDAO();	
	/**
	 * We are testing the db and various entites over here....
	 * 
	 */
	@Test
	public void testDb() {
		running(fakeApplication(), new Runnable() {

			@Override
			public void run() {
				
					System.out.println("Fetching data.............");				    
				    try {
						findAllUsers();
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				    
				    findAllRecentGames();
				    findAllRecentReleasedGames();	
				    testStringEncoder();
				    testFetchIdFromTitle();				    
				    countUsers();
				    testEnums();
				    testPasswordHash();
				    testUsernameDetection();
				    testAbsoluteFilePath();
				    testUserFilePath();
				    testWilson();				 
				    testShortenString();				    				   				   
				    testSomeConversion();
				    testLevelCalculator();
				    testGPCalculator();				    
				
			}
			



			private void testGPCalculator() {
				System.out.println("GP is: "+ Gamification.calculateGP(600, 4));				
			}




			private void testLevelCalculator() {
				System.out.println("Level is: "+ Gamification.calculateLevel(400, 2));
				
			}




			private void testSomeConversion() {
				double val =1382518691000L;				
				System.out.println(val/(1000*60));
				
			}


			private void testShortenString() {
				System.out.println("Tom Clancy's Splinter Cell: Blacklist: "+Utility.shortenString("Tom Clancy's Splinter Cell: Blacklist"));
				
			}


			


			private void testWilson() {
				System.out.println("Wilson score is: "+RankAlgorithm.wilsonScoreCalculator(1000, 350));
				
			}


			private void testUserFilePath() {
				String uptoUsername =AppConstants.APP_ABSOLUTE_IMAGE_PATH+"rayray86\\";
				System.out.println(uptoUsername);
				
			}


			private void testAbsoluteFilePath() {
				try {
					System.out.println("Abs path: "+Play.current().path().getCanonicalPath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}


			private void testUsernameDetection() {
//				System.out.println("User is: "+userDaoInstance.findByUsername("guest"));
				
			}


			private void testPasswordHash() {
				try {
					HashMap<String, String> passwordHash = SecurePassword.createHash("secret");
					String saltHex=passwordHash.get("saltHex");
					String hashHex=passwordHash.get("hashHex");	
					System.out.println("Salt hex: "+passwordHash.get("saltHex"));
					System.out.println("Hash hex: "+passwordHash.get("hashHex"));
					
					if(SecurePassword.validatePassword("asdad", hashHex, saltHex))
					{
						System.out.println("Password matched!!!");
					}
					else
					{
						System.out.println("Password didnt match!!!");
					}
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidKeySpecException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
			}




			private void testEnums() {
				System.out.println("Genre Enum: "+ Genre.Action);
				System.out.println("Category Review: "+Category.Review);				
				System.out.println(Category.valueOf("Review"));								
				System.out.println("TPS: "+Genre.EndlessRunner);
			}




			private void countUsers() {
				UserDAO userDAOInstance =UserDAO.instantiateDAO();
				
				System.out.println("Total Users: "+userDAOInstance.count());
				
			}



		
			
			private void findAllUsers() throws MalformedURLException
			{
				UserDAO userInstance =UserDAO.instantiateDAO();
				List<HashMap<String, Object>> sortedUsers = userInstance.getTopNUsers(-1);
				  
				  if(sortedUsers.size()>0)
				  {
					  System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
					  for(HashMap<String, Object> user: sortedUsers)
					  {
						  if(user!=null)
						  {							  
							   
							  System.out.println("User username: "+user.get("userUserName"));
							  System.out.println("User Avatar: "+user.get("userAvatar"));
							  System.out.println("User Total Achievements: "+user.get("userAchievementCount"));
							  System.out.println("User Total Followers: "+user.get("totalFollowers"));
							  System.out.println("--------------->><<----------------------");
						  }
					  }
					  System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				  }
			}
			
			
			private void findAllRecentGames()
			{				
				List<Game> sortedGames = gameDaoInstance.getRecentReleasedGames(-1);
				
				System.out.println("Recent sorted games list size: "+ sortedGames.size());
				
				if(sortedGames.size()>0)
				{
					System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
					  for(Game game: sortedGames)
					  {
						  if(game!=null)
						  {
							  System.out.println("Game Title: "+game.getTitle());
							  System.out.println("Game Release Date: "+game.getReleaseDate());
							  System.out.println("Game Developer: "+game.getDeveloper());
							  System.out.println("Game Publisher "+game.getPublisher());							  				 
							  System.out.println("--------------->><<----------------------");
						  }
					  }
					  System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				}	
			}
			
			
			private void findAllRecentReleasedGames()
			{				
				List<Game> sortedGames = gameDaoInstance.getRecentReleasedGames(5);
				
				System.out.println("Released sorted games list size: "+ sortedGames.size());
				if(sortedGames.size()>0)
				{
					System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
					  for(Game game: sortedGames)
					  {
						  if(game!=null)
						  {
							  System.out.println("Game Title: "+game.getTitle());
							  System.out.println("Game Release Date: "+game.getReleaseDate());
							  System.out.println("Game Developer: "+game.getDeveloper());
							  System.out.println("Game Publisher "+game.getPublisher());							  						 
							  System.out.println("--------------->><<----------------------");
						  }
					  }
					  System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				}	
			}
			
						
			private void testStringEncoder()
			{
				System.out.println(Utility.encodeForUrl("Far Cry 3 Feature: The sense of wild"));
			}
			
			private void testFetchIdFromTitle()
			{
				System.out.println(Utility.fetchIdFromTitle("rocksteady-is-working-on-supposedly-a-superman-game-517c1668ed7eb00e4adb8c61"));
			}
						
			
		});
	}

}

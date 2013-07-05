package com.gamealoon.tests;



import java.util.HashMap;
import java.util.List;
import org.junit.Test;
import com.gamealoon.database.daos.ArticleDAO;
import com.gamealoon.database.daos.GameDAO;
import com.gamealoon.database.daos.UserDAO;
import com.gamealoon.models.Game;
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
				    findAllGames();
				    findAllUsers();				    
				    findAllRecentGames();
				    findAllRecentReleasedGames();	
				    testStringEncoder();
				    testFetchIdFromTitle();
				    testMapReduceTotalPageHitsCalc();
				
			}
			
			
			
			
			//Query test
			
			private void findAllGames()
			{
			 /* List<Game> games = gloonDatastore.find(Game.class).asList();
			  
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
			  }*/
			}
			
			private void findAllUsers()
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
							  System.out.println("Game score(out of 10): "+game.getScore());							  
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
							  System.out.println("Game score(out of 10): "+game.getScore());							  
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
			
			private void testMapReduceTotalPageHitsCalc()
			{				
				System.out.println("TOTAL PAGE HITS: "+articleDaoInstance.getTotalPageHits());
			}
			
		});
	}

}

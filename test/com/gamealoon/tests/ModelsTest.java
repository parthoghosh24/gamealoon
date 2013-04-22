package com.gamealoon.tests;



import java.util.List;
import org.junit.Test;
import com.gamealoon.database.GloonDAO;
import com.gamealoon.models.Game;
import com.gamealoon.models.User;
import com.google.code.morphia.Datastore;



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
				
					System.out.println("Fetching data.............");
				    findAllGames();
				    findAllUsers();				    
				    findAllRecentGames();
				    findAllRecentReleasedGames();				    
				
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
				List<User> sortedUsers = gloonDatastore.find(User.class).order("-totalScore").asList();
//				List<User> users = gloonDatastore.find(User.class).asList();
				  
				  if(sortedUsers.size()>0)
				  {
					  System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
					  for(User user: sortedUsers)
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
			
			
			private void findAllRecentGames()
			{
				List<Game> sortedGames = gloonDatastore.find(Game.class).order("-releaseDate").asList();
				
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
				List<Game> sortedGames = daoInstance.getRecentReleasedGames(gloonDatastore,5);
				
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
			
						
			
			
		});
	}

}

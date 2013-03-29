package com.gamealoon.tests;

import java.util.ArrayList;

import models.Article;
import models.Game;
import models.Platform;
import models.User;

import org.junit.Test;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;

import utility.GloonDAO;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;


/**
 * Testing class for Db and various Models/Entities of gamealoon
 * over here.
 * All Models and db related tests should be written over here
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
	public void testDb()
	{
		running(fakeApplication(),new Runnable() {
			
			@Override
			public void run() {
				
				GloonDAO daoInstance = GloonDAO.instantiateDAO();
				Morphia gloonMorphiaInstance = new Morphia();
				gloonMorphiaInstance.map(User.class).map(Article.class).map(Game.class).map(Platform.class);
				Datastore gloonDatastore = daoInstance.initDatastore(gloonMorphiaInstance);
								
				if(gloonDatastore.getCount(Platform.class)<1)
				{
					Platform playstation3 = new Platform();
					playstation3.setTitle("Playstation 3");
					playstation3.setDescription("The cell powered third generation beast");
					playstation3.setManufacturer("Sony Inc.");
					
					gloonDatastore.save(playstation3);
					
					ArrayList<Platform> platforms = new ArrayList<>();
					platforms.add(playstation3);
					
					
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
		
				}
				else
				{
					Game gameDetail = gloonDatastore.find(Game.class,"title","Max Payne 3").get();
					
					if(gameDetail!=null)
					{
						System.out.println("Game Title: "+gameDetail.getTitle());
						System.out.println("Game Description: "+gameDetail.getDescription());
						System.out.println("Game Developer: "+gameDetail.getDeveloper());
						System.out.println("Game Publisher: "+gameDetail.getPublisher());
						System.out.println("Game Price: "+gameDetail.getPrice());
						System.out.println("Game Rating: "+gameDetail.getRating());
						System.out.println("Game Release Date: "+gameDetail.getReleaseDate());
						System.out.println("Game Score(out of 10): "+gameDetail.getScore());
						System.out.println("Game Genere: "+gameDetail.getGenere());
						System.out.println("Game Platforms: "+gameDetail.getPlatforms());
					}
				}
						}
		});
	}

}

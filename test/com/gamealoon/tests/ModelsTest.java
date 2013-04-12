package com.gamealoon.tests;



import java.util.Date;
import java.util.HashMap;
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

				System.out.println("daoInstance:"+daoInstance);
				System.out.println("gloonDatastore: "+gloonDatastore); 
				if (gloonDatastore.getCount(Platform.class) < 1) {
					System.out.println("Data getting created.............");
					
					createPlatforms();					
					createGames();
					createAchievements();
					createUsers();
					createArticles();
					

				} else {
					System.out.println("Fetching data.............");
				    findAllGames();
				    findAllUsers();
				    getAllArticlesForCarousel();
				    findAllRecentGames();
				    findAllRecentReleasedGames();
				}
				
			}
			
			
			private void createAchievements()
			{
				
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
			}
			
			
			private void createUsers()
			{
				
				
				//user partho
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
				partho.setInsertTime(Utility.convertDateToString(new Date()));
				partho.setGameBio("I love gaming. Started my gaming adventure from 1999 and still going strong...");					
				
				
				
				Set<Achievement> parthoAchievements = new HashSet<>();
				parthoAchievements.add(gloonDatastore.find(Achievement.class, "title", "New Gloonie!").get());
				parthoAchievements.add(gloonDatastore.find(Achievement.class, "title", "Glooniac Writer!!!").get());
				parthoAchievements.add(gloonDatastore.find(Achievement.class, "title", "Gloony about games!").get());
				parthoAchievements.add(gloonDatastore.find(Achievement.class, "title", "Gloony about videos!").get());
				
				partho.setAchievements(parthoAchievements);
				
				
				
				partho.setAchievementsBasedScore(parthoAchievements.size()*10);
				partho.setVideoUploadBasedScore(50);
				partho.setUserFollowScore(10);
				partho.setArticleBasedScore(600);
				partho.setTotalScore(partho.getAchievementsBasedScore()+partho.getVideoUploadBasedScore()+partho.getUserFollowScore()+partho.getArticleBasedScore());

	
				//user swati
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
                swati.setInsertTime(Utility.convertDateToString(new Date()));
                swati.setGameBio("I love racing and puzzle games a lot. Hosted many gaming contests in my college days...");					                
                
                Set<Achievement> swatiAchievements = new HashSet<>();
                swatiAchievements.add(gloonDatastore.find(Achievement.class, "title", "New Gloonie!").get());
                swatiAchievements.add(gloonDatastore.find(Achievement.class, "title", "Gloony about videos!").get());
                swati.setAchievements(swatiAchievements);
				
                swati.setAchievementsBasedScore(swatiAchievements.size()*10);
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
				
				//user bhumika
				User bhumika = new User();
				bhumika.setEmail("bhumika.bhu@gmail.com");
				bhumika.setPassword("secret");
				bhumika.setFirstName("bhumika");
				bhumika.setMiddleName("");
				bhumika.setLastName("tiwari");
				bhumika.setUsername("chocobo87");
				bhumika.setDay(11);
				bhumika.setMonth(7);
				bhumika.setYear(1987);
				bhumika.setAvatarPath("myAvatarPath");
				bhumika.setInsertTime(Utility.convertDateToString(new Date()));
				bhumika.setGameBio("I am crazy about old school games like mario and tarzan boy. Recently in love with Temple run...");					                
                
                Set<Achievement> bhumikaAchievements = new HashSet<>();
                bhumikaAchievements.add(gloonDatastore.find(Achievement.class, "title", "New Gloonie!").get());
                bhumikaAchievements.add(gloonDatastore.find(Achievement.class, "title", "Gloony about games!").get());
                bhumikaAchievements.add(gloonDatastore.find(Achievement.class, "title", "Gloony about videos!").get());
                bhumika.setAchievements(bhumikaAchievements);
				
                bhumika.setAchievementsBasedScore(bhumikaAchievements.size()*10);
                bhumika.setVideoUploadBasedScore(50);   
                bhumika.setArticleBasedScore(250);                
                Set<User> bhumikaFollowers=new HashSet<User>();
                bhumikaFollowers.add(partho);
                bhumikaFollowers.add(swati);
                bhumika.setFollowedBy(bhumikaFollowers);
                bhumika.setUserFollowScore(bhumikaFollowers.size()*10);
                bhumika.setTotalScore(bhumika.getAchievementsBasedScore()+bhumika.getVideoUploadBasedScore()+bhumika.getUserFollowScore()+bhumika.getArticleBasedScore());
                gloonDatastore.save(bhumika);
               
              //user dada
                User dada = new User();
                dada.setEmail("prodipto.ghosh@gmail.com");
                dada.setPassword("secret");
                dada.setFirstName("prodipto");
                dada.setMiddleName("");
                dada.setLastName("ghosh");
                dada.setUsername("jaguarpaw80");
                dada.setDay(19);
                dada.setMonth(7);
				dada.setYear(1980);
				dada.setAvatarPath("myAvatarPath");
				dada.setInsertTime(Utility.convertDateToString(new Date()));
				dada.setGameBio("Gaming had been and still a very large part of my life. Love to play every kind of video game...");					                
                
                Set<Achievement> dadaAchievements = new HashSet<>();
                dadaAchievements.add(gloonDatastore.find(Achievement.class, "title", "New Gloonie!").get());
                dadaAchievements.add(gloonDatastore.find(Achievement.class, "title", "Video Glooniac!").get());
                dadaAchievements.add(gloonDatastore.find(Achievement.class, "title", "Glooniac Writer!!!").get());
                dadaAchievements.add(gloonDatastore.find(Achievement.class, "title", "Gloony about games!").get());
                dadaAchievements.add(gloonDatastore.find(Achievement.class, "title", "Gloony about videos!").get());
                dada.setAchievements(dadaAchievements);
				
                dada.setAchievementsBasedScore(dadaAchievements.size()*10);
                dada.setVideoUploadBasedScore(50);   
                dada.setArticleBasedScore(400);                
                Set<User> dadaFollowers=new HashSet<User>();
                dadaFollowers.add(partho);
                dadaFollowers.add(swati);
                dadaFollowers.add(bhumika);
                dada.setFollowedBy(dadaFollowers);
                dada.setUserFollowScore(dadaFollowers.size()*10);
                dada.setTotalScore(dada.getAchievementsBasedScore()+dada.getVideoUploadBasedScore()+dada.getUserFollowScore()+dada.getArticleBasedScore());
                gloonDatastore.save(dada);
                
                bhumikaFollowers.add(dada);
                bhumika.setFollowedBy(bhumikaFollowers);
                bhumika.setUserFollowScore(bhumika.getFollowedBy().size()*10);
                bhumika.setTotalScore(bhumika.getAchievementsBasedScore()+bhumika.getVideoUploadBasedScore()+bhumika.getUserFollowScore()+bhumika.getArticleBasedScore());
                gloonDatastore.save(bhumika);
                
                
              //user buni
                User buni = new User();
                buni.setEmail("karleo84@gmail.com");
                buni.setPassword("secret");
                buni.setFirstName("debashree");
                buni.setMiddleName("");
                buni.setLastName("ghosh");
                buni.setUsername("buno84");
                buni.setDay(13);
                buni.setMonth(8);
                buni.setYear(1984);
                buni.setAvatarPath("myAvatarPath");
                buni.setInsertTime(Utility.convertDateToString(new Date()));
                buni.setGameBio("I love mobile gaming. I go crazy and forget everything when i get an android or ios device in hand... Love infinite running games");					                
                
                Set<Achievement> buniAchievements = new HashSet<>();
                buniAchievements.add(gloonDatastore.find(Achievement.class, "title", "New Gloonie!").get());
                buniAchievements.add(gloonDatastore.find(Achievement.class, "title", "Gloony about games!").get());                                               
                buni.setAchievements(buniAchievements);
				
                buni.setAchievementsBasedScore(buniAchievements.size()*10);
                buni.setVideoUploadBasedScore(20);   
                buni.setArticleBasedScore(100);                
                Set<User> buniFollowers=new HashSet<User>();
                buniFollowers.add(partho);
                buniFollowers.add(swati);
                buniFollowers.add(bhumika);
                buniFollowers.add(dada);
                buni.setFollowedBy(buniFollowers);
                buni.setUserFollowScore(buniFollowers.size()*10);
                buni.setTotalScore(buni.getAchievementsBasedScore()+buni.getVideoUploadBasedScore()+buni.getUserFollowScore()+buni.getArticleBasedScore());
                gloonDatastore.save(buni);
                
                dadaFollowers.add(buni);
                dada.setFollowedBy(dadaFollowers);
                dada.setUserFollowScore(dada.getFollowedBy().size()*10);
                dada.setTotalScore(dada.getAchievementsBasedScore()+dada.getVideoUploadBasedScore()+dada.getUserFollowScore()+dada.getArticleBasedScore());
                gloonDatastore.save(dada);
                
                //User neo
                User neo = new User();
                neo.setEmail("neo.anderson@gmail.com");
                neo.setPassword("secret");
                neo.setFirstName("neo");
                neo.setMiddleName("");
                neo.setLastName("anderson");
                neo.setUsername("theone90");
                neo.setDay(21);
                neo.setMonth(8);
                neo.setYear(1990);
				neo.setAvatarPath("myAvatarPath");
				neo.setInsertTime(Utility.convertDateToString(new Date()));
				neo.setGameBio("Badly crazy about video games... ");					                
                
                Set<Achievement> neoAchievements = new HashSet<>();
                neoAchievements.add(gloonDatastore.find(Achievement.class, "title", "New Gloonie!").get());
                neoAchievements.add(gloonDatastore.find(Achievement.class, "title", "Video Glooniac!").get());
                neoAchievements.add(gloonDatastore.find(Achievement.class, "title", "Glooniac Writer!!!").get());
                neoAchievements.add(gloonDatastore.find(Achievement.class, "title", "Gloony about games!").get());
                neoAchievements.add(gloonDatastore.find(Achievement.class, "title", "Gloony about videos!").get());
                neo.setAchievements(neoAchievements);
				
                neo.setAchievementsBasedScore(neoAchievements.size()*10);
                neo.setVideoUploadBasedScore(100);   
                neo.setArticleBasedScore(450);                
                Set<User> neoFollowers=new HashSet<User>();
                neoFollowers.add(partho);
                neoFollowers.add(swati);
                neoFollowers.add(bhumika);
                neoFollowers.add(dada);
                neo.setFollowedBy(neoFollowers);
                neo.setUserFollowScore(neoFollowers.size()*10);
                neo.setTotalScore(neo.getAchievementsBasedScore()+neo.getVideoUploadBasedScore()+neo.getUserFollowScore()+neo.getArticleBasedScore());
                gloonDatastore.save(neo);
                
                
                //User brian
                User brian = new User();
                brian.setEmail("brian.mcfury@gmail.com");
                brian.setPassword("secret");
                brian.setFirstName("brian");
                brian.setMiddleName("");
                brian.setLastName("mcfury");
                brian.setUsername("brianzilla84");
                brian.setDay(5);
                brian.setMonth(1);
                brian.setYear(1984);
                brian.setAvatarPath("myAvatarPath");
				brian.setInsertTime(Utility.convertDateToString(new Date()));
				brian.setGameBio("Raised amongst nintendo, commodore 64, playstation...");					                
                
                Set<Achievement> brianAchievements = new HashSet<>();
                brianAchievements.add(gloonDatastore.find(Achievement.class, "title", "New Gloonie!").get());
                brianAchievements.add(gloonDatastore.find(Achievement.class, "title", "Video Glooniac!").get());                
                brianAchievements.add(gloonDatastore.find(Achievement.class, "title", "Gloony about games!").get());
                brianAchievements.add(gloonDatastore.find(Achievement.class, "title", "Gloony about videos!").get());
                brian.setAchievements(brianAchievements);
				
                brian.setAchievementsBasedScore(brianAchievements.size()*10);
                brian.setVideoUploadBasedScore(150);   
                brian.setArticleBasedScore(250);                
                Set<User> brianFollowers=new HashSet<User>();
                brianFollowers.add(partho);
                brianFollowers.add(swati);
                brianFollowers.add(bhumika);
                brianFollowers.add(neo);
                brian.setFollowedBy(brianFollowers);
                brian.setUserFollowScore(brianFollowers.size()*10);
                brian.setTotalScore(brian.getAchievementsBasedScore()+brian.getVideoUploadBasedScore()+brian.getUserFollowScore()+brian.getArticleBasedScore());
                gloonDatastore.save(brian);
                
                //User ken
                User ken = new User();
                ken.setEmail("ken.smith@gmail.com");
                ken.setPassword("secret");
                ken.setFirstName("ken");
                ken.setMiddleName("");
                ken.setLastName("smith");
                ken.setUsername("kenhaduken82");
                ken.setDay(5);
                ken.setMonth(6);
                ken.setYear(1982);
                ken.setAvatarPath("myAvatarPath");
                ken.setInsertTime(Utility.convertDateToString(new Date()));
                ken.setGameBio("Although my name resembles a very popular game character, but trust me this is not the reason I love Street Fighter. A hardcore gamer at heart.");					                
                
                Set<Achievement> kenAchievements = new HashSet<>();
                kenAchievements.add(gloonDatastore.find(Achievement.class, "title", "New Gloonie!").get());
                kenAchievements.add(gloonDatastore.find(Achievement.class, "title", "Video Glooniac!").get());
                kenAchievements.add(gloonDatastore.find(Achievement.class, "title", "Glooniac Writer!!!").get());
                kenAchievements.add(gloonDatastore.find(Achievement.class, "title", "Gloony about games!").get());
                kenAchievements.add(gloonDatastore.find(Achievement.class, "title", "Gloony about videos!").get());
                ken.setAchievements(kenAchievements);
				
                ken.setAchievementsBasedScore(kenAchievements.size()*10);
                ken.setVideoUploadBasedScore(250);   
                ken.setArticleBasedScore(800);                
                Set<User> kenFollowers=new HashSet<User>();
                kenFollowers.add(partho);
                kenFollowers.add(swati);
                kenFollowers.add(bhumika);
                kenFollowers.add(dada);
                kenFollowers.add(neo);
                kenFollowers.add(buni);
                kenFollowers.add(brian);
                ken.setFollowedBy(dadaFollowers);
                ken.setUserFollowScore(dadaFollowers.size()*10);
                ken.setTotalScore(ken.getAchievementsBasedScore()+ken.getVideoUploadBasedScore()+ken.getUserFollowScore()+ken.getArticleBasedScore());
                gloonDatastore.save(ken);
                
                
                //User anand
                User anand = new User();
                anand.setEmail("anand.srinivas@gmail.com");
                anand.setPassword("secret");
                anand.setFirstName("anand");
                anand.setMiddleName("");
                anand.setLastName("srinivas");
                anand.setUsername("anandcrazygamer89");
                anand.setDay(9);
                anand.setMonth(8);
                anand.setYear(1989);
                anand.setAvatarPath("myAvatarPath");
                anand.setInsertTime(Utility.convertDateToString(new Date()));
                anand.setGameBio("Had been always crazy about video games from my childhood. FIFA had been mine favorite series till now.");					                
                
                Set<Achievement> anandAchievements = new HashSet<>();
                anandAchievements.add(gloonDatastore.find(Achievement.class, "title", "New Gloonie!").get());
                anandAchievements.add(gloonDatastore.find(Achievement.class, "title", "Video Glooniac!").get());                
                anandAchievements.add(gloonDatastore.find(Achievement.class, "title", "Gloony about games!").get());                
                anand.setAchievements(anandAchievements);
				
                anand.setAchievementsBasedScore(anandAchievements.size()*10);
                anand.setVideoUploadBasedScore(100);   
                anand.setArticleBasedScore(200);                
                Set<User> anandFollowers=new HashSet<User>();
                anandFollowers.add(partho);
                anandFollowers.add(swati);
                anandFollowers.add(bhumika);
                anandFollowers.add(dada);
                anandFollowers.add(ken);
                anandFollowers.add(neo);
                anand.setFollowedBy(anandFollowers);
                anand.setUserFollowScore(dadaFollowers.size()*10);
                anand.setTotalScore(anand.getAchievementsBasedScore()+anand.getVideoUploadBasedScore()+anand.getUserFollowScore()+anand.getArticleBasedScore());
                gloonDatastore.save(anand);
                
                //User radha
                User radha = new User();
                radha.setEmail("radha.gupta@gmail.com");
                radha.setPassword("secret");
                radha.setFirstName("radha");
                radha.setMiddleName("");
                radha.setLastName("gupta");
                radha.setUsername("radhashooter95");
                radha.setDay(12);
                radha.setMonth(3);
                radha.setYear(1995);
                radha.setAvatarPath("myAvatarPath");
                radha.setInsertTime(Utility.convertDateToString(new Date()));
                radha.setGameBio("Proud to be a gamer. Won many awards in many gaming tournaments. Big fan of FPS and racing games.");					                
                
                Set<Achievement> radhaAchievements = new HashSet<>();
                radhaAchievements.add(gloonDatastore.find(Achievement.class, "title", "New Gloonie!").get());                               
                radhaAchievements.add(gloonDatastore.find(Achievement.class, "title", "Gloony about games!").get());
                radhaAchievements.add(gloonDatastore.find(Achievement.class, "title", "Gloony about videos!").get());
                radha.setAchievements(radhaAchievements);
				
                radha.setAchievementsBasedScore(radhaAchievements.size()*10);
                radha.setVideoUploadBasedScore(40);   
                radha.setArticleBasedScore(190);                
                Set<User> radhaFollowers=new HashSet<User>();
                radhaFollowers.add(partho);
                radhaFollowers.add(swati);
                radhaFollowers.add(bhumika);
                radhaFollowers.add(buni);
                radhaFollowers.add(neo);
                radhaFollowers.add(anand);
                radhaFollowers.add(brian);
                radhaFollowers.add(ken);
                radha.setFollowedBy(radhaFollowers);
                radha.setUserFollowScore(radhaFollowers.size()*10);
                radha.setTotalScore(radha.getAchievementsBasedScore()+radha.getVideoUploadBasedScore()+radha.getUserFollowScore()+radha.getArticleBasedScore());
                gloonDatastore.save(radha);
                
                //User tiffany
                User tiffany = new User();
                tiffany.setEmail("tiffany.parker@gmail.com");
                tiffany.setPassword("secret");
                tiffany.setFirstName("tiffany");
                tiffany.setMiddleName("");
                tiffany.setLastName("parker");
                tiffany.setUsername("tparkerponny98");
                tiffany.setDay(5);
                tiffany.setMonth(6);
                tiffany.setYear(1998);
                tiffany.setAvatarPath("myAvatarPath");
                tiffany.setInsertTime(Utility.convertDateToString(new Date()));
                tiffany.setGameBio("Love video gaming.");					                
                
                Set<Achievement> tiffanyAchievements = new HashSet<>();
                tiffanyAchievements.add(gloonDatastore.find(Achievement.class, "title", "New Gloonie!").get());                
                tiffanyAchievements.add(gloonDatastore.find(Achievement.class, "title", "Glooniac Writer!!!").get());
                tiffanyAchievements.add(gloonDatastore.find(Achievement.class, "title", "Gloony about games!").get());                
                tiffany.setAchievements(tiffanyAchievements);
				
                tiffany.setAchievementsBasedScore(tiffanyAchievements.size()*10);
                tiffany.setVideoUploadBasedScore(40);   
                tiffany.setArticleBasedScore(500);                
                Set<User> tiffanyFollowers=new HashSet<User>();
                tiffanyFollowers.add(partho);
                tiffanyFollowers.add(swati);
                tiffanyFollowers.add(bhumika);
                tiffanyFollowers.add(radha);
                tiffanyFollowers.add(buni);
                tiffanyFollowers.add(ken);
                tiffany.setFollowedBy(tiffanyFollowers);
                tiffany.setUserFollowScore(tiffanyFollowers.size()*10);
                tiffany.setTotalScore(tiffany.getAchievementsBasedScore()+tiffany.getVideoUploadBasedScore()+tiffany.getUserFollowScore()+tiffany.getArticleBasedScore());
                gloonDatastore.save(tiffany);
                
                //User xiang
                User xiang = new User();
                xiang.setEmail("xiang.hu@gmail.com");
                xiang.setPassword("secret");
                xiang.setFirstName("xiang");
                xiang.setMiddleName("");
                xiang.setLastName("hu");
                xiang.setUsername("xwarior81");
                xiang.setDay(2);
                xiang.setMonth(5);
                xiang.setYear(1981);
                xiang.setAvatarPath("myAvatarPath");
                xiang.setInsertTime(Utility.convertDateToString(new Date()));
                xiang.setGameBio("Love video games");					                
                
                Set<Achievement> xiangAchievements = new HashSet<>();
                xiangAchievements.add(gloonDatastore.find(Achievement.class, "title", "New Gloonie!").get());
                xiangAchievements.add(gloonDatastore.find(Achievement.class, "title", "Video Glooniac!").get());
                xiangAchievements.add(gloonDatastore.find(Achievement.class, "title", "Glooniac Writer!!!").get());
                xiangAchievements.add(gloonDatastore.find(Achievement.class, "title", "Gloony about games!").get());
                xiangAchievements.add(gloonDatastore.find(Achievement.class, "title", "Gloony about videos!").get());
                xiang.setAchievements(xiangAchievements);
				
                xiang.setAchievementsBasedScore(xiangAchievements.size()*10);
                xiang.setVideoUploadBasedScore(150);   
                xiang.setArticleBasedScore(650);                
                Set<User> xiangFollowers=new HashSet<User>();
                xiangFollowers.add(partho);
                xiangFollowers.add(swati);
                xiangFollowers.add(bhumika);
                xiangFollowers.add(dada);
                xiangFollowers.add(ken);
                xiangFollowers.add(brian);
                xiangFollowers.add(tiffany);
                xiangFollowers.add(neo);
                xiang.setFollowedBy(xiangFollowers);
                xiang.setUserFollowScore(xiangFollowers.size()*10);
                xiang.setTotalScore(xiang.getAchievementsBasedScore()+xiang.getVideoUploadBasedScore()+xiang.getUserFollowScore()+xiang.getArticleBasedScore());
                gloonDatastore.save(xiang);
                
                //User ted
                User ted = new User();
                ted.setEmail("ted.muchoo@gmail.com");
                ted.setPassword("secret");
                ted.setFirstName("ted");
                ted.setMiddleName("");
                ted.setLastName("muchoo");
                ted.setUsername("muchooomg90");
                ted.setDay(10);
                ted.setMonth(1);
				ted.setYear(1990);
				ted.setAvatarPath("myAvatarPath");
				ted.setInsertTime(Utility.convertDateToString(new Date()));
				ted.setGameBio("blah blah blah.... nuff said, lemme play video games now... bye");					                
                
                Set<Achievement> tedAchievements = new HashSet<>();
                tedAchievements.add(gloonDatastore.find(Achievement.class, "title", "New Gloonie!").get());
                tedAchievements.add(gloonDatastore.find(Achievement.class, "title", "Video Glooniac!").get());
                tedAchievements.add(gloonDatastore.find(Achievement.class, "title", "Glooniac Writer!!!").get());
                tedAchievements.add(gloonDatastore.find(Achievement.class, "title", "Gloony about games!").get());
                tedAchievements.add(gloonDatastore.find(Achievement.class, "title", "Gloony about videos!").get());
                ted.setAchievements(tedAchievements);
				
                ted.setAchievementsBasedScore(tedAchievements.size()*10);
                ted.setVideoUploadBasedScore(100);   
                ted.setArticleBasedScore(500);                
                Set<User> tedFollowers=new HashSet<User>();
                tedFollowers.add(partho);
                tedFollowers.add(swati);
                tedFollowers.add(bhumika);
                tedFollowers.add(dada);
                tedFollowers.add(neo);
                tedFollowers.add(tiffany);
                tedFollowers.add(brian);
                tedFollowers.add(anand);
                tedFollowers.add(xiang);
                ted.setFollowedBy(tedFollowers);
                ted.setUserFollowScore(tedFollowers.size()*10);
                ted.setTotalScore(ted.getAchievementsBasedScore()+ted.getVideoUploadBasedScore()+ted.getUserFollowScore()+ted.getArticleBasedScore());
                gloonDatastore.save(ted);
                
                //User john
                User john = new User();
                john.setEmail("john.doe@gmail.com");
                john.setPassword("secret");
                john.setFirstName("john");
                john.setMiddleName("");
                john.setLastName("doe");
                john.setUsername("rayray86");
                john.setDay(24);
                john.setMonth(3);
				john.setYear(1986);
				john.setAvatarPath("myAvatarPath");
				john.setInsertTime(Utility.convertDateToString(new Date()));
				john.setGameBio("Love doing pew pew...");					                
                
                Set<Achievement> johnAchievements = new HashSet<>();
                johnAchievements.add(gloonDatastore.find(Achievement.class, "title", "New Gloonie!").get());                               
                johnAchievements.add(gloonDatastore.find(Achievement.class, "title", "Gloony about games!").get());
                johnAchievements.add(gloonDatastore.find(Achievement.class, "title", "Gloony about videos!").get());
                john.setAchievements(johnAchievements);
				
                john.setAchievementsBasedScore(johnAchievements.size()*10);
                john.setVideoUploadBasedScore(200);   
                john.setArticleBasedScore(300);                
                Set<User> johnFollowers=new HashSet<User>();
                johnFollowers.add(partho);
                johnFollowers.add(swati);
                johnFollowers.add(ted);
                johnFollowers.add(dada);
                johnFollowers.add(xiang);
                johnFollowers.add(tiffany);
                johnFollowers.add(radha);
                johnFollowers.add(anand);
                john.setFollowedBy(johnFollowers);
                john.setUserFollowScore(johnFollowers.size()*10);
                john.setTotalScore(john.getAchievementsBasedScore()+john.getVideoUploadBasedScore()+john.getUserFollowScore()+john.getArticleBasedScore());
                gloonDatastore.save(john);
                
                //User jane
                User jane = new User();
                jane.setEmail("jane.doe@gmail.com");
                jane.setPassword("secret");
                jane.setFirstName("jane");
                jane.setMiddleName("");
                jane.setLastName("doe");
                jane.setUsername("pookie87");
                jane.setDay(7);
                jane.setMonth(3);
				jane.setYear(1987);
				jane.setAvatarPath("myAvatarPath");
				jane.setInsertTime(Utility.convertDateToString(new Date()));
				jane.setGameBio("I find gaming fun, exciting and beautiful");					                
                
                Set<Achievement> janeAchievements = new HashSet<>();
                janeAchievements.add(gloonDatastore.find(Achievement.class, "title", "New Gloonie!").get());                               
                janeAchievements.add(gloonDatastore.find(Achievement.class, "title", "Gloony about games!").get());
                janeAchievements.add(gloonDatastore.find(Achievement.class, "title", "Gloony about videos!").get());
                jane.setAchievements(janeAchievements);
				
                jane.setAchievementsBasedScore(janeAchievements.size()*10);
                jane.setVideoUploadBasedScore(30);   
                jane.setArticleBasedScore(120);                
                Set<User> janeFollowers=new HashSet<User>();
                janeFollowers.add(partho);
                janeFollowers.add(swati);
                janeFollowers.add(bhumika);
                janeFollowers.add(dada);
                janeFollowers.add(buni);
                janeFollowers.add(neo);
                jane.setFollowedBy(janeFollowers);
                jane.setUserFollowScore(janeFollowers.size()*10);
                jane.setTotalScore(jane.getAchievementsBasedScore()+jane.getVideoUploadBasedScore()+jane.getUserFollowScore()+jane.getArticleBasedScore());
                gloonDatastore.save(jane);
                
			}
			
			
			private void createPlatforms()
			{
				
				Platform playstation3 = new Platform();
				playstation3.setTitle("Playstation 3");
				playstation3.setDescription("The cell powered third generation beast by Sony");
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
				
				Platform ios = new Platform();
				ios.setTitle("IOS");
				ios.setDescription("The platform which runs on Iphone, IPAD and IPOD");
				ios.setManufacturer("Apple");
				gloonDatastore.save(ios);
				
				Platform android = new Platform();
				android.setTitle("Android");
				android.setDescription("Started by OHA but later on picked up and maintained by Google, it is the largest mobile OS today.");
				android.setManufacturer("Google");
				gloonDatastore.save(android);
				
				Platform wiiu = new Platform();
				wiiu.setTitle("WII-U");
				wiiu.setDescription("Follow-up to Wii, this console matches the graphics power of current generation consoles along with providing touch based controller.");
				wiiu.setManufacturer("Nintendo");
				gloonDatastore.save(wiiu);
				
				Platform n3ds = new Platform();
				n3ds.setTitle("3DS");
				n3ds.setDescription("Follow-up to DS handhalded device with 3d support and updated graphics");
				n3ds.setManufacturer("Nintendo");
				gloonDatastore.save(n3ds);
				
				Platform vita = new Platform();
				vita.setTitle("PS-VITA");
				vita.setDescription("Sony's follow-up to successful PSP");
				vita.setManufacturer("Sony");
				gloonDatastore.save(vita);

				
			}
			
			private void createGames()
			{
				                   
				Set<Platform> platforms = new HashSet<>();
				platforms.add(gloonDatastore.find(Platform.class, "title", "Playstation 3").get());
				platforms.add(gloonDatastore.find(Platform.class, "title", "Xbox 360").get());
				platforms.add(gloonDatastore.find(Platform.class, "title", "PC").get());

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
				maxPayne3.setScore(8.5);

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
				farCry3.setScore(9.0);
				
				gloonDatastore.save(farCry3);
			}
			
			private void createArticles()
			{				
				Set<Platform> platforms = new HashSet<>();
				platforms.add(gloonDatastore.find(Platform.class, "title", "Playstation 3").get());
				platforms.add(gloonDatastore.find(Platform.class, "title", "Xbox 360").get());
				platforms.add(gloonDatastore.find(Platform.class, "title", "PC").get());
				
				
				Article maxPayne3Review = new Article();
				maxPayne3Review.setTitle("max payne 3 review");
				User author = gloonDatastore.find(User.class, "username", "loonatic86").get();						
				maxPayne3Review.setAuthor(author);
				maxPayne3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
				maxPayne3Review.setCategory(Category.Review);
				maxPayne3Review.setCreationDate(Utility.convertDateToString(new Date()));
				Game maxPayne3 = gloonDatastore.find(Game.class, "title", "Max Payne 3").get();
				maxPayne3Review.setGame(maxPayne3);
				maxPayne3Review.setFeaturedImagePath("maxPayne3FeaturedImage");
				//maxPayne3Review.setInsertTime(Utility.convertDateToString(new Date()));
				maxPayne3Review.setInsertTime("2013-04-08 00:05:59");
				maxPayne3Review.setScore(0.56); //not game score, its article score			
				maxPayne3Review.setPlatforms(platforms);
				gloonDatastore.save(maxPayne3Review);
				
				
				Article farCry3Review = new Article();
				farCry3Review.setTitle("far cry 3 review");
				User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
				farCry3Review.setAuthor(jaguarpaw80Author);
				farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
				farCry3Review.setCategory(Category.Review);
				farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
				Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
				farCry3Review.setGame(farCry3);
				farCry3Review.setFeaturedImagePath("farcry3FeaturedImage");
				//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
				farCry3Review.setInsertTime("2013-04-11 09:00:00");
				farCry3Review.setScore(0.78);
				farCry3Review.setPlatforms(platforms);
				gloonDatastore.save(farCry3Review);
					
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
				List<User> sortedUsers = daoInstance.getTopUsers(gloonDatastore);
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
				List<Game> sortedGames = daoInstance.getRecentGames(gloonDatastore);
				
				if(sortedGames.size()>0)
				{
					System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
					  for(Game game: sortedGames)
					  {
						  if(game!=null)
						  {
							  System.out.println("Game Title: "+game.getTitle());
							  System.out.println("Game Release Date: "+game.getReleaseDate());
							  System.out.println("Game score(out of 10): "+game.getScore());							  
							  System.out.println("--------------->><<----------------------");
						  }
					  }
					  System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				}	
			}
			
			
			private void findAllRecentReleasedGames()
			{
				List<Game> sortedGames = daoInstance.getRecentReleasedGames(gloonDatastore);
				
				System.out.println("sorted games list size: "+ sortedGames.size());
				if(sortedGames.size()>0)
				{
					System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
					  for(Game game: sortedGames)
					  {
						  if(game!=null)
						  {
							  System.out.println("Game Title: "+game.getTitle());
							  System.out.println("Game Release Date: "+game.getReleaseDate());
							  System.out.println("Game score(out of 10): "+game.getScore());							  
							  System.out.println("--------------->><<----------------------");
						  }
					  }
					  System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				}	
			}
			private void getAllArticlesForCarousel()
		    {
				List<HashMap<String, Object>> allArticles = daoInstance.getAllArticlesForCarousel(gloonDatastore, "all");
				System.out.println(allArticles);
		    }
			
			
		});
	}

}



import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.gamealoon.database.GloonDAO;
import com.gamealoon.models.Achievement;
import com.gamealoon.models.Article;
import com.gamealoon.models.Category;
import com.gamealoon.models.Game;
import com.gamealoon.models.Platform;
import com.gamealoon.models.User;
import com.gamealoon.utility.Utility;
import com.google.code.morphia.Datastore;

import play.Application;
import play.GlobalSettings;

public class GloonGlobal extends GlobalSettings {

	final GloonDAO daoInstance = GloonDAO.instantiateDAO();	
	final Datastore gloonDatastore = daoInstance.initDatastore();
	
	 @Override
	public void onStart(Application app) {
		 if (gloonDatastore.getCount(User.class) < 1) {
				System.out.println("Data getting created.............");
				
				createAchievements();
				createUsers();
				createPlatforms();					
				createGames();							
				createArticles();
				

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
			maxPayne3.setReleaseDate("2012-05-15");
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
			
			Game guacamelee = new Game();
			guacamelee.setTitle("Guacamelee!");
			guacamelee.setDescription("Guacamelee! is a Metroid-vania style action-platformer set in a magical Mexican inspired world. The game draws its inspiration from traditional Mexican culture and folklore, and features many interesting and unique characters.");
			guacamelee.setDeveloper("Drinkbox Studios");
			guacamelee.setPublisher("Drinkbox Studios");
			guacamelee.setGenere("Action");
			guacamelee.setPrice("12$");
			Set<Platform> guacameleePlatforms = new HashSet<>();
			guacameleePlatforms.add(gloonDatastore.find(Platform.class, "title", "Playstation 3").get());
			guacameleePlatforms.add(gloonDatastore.find(Platform.class, "title", "Xbox 360").get());
			guacamelee.setPlatforms(guacameleePlatforms);
			guacamelee.setRating("Everyone");
			guacamelee.setReleaseDate("2013-04-09");
			guacamelee.setScore(9.1);
			
			gloonDatastore.save(guacamelee);
			
			Game bioshockInfinite = new Game();
			bioshockInfinite.setTitle("Bioshock Infinite");
			bioshockInfinite.setDescription("Originally conceived as a floating symbol of American ideals at a time when the United States was emerging as a world power, Columbia is dispatched to distant shores with great fanfare by a captivated public. What begins as a brand new endeavor of hope turns drastically wrong as the city soon disappears into the clouds to whereabouts unknown.");
			bioshockInfinite.setDeveloper("Ubisoft Montreal");
			bioshockInfinite.setPublisher("Ubisoft");
			bioshockInfinite.setGenere("First Person Shooter");
			bioshockInfinite.setPrice("60$");			
			bioshockInfinite.setPlatforms(platforms);
			bioshockInfinite.setRating("Mature");
			bioshockInfinite.setReleaseDate("2013-03-26");
			bioshockInfinite.setScore(9.2);
			
			gloonDatastore.save(bioshockInfinite);
			
			
			Game halo4 = new Game();
			halo4.setTitle("Halo4");
			halo4.setDescription("The Master Chief returns to battle an ancient evil bent on vengeance and annihilation. Shipwrecked on a mysterious world, faced with new enemies and deadly technology, the universe will never be the same. ");
			halo4.setDeveloper("343 Industries");
			halo4.setPublisher("Microsoft Game Studios");
			halo4.setGenere("First Person Shooter");
			halo4.setPrice("60$");
			Set<Platform> halo4Platforms = new HashSet<>();
			halo4Platforms.add(gloonDatastore.find(Platform.class, "title", "Xbox 360").get());
			halo4.setPlatforms(halo4Platforms);
			halo4.setRating("Mature");
			halo4.setReleaseDate("2012-11-06");
			halo4.setScore(8.8);
			
			gloonDatastore.save(halo4);
			
			Game defiance = new Game();
			defiance.setTitle("Defiance");
			defiance.setDescription("Defiance is the first multi-platform shooter MMO which, in a ground-breaking entertainment experience, interconnects with a global television program by Syfy, cable's premier imagination-based entertainment channel.");
			defiance.setDeveloper("Trion Worlds");
			defiance.setPublisher("Trion Worlds");
			defiance.setGenere("MMO");
			defiance.setPrice("60$");
			defiance.setPlatforms(platforms);
			defiance.setRating("Mature");
			defiance.setReleaseDate("2013-04-02");
			defiance.setScore(6.1);
			
			gloonDatastore.save(defiance);
			
			Game terraria= new Game();
			terraria.setTitle("Terraria");
			terraria.setDescription("Terraria? What's that? Terraria is a land of adventure! A land of mystery! A land that's yours to shape, to defend and to enjoy. Your options in Terraria are limitless, are you an action gamer with an itchy trigger finger? A master builder? A collector? An explorer? There's something for everyone here. Dig, fight, explore, build! Nothing is impossible in this action-packed adventure game.");
			terraria.setDeveloper("Re-Logic, Engine Software");
			terraria.setPublisher("505 Games");
			terraria.setGenere("Action");
			terraria.setPrice("10$");
			terraria.setPlatforms(platforms);
			terraria.setRating("Teen");
			terraria.setReleaseDate("2013-03-27");
			terraria.setScore(9.0);
			
			gloonDatastore.save(terraria);
			
			Game batmanArkhamCity = new Game();
			batmanArkhamCity.setTitle("Batman Arkham City");
			batmanArkhamCity.setDescription("With Far Cry 3, players step into the shoes of Jason Brody, a man alone at the edge of the world, stranded on a mysterious tropical island. In this savage paradise where lawlessness and violence are the only sure thing, players dictate how the story unfolds, from the battles they choose to fight to the allies or enemies they make along the way. As Jason Brody, players will slash, sneak, detonate and shoot their way across the island in a world that has lost all sense of right and wrong.");
			batmanArkhamCity.setDeveloper("Warner Bros. Interactive, Eidos Interactive");
			batmanArkhamCity.setPublisher(" Rocksteady Studios");
			batmanArkhamCity.setGenere("Action");
			batmanArkhamCity.setPrice("60$");
			batmanArkhamCity.setPlatforms(platforms);
			batmanArkhamCity.setRating("Teen");
			batmanArkhamCity.setReleaseDate("2012-09-07");
			batmanArkhamCity.setScore(9.6);
			
			gloonDatastore.save(batmanArkhamCity);
			
			Game motocrossMadness = new Game();
			motocrossMadness.setTitle("Motocross Madness");
			motocrossMadness.setDescription("Motocross Madness is back! Take your bike freeriding across massive environments, from the deserts of Egypt to the snows of Iceland. Drift, trick and turbo boost your way across 9 expansive offroad tracks in single player events, ghost challenges and 8-player multiplayer races.");
			motocrossMadness.setDeveloper("Bongfish");
			motocrossMadness.setPublisher("Microsoft Game Studios");
			motocrossMadness.setGenere("Racing");
			motocrossMadness.setPrice("11$");
			Set<Platform> motocrossMadnessPlatforms = new HashSet<>();
			motocrossMadnessPlatforms.add(gloonDatastore.find(Platform.class, "title", "Xbox 360").get());
			motocrossMadness.setPlatforms(motocrossMadnessPlatforms);
			motocrossMadness.setRating("Everyone");
			motocrossMadness.setReleaseDate("2013-04-10");
			motocrossMadness.setScore(7.2);
			
			gloonDatastore.save(motocrossMadness);
			
			Game ageOfWushu = new Game();
			ageOfWushu.setTitle("Age of Wushu");
			ageOfWushu.setDescription("Endorsed by martial artist legend and wushu champion Jet-Li, Age of Wushu is the world's first true Wuxia themed MMORPG, with cutting edge graphics and innovative gameplay. Players must journey to master the ancient Chinese art of Wushu, delving into their character's unique story and facing the consequences of every action without the crutch of MMO staples like levels and classes.");
			ageOfWushu.setDeveloper("Suzhou Snail Electronics co. ltd");
			ageOfWushu.setPublisher("Snail Games");
			ageOfWushu.setGenere("MMO");
			ageOfWushu.setPrice("60$");
			ageOfWushu.setPlatforms(platforms);
			ageOfWushu.setRating("Mature");
			ageOfWushu.setReleaseDate("2013-04-10");
			ageOfWushu.setScore(7.3);
			
			gloonDatastore.save(ageOfWushu);
			
			Game splinterCellBlacklist = new Game();
			splinterCellBlacklist.setTitle("Tom Clancy's Splinter Cell: Blacklist");
			splinterCellBlacklist.setDescription("The United States has a military presence in two thirds of countries around the world. A group of rogue nations have had enough and initiate a terror ultimatum called the Blacklist - a deadly countdown of escalating terrorist attacks on U.S. interests. Sam Fisher is the leader of the newly formed 4th Echelon unit: a clandestine unit that answers solely to the President of the United States.");
			splinterCellBlacklist.setDeveloper("Ubisoft Toronto");
			splinterCellBlacklist.setPublisher("Ubisoft");
			splinterCellBlacklist.setGenere("Action");
			splinterCellBlacklist.setPrice("60$");
			splinterCellBlacklist.setPlatforms(platforms);
			splinterCellBlacklist.setRating("Rating Pending");
			splinterCellBlacklist.setReleaseDate("2013-08-20");			
			
			gloonDatastore.save(splinterCellBlacklist);
			
			Game gta5 = new Game();
			gta5.setTitle("Grand Theft Auto V");
			gta5.setDescription("Like GTA: San Andreas before it, GTA V will be set in and around the LA-inspired city of Los Santos.");
			gta5.setDeveloper("Rockstar North");
			gta5.setPublisher("Rockstar Games");
			gta5.setGenere("Action");
			gta5.setPrice("60$");
			gta5.setPlatforms(platforms);
			gta5.setRating("Mature");
			gta5.setReleaseDate("2013-09-17");			
			
			gloonDatastore.save(gta5);
			
			Game mgsRising = new Game();
			mgsRising.setTitle("Metal Gear Rising: Revengence");
			mgsRising.setDescription("METAL GEAR RISING: REVENGEANCE takes the renowned METAL GEAR franchise into exciting new territory by focusing on delivering an all-new action experience unlike anything that has come before. Combining world-class development teams at Kojima Productions and PlatinumGames, METAL GEAR RISING: REVENGEANCE brings two of the world's most respected teams together with a common goal of providing players with a fresh synergetic experience that combines the best elements of pure action and epic storytelling, all within the expansive MG universe");
			mgsRising.setDeveloper("Platinum Games");
			mgsRising.setPublisher("Kojima Productions");
			mgsRising.setGenere("Action");
			mgsRising.setPrice("60$");
			mgsRising.setPlatforms(platforms);
			mgsRising.setRating("Mature");
			mgsRising.setScore(8.1);
			mgsRising.setReleaseDate("2013-02-19");			
			
			gloonDatastore.save(mgsRising);
			
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
			maxPayne3Review.setCreationDate("2012-05-16 22:05:59");
			Game maxPayne3 = gloonDatastore.find(Game.class, "title", "Max Payne 3").get();
			maxPayne3Review.setGame(maxPayne3);
			maxPayne3Review.setFeaturedImagePath("articleFeaturedImage");
			//maxPayne3Review.setInsertTime(Utility.convertDateToString(new Date()));
			maxPayne3Review.setInsertTime("2012-05-16 22:05:59");
			maxPayne3Review.setScore(0.56); //not game score, its article score			
			maxPayne3Review.setPlatforms(platforms);
			gloonDatastore.save(maxPayne3Review);
			
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("The third game in the excellent series excels in every manner. Solid entry in Far Cry series.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate("2012-12-15 09:30:00");
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2012-12-15 09:30:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			Article guacameleeReview1 = new Article();
			guacameleeReview1.setTitle("Guacamelee! review");						
			guacameleeReview1.setAuthor(gloonDatastore.find(User.class, "username", "jaguarpaw80").get());
			guacameleeReview1.setBody("Guacamelee is an awseome metroid fueled mexican wave ride.");
			guacameleeReview1.setCategory(Category.Review);
			guacameleeReview1.setCreationDate("2013-04-10 09:00:00");			
			guacameleeReview1.setGame(gloonDatastore.find(Game.class, "title", "Guacamelee!").get());
			guacameleeReview1.setFeaturedImagePath("articleFeaturedImage");
			//guacameleeReview.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			guacameleeReview1.setInsertTime("2013-04-10 09:00:00");
			guacameleeReview1.setScore(0.80);
			guacameleeReview1.setPlatforms(platforms);
			gloonDatastore.save(guacameleeReview1);
			
			Article guacameleeReview2 = new Article();
			guacameleeReview2.setTitle("Guacamelee! review");						
			guacameleeReview2.setAuthor(gloonDatastore.find(User.class, "username", "kenhaduken82").get());
			guacameleeReview2.setBody("Guacamelee is mexican luchador beauty.");
			guacameleeReview2.setCategory(Category.Review);
			guacameleeReview2.setCreationDate("2013-04-10 10:00:00");			
			guacameleeReview2.setGame(gloonDatastore.find(Game.class, "title", "Guacamelee!").get());
			guacameleeReview2.setFeaturedImagePath("articleFeaturedImage");
			//guacameleeReview.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			guacameleeReview2.setInsertTime("2013-04-10 10:00:00");
			guacameleeReview2.setScore(0.90);
			guacameleeReview2.setPlatforms(platforms);
			gloonDatastore.save(guacameleeReview2);
			
			Article guacameleeReview3 = new Article();
			guacameleeReview3.setTitle("Guacamelee! review");						
			guacameleeReview3.setAuthor(gloonDatastore.find(User.class, "username", "loonatic86").get());
			guacameleeReview3.setBody("Being a mexican chicken is so fun.");
			guacameleeReview3.setCategory(Category.Review);
			guacameleeReview3.setCreationDate("2013-04-10 11:00:00");			
			guacameleeReview3.setGame(gloonDatastore.find(Game.class, "title", "Guacamelee!").get());
			guacameleeReview3.setFeaturedImagePath("articleFeaturedImage");
			//guacameleeReview.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			guacameleeReview3.setInsertTime("2013-04-10 11:00:00");
			guacameleeReview3.setScore(0.91);
			guacameleeReview3.setPlatforms(platforms);
			gloonDatastore.save(guacameleeReview3);
			
			Article ageOfWushuReview1 = new Article();
			ageOfWushuReview1.setTitle("Age of Wushu review");								
			ageOfWushuReview1.setAuthor(gloonDatastore.find(User.class, "username", "loonatic86").get());
			ageOfWushuReview1.setBody("Age of Wushu lets you enjoy the world of Martial Arts in its purest form.");
			ageOfWushuReview1.setCategory(Category.Review);
			ageOfWushuReview1.setCreationDate("2013-04-11 09:00:00");			
			ageOfWushuReview1.setGame(gloonDatastore.find(Game.class, "title", "Age of Wushu").get());
			ageOfWushuReview1.setFeaturedImagePath("articleFeaturedImage");
			//ageOfWushuReview.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			ageOfWushuReview1.setInsertTime("2013-04-11 09:00:00");
			ageOfWushuReview1.setScore(0.80);
			ageOfWushuReview1.setPlatforms(platforms);
			gloonDatastore.save(ageOfWushuReview1);
			
			Article ageOfWushuReview2 = new Article();
			ageOfWushuReview2.setTitle("Age of Wushu review");								
			ageOfWushuReview2.setAuthor(gloonDatastore.find(User.class, "username", "xwarior81").get());
			ageOfWushuReview2.setBody("If you are fan of sugar hardcore martial arts movie, you will find your salvation in Age of Wushu.");
			ageOfWushuReview2.setCategory(Category.Review);
			ageOfWushuReview2.setCreationDate("2013-04-11 12:20:00");			
			ageOfWushuReview2.setGame(gloonDatastore.find(Game.class, "title", "Age of Wushu").get());
			ageOfWushuReview2.setFeaturedImagePath("articleFeaturedImage");
			//ageOfWushuReview.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			ageOfWushuReview2.setInsertTime("2013-04-11 12:20:00");
			ageOfWushuReview2.setScore(0.85);
			ageOfWushuReview2.setPlatforms(platforms);
			gloonDatastore.save(ageOfWushuReview2);
			
			Article motocrossMadnessReview1 = new Article();
			motocrossMadnessReview1.setTitle("Motocross Madness review");							
			motocrossMadnessReview1.setAuthor(gloonDatastore.find(User.class, "username", "muchooomg90").get());
			motocrossMadnessReview1.setBody("This Motocross Madness is medicore at its best.");
			motocrossMadnessReview1.setCategory(Category.Review);
			motocrossMadnessReview1.setCreationDate("2013-04-11 00:30:00");			
			motocrossMadnessReview1.setGame(gloonDatastore.find(Game.class, "title", "Motocross Madness").get());
			motocrossMadnessReview1.setFeaturedImagePath("articleFeaturedImage");
			//motocrossMadnessReview1.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			motocrossMadnessReview1.setInsertTime("2013-04-11 00:30:00");
			motocrossMadnessReview1.setScore(0.78);
			motocrossMadnessReview1.setPlatforms(platforms);
			gloonDatastore.save(motocrossMadnessReview1);
			
			Article motocrossMadnessReview2 = new Article();
			motocrossMadnessReview2.setTitle("Motocross Madness review");							
			motocrossMadnessReview2.setAuthor(gloonDatastore.find(User.class, "username", "kenhaduken82").get());
			motocrossMadnessReview2.setBody("Common Microsoft, stop serving us enough garbage now!");
			motocrossMadnessReview2.setCategory(Category.Review);
			motocrossMadnessReview2.setCreationDate("2013-04-11 09:30:00");			
			motocrossMadnessReview2.setGame(gloonDatastore.find(Game.class, "title", "Motocross Madness").get());
			motocrossMadnessReview2.setFeaturedImagePath("articleFeaturedImage");
			//motocrossMadnessReview2.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			motocrossMadnessReview2.setInsertTime("2013-04-11 09:30:00");
			motocrossMadnessReview2.setScore(0.70);
			
			Set<Platform> motocrossPlatforms = new HashSet<>();
			motocrossPlatforms.add(gloonDatastore.find(Platform.class, "title", "Xbox 360").get());
			motocrossMadnessReview2.setPlatforms(motocrossPlatforms);
			gloonDatastore.save(motocrossMadnessReview2);
			
			Article terrariaReview1 = new Article();
			terrariaReview1.setTitle("Terraria review");								
			terrariaReview1.setAuthor(gloonDatastore.find(User.class, "username", "loonatic86").get());
			terrariaReview1.setBody("A game which lets you do EVERYTHING!!!");
			terrariaReview1.setCategory(Category.Review);
			terrariaReview1.setCreationDate("2013-03-28 09:00:00");			
			terrariaReview1.setGame(gloonDatastore.find(Game.class, "title", "Terraria").get());
			terrariaReview1.setFeaturedImagePath("articleFeaturedImage");
			//terraria.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			terrariaReview1.setInsertTime("2013-03-28 09:00:00");
			terrariaReview1.setScore(0.78);
			terrariaReview1.setPlatforms(platforms);
			gloonDatastore.save(terrariaReview1);
			
			Article terrariaReview2= new Article();
			terrariaReview2.setTitle("Terraria review");								
			terrariaReview2.setAuthor(gloonDatastore.find(User.class, "username", "theone90").get());
			terrariaReview2.setBody("Its an action game, a rpg, an exploration game. In short, play it as any way possible. Its awesome in short. ");
			terrariaReview2.setCategory(Category.Review);
			terrariaReview2.setCreationDate("2013-03-28 10:00:00");			
			terrariaReview2.setGame(gloonDatastore.find(Game.class, "title", "Terraria").get());
			terrariaReview2.setFeaturedImagePath("articleFeaturedImage");
			//terraria.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			terrariaReview2.setInsertTime("2013-03-28 10:00:00");
			terrariaReview2.setScore(0.90);
			terrariaReview2.setPlatforms(platforms);
			gloonDatastore.save(terrariaReview2);
			
			Article terrariaReview3= new Article();
			terrariaReview3.setTitle("Terraria review");								
			terrariaReview3.setAuthor(gloonDatastore.find(User.class, "username", "kenhaduken82").get());
			terrariaReview3.setBody("This game is fun and take you back into 90s bomberman and load runner era... ");
			terrariaReview3.setCategory(Category.Review);
			terrariaReview3.setCreationDate("2013-03-28 08:00:00");			
			terrariaReview3.setGame(gloonDatastore.find(Game.class, "title", "Terraria").get());
			terrariaReview3.setFeaturedImagePath("articleFeaturedImage");
			//terraria.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			terrariaReview3.setInsertTime("2013-03-28 08:00:00");
			terrariaReview3.setScore(0.87);
			terrariaReview3.setPlatforms(platforms);
			gloonDatastore.save(terrariaReview3);
			
			Article defianceReview1 = new Article();
			defianceReview1.setTitle("Defiance review");								
			defianceReview1.setAuthor(gloonDatastore.find(User.class, "username", "xwarior81").get());
			defianceReview1.setBody("Seriously!!! What the heck was that?");
			defianceReview1.setCategory(Category.Review);
			defianceReview1.setCreationDate("2013-04-03 00:30:00");			
			defianceReview1.setGame(gloonDatastore.find(Game.class, "title", "Defiance").get());
			defianceReview1.setFeaturedImagePath("articleFeaturedImage");
			//defianceReview1.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			defianceReview1.setInsertTime("2013-04-03 00:30:00");
			defianceReview1.setScore(0.95);
			defianceReview1.setPlatforms(platforms);
			gloonDatastore.save(defianceReview1);
			
			Article defianceReview2 = new Article();
			defianceReview2.setTitle("Defiance review");								
			defianceReview2.setAuthor(gloonDatastore.find(User.class, "username", "loonatic86").get());
			defianceReview2.setBody("Concept was great on paper but the execution was not just right.");
			defianceReview2.setCategory(Category.Review);
			defianceReview2.setCreationDate("2013-04-03 01:30:00");			
			defianceReview2.setGame(gloonDatastore.find(Game.class, "title", "Defiance").get());
			defianceReview2.setFeaturedImagePath("articleFeaturedImage");
			//defianceReview2.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			defianceReview2.setInsertTime("2013-04-03 01:30:00");
			defianceReview2.setScore(0.90);
			defianceReview2.setPlatforms(platforms);
			gloonDatastore.save(defianceReview2);
			
			Article defianceReview3 = new Article();
			defianceReview3.setTitle("Defiance review");								
			defianceReview3.setAuthor(gloonDatastore.find(User.class, "username", "muchooomg90").get());
			defianceReview3.setBody("Defiance just doesnt work. Period.");
			defianceReview3.setCategory(Category.Review);
			defianceReview3.setCreationDate("2013-04-03 01:30:00");			
			defianceReview3.setGame(gloonDatastore.find(Game.class, "title", "Defiance").get());
			defianceReview3.setFeaturedImagePath("articleFeaturedImage");
			//defianceReview3.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			defianceReview3.setInsertTime("2013-04-03 01:30:00");
			defianceReview3.setScore(0.91);
			defianceReview3.setPlatforms(platforms);
			gloonDatastore.save(defianceReview3);
			
			Article maxPayne3Feature = new Article();
			maxPayne3Feature.setTitle("Max Payne 3 Feature: The pain of bullet time 3.0");								
			maxPayne3Feature.setAuthor(gloonDatastore.find(User.class, "username", "loonatic86").get());
			maxPayne3Feature.setBody("Bullet time 3.0 felt kind of fresh even though bullet time used in games like anything. The new bullet time in Max Payne 3 enhance the poetic nature of Max thanks to rage engine and Euphoria.");
			maxPayne3Feature.setCategory(Category.Feature);
			maxPayne3Feature.setCreationDate("2013-04-13 11:05:00");			
			maxPayne3Feature.setGame(gloonDatastore.find(Game.class, "title", "Max Payne 3").get());
			maxPayne3Feature.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			maxPayne3Feature.setInsertTime("2013-04-13 11:05:00");
			maxPayne3Feature.setScore(0.80);
			maxPayne3Feature.setPlatforms(platforms);
			gloonDatastore.save(maxPayne3Feature);
			
			Article farCry3Feature = new Article();
			farCry3Feature.setTitle("Far Cry 3 Feature: The sense of wild");								
			farCry3Feature.setAuthor(gloonDatastore.find(User.class, "username", "loonatic86").get());
			farCry3Feature.setBody("Wild was really alive in the game. IMHO, this is the second game after Metal Gear Solid: Snake Eater in which I stay alerted while playing the game. Just that this game is more open and random in nature. Danger is lurking everywhere.");
			farCry3Feature.setCategory(Category.Feature);
			farCry3Feature.setCreationDate("2013-04-11 15:00:00");			
			farCry3Feature.setGame(gloonDatastore.find(Game.class, "title", "Far Cry 3").get());
			farCry3Feature.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Feature.setInsertTime("2013-04-11 15:00:00");
			farCry3Feature.setScore(0.78);
			farCry3Feature.setPlatforms(platforms);
			gloonDatastore.save(farCry3Feature);
			
			Article mgsRisingFeature = new Article();
			mgsRisingFeature.setTitle("MGS Rising Feature: The cut mechanics");							
			mgsRisingFeature.setAuthor(gloonDatastore.find(User.class, "username", "kenhaduken82").get());
			mgsRisingFeature.setBody("Atleast, Konami made sure that we get that same cut experience which they demoed in E3 2011. I was overwhelmed by it.");
			mgsRisingFeature.setCategory(Category.Feature);
			mgsRisingFeature.setCreationDate("2013-03-29 15:45:00");			
			mgsRisingFeature.setGame(gloonDatastore.find(Game.class, "title", "Metal Gear Rising: Revengence").get());
			mgsRisingFeature.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			mgsRisingFeature.setInsertTime("2013-03-29 15:45:00");
			mgsRisingFeature.setScore(0.78);
			mgsRisingFeature.setPlatforms(platforms);
			gloonDatastore.save(mgsRisingFeature);
			
			/*Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = gloonDatastore.find(User.class, "username", "jaguarpaw80").get();						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);*/
				
		}
}

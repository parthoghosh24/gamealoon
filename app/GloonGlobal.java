import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import com.gamealoon.algorithm.SecurePassword;
import com.gamealoon.database.daos.AchievementDAO;
import com.gamealoon.database.daos.ActivityDAO;
import com.gamealoon.database.daos.ArticleDAO;
import com.gamealoon.database.daos.GameDAO;
import com.gamealoon.database.daos.PlatformDAO;
import com.gamealoon.database.daos.UserDAO;
//import com.gamealoon.database.daos.UserGameScoreMapDAO;
import com.gamealoon.models.Achievement;
import com.gamealoon.models.Activity;
//import com.gamealoon.models.Article;
//import com.gamealoon.models.Category;
import com.gamealoon.models.Game;
import com.gamealoon.models.Platform;
import com.gamealoon.models.User;
import com.gamealoon.utility.AppConstants;
import com.gamealoon.utility.Utility;
import play.Application;
import play.GlobalSettings;

public class GloonGlobal extends GlobalSettings {

	final AchievementDAO achievementDAOInstance = AchievementDAO.instantiateDAO();
	final ArticleDAO articleDAOInstance = ArticleDAO.instantiateDAO();
	final GameDAO gameDAOInstance = GameDAO.instantiateDAO();
	final UserDAO userDAOInstance = UserDAO.instantiateDAO();
	final PlatformDAO platformDAOInstance = PlatformDAO.instantiateDAO();
	final ActivityDAO activityDAOInstance = ActivityDAO.instantiateDAO();
	
	
	 @Override
	public void onStart(Application app) {
		 if (userDAOInstance.count() < 1) {
				System.out.println("Data getting created.............");
				
				createAchievements();
				/*try {
					createUsers();
				} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				createPlatforms();					
				createGames();							
//				try {
//					createArticles();
//				} catch (ParseException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				

			}
	}


	private void createAchievements()
		{
			
			Achievement newGloonie = new Achievement();
			newGloonie.setTitle("New Gloonie!");
			newGloonie.setDescription("Welcome to gamealoon!!!");
			newGloonie.setImagePath("newGloonieImagePath");
			achievementDAOInstance.save(newGloonie);
			
			Achievement videoGlooniac= new Achievement();
			videoGlooniac.setTitle("Video Glooniac!");
			videoGlooniac.setDescription("Uploaded 500 videos!!!");
			videoGlooniac.setImagePath("videoGlooniacPath");
			achievementDAOInstance.save(videoGlooniac);
			
			Achievement  glooniacWriter = new Achievement();
			glooniacWriter.setTitle("Glooniac Writer!!!");
			glooniacWriter.setDescription("Wrote 1000 articles in total!!!");
			glooniacWriter.setImagePath("glooniacWriterPath");
			achievementDAOInstance.save(glooniacWriter);
			
			Achievement gloonyAboutgames = new Achievement();
			gloonyAboutgames.setTitle("Gloony about games!");
			gloonyAboutgames.setDescription("Played 1000 times in 1 week!!!");
			gloonyAboutgames.setImagePath("gloonyAboutGamesPath");
			achievementDAOInstance.save(gloonyAboutgames);
			
			Achievement gloonyAboutVideos = new Achievement();
			gloonyAboutVideos.setTitle("Gloony about videos!");
			gloonyAboutVideos.setDescription("Seen 1000 videos in 1 week!!!");					
			gloonyAboutVideos.setImagePath("gloonyAboutVideosPath");
			achievementDAOInstance.save(gloonyAboutVideos);
		}
		
		
		private void createUsers() throws NoSuchAlgorithmException, InvalidKeySpecException, ParseException
		{
			Achievement newGloonie = achievementDAOInstance.findByTitle("New Gloonie!");
			Achievement glooniacWriter = achievementDAOInstance.findByTitle("Glooniac Writer!!!");
			Achievement gloonyAboutgames = achievementDAOInstance.findByTitle("Gloony about games!");
			Achievement gloonyAboutVideos = achievementDAOInstance.findByTitle("Gloony about videos!");
			Achievement videoGlooniac = achievementDAOInstance.findByTitle("Video Glooniac!");
			
			//user partho
			User partho = new User();
			partho.setEmail("defjam24@gmail.com");
			partho.setEmailConfirmed(AppConstants.EMAIL_CONFIRMED);
			HashMap<String, String> parthoSecretMap = SecurePassword.createHash("secret");
			partho.setPasswordHash(parthoSecretMap.get("hashHex"));
			partho.setPasswordSalt(parthoSecretMap.get("saltHex"));			
			partho.setFirstName("partho");			
			partho.setLastName("ghosh");
			partho.setUsername("loonatic86");
			partho.setCountry("India");
			partho.setBirthdayVisibility(User.PUBLIC);
			partho.setDay(24);
			partho.setMonth(3);
			partho.setYear(1986);
			partho.setAvatarPath("");
			partho.setInsertTime(Utility.convertDateToString(new Date()));
			partho.setGameBio("I love gaming. Started my gaming adventure from 1999 and still going strong...");	
			String uptoUsername =AppConstants.APP_ABSOLUTE_IMAGE_USER_PATH+"loonatic86\\uploads\\";
			File makeUsernameDir = new File(uptoUsername);
			makeUsernameDir.mkdirs();			
			userDAOInstance.save(partho);
			
			ArrayList<Achievement> parthoAchievements = new ArrayList<>();
			parthoAchievements.add(newGloonie);
			String dateText="";
			Date date=null;
			dateText="2012-05-16 22:05:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, partho.getId().toString(), newGloonie.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
			parthoAchievements.add(glooniacWriter);
			dateText="2012-07-16 02:15:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, partho.getId().toString(), glooniacWriter.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
			parthoAchievements.add(gloonyAboutgames);
			dateText="2012-08-20 16:15:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, partho.getId().toString(), gloonyAboutgames.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
			parthoAchievements.add(gloonyAboutVideos);
			dateText="2012-09-20 20:15:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, partho.getId().toString(), gloonyAboutVideos.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
			partho.setAchievements(parthoAchievements);														
			partho.setTotalScore(0);
			userDAOInstance.save(partho);
			
			
			
			


			//user swati
         User swati = new User();
         swati.setEmail("swati.hindu@gmail.com");
         swati.setEmailConfirmed(AppConstants.EMAIL_CONFIRMED);
         HashMap<String, String> swatiSecretMap = SecurePassword.createHash("secret");
         swati.setPasswordHash(swatiSecretMap.get("hashHex"));
         swati.setPasswordSalt(swatiSecretMap.get("saltHex"));	         
         swati.setFirstName("swati");         
         swati.setLastName("mittal");
         swati.setUsername("loonatic87");
         swati.setBirthdayVisibility(User.PUBLIC);
         swati.setDay(7);
         swati.setCountry("India");
         swati.setMonth(3);
         swati.setYear(1987);
         swati.setAvatarPath("");
         swati.setInsertTime(Utility.convertDateToString(new Date()));
         swati.setGameBio("I love racing and puzzle games a lot. Hosted many gaming contests in my college days...");	
         uptoUsername =AppConstants.APP_ABSOLUTE_IMAGE_USER_PATH+"loonatic87\\uploads\\";
         makeUsernameDir = new File(uptoUsername);
         makeUsernameDir.mkdirs();
         userDAOInstance.save(swati);
         
         ArrayList<Achievement> swatiAchievements = new ArrayList<>();
         
         swatiAchievements.add(newGloonie);
         dateText="2012-09-20 20:15:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, swati.getId().toString(), newGloonie.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         swatiAchievements.add(gloonyAboutVideos);
         dateText="2012-10-21 17:15:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, swati.getId().toString(), gloonyAboutVideos.getId().toString(), AppConstants.PRIVATE,dateText, date.getTime());
         swati.setAchievements(swatiAchievements);			         
                                 						
			userDAOInstance.save(partho);					
			swati.setTotalScore(0);												
			userDAOInstance.save(swati);
			
			//user bhumika
			User bhumika = new User();
			bhumika.setEmail("bhumika.bhu@gmail.com");
			bhumika.setCountry("India");
			bhumika.setEmailConfirmed(AppConstants.EMAIL_CONFIRMED);
			 HashMap<String, String> bhumikaSecretMap = SecurePassword.createHash("secret");
			 bhumika.setPasswordHash(bhumikaSecretMap.get("hashHex"));
			 bhumika.setPasswordSalt(bhumikaSecretMap.get("saltHex"));
			bhumika.setFirstName("bhumika");			
			bhumika.setLastName("tiwari");
			bhumika.setUsername("chocobo87");
			bhumika.setBirthdayVisibility(User.PUBLIC);
			bhumika.setDay(11);
			bhumika.setMonth(7);
			bhumika.setYear(1987);
			bhumika.setAvatarPath("");
			bhumika.setInsertTime(Utility.convertDateToString(new Date()));
			bhumika.setGameBio("I am crazy about old school games like mario and tarzan boy. Recently in love with Temple run...");		
			uptoUsername =AppConstants.APP_ABSOLUTE_IMAGE_USER_PATH+"chocobo87\\uploads\\";
	         makeUsernameDir = new File(uptoUsername);
	         makeUsernameDir.mkdirs();
			userDAOInstance.save(bhumika);
         
         ArrayList<Achievement> bhumikaAchievements = new ArrayList<>();
         bhumikaAchievements.add(newGloonie);
         dateText="2013-01-01 20:10:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, bhumika.getId().toString(), newGloonie.getId().toString(), AppConstants.PRIVATE, dateText,date.getTime());
         bhumikaAchievements.add(gloonyAboutgames);
         dateText="2013-04-10 17:25:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, bhumika.getId().toString(), gloonyAboutgames.getId().toString(), AppConstants.PRIVATE, dateText,date.getTime());
         bhumikaAchievements.add(gloonyAboutVideos);
         dateText="2013-04-21 21:15:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, bhumika.getId().toString(), gloonyAboutVideos.getId().toString(), AppConstants.PRIVATE, dateText,date.getTime());
         bhumika.setAchievements(bhumikaAchievements);
			
                                                   
        
       //user dada
         User dada = new User();
         dada.setEmail("prodipto.ghosh@gmail.com");
         dada.setEmailConfirmed(AppConstants.EMAIL_CONFIRMED);
         HashMap<String, String> dadaSecretMap = SecurePassword.createHash("secret");
         dada.setPasswordHash(dadaSecretMap.get("hashHex"));
         dada.setPasswordSalt(dadaSecretMap.get("saltHex"));
         dada.setFirstName("prodipto");         
         dada.setLastName("ghosh");
         dada.setCountry("India");
         dada.setUsername("jaguarpaw80");
         dada.setBirthdayVisibility(User.PUBLIC);
         dada.setDay(19);
         dada.setMonth(7);
         dada.setYear(1980);
         dada.setAvatarPath("");
         dada.setInsertTime(Utility.convertDateToString(new Date()));
         dada.setGameBio("Gaming had been and still a very large part of my life. Love to play every kind of video game...");
         uptoUsername =AppConstants.APP_ABSOLUTE_IMAGE_USER_PATH+"jaguarpaw80\\uploads\\";
         makeUsernameDir = new File(uptoUsername);
         makeUsernameDir.mkdirs();
         userDAOInstance.save(dada);
         
         ArrayList<Achievement> dadaAchievements = new ArrayList<>();
         dadaAchievements.add(newGloonie);
         dateText="2013-01-03 11:10:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, dada.getId().toString(), newGloonie.getId().toString(), AppConstants.PRIVATE, dateText,date.getTime());
         dadaAchievements.add(videoGlooniac);
         dateText="2013-05-05 21:10:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, dada.getId().toString(), videoGlooniac.getId().toString(), AppConstants.PRIVATE, dateText,date.getTime());
         dadaAchievements.add(glooniacWriter);
         dateText="2013-03-04 14:15:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, dada.getId().toString(), glooniacWriter.getId().toString(), AppConstants.PRIVATE, dateText,date.getTime());
         dadaAchievements.add(gloonyAboutgames);
         dateText="2013-05-04 19:10:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, dada.getId().toString(), gloonyAboutgames.getId().toString(), AppConstants.PRIVATE, dateText,date.getTime());
         dadaAchievements.add(gloonyAboutVideos);
         dateText="2013-06-06 21:15:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, dada.getId().toString(), gloonyAboutVideos.getId().toString(), AppConstants.PRIVATE, dateText,date.getTime());
         dada.setAchievements(dadaAchievements);
         
			                          
         dada.setTotalScore(0);
         userDAOInstance.save(dada);         
         bhumika.setTotalScore(0);
         userDAOInstance.save(bhumika);
         
         
       //user buni
         User buni = new User();
         buni.setEmail("karleo84@gmail.com");
         buni.setEmailConfirmed(AppConstants.EMAIL_CONFIRMED);
         HashMap<String, String> buniSecretMap = SecurePassword.createHash("secret");
         buni.setPasswordHash(buniSecretMap.get("hashHex"));
         buni.setPasswordSalt(buniSecretMap.get("saltHex"));
         buni.setFirstName("debashree");         
         buni.setLastName("ghosh");
         buni.setUsername("buno84");
         buni.setCountry("India");
         buni.setBirthdayVisibility(User.PUBLIC);
         buni.setDay(13);
         buni.setMonth(8);
         buni.setYear(1984);
         buni.setAvatarPath("");
         buni.setInsertTime(Utility.convertDateToString(new Date()));
         buni.setGameBio("I love mobile gaming. I go crazy and forget everything when i get an android or ios device in hand..");		
         uptoUsername =AppConstants.APP_ABSOLUTE_IMAGE_USER_PATH+"buno84\\uploads\\";
         makeUsernameDir = new File(uptoUsername);
         makeUsernameDir.mkdirs();
         userDAOInstance.save(buni);
         
         ArrayList<Achievement> buniAchievements = new ArrayList<>();
         buniAchievements.add(newGloonie);
         dateText="2013-01-03 13:10:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, buni.getId().toString(), newGloonie.getId().toString(), AppConstants.PRIVATE, dateText,date.getTime());
         buniAchievements.add(gloonyAboutgames);              
         dateText="2013-06-03 19:10:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, buni.getId().toString(), gloonyAboutgames.getId().toString(), AppConstants.PRIVATE, dateText,date.getTime());
         buni.setAchievements(buniAchievements);
			                           
         buni.setTotalScore(0);
         userDAOInstance.save(buni);      
         
         //User neo
         User neo = new User();
         neo.setEmail("neo.anderson@gmail.com");
         neo.setEmailConfirmed(AppConstants.EMAIL_CONFIRMED);
         HashMap<String, String> neoSecretMap = SecurePassword.createHash("secret");
         neo.setPasswordHash(neoSecretMap.get("hashHex"));
         neo.setPasswordSalt(neoSecretMap.get("saltHex"));
         neo.setCountry("United States");
         neo.setFirstName("neo");         
         neo.setLastName("anderson");
         neo.setUsername("theone90");
         neo.setBirthdayVisibility(User.PUBLIC);
         neo.setDay(21);
         neo.setMonth(8);
         neo.setYear(1990);
			neo.setAvatarPath("");
			neo.setInsertTime(Utility.convertDateToString(new Date()));
			neo.setGameBio("Badly crazy about video games... ");	
			uptoUsername =AppConstants.APP_ABSOLUTE_IMAGE_USER_PATH+"theone90\\uploads\\";
	         makeUsernameDir = new File(uptoUsername);
	         makeUsernameDir.mkdirs();
			userDAOInstance.save(neo);
         
         ArrayList<Achievement> neoAchievements = new ArrayList<>();
         neoAchievements.add(newGloonie);
         dateText="2013-01-10 13:10:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, neo.getId().toString(), newGloonie.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         neoAchievements.add(videoGlooniac);
         dateText="2013-03-03 23:15:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, neo.getId().toString(), videoGlooniac.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         neoAchievements.add(glooniacWriter);
         dateText="2013-04-05 03:10:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, neo.getId().toString(), glooniacWriter.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         neoAchievements.add(gloonyAboutgames);
         dateText="2013-04-11 16:10:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, neo.getId().toString(), gloonyAboutgames.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         neoAchievements.add(gloonyAboutVideos);
         dateText="2013-10-03 04:10:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, neo.getId().toString(), gloonyAboutVideos.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         neo.setAchievements(neoAchievements);
			         
         neo.setTotalScore(0);
         userDAOInstance.save(neo);        
         
         
         //User brian
         User brian = new User();
         brian.setEmail("brian.mcfury@gmail.com");
         brian.setEmailConfirmed(AppConstants.EMAIL_CONFIRMED);
         HashMap<String, String> brianSecretMap = SecurePassword.createHash("secret");
         brian.setPasswordHash(brianSecretMap.get("hashHex"));
         brian.setPasswordSalt(brianSecretMap.get("saltHex"));
         brian.setCountry("United Kingdom");
         brian.setFirstName("brian");         
         brian.setLastName("mcfury");
         brian.setUsername("brianzilla84");
         brian.setBirthdayVisibility(User.PUBLIC);
         brian.setDay(5);
         brian.setMonth(1);
         brian.setYear(1984);
         brian.setAvatarPath("");
			brian.setInsertTime(Utility.convertDateToString(new Date()));
			brian.setGameBio("Raised amongst nintendo, commodore 64, playstation...");		
			uptoUsername =AppConstants.APP_ABSOLUTE_IMAGE_USER_PATH+"brianzilla84\\uploads\\";
	         makeUsernameDir = new File(uptoUsername);
	         makeUsernameDir.mkdirs();
			userDAOInstance.save(brian);
         
         ArrayList<Achievement> brianAchievements = new ArrayList<>();
         brianAchievements.add(newGloonie);
         dateText="2013-01-11 16:10:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, brian.getId().toString(), newGloonie.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         brianAchievements.add(videoGlooniac);                
         dateText="2013-02-12 13:11:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, brian.getId().toString(), videoGlooniac.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         brianAchievements.add(gloonyAboutgames);
         dateText="2013-03-14 10:09:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, brian.getId().toString(), gloonyAboutgames.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         brianAchievements.add(gloonyAboutVideos);
         dateText="2013-05-10 02:10:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, brian.getId().toString(), gloonyAboutVideos.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         brian.setAchievements(brianAchievements);
			                          
         brian.setTotalScore(0);
         userDAOInstance.save(brian);        
         
         
         //User ken
         User ken = new User();
         ken.setEmail("ken.smith@gmail.com");
         ken.setEmailConfirmed(AppConstants.EMAIL_CONFIRMED);
         HashMap<String, String> kenSecretMap = SecurePassword.createHash("secret");
         ken.setPasswordHash(kenSecretMap.get("hashHex"));
         ken.setCountry("United States");
         ken.setPasswordSalt(kenSecretMap.get("saltHex"));
         ken.setFirstName("ken");         
         ken.setLastName("smith");
         ken.setUsername("kenhaduken82");
         ken.setBirthdayVisibility(User.PUBLIC);
         ken.setDay(5);
         ken.setMonth(6);
         ken.setYear(1982);
         ken.setAvatarPath("");
         ken.setInsertTime(Utility.convertDateToString(new Date()));
         ken.setGameBio("I love gaming and my name resembles a famous character in gaming.");	
         uptoUsername =AppConstants.APP_ABSOLUTE_IMAGE_USER_PATH+"kenhaduken82\\uploads\\";
         makeUsernameDir = new File(uptoUsername);
         makeUsernameDir.mkdirs();
         userDAOInstance.save(ken);
         
         ArrayList<Achievement> kenAchievements = new ArrayList<>();
         kenAchievements.add(newGloonie);
         dateText="2013-01-12 14:10:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, ken.getId().toString(), newGloonie.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         kenAchievements.add(videoGlooniac);
         dateText="2013-05-10 13:10:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, ken.getId().toString(), videoGlooniac.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         kenAchievements.add(glooniacWriter);
         dateText="2013-07-19 17:10:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, ken.getId().toString(), glooniacWriter.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         kenAchievements.add(gloonyAboutgames);
         dateText="2013-09-18 16:19:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, ken.getId().toString(), gloonyAboutgames.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         kenAchievements.add(gloonyAboutVideos);
         dateText="2013-09-20 23:10:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, ken.getId().toString(), gloonyAboutVideos.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         ken.setAchievements(kenAchievements);
			
                  
         ken.setTotalScore(0);
         userDAOInstance.save(ken);        
         
         //User anand
         User anand = new User();
         anand.setEmail("anand.srinivas@gmail.com");
         anand.setEmailConfirmed(AppConstants.EMAIL_CONFIRMED);
         HashMap<String, String> anandSecretMap = SecurePassword.createHash("secret");
         anand.setPasswordHash(anandSecretMap.get("hashHex"));
         anand.setPasswordSalt(anandSecretMap.get("saltHex"));
         anand.setCountry("United States");
         anand.setFirstName("anand");         
         anand.setLastName("srinivas");
         anand.setUsername("anandcrazygamer89");
         anand.setBirthdayVisibility(User.PUBLIC);
         anand.setDay(9);
         anand.setMonth(8);
         anand.setYear(1989);
         anand.setAvatarPath("");
         anand.setInsertTime(Utility.convertDateToString(new Date()));
         anand.setGameBio("Had been always crazy about video games from my childhood. FIFA had been mine favorite series till now.");
         uptoUsername =AppConstants.APP_ABSOLUTE_IMAGE_USER_PATH+"anandcrazygamer89\\uploads\\";
         makeUsernameDir = new File(uptoUsername);
         makeUsernameDir.mkdirs();
         userDAOInstance.save(anand);
         
         ArrayList<Achievement> anandAchievements = new ArrayList<>();
         anandAchievements.add(newGloonie);
         dateText="2013-01-13 15:10:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, anand.getId().toString(), newGloonie.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         anandAchievements.add(videoGlooniac);                
         dateText="2013-04-20 00:10:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, anand.getId().toString(), videoGlooniac.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         anandAchievements.add(gloonyAboutgames);                
         dateText="2013-06-10 03:10:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, anand.getId().toString(), gloonyAboutgames.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         anand.setAchievements(anandAchievements);
			
                            
         anand.setTotalScore(0);
         userDAOInstance.save(anand);        
         
         //User radha
         User radha = new User();
         radha.setEmail("radha.gupta@gmail.com");
         radha.setEmailConfirmed(AppConstants.EMAIL_CONFIRMED);
         HashMap<String, String> radhaSecretMap = SecurePassword.createHash("secret");
         radha.setPasswordHash(radhaSecretMap.get("hashHex"));
         radha.setPasswordSalt(radhaSecretMap.get("saltHex"));
         radha.setFirstName("radha");         
         radha.setLastName("gupta");
         radha.setCountry("India");
         radha.setUsername("radhashooter95");
         radha.setBirthdayVisibility(User.PUBLIC);
         radha.setDay(12);
         radha.setMonth(3);
         radha.setYear(1995);
         radha.setAvatarPath("");
         radha.setInsertTime(Utility.convertDateToString(new Date()));
         radha.setGameBio("Proud to be a gamer. Won many awards in many gaming tournaments. Big fan of FPS and racing games.");		
         uptoUsername =AppConstants.APP_ABSOLUTE_IMAGE_USER_PATH+"radhashooter95\\uploads\\";
         makeUsernameDir = new File(uptoUsername);
         makeUsernameDir.mkdirs();
         userDAOInstance.save(radha);
         
         ArrayList<Achievement> radhaAchievements = new ArrayList<>();
         radhaAchievements.add(newGloonie);                
         dateText="2013-01-15 15:10:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, radha.getId().toString(), newGloonie.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         radhaAchievements.add(gloonyAboutgames);
         dateText="2013-03-13 16:20:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, radha.getId().toString(), gloonyAboutgames.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         radhaAchievements.add(gloonyAboutVideos);
         dateText="2013-07-23 11:19:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, radha.getId().toString(), gloonyAboutVideos.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         radha.setAchievements(radhaAchievements);
			
                          
         radha.setTotalScore(0);
         userDAOInstance.save(radha);        
         
         //User tiffany
         User tiffany = new User();
         tiffany.setEmail("tiffany.parker@gmail.com");
         tiffany.setEmailConfirmed(AppConstants.EMAIL_CONFIRMED);
         HashMap<String, String> tiffanySecretMap = SecurePassword.createHash("secret");
         tiffany.setPasswordHash(tiffanySecretMap.get("hashHex"));
         tiffany.setPasswordSalt(tiffanySecretMap.get("saltHex"));
         tiffany.setFirstName("tiffany");         
         tiffany.setLastName("parker");
         tiffany.setCountry("United States");
         tiffany.setUsername("tparkerponny98");
         tiffany.setBirthdayVisibility(User.PUBLIC);
         tiffany.setDay(5);
         tiffany.setMonth(6);
         tiffany.setYear(1998);
         tiffany.setAvatarPath("");
         tiffany.setInsertTime(Utility.convertDateToString(new Date()));
         tiffany.setGameBio("Love video gaming.");			
         uptoUsername =AppConstants.APP_ABSOLUTE_IMAGE_USER_PATH+"tparkerponny98\\uploads\\";
         makeUsernameDir = new File(uptoUsername);
         makeUsernameDir.mkdirs();
         userDAOInstance.save(tiffany);
         
         ArrayList<Achievement> tiffanyAchievements = new ArrayList<>();
         tiffanyAchievements.add(newGloonie);                
         dateText="2013-01-16 15:10:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, tiffany.getId().toString(), newGloonie.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         tiffanyAchievements.add(glooniacWriter);
         dateText="2013-04-15 14:15:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, tiffany.getId().toString(), glooniacWriter.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         tiffanyAchievements.add(gloonyAboutgames);                
         dateText="2013-09-25 04:00:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, tiffany.getId().toString(), gloonyAboutgames.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         tiffany.setAchievements(tiffanyAchievements);
			
         
         tiffany.setTotalScore(0);
         userDAOInstance.save(tiffany);        
         
         //User xiang
         User xiang = new User();
         xiang.setEmail("xiang.hu@gmail.com");
         xiang.setEmailConfirmed(AppConstants.EMAIL_CONFIRMED);
         HashMap<String, String> xiangSecretMap = SecurePassword.createHash("secret");
         xiang.setPasswordHash(xiangSecretMap.get("hashHex"));
         xiang.setPasswordSalt(xiangSecretMap.get("saltHex"));
         xiang.setFirstName("xiang");   
         xiang.setCountry("China");
         xiang.setLastName("hu");
         xiang.setUsername("xwarior81");
         xiang.setBirthdayVisibility(User.PUBLIC);
         xiang.setDay(2);
         xiang.setMonth(5);
         xiang.setYear(1981);
         xiang.setAvatarPath("");
         xiang.setInsertTime(Utility.convertDateToString(new Date()));
         xiang.setGameBio("Love video games");				
         uptoUsername =AppConstants.APP_ABSOLUTE_IMAGE_USER_PATH+"xwarior81\\uploads\\";
         makeUsernameDir = new File(uptoUsername);
         makeUsernameDir.mkdirs();
         userDAOInstance.save(xiang);
         
         ArrayList<Achievement> xiangAchievements = new ArrayList<>();
         xiangAchievements.add(newGloonie);
         dateText="2013-01-16 19:10:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, xiang.getId().toString(), newGloonie.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         xiangAchievements.add(videoGlooniac);
         dateText="2013-02-16 00:10:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, xiang.getId().toString(), videoGlooniac.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         xiangAchievements.add(glooniacWriter);
         dateText="2013-05-26 04:10:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, xiang.getId().toString(), glooniacWriter.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         xiangAchievements.add(gloonyAboutgames);
         dateText="2013-05-28 01:10:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, xiang.getId().toString(), gloonyAboutgames.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         xiangAchievements.add(gloonyAboutVideos);
         dateText="2013-06-01 19:10:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, xiang.getId().toString(), gloonyAboutVideos.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         xiang.setAchievements(xiangAchievements);
			
         
         xiang.setTotalScore(0);
         userDAOInstance.save(xiang);        
         
         
         //User ted
         User ted = new User();
         ted.setEmail("ted.muchoo@gmail.com");
         ted.setEmailConfirmed(AppConstants.EMAIL_CONFIRMED);
         HashMap<String, String> tedSecretMap = SecurePassword.createHash("secret");
         ted.setPasswordHash(tedSecretMap.get("hashHex"));
         ted.setPasswordSalt(tedSecretMap.get("saltHex"));
         ted.setCountry("Australia");
         ted.setFirstName("ted");         
         ted.setLastName("muchoo");
         ted.setUsername("muchooomg90");
         ted.setBirthdayVisibility(User.PUBLIC);
         ted.setDay(10);
         ted.setMonth(1);
			ted.setYear(1990);
			ted.setAvatarPath("");
			ted.setInsertTime(Utility.convertDateToString(new Date()));
			ted.setGameBio("blah blah blah.... nuff said, lemme play video games now... bye");		
			uptoUsername =AppConstants.APP_ABSOLUTE_IMAGE_USER_PATH+"muchooomg90\\uploads\\";
	         makeUsernameDir = new File(uptoUsername);
	         makeUsernameDir.mkdirs();
			userDAOInstance.save(ted);
         
         ArrayList<Achievement> tedAchievements = new ArrayList<>();
         tedAchievements.add(newGloonie);
         dateText="2013-01-17 18:05:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, ted.getId().toString(), newGloonie.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         tedAchievements.add(videoGlooniac);
         dateText="2013-03-19 20:10:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, ted.getId().toString(), videoGlooniac.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         tedAchievements.add(glooniacWriter);
         dateText="2013-03-26 16:10:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, ted.getId().toString(), glooniacWriter.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         tedAchievements.add(gloonyAboutgames);
         dateText="2013-04-16 09:10:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, ted.getId().toString(), gloonyAboutgames.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         tedAchievements.add(gloonyAboutVideos);
         dateText="2013-05-16 00:10:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, ted.getId().toString(), gloonyAboutVideos.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         ted.setAchievements(tedAchievements);
			
                                    
         ted.setTotalScore(0);
         userDAOInstance.save(ted);
        
         
         
         //User john
         User john = new User();
         john.setEmail("john.doe@gmail.com");
         john.setEmailConfirmed(AppConstants.EMAIL_CONFIRMED);
         HashMap<String, String> johnSecretMap = SecurePassword.createHash("secret");
         john.setPasswordHash(johnSecretMap.get("hashHex"));
         john.setPasswordSalt(johnSecretMap.get("saltHex"));
         john.setFirstName("john");         
         john.setLastName("doe");
         john.setUsername("rayray86");
         john.setCountry("Argentina");
         john.setBirthdayVisibility(User.PUBLIC);
         john.setDay(24);
         john.setMonth(3);
			john.setYear(1986);
			john.setAvatarPath("");
			john.setInsertTime(Utility.convertDateToString(new Date()));
			john.setGameBio("Love doing pew pew...");				
			uptoUsername =AppConstants.APP_ABSOLUTE_IMAGE_USER_PATH+"rayray86\\uploads\\";
	         makeUsernameDir = new File(uptoUsername);
	         makeUsernameDir.mkdirs();
			userDAOInstance.save(john);
         
         ArrayList<Achievement> johnAchievements =new ArrayList<>();
         johnAchievements.add(newGloonie);                      
         dateText="2013-01-18 18:05:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, john.getId().toString(), newGloonie.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         johnAchievements.add(gloonyAboutgames);
         dateText="2013-05-17 00:05:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, john.getId().toString(), gloonyAboutgames.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         johnAchievements.add(gloonyAboutVideos);
         dateText="2013-07-27 08:05:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, john.getId().toString(), gloonyAboutVideos.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         john.setAchievements(johnAchievements);
			                                           
         john.setTotalScore(0);
         userDAOInstance.save(john);
                  
        
        
         
         
         //User jane
         User jane = new User();
         jane.setEmail("jane.doe@gmail.com");
         jane.setEmailConfirmed(AppConstants.EMAIL_CONFIRMED);
         HashMap<String, String> janeSecretMap = SecurePassword.createHash("secret");
         jane.setPasswordHash(janeSecretMap.get("hashHex"));
         jane.setPasswordSalt(janeSecretMap.get("saltHex"));
         jane.setFirstName("jane");         
         jane.setLastName("doe");
         jane.setUsername("pookie87");
         jane.setCountry("Canada");
         jane.setBirthdayVisibility(User.PUBLIC);
         jane.setDay(7);
         jane.setMonth(3);
			jane.setYear(1987);
			jane.setAvatarPath("");
			jane.setInsertTime(Utility.convertDateToString(new Date()));
			jane.setGameBio("I find gaming fun, exciting and beautiful");
			uptoUsername =AppConstants.APP_ABSOLUTE_IMAGE_USER_PATH+"pookie87\\uploads\\";
	         makeUsernameDir = new File(uptoUsername);
	         makeUsernameDir.mkdirs();
			 userDAOInstance.save(jane);
         
         ArrayList<Achievement> janeAchievements = new ArrayList<>();
         janeAchievements.add(newGloonie);            
         dateText="2013-01-20 18:05:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, jane.getId().toString(), newGloonie.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         janeAchievements.add(gloonyAboutgames);
         dateText="2013-05-18 00:05:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, jane.getId().toString(), gloonyAboutgames.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         janeAchievements.add(gloonyAboutVideos);
         dateText="2013-10-18 16:15:00";
         date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, jane.getId().toString(), gloonyAboutVideos.getId().toString(), AppConstants.PRIVATE, dateText, date.getTime());
         jane.setAchievements(janeAchievements);
			                                   
         jane.setTotalScore(0);
         userDAOInstance.save(jane);
         
                 
         
		}
		
		
		private void createPlatforms()
		{
			
			Platform playstation3 = new Platform();
			playstation3.setTitle("Playstation 3");
			playstation3.setShortTitle("ps3");
			playstation3.setDescription("The cell powered third generation beast by Sony");
			playstation3.setManufacturer("Sony Inc.");

			platformDAOInstance.save(playstation3);
			
			Platform playstation4 = new Platform();
			playstation4.setTitle("Playstation 4");
			playstation4.setShortTitle("ps4");
			playstation4.setDescription("The 4th generation powerhouse developed by Sony");
			playstation4.setManufacturer("Sony Inc.");

			platformDAOInstance.save(playstation4);
			
			Platform xbox360 = new Platform();
			xbox360.setTitle("Xbox 360");
			xbox360.setShortTitle("xbox360");
			xbox360.setDescription("Microsoft's 2nd generation beast");
			xbox360.setManufacturer("Microsoft Inc.");
			platformDAOInstance.save(xbox360);
			
			Platform xboxOne = new Platform();
			xboxOne.setTitle("Xbox One");
			xboxOne.setShortTitle("xboxOne");
			xboxOne.setDescription("Microsoft's 3rd generation beast");
			xboxOne.setManufacturer("Microsoft Inc.");
			platformDAOInstance.save(xboxOne);
			
			Platform pc = new Platform();
			pc.setTitle("PC");
			pc.setShortTitle("pc");
			pc.setDescription("The world is owned by Microsoft, Apple and Linux");
			pc.setManufacturer("Many");
			platformDAOInstance.save(pc);
			
			Platform ios = new Platform();
			ios.setTitle("IOS");
			ios.setShortTitle("ios");
			ios.setDescription("The platform which runs on Iphone, IPAD and IPOD");
			ios.setManufacturer("Apple");
			platformDAOInstance.save(ios);
			
			Platform android = new Platform();
			android.setTitle("Android");
			android.setShortTitle("android");
			android.setDescription("Started by OHA but later on picked up and maintained by Google, it is the largest mobile OS today.");
			android.setManufacturer("Google");
			platformDAOInstance.save(android);
			
			Platform wiiu = new Platform();
			wiiu.setTitle("WII-U");
			wiiu.setShortTitle("wiiu");
			wiiu.setDescription("Follow-up to Wii, this console matches the graphics power of current generation consoles along with providing touch based controller.");
			wiiu.setManufacturer("Nintendo");
			platformDAOInstance.save(wiiu);
			
			Platform n3ds = new Platform();
			n3ds.setTitle("3DS");
			n3ds.setShortTitle("n3ds");
			n3ds.setDescription("Follow-up to DS handhalded device with 3d support and updated graphics");
			n3ds.setManufacturer("Nintendo");
			platformDAOInstance.save(n3ds);
			
			Platform vita = new Platform();
			vita.setTitle("PS-VITA");
			vita.setShortTitle("vita");
			vita.setDescription("Sony's follow-up to successful PSP");
			vita.setManufacturer("Sony");
			platformDAOInstance.save(vita);

			
		}
		
		private void createGames()
		{
		
			Platform ps3 = platformDAOInstance.findByTitle("Playstation 3");
			Platform x360 = platformDAOInstance.findByTitle("Xbox 360");
			Platform pc = platformDAOInstance.findByTitle("PC");
			Platform vita = platformDAOInstance.findByTitle("PS-VITA");
			
			
			ArrayList<Platform> platforms = new ArrayList<>();
			platforms.add(ps3);
			platforms.add(x360);
			platforms.add(pc);

			Game maxPayne3 = new Game();
			maxPayne3.setTitle("Max Payne 3");			
			maxPayne3.setDescription("The third and final chapter in the Max Payne Trilogy. Max goes for final kill");
			maxPayne3.setDeveloper("Rockstar Games");
			maxPayne3.setPublisher("Rockstar Games");
			maxPayne3.setGenere("Third Person Action"); 
			maxPayne3.setPrice("60$");
			maxPayne3.setPlatforms(platforms);
			maxPayne3.setRating(Game.MATURE);
			maxPayne3.setReleaseDate("2012-05-15");
			maxPayne3.setGameReleaseStatus(Game.RELEASED);
//			maxPayne3.setScore(8.5);
			maxPayne3.setGameBoxShotPath("mp_cover.jpg");			
			gameDAOInstance.save(maxPayne3);
			String uptoGamename =AppConstants.APP_ABSOLUTE_IMAGE_GAME_PATH+Utility.shortenString(maxPayne3.getTitle())+"\\uploads\\boxshot\\";
			File makeGamenameDir = new File(uptoGamename);
			makeGamenameDir.mkdirs();
			gameDAOInstance.save(maxPayne3);
			
			Game farCry3 = new Game();
			farCry3.setTitle("Far Cry 3");
			farCry3.setDescription("With Far Cry 3, players step into the shoes of Jason Brody, a man alone at the edge of the world, stranded on a mysterious tropical island. In this savage paradise where lawlessness and violence are the only sure thing, players dictate how the story unfolds, from the battles they choose to fight to the allies or enemies they make along the way. As Jason Brody, players will slash, sneak, detonate and shoot their way across the island in a world that has lost all sense of right and wrong.");
			farCry3.setDeveloper("Ubisoft Montreal");
			farCry3.setPublisher("Ubisoft");
			farCry3.setGenere("First Person Shooter");
			farCry3.setPrice("60$");
			farCry3.setPlatforms(platforms);
			farCry3.setRating(Game.MATURE);
			farCry3.setReleaseDate("2012-12-04");
			farCry3.setGameReleaseStatus(Game.RELEASED);
//			farCry3.setScore(9.0);
			farCry3.setGameBoxShotPath("fc3_cover.jpg");			
			gameDAOInstance.save(farCry3);
			uptoGamename =AppConstants.APP_ABSOLUTE_IMAGE_GAME_PATH+Utility.shortenString(farCry3.getTitle())+"\\uploads\\boxshot\\";
			makeGamenameDir = new File(uptoGamename);
			makeGamenameDir.mkdirs();
			gameDAOInstance.save(farCry3);
			
			Game guacamelee = new Game();
			guacamelee.setTitle("Guacamelee!");
			guacamelee.setDescription("Guacamelee! is a Metroid-vania style action-platformer set in a magical Mexican inspired world. The game draws its inspiration from traditional Mexican culture and folklore, and features many interesting and unique characters.");
			guacamelee.setDeveloper("Drinkbox Studios");
			guacamelee.setPublisher("Drinkbox Studios");
			guacamelee.setGenere("Action");
			guacamelee.setPrice("12$");
			ArrayList<Platform> guacameleePlatforms = new ArrayList<>();
			guacameleePlatforms.add(ps3);
			guacameleePlatforms.add(x360);
			guacameleePlatforms.add(vita);
			guacamelee.setPlatforms(guacameleePlatforms);
			guacamelee.setRating(Game.EVERYONE);
			guacamelee.setReleaseDate("2013-04-09");
			guacamelee.setGameReleaseStatus(Game.RELEASED);
//			guacamelee.setScore(9.1);
			guacamelee.setGameBoxShotPath("gmelee_cover.jpg");			
			gameDAOInstance.save(guacamelee);
			uptoGamename =AppConstants.APP_ABSOLUTE_IMAGE_GAME_PATH+Utility.shortenString(guacamelee.getTitle())+"\\uploads\\boxshot\\";
			makeGamenameDir = new File(uptoGamename);
			makeGamenameDir.mkdirs();
			gameDAOInstance.save(guacamelee);
			
			
			Game bioshockInfinite = new Game();
			bioshockInfinite.setTitle("Bioshock Infinite");
			bioshockInfinite.setDescription("Originally conceived as a floating symbol of American ideals at a time when the United States was emerging as a world power, Columbia is dispatched to distant shores with great fanfare by a captivated public. What begins as a brand new endeavor of hope turns drastically wrong as the city soon disappears into the clouds to whereabouts unknown.");
			bioshockInfinite.setDeveloper("Ubisoft Montreal");
			bioshockInfinite.setPublisher("Ubisoft");
			bioshockInfinite.setGenere("First Person Shooter");
			bioshockInfinite.setPrice("60$");			
			bioshockInfinite.setPlatforms(platforms);
			bioshockInfinite.setRating(Game.MATURE);
			bioshockInfinite.setReleaseDate("2013-03-26");
			bioshockInfinite.setGameReleaseStatus(Game.RELEASED);
//			bioshockInfinite.setScore(9.2);
			bioshockInfinite.setGameBoxShotPath("bi_cover.jpg");			
			gameDAOInstance.save(bioshockInfinite);
			uptoGamename =AppConstants.APP_ABSOLUTE_IMAGE_GAME_PATH+Utility.shortenString(bioshockInfinite.getTitle())+"\\uploads\\boxshot\\";
			makeGamenameDir = new File(uptoGamename);
			makeGamenameDir.mkdirs();
			gameDAOInstance.save(bioshockInfinite);
			
			
			Game halo4 = new Game();
			halo4.setTitle("Halo4");
			halo4.setDescription("The Master Chief returns to battle an ancient evil bent on vengeance and annihilation. Shipwrecked on a mysterious world, faced with new enemies and deadly technology, the universe will never be the same. ");
			halo4.setDeveloper("343 Industries");
			halo4.setPublisher("Microsoft Game Studios");
			halo4.setGenere("First Person Shooter");
			halo4.setPrice("60$");
			ArrayList<Platform> halo4Platforms = new ArrayList<>();
			halo4Platforms.add(x360);
			halo4.setPlatforms(halo4Platforms);
			halo4.setRating(Game.MATURE);
			halo4.setReleaseDate("2012-11-06");
			halo4.setGameReleaseStatus(Game.RELEASED);
//			halo4.setScore(8.8);
			halo4.setGameBoxShotPath("h4_cover.jpg");			
			gameDAOInstance.save(halo4);
			uptoGamename =AppConstants.APP_ABSOLUTE_IMAGE_GAME_PATH+Utility.shortenString(halo4.getTitle())+"\\uploads\\boxshot\\";
			makeGamenameDir = new File(uptoGamename);
			makeGamenameDir.mkdirs();
			gameDAOInstance.save(halo4);
			
			
			Game defiance = new Game();
			defiance.setTitle("Defiance");
			defiance.setDescription("Defiance is the first multi-platform shooter MMO which, in a ground-breaking entertainment experience, interconnects with a global television program by Syfy, cable's premier imagination-based entertainment channel.");
			defiance.setDeveloper("Trion Worlds");
			defiance.setPublisher("Trion Worlds");
			defiance.setGenere("MMO");
			defiance.setPrice("60$");
			defiance.setPlatforms(platforms);
			defiance.setRating(Game.MATURE);
			defiance.setReleaseDate("2013-04-02");
			defiance.setGameReleaseStatus(Game.RELEASED);
//			defiance.setScore(6.1);
			defiance.setGameBoxShotPath("def_cover.jpg");		
			gameDAOInstance.save(defiance);
			uptoGamename =AppConstants.APP_ABSOLUTE_IMAGE_GAME_PATH+Utility.shortenString(defiance.getTitle())+"\\uploads\\boxshot\\";
			makeGamenameDir = new File(uptoGamename);
			makeGamenameDir.mkdirs();
			gameDAOInstance.save(defiance);
			
			
			Game terraria= new Game();
			terraria.setTitle("Terraria");
			terraria.setDescription("Terraria? What's that? Terraria is a land of adventure! A land of mystery! A land that's yours to shape, to defend and to enjoy. Your options in Terraria are limitless, are you an action gamer with an itchy trigger finger? A master builder? A collector? An explorer? There's something for everyone here. Dig, fight, explore, build! Nothing is impossible in this action-packed adventure game.");
			terraria.setDeveloper("Re-Logic, Engine Software");
			terraria.setPublisher("505 Games");
			terraria.setGenere("Action");
			terraria.setPrice("10$");
			terraria.setPlatforms(platforms);
			terraria.setRating(Game.TEEN);
			terraria.setReleaseDate("2013-03-27");
			terraria.setGameReleaseStatus(Game.RELEASED);
//			terraria.setScore(9.0);
			terraria.setGameBoxShotPath("ter_cover.jpg");			
			gameDAOInstance.save(terraria);
			uptoGamename =AppConstants.APP_ABSOLUTE_IMAGE_GAME_PATH+Utility.shortenString(terraria.getTitle())+"\\uploads\\boxshot\\";
			makeGamenameDir = new File(uptoGamename);
			makeGamenameDir.mkdirs();
			gameDAOInstance.save(terraria);
			
			Game batmanArkhamCity = new Game();
			batmanArkhamCity.setTitle("Batman Arkham City");
			batmanArkhamCity.setDescription(" Set inside the heavily fortified walls of a sprawling district in the heart of Gotham City, this highly anticipated sequel introduces a brand-new story that draws together a new all-star cast of classic characters and murderous villains from the Batman universe, as well as a vast range of new and enhanced gameplay features to deliver the ultimate experience as the Dark Knight.");
			batmanArkhamCity.setDeveloper("Warner Bros. Interactive, Eidos Interactive");
			batmanArkhamCity.setPublisher(" Rocksteady Studios");
			batmanArkhamCity.setGenere("Action");
			batmanArkhamCity.setPrice("60$");
			batmanArkhamCity.setPlatforms(platforms);
			batmanArkhamCity.setRating(Game.TEEN);
			batmanArkhamCity.setReleaseDate("2012-09-07");
			batmanArkhamCity.setGameReleaseStatus(Game.RELEASED);
//			batmanArkhamCity.setScore(9.6);
			batmanArkhamCity.setGameBoxShotPath("bac_cover.png");			
			gameDAOInstance.save(batmanArkhamCity);
			uptoGamename =AppConstants.APP_ABSOLUTE_IMAGE_GAME_PATH+Utility.shortenString(batmanArkhamCity.getTitle())+"\\uploads\\boxshot\\";
			makeGamenameDir = new File(uptoGamename);
			makeGamenameDir.mkdirs();
			gameDAOInstance.save(batmanArkhamCity);
			
			Game motocrossMadness = new Game();
			motocrossMadness.setTitle("Motocross Madness");
			motocrossMadness.setDescription("Motocross Madness is back! Take your bike freeriding across massive environments, from the deserts of Egypt to the snows of Iceland. Drift, trick and turbo boost your way across 9 expansive offroad tracks in single player events, ghost challenges and 8-player multiplayer races.");
			motocrossMadness.setDeveloper("Bongfish");
			motocrossMadness.setPublisher("Microsoft Game Studios");
			motocrossMadness.setGenere("Racing");
			motocrossMadness.setPrice("11$");
			ArrayList<Platform> motocrossMadnessPlatforms = new ArrayList<>();
			motocrossMadnessPlatforms.add(x360);
			motocrossMadness.setPlatforms(motocrossMadnessPlatforms);
			motocrossMadness.setRating(Game.EVERYONE);
			motocrossMadness.setReleaseDate("2013-04-10");
			motocrossMadness.setGameReleaseStatus(Game.RELEASED);
//			motocrossMadness.setScore(6.2);
			motocrossMadness.setGameBoxShotPath("mm_cover.jpg");			
			gameDAOInstance.save(motocrossMadness);
			uptoGamename =AppConstants.APP_ABSOLUTE_IMAGE_GAME_PATH+Utility.shortenString(motocrossMadness.getTitle())+"\\uploads\\boxshot\\";
			makeGamenameDir = new File(uptoGamename);
			makeGamenameDir.mkdirs();
			gameDAOInstance.save(motocrossMadness);
			
			Game ageOfWushu = new Game();
			ageOfWushu.setTitle("Age of Wushu");
			ageOfWushu.setDescription("Endorsed by martial artist legend and wushu champion Jet-Li, Age of Wushu is the world's first true Wuxia themed MMORPG, with cutting edge graphics and innovative gameplay. Players must journey to master the ancient Chinese art of Wushu, delving into their character's unique story and facing the consequences of every action without the crutch of MMO staples like levels and classes.");
			ageOfWushu.setDeveloper("Suzhou Snail Electronics co. ltd");
			ageOfWushu.setPublisher("Snail Games");
			ageOfWushu.setGenere("MMO");
			ageOfWushu.setPrice("60$");
			ageOfWushu.setPlatforms(platforms);
			ageOfWushu.setRating(Game.MATURE);
			ageOfWushu.setReleaseDate("2013-04-10");
			ageOfWushu.setGameReleaseStatus(Game.RELEASED);
//			ageOfWushu.setScore(7.3);
			ageOfWushu.setGameBoxShotPath("aow_cover.jpg");			
			gameDAOInstance.save(ageOfWushu);
			uptoGamename =AppConstants.APP_ABSOLUTE_IMAGE_GAME_PATH+Utility.shortenString(ageOfWushu.getTitle())+"\\uploads\\boxshot\\";
			makeGamenameDir = new File(uptoGamename);
			makeGamenameDir.mkdirs();
			gameDAOInstance.save(ageOfWushu);
			
			Game splinterCellBlacklist = new Game();
			splinterCellBlacklist.setTitle("Tom Clancy's Splinter Cell: Blacklist");
			splinterCellBlacklist.setDescription("The United States has a military presence in two thirds of countries around the world. A group of rogue nations have had enough and initiate a terror ultimatum called the Blacklist - a deadly countdown of escalating terrorist attacks on U.S. interests. Sam Fisher is the leader of the newly formed 4th Echelon unit: a clandestine unit that answers solely to the President of the United States.");
			splinterCellBlacklist.setDeveloper("Ubisoft Toronto");
			splinterCellBlacklist.setPublisher("Ubisoft");
			splinterCellBlacklist.setGenere("Action");
			splinterCellBlacklist.setPrice("60$");
			splinterCellBlacklist.setPlatforms(platforms);
			splinterCellBlacklist.setRating(Game.MATURE);
			splinterCellBlacklist.setGameReleaseStatus(Game.RELEASED);
			splinterCellBlacklist.setReleaseDate("2013-08-20");	
			splinterCellBlacklist.setGameBoxShotPath("tcscb_cover.png");			
			gameDAOInstance.save(splinterCellBlacklist);
			uptoGamename =AppConstants.APP_ABSOLUTE_IMAGE_GAME_PATH+Utility.shortenString(splinterCellBlacklist.getTitle())+"\\uploads\\boxshot\\";
			makeGamenameDir = new File(uptoGamename);
			makeGamenameDir.mkdirs();
			gameDAOInstance.save(splinterCellBlacklist);
			
			Game gta5 = new Game();
			gta5.setTitle("Grand Theft Auto V");
			gta5.setDescription("Like GTA: San Andreas before it, GTA V will be set in and around the LA-inspired city of Los Santos.");
			gta5.setDeveloper("Rockstar North");
			gta5.setPublisher("Rockstar Games");
			gta5.setGenere("Action");
			gta5.setPrice("60$");
			gta5.setPlatforms(platforms);
			gta5.setRating(Game.MATURE);
			gta5.setGameReleaseStatus(Game.RELEASED);
			gta5.setReleaseDate("2013-09-17");	
			gta5.setGameBoxShotPath("gta5_cover.png");			
			gameDAOInstance.save(gta5);
			uptoGamename =AppConstants.APP_ABSOLUTE_IMAGE_GAME_PATH+Utility.shortenString(gta5.getTitle())+"\\uploads\\boxshot\\";
			makeGamenameDir = new File(uptoGamename);
			makeGamenameDir.mkdirs();
			gameDAOInstance.save(gta5);
			
			Game batmanArkhamOrigins = new Game();
			batmanArkhamOrigins.setTitle("Batman Arkham Origins");
			batmanArkhamOrigins.setDescription("Taking place before the rise of Gotham City's most dangerous criminals, the game showcases a young and unrefined Batman as he faces a defining moment in his early career as a crime fighter that sets his path to becoming the Dark Knight.");
			batmanArkhamOrigins.setDeveloper("WB Games Montreal");
			batmanArkhamOrigins.setPublisher("Warner Bros. Interactive Entertainment");
			batmanArkhamOrigins.setGenere("Action");
			batmanArkhamOrigins.setPrice("60$");
			batmanArkhamOrigins.setPlatforms(platforms);
			batmanArkhamOrigins.setRating(Game.RATING_PENDING);
			batmanArkhamOrigins.setGameReleaseStatus(Game.NOT_RELEASED);
			batmanArkhamOrigins.setReleaseDate("2013-10-25");	
			batmanArkhamOrigins.setGameBoxShotPath("bao_cover.jpg");			
			gameDAOInstance.save(batmanArkhamOrigins);
			uptoGamename =AppConstants.APP_ABSOLUTE_IMAGE_GAME_PATH+Utility.shortenString(batmanArkhamOrigins.getTitle())+"\\uploads\\boxshot\\";
			makeGamenameDir = new File(uptoGamename);
			makeGamenameDir.mkdirs();
			gameDAOInstance.save(batmanArkhamOrigins);
			
			Game mgsRising = new Game();
			mgsRising.setTitle("Metal Gear Rising: Revengence");
			mgsRising.setDescription("METAL GEAR RISING: REVENGEANCE takes the renowned METAL GEAR franchise into exciting new territory by focusing on delivering an all-new action experience unlike anything that has come before. Combining world-class development teams at Kojima Productions and PlatinumGames, METAL GEAR RISING: REVENGEANCE brings two of the world's most respected teams together with a common goal of providing players with a fresh synergetic experience that combines the best elements of pure action and epic storytelling, all within the expansive MG universe");
			mgsRising.setDeveloper("Platinum Games");
			mgsRising.setPublisher("Kojima Productions");
			mgsRising.setGenere("Action");
			mgsRising.setPrice("60$");
			mgsRising.setPlatforms(platforms);
			mgsRising.setRating(Game.RATING_PENDING);
			mgsRising.setGameReleaseStatus(Game.RELEASED);
//			mgsRising.setScore(8.4);
			mgsRising.setReleaseDate("2013-02-19");		
			mgsRising.setGameBoxShotPath("mgsr_cover.jpg");			
			gameDAOInstance.save(mgsRising);
			uptoGamename =AppConstants.APP_ABSOLUTE_IMAGE_GAME_PATH+Utility.shortenString(mgsRising.getTitle())+"\\uploads\\boxshot\\";
			makeGamenameDir = new File(uptoGamename);
			makeGamenameDir.mkdirs();
			gameDAOInstance.save(mgsRising);
			
		}
		
		private void createArticles() throws ParseException
		{	
		/*	
			ArrayList<Article> gameArticles= new ArrayList<>();
			ArrayList<User> userWhoPlayedGame = new ArrayList<>();
			ArrayList<User> userWhoScoredGame = new ArrayList<>();
			UserGameScoreMapDAO scoreMap = UserGameScoreMapDAO.instantiateDAO();
			
			Platform ps3 = platformDAOInstance.findByTitle("Playstation 3");
			Platform x360 = platformDAOInstance.findByTitle("Xbox 360");
			Platform pc = platformDAOInstance.findByTitle("PC");
			Platform vita = platformDAOInstance.findByTitle("PS-VITA");
			Platform ps4 = platformDAOInstance.findByShortTitle("ps4");
			
			Game maxPayne3 = gameDAOInstance.findByTitle("Max Payne 3");
			Game farCry3 =   gameDAOInstance.findByTitle("Far Cry 3"); 				
			Game guacamelee = gameDAOInstance.findByTitle("Guacamelee!"); 
			Game ageOfWushu = gameDAOInstance.findByTitle("Age of Wushu"); 
			Game motocrossMadness = gameDAOInstance.findByTitle("Motocross Madness"); 		
			Game terraria = gameDAOInstance.findByTitle("Terraria");  	
			Game defiance = gameDAOInstance.findByTitle("Defiance"); 		
			Game mgsr = gameDAOInstance.findByTitle("Metal Gear Rising: Revengence");
			
			User loonatic86 = userDAOInstance.findByUsername("loonatic86");			
			User jaguarpaw80 = userDAOInstance.findByUsername("jaguarpaw80"); 					
			User kenhaduken82 =	userDAOInstance.findByUsername("kenhaduken82");		
			User xwarior81 = userDAOInstance.findByUsername("xwarior81");
			User muchooomg90 = userDAOInstance.findByUsername("muchooomg90");
			User theone90 = userDAOInstance.findByUsername("theone90");
			User loonatic87 = userDAOInstance.findByUsername("loonatic87");
																
			ArrayList<Platform> platforms = new ArrayList<>();
			platforms.add(ps3);
			platforms.add(x360);
			platforms.add(pc);
			
			
			Article maxPayne3Review = new Article();
			maxPayne3Review.setTitle("max payne 3 review");
			maxPayne3Review.setSubtitle("How does Max fared third time?");						
			maxPayne3Review.setAuthor("loonatic86");
			maxPayne3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			maxPayne3Review.setCategory(Category.Review);
			maxPayne3Review.setPublishDate("2012-05-16 22:05:59");	
			maxPayne3Review.setFeaturedImagePath("");
			maxPayne3Review.setGame(maxPayne3.getId().toString());						
			//maxPayne3Review.setInsertTime(Utility.convertDateToString(new Date()));
			maxPayne3Review.setInsertTime("2012-05-16 22:05:59");
			maxPayne3Review.setUpdateTime("2012-05-16 22:05:59");				
			maxPayne3Review.setTimestamp(Utility.convertFromStringToDate("2012-05-16 22:05:59").getTime());
			maxPayne3Review.setPlatforms(platforms);									
			maxPayne3Review.setState(Article.PUBLISH);	
			scoreMap.createScoreMap(maxPayne3.getId().toString(), loonatic86.getUsername() , 9.1);			
			articleDAOInstance.save(maxPayne3Review);
			
			
			gameArticles.add(maxPayne3Review);
			userWhoPlayedGame.add(loonatic86);
			userWhoScoredGame.add(loonatic86);
						
			
			gameDAOInstance.save(maxPayne3);
			String dateText="";
			Date date = null;
			
			dateText="2012-05-16 22:05:59";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, loonatic86.getUsername(), maxPayne3Review.getId().toString(), AppConstants.PUBLIC, dateText, date.getTime());
			
			Article farCry3Review = new Article();
  			farCry3Review.setTitle("far cry 3 review");
			farCry3Review.setSubtitle("Return to the jungle, tropical style...");								
			farCry3Review.setAuthor("jaguarpaw80");
			farCry3Review.setBody("The third game in the excellent series excels in every manner. Solid entry in Far Cry series.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setPublishDate("2012-12-15 09:30:00");
			farCry3Review.setFeaturedImagePath("");
			farCry3Review.setGame(farCry3.getId().toString());			
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2012-12-15 09:30:00");
			farCry3Review.setUpdateTime("2012-12-15 09:30:00");		
			farCry3Review.setTimestamp(Utility.convertFromStringToDate("2012-12-15 09:30:00").getTime());
			farCry3Review.setPlatforms(platforms);				
			farCry3Review.setState(Article.PUBLISH);	
			scoreMap.createScoreMap(farCry3.getId().toString(), jaguarpaw80.getUsername() , 9.0);
			articleDAOInstance.save(farCry3Review);
			
			
			gameArticles.add(farCry3Review);
			userWhoPlayedGame.add(jaguarpaw80);
			userWhoScoredGame.add(jaguarpaw80);
						
			
			gameDAOInstance.save(farCry3);							
			dateText="2012-12-15 09:30:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, jaguarpaw80.getUsername(), farCry3Review.getId().toString(), AppConstants.PUBLIC, dateText, date.getTime());
			
			
			ArrayList<Platform> guacameleePlatforms = new ArrayList<>();
			guacameleePlatforms.add(ps3);
			guacameleePlatforms.add(x360);
			guacameleePlatforms.add(vita);
			
			
			Article guacameleeReview1 = new Article();
			guacameleeReview1.setTitle("Guacamelee! review");		
			guacameleeReview1.setSubtitle("Get ready for good old meelee fun.");
			guacameleeReview1.setAuthor("jaguarpaw80");
			guacameleeReview1.setBody("Guacamelee is an awseome metroid fueled mexican wave ride.");
			guacameleeReview1.setCategory(Category.Review);
			guacameleeReview1.setPublishDate("2013-04-10 09:00:00");		
			guacameleeReview1.setFeaturedImagePath("");
			guacameleeReview1.setGame(guacamelee.getId().toString());			
			//guacameleeReview.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			guacameleeReview1.setInsertTime("2013-04-10 09:00:00");		
			guacameleeReview1.setUpdateTime("2013-04-10 09:00:00");
			guacameleeReview1.setTimestamp(Utility.convertFromStringToDate("2013-04-10 09:00:00").getTime());
			guacameleeReview1.setPlatforms(guacameleePlatforms);		
			guacameleeReview1.setState(Article.PUBLISH);
			scoreMap.createScoreMap(guacamelee.getId().toString(), jaguarpaw80.getUsername() , 9.5);
			articleDAOInstance.save(guacameleeReview1);
		
			
			
			gameArticles.add(guacameleeReview1);
			userWhoPlayedGame.add(jaguarpaw80);
			userWhoScoredGame.add(jaguarpaw80);
			
			
			
			gameDAOInstance.save(guacamelee);
												
			dateText="2013-04-10 09:00:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, jaguarpaw80.getUsername(), guacameleeReview1.getId().toString(), AppConstants.PUBLIC, dateText, date.getTime());
			
			
			Article guacameleeReview2 = new Article();
			guacameleeReview2.setTitle("Guacamelee! review");	
			guacameleeReview2.setSubtitle("Get ready for some nice mexicano side scrolling brawler");
			guacameleeReview2.setAuthor("kenhaduken82");
			guacameleeReview2.setBody("Guacamelee is mexican luchador beauty.");
			guacameleeReview2.setCategory(Category.Review);
			guacameleeReview2.setPublishDate("2013-04-10 10:00:00");		
			guacameleeReview2.setFeaturedImagePath("");
			guacameleeReview2.setGame(guacamelee.getId().toString());			
			//guacameleeReview.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			guacameleeReview2.setInsertTime("2013-04-10 10:00:00");		
			guacameleeReview2.setUpdateTime("2013-04-10 10:00:00");
			guacameleeReview2.setTimestamp(Utility.convertFromStringToDate("2013-04-10 10:00:00").getTime());
			guacameleeReview2.setPlatforms(guacameleePlatforms);	
			guacameleeReview2.setState(Article.PUBLISH);
			scoreMap.createScoreMap(guacamelee.getId().toString(), kenhaduken82.getUsername() , 9.7);
			articleDAOInstance.save(guacameleeReview2);
			
			
			
			gameArticles.add(guacameleeReview2);
			userWhoPlayedGame.add(kenhaduken82);
			userWhoScoredGame.add(kenhaduken82);
			
			
			
			gameDAOInstance.save(guacamelee);
				
			dateText="2013-04-10 10:00:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, kenhaduken82.getUsername(), guacameleeReview2.getId().toString(), AppConstants.PUBLIC, dateText, date.getTime());
			
			Article guacameleeReview3 = new Article();
			guacameleeReview3.setTitle("Guacamelee! review");	
			guacameleeReview3.setSubtitle("Guacamelee is fun pendejo...");
			guacameleeReview3.setAuthor("loonatic86");
			guacameleeReview3.setBody("Being a mexican chicken is so fun.");
			guacameleeReview3.setCategory(Category.Review);
			guacameleeReview3.setPublishDate("2013-04-10 11:00:00");		
			guacameleeReview3.setFeaturedImagePath("");
			guacameleeReview3.setGame(guacamelee.getId().toString());			
			//guacameleeReview.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			guacameleeReview3.setInsertTime("2013-04-10 11:00:00");
			guacameleeReview3.setUpdateTime("2013-04-10 11:00:00");
			guacameleeReview3.setTimestamp(Utility.convertFromStringToDate("2013-04-10 11:00:00").getTime());
			guacameleeReview3.setPlatforms(guacameleePlatforms);		
			guacameleeReview3.setState(Article.PUBLISH);
			scoreMap.createScoreMap(guacamelee.getId().toString(), loonatic86.getUsername() , 9.5);
			articleDAOInstance.save(guacameleeReview3);
			
			
			
			
			gameArticles.add(guacameleeReview3);
			userWhoPlayedGame.add(loonatic86);
			userWhoScoredGame.add(loonatic86);
			
			
			
			gameDAOInstance.save(guacamelee);					
			dateText="2013-04-10 11:00:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, loonatic86.getUsername(), guacameleeReview3.getId().toString(), AppConstants.PUBLIC, dateText, date.getTime());
			
			Article ageOfWushuReview1 = new Article();
			ageOfWushuReview1.setTitle("Age of Wushu review");		
			ageOfWushuReview1.setSubtitle("How does this kung fu MMORPG romp fares?");
			ageOfWushuReview1.setAuthor("loonatic86");
			ageOfWushuReview1.setBody("Age of Wushu lets you enjoy the world of Martial Arts in its purest form.");
			ageOfWushuReview1.setCategory(Category.Review);
			ageOfWushuReview1.setPublishDate("2013-04-11 09:00:00");			
			ageOfWushuReview1.setFeaturedImagePath("");
			ageOfWushuReview1.setGame(ageOfWushu.getId().toString());			
			//ageOfWushuReview.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			ageOfWushuReview1.setInsertTime("2013-04-11 09:00:00");
			ageOfWushuReview1.setUpdateTime("2013-04-11 09:00:00");
			ageOfWushuReview1.setTimestamp(Utility.convertFromStringToDate("2013-04-11 09:00:00").getTime());
			ageOfWushuReview1.setPlatforms(platforms);		
			ageOfWushuReview1.setState(Article.PUBLISH);
			scoreMap.createScoreMap(ageOfWushu.getId().toString(), loonatic86.getUsername() , 8.5);
			articleDAOInstance.save(ageOfWushuReview1);
		
			
			
			gameArticles.add(ageOfWushuReview1);
			userWhoPlayedGame.add(loonatic86);
			userWhoScoredGame.add(loonatic86);
			
			
			
			gameDAOInstance.save(ageOfWushu);
					
			dateText="2013-04-11 09:00:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, loonatic86.getUsername(), ageOfWushuReview1.getId().toString(), AppConstants.PUBLIC, dateText, date.getTime());
			
			Article ageOfWushuReview2 = new Article();
			ageOfWushuReview2.setTitle("Age of Wushu review");				
			ageOfWushuReview2.setSubtitle("Get ready for some wall-running, flying dagger style action...");
			ageOfWushuReview2.setAuthor("xwarior81");
			ageOfWushuReview2.setBody("If you are fan of sugar hardcore martial arts movie, you will find your salvation in Age of Wushu.");
			ageOfWushuReview2.setCategory(Category.Review);
			ageOfWushuReview2.setPublishDate("2013-04-11 12:20:00");	
			ageOfWushuReview2.setFeaturedImagePath("");
			ageOfWushuReview2.setGame(ageOfWushu.getId().toString());			
			//ageOfWushuReview.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			ageOfWushuReview2.setInsertTime("2013-04-11 12:20:00");
			ageOfWushuReview2.setUpdateTime("2013-04-11 12:20:00");
			ageOfWushuReview2.setTimestamp(Utility.convertFromStringToDate("2013-04-11 12:20:00").getTime());
			ageOfWushuReview2.setPlatforms(platforms);			
			scoreMap.createScoreMap(ageOfWushu.getId().toString(), xwarior81.getUsername() , 8.8);
			ageOfWushuReview2.setState(Article.PUBLISH);
			articleDAOInstance.save(ageOfWushuReview2);
		
			
			
			gameArticles.add(ageOfWushuReview2);
			userWhoPlayedGame.add(xwarior81);
			userWhoScoredGame.add(xwarior81);
			
			
			
			gameDAOInstance.save(ageOfWushu);
							
			
			dateText="2013-04-11 12:20:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, xwarior81.getUsername(), ageOfWushuReview2.getId().toString(), AppConstants.PUBLIC, dateText, date.getTime());
			
			
			ArrayList<Platform> motocrossPlatforms = new ArrayList<>();
			motocrossPlatforms.add(x360);
			
			
			Article motocrossMadnessReview1 = new Article();
			motocrossMadnessReview1.setTitle("Motocross Madness review");			
			ageOfWushuReview2.setSubtitle("Motocross Madness is back. But is it any good?");
			motocrossMadnessReview1.setAuthor("muchooomg90");
			motocrossMadnessReview1.setBody("This Motocross Madness is medicore at its best.");
			motocrossMadnessReview1.setCategory(Category.Review);
			motocrossMadnessReview1.setPublishDate("2013-04-11 00:30:00");	
			motocrossMadnessReview1.setFeaturedImagePath("");
			motocrossMadnessReview1.setGame(motocrossMadness.getId().toString());			
			//motocrossMadnessReview1.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			motocrossMadnessReview1.setInsertTime("2013-04-11 00:30:00");
			motocrossMadnessReview1.setUpdateTime("2013-04-11 00:30:00");
			motocrossMadnessReview1.setTimestamp(Utility.convertFromStringToDate("2013-04-11 00:30:00").getTime());
			motocrossMadnessReview1.setPlatforms(motocrossPlatforms);
			motocrossMadnessReview1.setState(Article.PUBLISH);
			scoreMap.createScoreMap(motocrossMadness.getId().toString(), muchooomg90.getUsername() , 7.5);
			articleDAOInstance.save(motocrossMadnessReview1);
		
			
			
			gameArticles.add(motocrossMadnessReview1);
			userWhoPlayedGame.add(muchooomg90);
			userWhoScoredGame.add(muchooomg90);
			
			
			
			gameDAOInstance.save(motocrossMadness);
					
			dateText="2013-04-11 12:20:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, muchooomg90.getUsername(), motocrossMadnessReview1.getId().toString(), AppConstants.PUBLIC,dateText, date.getTime());
			
			Article motocrossMadnessReview2 = new Article();
			motocrossMadnessReview2.setTitle("Motocross Madness review");
			motocrossMadnessReview2.setSubtitle("Motocross Madness is bad. Period!");
			motocrossMadnessReview2.setAuthor("kenhaduken82");
			motocrossMadnessReview2.setBody("Common Microsoft, stop serving us enough garbage now!");
			motocrossMadnessReview2.setCategory(Category.Review);
			motocrossMadnessReview2.setPublishDate("2013-04-11 09:30:00");		
			motocrossMadnessReview2.setFeaturedImagePath("");
			motocrossMadnessReview2.setGame(motocrossMadness.getId().toString());			
			//motocrossMadnessReview2.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			motocrossMadnessReview2.setInsertTime("2013-04-11 09:30:00");
			motocrossMadnessReview2.setUpdateTime("2013-04-11 09:30:00");
			motocrossMadnessReview2.setTimestamp(Utility.convertFromStringToDate("2013-04-11 09:30:00").getTime());
			motocrossMadnessReview2.setPlatforms(motocrossPlatforms);
			motocrossMadnessReview2.setState(Article.PUBLISH);
			scoreMap.createScoreMap(motocrossMadness.getId().toString(), kenhaduken82.getUsername() , 5.0);
			articleDAOInstance.save(motocrossMadnessReview2);

			
			
			gameArticles.add(motocrossMadnessReview2);
			userWhoPlayedGame.add(kenhaduken82);
			userWhoScoredGame.add(kenhaduken82);
			
			
			
			gameDAOInstance.save(motocrossMadness);						
			
			dateText="2013-04-11 09:30:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, kenhaduken82.getUsername(), motocrossMadnessReview2.getId().toString(), AppConstants.PUBLIC,dateText, date.getTime());
			
			Article terrariaReview1 = new Article();
			terrariaReview1.setTitle("Terraria review");				
			terrariaReview1.setSubtitle("Load runner gets EVERYTHING is toppings.");
			terrariaReview1.setAuthor("loonatic86");
			terrariaReview1.setBody("A game which lets you do EVERYTHING!!!");
			terrariaReview1.setCategory(Category.Review);
			terrariaReview1.setPublishDate("2013-03-28 09:00:00");		
			terrariaReview1.setFeaturedImagePath("");
			terrariaReview1.setGame(terraria.getId().toString());			
			//terraria.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			terrariaReview1.setInsertTime("2013-03-28 09:00:00");
			terrariaReview1.setUpdateTime("2013-03-28 09:00:00");
			terrariaReview1.setTimestamp(Utility.convertFromStringToDate("2013-03-28 09:00:00").getTime());
			terrariaReview1.setPlatforms(platforms);
			terrariaReview1.setState(Article.PUBLISH);
			scoreMap.createScoreMap(terraria.getId().toString(), loonatic86.getUsername() , 9.6);
			articleDAOInstance.save(terrariaReview1);
	
			
			gameArticles.add(terrariaReview1);
			userWhoPlayedGame.add(loonatic86);
			userWhoScoredGame.add(loonatic86);
			
			
			
			gameDAOInstance.save(terraria);			
			dateText="2013-03-28 09:00:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, loonatic86.getUsername(), terrariaReview1.getId().toString(), AppConstants.PUBLIC,dateText, date.getTime());
			
			Article terrariaReview2= new Article();
			terrariaReview2.setTitle("Terraria review");						
			terrariaReview2.setSubtitle("Get many flavors at a price of one. And it is fun...");
			terrariaReview2.setAuthor("theone90");
			terrariaReview2.setBody("Its an action game, a rpg, an exploration game. In short, play it as any way possible. Its awesome in short. ");
			terrariaReview2.setCategory(Category.Review);
			terrariaReview2.setPublishDate("2013-03-28 10:00:00");			
			terrariaReview2.setFeaturedImagePath("");
			terrariaReview2.setGame(terraria.getId().toString());			
			//terraria.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			terrariaReview2.setInsertTime("2013-03-28 10:00:00");			
			terrariaReview2.setUpdateTime("2013-03-28 10:00:00");
			terrariaReview2.setTimestamp(Utility.convertFromStringToDate("2013-03-28 10:00:00").getTime());
			terrariaReview2.setPlatforms(platforms);
			terrariaReview2.setState(Article.PUBLISH);
			scoreMap.createScoreMap(terraria.getId().toString(), theone90.getUsername(), 9.7);
			articleDAOInstance.save(terrariaReview2);
			
			
			
			gameArticles.add(terrariaReview2);
			userWhoPlayedGame.add(theone90);
			userWhoScoredGame.add(theone90);
			
			
			
			gameDAOInstance.save(terraria);	

			dateText="2013-03-28 10:00:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, theone90.getUsername(), terrariaReview2.getId().toString(), AppConstants.PUBLIC,dateText, date.getTime());
			
			Article terrariaReview3= new Article();
			terrariaReview3.setTitle("Terraria review");							
			terrariaReview3.setSubtitle("A true homage to sugar retro gaming.");
			terrariaReview3.setAuthor("kenhaduken82");
			terrariaReview3.setBody("This game is fun and take you back into 90s bomberman and load runner era... ");
			terrariaReview3.setCategory(Category.Review);
			terrariaReview3.setPublishDate("2013-03-28 08:00:00");	
			terrariaReview3.setFeaturedImagePath("");
			terrariaReview3.setGame(terraria.getId().toString());			
			//terraria.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			terrariaReview3.setInsertTime("2013-03-28 08:00:00");
			terrariaReview3.setUpdateTime("2013-03-28 08:00:00");
			terrariaReview3.setTimestamp(Utility.convertFromStringToDate("2013-03-28 08:00:00").getTime());
			terrariaReview3.setPlatforms(platforms);
			terrariaReview3.setState(Article.PUBLISH);
			scoreMap.createScoreMap(terraria.getId().toString(), kenhaduken82.getUsername() , 9.7);
			articleDAOInstance.save(terrariaReview3);
	
			
			
			gameArticles.add(terrariaReview3);
			userWhoPlayedGame.add(kenhaduken82);
			userWhoScoredGame.add(kenhaduken82);
			
			
			
			gameDAOInstance.save(terraria);	

			dateText="2013-03-28 08:00:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, kenhaduken82.getUsername(), terrariaReview3.getId().toString(), AppConstants.PUBLIC,dateText, date.getTime());
			
			Article defianceReview1 = new Article();
			defianceReview1.setTitle("Defiance review");		
			defianceReview1.setSubtitle("TV to game tie-not that good");
			defianceReview1.setAuthor("xwarior81");
			defianceReview1.setBody("Seriously!!! What the heck was that?");
			defianceReview1.setCategory(Category.Review);
			defianceReview1.setPublishDate("2013-04-03 00:30:00");		
			defianceReview1.setFeaturedImagePath("");
			defianceReview1.setGame(defiance.getId().toString());			
			//defianceReview1.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			defianceReview1.setInsertTime("2013-04-03 00:30:00");
			defianceReview1.setUpdateTime("2013-04-03 00:30:00");
			defianceReview1.setTimestamp(Utility.convertFromStringToDate("2013-04-03 00:30:00").getTime());
			defianceReview1.setPlatforms(platforms);
			defianceReview1.setState(Article.PUBLISH);
			scoreMap.createScoreMap(defiance.getId().toString(), xwarior81.getUsername() , 6.1);
			articleDAOInstance.save(defianceReview1);
			
			
			
			gameArticles.add(defianceReview1);
			userWhoPlayedGame.add(xwarior81);
			userWhoScoredGame.add(xwarior81);
			
			
			
			gameDAOInstance.save(defiance);				
			dateText="2013-04-03 00:30:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, xwarior81.getUsername(), defianceReview1.getId().toString(), AppConstants.PUBLIC,dateText, date.getTime());
			
			Article defianceReview2 = new Article();
			defianceReview2.setTitle("Defiance review");		
			defianceReview2.setSubtitle("A great concept but a failed implentation");
			defianceReview2.setAuthor("loonatic86");
			defianceReview2.setBody("Concept was great on paper but the execution was not just right.");
			defianceReview2.setCategory(Category.Review);
			defianceReview2.setPublishDate("2013-04-03 01:30:00");		
			defianceReview2.setFeaturedImagePath("");
			defianceReview2.setGame(defiance.getId().toString());			
			//defianceReview2.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			defianceReview2.setInsertTime("2013-04-03 01:30:00");			
			defianceReview2.setUpdateTime("2013-04-03 01:30:00");
			defianceReview2.setTimestamp(Utility.convertFromStringToDate("2013-04-03 01:30:00").getTime());
			defianceReview2.setPlatforms(platforms);
			defianceReview2.setState(Article.PUBLISH);
			scoreMap.createScoreMap(defiance.getId().toString(), loonatic86.getUsername() , 6.0);
			articleDAOInstance.save(defianceReview2);
	
			
			
			gameArticles.add(defianceReview2);
			userWhoPlayedGame.add(loonatic86);
			userWhoScoredGame.add(loonatic86);
			
			
			
			gameDAOInstance.save(defiance);					
			dateText="2013-04-03 01:30:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, loonatic86.getUsername(), defianceReview2.getId().toString(), AppConstants.PUBLIC,dateText,date.getTime());
			
			Article defianceReview3 = new Article();			
			defianceReview3.setTitle("Defiance review");
			defianceReview3.setSubtitle("Defiance tries hard to be cool.");
			defianceReview3.setAuthor("muchooomg90");
			defianceReview3.setBody("Defiance just doesnt work. Period.");
			defianceReview3.setCategory(Category.Review);
			defianceReview3.setPublishDate("2013-04-03 01:30:00");		
			defianceReview3.setFeaturedImagePath("");
			defianceReview3.setGame(defiance.getId().toString());			
			//defianceReview3.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			defianceReview3.setInsertTime("2013-04-03 01:30:00");			
			defianceReview3.setUpdateTime("2013-04-03 01:30:00");
			defianceReview3.setTimestamp(Utility.convertFromStringToDate("2013-04-03 01:30:00").getTime());
			defianceReview3.setPlatforms(platforms);
			defianceReview3.setState(Article.PUBLISH);
			scoreMap.createScoreMap(defiance.getId().toString(), muchooomg90.getUsername() , 6.2);
			articleDAOInstance.save(defianceReview3);
		
		
			
			gameArticles.add(defianceReview3);
			userWhoPlayedGame.add(muchooomg90);
			userWhoScoredGame.add(muchooomg90);
			
		
			
			gameDAOInstance.save(defiance);	
			
			dateText="2013-04-03 01:30:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, muchooomg90.getUsername(), defianceReview3.getId().toString(), AppConstants.PUBLIC,dateText, date.getTime());
			
			Article maxPayne3Feature = new Article();
			maxPayne3Feature.setTitle("Max Payne 3 Feature: The pain of bullet time 3.0");	
			maxPayne3Feature.setSubtitle("We look into the bullet time feature of Max Payne 3.0");
			maxPayne3Feature.setAuthor("loonatic86");
			maxPayne3Feature.setBody("Bullet time 3.0 felt kind of fresh even though bullet time used in games like anything. The new bullet time in Max Payne 3 enhance the poetic nature of Max thanks to rage engine and Euphoria.");
			maxPayne3Feature.setCategory(Category.Feature);
			maxPayne3Feature.setPublishDate("2013-04-13 11:05:00");			
			maxPayne3Feature.setFeaturedImagePath("");
			maxPayne3Feature.setGame(maxPayne3.getId().toString());			
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			maxPayne3Feature.setInsertTime("2013-04-13 11:05:00");			
			maxPayne3Feature.setUpdateTime("2013-04-13 11:05:00");
			maxPayne3Feature.setTimestamp(Utility.convertFromStringToDate("2013-04-13 11:05:00").getTime());
			maxPayne3Feature.setPlatforms(platforms);
			maxPayne3Feature.setState(Article.PUBLISH);
			articleDAOInstance.save(maxPayne3Feature);			
		
			gameArticles.add(maxPayne3Feature);

			gameDAOInstance.save(maxPayne3);
			dateText="2013-04-13 11:05:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, loonatic86.getUsername(), maxPayne3Feature.getId().toString(), AppConstants.PUBLIC,dateText, date.getTime());
			
			Article farCry3Feature = new Article();
			farCry3Feature.setTitle("Far Cry 3 Feature: The sense of wild");	
			farCry3Feature.setSubtitle("Lets have a look into the open world of Far cry 3");
			farCry3Feature.setAuthor("loonatic86");
			farCry3Feature.setBody("Wild was really alive in the game. IMHO, this is the second game after Metal Gear Solid: Snake Eater in which I stay alerted while playing the game. Just that this game is more open and random in nature. Danger is lurking everywhere.");
			farCry3Feature.setCategory(Category.Feature);
			farCry3Feature.setPublishDate("2013-04-11 15:00:00");			
			farCry3Feature.setFeaturedImagePath("");
			farCry3Feature.setGame(farCry3.getId().toString());			
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Feature.setInsertTime("2013-04-11 15:00:00");			
			farCry3Feature.setUpdateTime("2013-04-11 15:00:00");
			farCry3Feature.setTimestamp(Utility.convertFromStringToDate("2013-04-11 15:00:00").getTime());
			farCry3Feature.setPlatforms(platforms);
			farCry3Feature.setState(Article.PUBLISH);
			articleDAOInstance.save(farCry3Feature);
		

			gameArticles.add(farCry3Feature);

			gameDAOInstance.save(farCry3);
			dateText="2013-04-11 15:00:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, loonatic86.getUsername(), farCry3Feature.getId().toString(), AppConstants.PUBLIC,dateText, date.getTime());
			
			Article mgsRisingFeature = new Article();
			mgsRisingFeature.setTitle("MGS Rising Feature: The cut mechanics");		
			mgsRisingFeature.setSubtitle("A look into MGS Rising cut feature");
			mgsRisingFeature.setAuthor("kenhaduken82");
			mgsRisingFeature.setBody("Atleast, Konami made sure that we get that same cut experience which they demoed in E3 2011. I was overwhelmed by it.");
			mgsRisingFeature.setCategory(Category.Feature);
			mgsRisingFeature.setPublishDate("2013-03-29 15:45:00");			
			mgsRisingFeature.setFeaturedImagePath("");
			mgsRisingFeature.setGame(mgsr.getId().toString());			
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			mgsRisingFeature.setInsertTime("2013-03-29 15:45:00");			
			mgsRisingFeature.setUpdateTime("2013-03-29 15:45:00");
			mgsRisingFeature.setTimestamp(Utility.convertFromStringToDate("2013-03-29 15:45:00").getTime());
			mgsRisingFeature.setPlatforms(platforms);
			mgsRisingFeature.setState(Article.PUBLISH);
			articleDAOInstance.save(mgsRisingFeature);
		

			gameArticles.add(mgsRisingFeature);

			gameDAOInstance.save(mgsr);
			dateText="2013-03-29 15:45:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, kenhaduken82.getUsername(), mgsRisingFeature.getId().toString(), AppConstants.PUBLIC,dateText, date.getTime());
			
			Article defianceFeature = new Article();
			defianceFeature.setTitle("Defiance Feature: Marrying TV with games");	
			defianceFeature.setSubtitle("A look into Defiance's unique integration with TV");
			defianceFeature.setAuthor("theone90");
			defianceFeature.setBody("Defiance tries to pull a brave move by clubbing tv with video gaming.");
			defianceFeature.setCategory(Category.Feature);
			defianceFeature.setPublishDate("2013-03-30 16:45:00");			
			defianceFeature.setFeaturedImagePath("");
			defianceFeature.setGame(defiance.getId().toString());			
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			defianceFeature.setInsertTime("2013-03-30 16:45:00");			
			defianceFeature.setUpdateTime("2013-03-30 16:45:00");
			defianceFeature.setTimestamp(Utility.convertFromStringToDate("2013-03-30 16:45:00").getTime());
			defianceFeature.setPlatforms(platforms);
			defianceFeature.setState(Article.PUBLISH);
			articleDAOInstance.save(defianceFeature);
		

			gameArticles.add(defianceFeature);

			gameDAOInstance.save(defiance);
			dateText="2013-03-30 16:45:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, theone90.getUsername(), defianceFeature.getId().toString(), AppConstants.PUBLIC,dateText, date.getTime());
			
			Article motocrossFeature = new Article();
			motocrossFeature.setTitle("Motocross Madness: Xbox live motocross");						
			motocrossFeature.setSubtitle("Hands-on Motocross Madness demo.");
			motocrossFeature.setAuthor("muchooomg90");
			motocrossFeature.setBody("This motocross madness looks the same");
			motocrossFeature.setCategory(Category.Feature);
			motocrossFeature.setPublishDate("2013-04-10 15:45:00");		
			motocrossFeature.setFeaturedImagePath("");
			motocrossFeature.setGame(motocrossMadness.getId().toString());			
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			motocrossFeature.setInsertTime("2013-04-10 15:45:00");			
			motocrossFeature.setUpdateTime("2013-04-10 15:45:00");
			motocrossFeature.setTimestamp(Utility.convertFromStringToDate("2013-04-10 15:45:00").getTime());			
			motocrossFeature.setPlatforms(motocrossPlatforms);
			motocrossFeature.setState(Article.PUBLISH);
			articleDAOInstance.save(motocrossFeature);
			

			gameArticles.add(motocrossFeature);

			gameDAOInstance.save(motocrossMadness);
			dateText="2013-04-10 15:45:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, muchooomg90.getUsername(), motocrossFeature.getId().toString(), AppConstants.PUBLIC,dateText, date.getTime());
			
			Article ageOfWushuFeature = new Article();
			ageOfWushuFeature.setTitle("Age of Wushu Feature: The rise of asian gaming out of Japan");	
			ageOfWushuFeature.setSubtitle("A look into Age of Wushu as well as gaming outside Japan");
			ageOfWushuFeature.setAuthor("xwarior81");
			ageOfWushuFeature.setBody("Age of wushu brings asian gaming out of japan and puts on face of the planet. There is hope for other asian countries.");
			ageOfWushuFeature.setCategory(Category.Feature);
			ageOfWushuFeature.setPublishDate("2013-04-14 15:45:00");	
			ageOfWushuFeature.setFeaturedImagePath("");
			ageOfWushuFeature.setGame(ageOfWushu.getId().toString());			
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			ageOfWushuFeature.setInsertTime("2013-04-14 15:45:00");			
			ageOfWushuFeature.setUpdateTime("2013-04-14 15:45:00");
			ageOfWushuFeature.setTimestamp(Utility.convertFromStringToDate("2013-04-14 15:45:00").getTime());
			ageOfWushuFeature.setPlatforms(platforms);
			ageOfWushuFeature.setState(Article.PUBLISH);
			articleDAOInstance.save(ageOfWushuFeature);
			

			gameArticles.add(ageOfWushuFeature);

			gameDAOInstance.save(ageOfWushu);
			dateText="2013-04-14 15:45:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, xwarior81.getUsername(), ageOfWushuFeature.getId().toString(), AppConstants.PUBLIC,dateText, date.getTime());
			
			Article cricketGloonicle = new Article();
			cricketGloonicle.setTitle("Why do we have to starve for a good cricket game?");		
			cricketGloonicle.setSubtitle("A look into history and future of cricket video games.");
			cricketGloonicle.setAuthor("loonatic86");
			cricketGloonicle.setBody("When on earth we are going to get next decent cricket game? Come on big great gaming companies, give us our next great cricket game");
			cricketGloonicle.setCategory(Category.Gloonicle);
			cricketGloonicle.setPublishDate("2013-04-15 09:45:00");		
			cricketGloonicle.setFeaturedImagePath("");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			cricketGloonicle.setInsertTime("2013-04-15 09:45:00");			
			cricketGloonicle.setUpdateTime("2013-04-15 09:45:00");
			cricketGloonicle.setTimestamp(Utility.convertFromStringToDate("2013-04-15 09:45:00").getTime());
			cricketGloonicle.setPlatforms(platforms);
			cricketGloonicle.setState(Article.PUBLISH);
			articleDAOInstance.save(cricketGloonicle);
		
			
			dateText="2013-04-15 09:45:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, loonatic86.getUsername(), cricketGloonicle.getId().toString(), AppConstants.PUBLIC,dateText, date.getTime());
			
			Article freemiumGloonicle = new Article();
			freemiumGloonicle.setTitle("The birth of \"Free\" gaming");				
			freemiumGloonicle.setSubtitle("A look into Freemium model fo gaming.");
			freemiumGloonicle.setAuthor("kenhaduken82");
			freemiumGloonicle.setBody("Freemium gaming is the new way to go");
			freemiumGloonicle.setCategory(Category.Gloonicle);
			freemiumGloonicle.setPublishDate("2013-01-15 10:45:00");	
			freemiumGloonicle.setFeaturedImagePath("");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			freemiumGloonicle.setInsertTime("2013-01-15 10:45:00");			
			freemiumGloonicle.setUpdateTime("2013-01-15 10:45:00");
			freemiumGloonicle.setTimestamp(Utility.convertFromStringToDate("2013-01-15 10:45:00").getTime());
			freemiumGloonicle.setPlatforms(platforms);
			freemiumGloonicle.setState(Article.PUBLISH);
			articleDAOInstance.save(freemiumGloonicle);
		
			
			dateText="2013-01-15 10:45:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, kenhaduken82.getUsername(), freemiumGloonicle.getId().toString(), AppConstants.PUBLIC,dateText, date.getTime());
			
			Article top10horrorGloonicle = new Article();
			top10horrorGloonicle.setTitle("Top 10 horror games");			
			top10horrorGloonicle.setSubtitle("My Top 10 horror games");
			top10horrorGloonicle.setAuthor("xwarior81");
			top10horrorGloonicle.setBody("My top 10 horrors are: 1) Silent hill 2) Resident Evil...");
			top10horrorGloonicle.setCategory(Category.Gloonicle);
			top10horrorGloonicle.setPublishDate("2013-04-15 21:45:00");		
			top10horrorGloonicle.setFeaturedImagePath("");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			top10horrorGloonicle.setInsertTime("2013-04-15 21:45:00");			
			top10horrorGloonicle.setUpdateTime("2013-04-15 21:45:00");
			top10horrorGloonicle.setTimestamp(Utility.convertFromStringToDate("2013-04-15 21:45:00").getTime());
			top10horrorGloonicle.setPlatforms(platforms);
			top10horrorGloonicle.setState(Article.PUBLISH);
			articleDAOInstance.save(top10horrorGloonicle);
	
			
			dateText="2013-04-15 21:45:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, xwarior81.getUsername(), top10horrorGloonicle.getId().toString(), AppConstants.PUBLIC,dateText, date.getTime());
			
			Article top10ActionGloonicle = new Article();
			top10ActionGloonicle.setTitle("Top 10 action games");	
			top10ActionGloonicle.setSubtitle("My Top 10 action games");
			top10ActionGloonicle.setAuthor("muchooomg90");
			top10ActionGloonicle.setBody("My top 10 action games: 1)Max Payne 3 2) Halo 4 3)Splinter cell series");
			top10ActionGloonicle.setCategory(Category.Gloonicle);
			top10ActionGloonicle.setPublishDate("2013-01-13 20:02:00");		
			top10ActionGloonicle.setFeaturedImagePath("");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			top10ActionGloonicle.setInsertTime("2013-01-13 20:02:00");			
			top10ActionGloonicle.setUpdateTime("2013-01-13 20:02:00");
			top10ActionGloonicle.setTimestamp(Utility.convertFromStringToDate("2013-01-13 20:02:00").getTime());
			top10ActionGloonicle.setPlatforms(platforms);
			top10ActionGloonicle.setState(Article.PUBLISH);
			articleDAOInstance.save(top10ActionGloonicle);
			
			
			dateText="2013-01-13 20:02:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, muchooomg90.getUsername(), top10ActionGloonicle.getId().toString(), AppConstants.PUBLIC,dateText, date.getTime());
			
			Article myWeeklyShow = new Article();
			myWeeklyShow.setTitle("My weekly gaming adventure");
			myWeeklyShow.setSubtitle("This week we are looking into funny things in gaming.");
			top10ActionGloonicle.setSubtitle("Episode 10");
			myWeeklyShow.setAuthor("theone90");
			myWeeklyShow.setBody("I will share my daily gaming adventure in this series.");
			myWeeklyShow.setCategory(Category.Gloonicle);
			myWeeklyShow.setPublishDate("2013-01-14 00:02:00");		
			myWeeklyShow.setFeaturedImagePath("");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			myWeeklyShow.setInsertTime("2013-01-14 00:02:00");			
			myWeeklyShow.setUpdateTime("2013-01-14 00:02:00");
			myWeeklyShow.setTimestamp(Utility.convertFromStringToDate("2013-01-14 00:02:00").getTime());
			myWeeklyShow.setPlatforms(platforms);
			myWeeklyShow.setState(Article.PUBLISH);
			articleDAOInstance.save(myWeeklyShow);
			
			
			dateText="2013-01-14 00:02:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, theone90.getUsername(), myWeeklyShow.getId().toString(), AppConstants.PUBLIC,dateText, date.getTime());
			
			Article eaDisasterNews = new Article();
			eaDisasterNews.setTitle("EA has been again ranked as worst company in America");				
			eaDisasterNews.setSubtitle("EA does it again");
			eaDisasterNews.setAuthor("theone90");
			eaDisasterNews.setBody("EA has been again ranked as worst company in America. This is the second time they have been rated like this.");
			eaDisasterNews.setCategory(Category.News);
			eaDisasterNews.setPublishDate("2013-01-12 10:02:00");				
			eaDisasterNews.setFeaturedImagePath("");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			eaDisasterNews.setInsertTime("2013-01-12 10:02:00");			
			eaDisasterNews.setUpdateTime("2013-01-12 10:02:00");
			eaDisasterNews.setTimestamp(Utility.convertFromStringToDate("2013-01-12 10:02:00").getTime());
			eaDisasterNews.setPlatforms(platforms);
			eaDisasterNews.setState(Article.PUBLISH);
			articleDAOInstance.save(eaDisasterNews);
		
			
			dateText="2013-01-12 10:02:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, theone90.getUsername(), eaDisasterNews.getId().toString(), AppConstants.PUBLIC,dateText, date.getTime());
			
			Article ps4News = new Article();
			ps4News.setTitle("PS4 launch price revealed");		
			ps4News.setSubtitle("About the launch way below $500");
			ps4News.setAuthor("muchooomg90");
			ps4News.setBody("PS4 is said to launch at a starting price of $430");
			ps4News.setCategory(Category.News);
			ps4News.setPublishDate("2013-04-02 10:02:00");		
			ps4News.setFeaturedImagePath("");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			ps4News.setInsertTime("2013-04-02 10:02:00");
			ps4News.setUpdateTime("2013-04-02 10:02:00");
			ps4News.setTimestamp(Utility.convertFromStringToDate("2013-04-02 10:02:00").getTime());
			ArrayList<Platform> sonyPlatforms = new ArrayList<>();
			sonyPlatforms.add(ps3);
			sonyPlatforms.add(vita);
			sonyPlatforms.add(ps4);
			ps4News.setPlatforms(sonyPlatforms);
			ps4News.setState(Article.PUBLISH);
			articleDAOInstance.save(ps4News);
			
			
			dateText="2013-04-02 10:02:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, muchooomg90.getUsername(), ps4News.getId().toString(), AppConstants.PUBLIC,dateText, date.getTime());
			
			Article warnerBrothersNews = new Article();
			warnerBrothersNews.setTitle("Warner Brothers registers Mad Max domains");		
			warnerBrothersNews.setSubtitle("Can this be a new game?");
			warnerBrothersNews.setAuthor("xwarior81");
			warnerBrothersNews.setBody("Warner Brothers registers multiple domains based on Mad Max");
			warnerBrothersNews.setCategory(Category.News);
			warnerBrothersNews.setPublishDate("2013-04-14 10:02:00");		
			warnerBrothersNews.setFeaturedImagePath("");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			warnerBrothersNews.setInsertTime("2013-04-14 10:02:00");
			warnerBrothersNews.setUpdateTime("2013-04-14 10:02:00");
			warnerBrothersNews.setTimestamp(Utility.convertFromStringToDate("2013-04-14 10:02:00").getTime());
			warnerBrothersNews.setPlatforms(platforms);
			warnerBrothersNews.setState(Article.PUBLISH);
			articleDAOInstance.save(warnerBrothersNews);
		
			
			dateText="2013-04-14 10:02:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, xwarior81.getUsername(), warnerBrothersNews.getId().toString(), AppConstants.PUBLIC,dateText, date.getTime());
			
			Article batmanOriginsNews = new Article();
			batmanOriginsNews.setTitle("Batman Origins game revealed");		
			batmanOriginsNews.setSubtitle("A new Batman game is about to arrive");
			batmanOriginsNews.setAuthor("kenhaduken82");
			batmanOriginsNews.setBody("A new batman arkham orignins game revealed. The important thing is rocksteady is not working on it.");
			batmanOriginsNews.setCategory(Category.News);
			batmanOriginsNews.setPublishDate("2013-04-10 10:02:00");		
			batmanOriginsNews.setFeaturedImagePath("");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			batmanOriginsNews.setInsertTime("2013-04-10 10:02:00");
			batmanOriginsNews.setUpdateTime("2013-04-10 10:02:00");
			batmanOriginsNews.setTimestamp(Utility.convertFromStringToDate("2013-04-10 10:02:00").getTime());
			batmanOriginsNews.setPlatforms(platforms);
			batmanOriginsNews.setState(Article.PUBLISH);
			articleDAOInstance.save(batmanOriginsNews);
			
			
			dateText="2013-04-10 10:02:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, kenhaduken82.getUsername(), batmanOriginsNews.getId().toString(), AppConstants.PUBLIC,dateText, date.getTime());
			
			Article supermanGameNews = new Article();
			supermanGameNews.setTitle("Rocksteady is working on supposedly a Superman game.");						
			supermanGameNews.setSubtitle("We can recently get a new superman game.");
			supermanGameNews.setAuthor("loonatic86");
			supermanGameNews.setBody("Rocksteady studios is most probably working on a Superman game. No details are available till now");
			supermanGameNews.setCategory(Category.News);
			supermanGameNews.setPublishDate("2013-04-20 10:02:00");			
			supermanGameNews.setFeaturedImagePath("");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			supermanGameNews.setInsertTime("2013-04-20 10:02:00");
			supermanGameNews.setUpdateTime("2013-04-20 10:02:00");
			supermanGameNews.setTimestamp(Utility.convertFromStringToDate("2013-04-20 10:02:00").getTime());
			supermanGameNews.setPlatforms(platforms);
			supermanGameNews.setState(Article.PUBLISH);
			articleDAOInstance.save(supermanGameNews);
		
			
			dateText="2013-04-20 10:02:00";
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, loonatic86.getUsername(), supermanGameNews.getId().toString(), AppConstants.PUBLIC,dateText, date.getTime());
			
			Article newPrinceOfPersiaGame = new Article();
			Date princeDate= new Date();
			newPrinceOfPersiaGame.setTitle("New Prince of Persia Game surfaced");			
			newPrinceOfPersiaGame.setSubtitle("Another Prince of Persia Game surfaced.");
			newPrinceOfPersiaGame.setAuthor("loonatic87");
			newPrinceOfPersiaGame.setBody("Climax Studio is supposedly working on a new prince of persia game.");
			newPrinceOfPersiaGame.setCategory(Category.News);
			newPrinceOfPersiaGame.setPublishDate(Utility.convertDateToString(princeDate));			
			newPrinceOfPersiaGame.setFeaturedImagePath("");			
			newPrinceOfPersiaGame.setInsertTime(Utility.convertDateToString(princeDate));
			newPrinceOfPersiaGame.setUpdateTime(Utility.convertDateToString(princeDate));
			newPrinceOfPersiaGame.setTimestamp(princeDate.getTime());
			newPrinceOfPersiaGame.setPlatforms(platforms);
			newPrinceOfPersiaGame.setState(Article.PUBLISH);
			articleDAOInstance.save(newPrinceOfPersiaGame);
		
			
			dateText=Utility.convertDateToString(new Date());
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, loonatic87.getUsername(), newPrinceOfPersiaGame.getId().toString(), AppConstants.PUBLIC,dateText, date.getTime());
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = jaguarpaw80;						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("/images/default/featuredBg.png");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			articleDAOInstance.save(farCry3Review);
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = jaguarpaw80;						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("/images/default/featuredBg.png");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			articleDAOInstance.save(farCry3Review);
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = jaguarpaw80;						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("/images/default/featuredBg.png");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			articleDAOInstance.save(farCry3Review);
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = jaguarpaw80;						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("/images/default/featuredBg.png");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			articleDAOInstance.save(farCry3Review);
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = jaguarpaw80;						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("/images/default/featuredBg.png");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			articleDAOInstance.save(farCry3Review);
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = jaguarpaw80;						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("/images/default/featuredBg.png");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			articleDAOInstance.save(farCry3Review);
			
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = jaguarpaw80;						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("/images/default/featuredBg.png");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			articleDAOInstance.save(farCry3Review);
			
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = jaguarpaw80;						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("/images/default/featuredBg.png");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			articleDAOInstance.save(farCry3Review);
			
			
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = jaguarpaw80;						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("/images/default/featuredBg.png");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			articleDAOInstance.save(farCry3Review);
			
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = jaguarpaw80;						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("/images/default/featuredBg.png");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			articleDAOInstance.save(farCry3Review);
			
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = jaguarpaw80;						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("/images/default/featuredBg.png");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			articleDAOInstance.save(farCry3Review);
			
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = jaguarpaw80;						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("/images/default/featuredBg.png");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			articleDAOInstance.save(farCry3Review);
			
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = jaguarpaw80;						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("/images/default/featuredBg.png");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			articleDAOInstance.save(farCry3Review);
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = jaguarpaw80;						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("/images/default/featuredBg.png");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			articleDAOInstance.save(farCry3Review);
			
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = jaguarpaw80;						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("/images/default/featuredBg.png");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = jaguarpaw80;						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("/images/default/featuredBg.png");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = jaguarpaw80;						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("/images/default/featuredBg.png");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = jaguarpaw80;						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("/images/default/featuredBg.png");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = jaguarpaw80;						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("/images/default/featuredBg.png");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = jaguarpaw80;						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("/images/default/featuredBg.png");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = jaguarpaw80;						
			farCry3Review.setAuthor(jaguarpaw80Author);
			farCry3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			farCry3Review.setCategory(Category.Review);
			farCry3Review.setCreationDate(Utility.convertDateToString(new Date()));
			Game farCry3 = gloonDatastore.find(Game.class, "title", "Far Cry 3").get();
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("/images/default/featuredBg.png");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);*/
				
		}
}

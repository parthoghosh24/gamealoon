import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import com.gamealoon.algorithm.RankAlgorithm;
import com.gamealoon.database.daos.AchievementDAO;
import com.gamealoon.database.daos.ActivityDAO;
import com.gamealoon.database.daos.ArticleDAO;
import com.gamealoon.database.daos.GameDAO;
import com.gamealoon.database.daos.PlatformDAO;
import com.gamealoon.database.daos.UserDAO;
import com.gamealoon.models.Achievement;
import com.gamealoon.models.Activity;
import com.gamealoon.models.Article;
import com.gamealoon.models.Category;
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
		
		
		private void createUsers()
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
			partho.setPassword("secret");
			partho.setFirstName("partho");			
			partho.setLastName("ghosh");
			partho.setUsername("loonatic86");
			partho.setDay(24);
			partho.setMonth(3);
			partho.setYear(1986);
			partho.setAvatarPath("myAvatarPath");
			partho.setInsertTime(Utility.convertDateToString(new Date()));
			partho.setGameBio("I love gaming. Started my gaming adventure from 1999 and still going strong...");	
			userDAOInstance.save(partho);
			
			ArrayList<Achievement> parthoAchievements = new ArrayList<>();
			parthoAchievements.add(newGloonie);
			activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, partho.getId().toString(), newGloonie.getId().toString(), AppConstants.PRIVATE, "2012-05-16 22:05:00");
			parthoAchievements.add(glooniacWriter);
			activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, partho.getId().toString(), glooniacWriter.getId().toString(), AppConstants.PRIVATE, "2012-07-16 02:15:00");
			parthoAchievements.add(gloonyAboutgames);
			activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, partho.getId().toString(), gloonyAboutgames.getId().toString(), AppConstants.PRIVATE, "2012-08-20 16:15:00");
			parthoAchievements.add(gloonyAboutVideos);
			activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, partho.getId().toString(), gloonyAboutVideos.getId().toString(), AppConstants.PRIVATE, "2012-09-20 20:15:00");
			partho.setAchievements(parthoAchievements);									
			partho.setAchievementsBasedScore(parthoAchievements.size()*10);
			partho.setVideoUploadBasedScore(50);
			partho.setUserFollowScore(10);
			partho.setArticleBasedScore(600);
			partho.setTotalScore(partho.getAchievementsBasedScore()+partho.getVideoUploadBasedScore()+partho.getUserFollowScore()+partho.getArticleBasedScore());
			userDAOInstance.save(partho);
			
			
			
			


			//user swati
         User swati = new User();
         swati.setEmail("swati.hindu@gmail.com");
         swati.setEmailConfirmed(AppConstants.EMAIL_CONFIRMED);
         swati.setPassword("secret");
         swati.setFirstName("swati");         
         swati.setLastName("mittal");
         swati.setUsername("loonatic87");
         swati.setDay(7);
         swati.setMonth(3);
         swati.setYear(1987);
         swati.setAvatarPath("myAvatarPath");
         swati.setInsertTime(Utility.convertDateToString(new Date()));
         swati.setGameBio("I love racing and puzzle games a lot. Hosted many gaming contests in my college days...");					                         
         userDAOInstance.save(swati);
         
         ArrayList<Achievement> swatiAchievements = new ArrayList<>();
         swatiAchievements.add(newGloonie);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, swati.getId().toString(), newGloonie.getId().toString(), AppConstants.PRIVATE, "2012-09-20 20:15:00");
         swatiAchievements.add(gloonyAboutVideos);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, swati.getId().toString(), gloonyAboutVideos.getId().toString(), AppConstants.PRIVATE, "2012-10-21 17:15:00");
         swati.setAchievements(swatiAchievements);
			
         swati.setAchievementsBasedScore(swatiAchievements.size()*10);
         swati.setVideoUploadBasedScore(70);
         swati.setUserFollowScore(0);
         swati.setArticleBasedScore(200);                
         
         	ArrayList<User> parthoFollowers = new ArrayList<>();
         	ArrayList<User> parthoFollows = new ArrayList<>();
			parthoFollowers.add(swati);						
			parthoFollows.add(swati);
			activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, partho.getId().toString(), swati.getId().toString(), AppConstants.PUBLIC, "2012-10-21 20:10:00");			
			userDAOInstance.save(swati);
			
			
			partho.setFollowedBy(parthoFollowers);
			partho.setFollowing(parthoFollows);
			userDAOInstance.save(partho);
		
			
			ArrayList<User> swatiFollowers = new ArrayList<>();
			ArrayList<User> swatiFollows = new ArrayList<>();
			swatiFollows.add(partho);
			activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, swati.getId().toString(), partho.getId().toString(), AppConstants.PUBLIC, "2012-10-21 19:15:00");
			swatiFollowers.add(partho);
			swati.setUserFollowScore(10);
			swati.setTotalScore(swati.getAchievementsBasedScore()+swati.getVideoUploadBasedScore()+swati.getUserFollowScore()+swati.getArticleBasedScore());
			
			
			
			swati.setFollowedBy(swatiFollowers);
			swati.setFollowing(swatiFollows);
			userDAOInstance.save(swati);
			
			//user bhumika
			User bhumika = new User();
			bhumika.setEmail("bhumika.bhu@gmail.com");
			bhumika.setEmailConfirmed(AppConstants.EMAIL_CONFIRMED);
			bhumika.setPassword("secret");
			bhumika.setFirstName("bhumika");			
			bhumika.setLastName("tiwari");
			bhumika.setUsername("chocobo87");
			bhumika.setDay(11);
			bhumika.setMonth(7);
			bhumika.setYear(1987);
			bhumika.setAvatarPath("myAvatarPath");
			bhumika.setInsertTime(Utility.convertDateToString(new Date()));
			bhumika.setGameBio("I am crazy about old school games like mario and tarzan boy. Recently in love with Temple run...");		
			userDAOInstance.save(bhumika);
         
         ArrayList<Achievement> bhumikaAchievements = new ArrayList<>();
         bhumikaAchievements.add(newGloonie);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, bhumika.getId().toString(), newGloonie.getId().toString(), AppConstants.PRIVATE, "2013-01-01 20:10:00");
         bhumikaAchievements.add(gloonyAboutgames);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, bhumika.getId().toString(), gloonyAboutgames.getId().toString(), AppConstants.PRIVATE, "2013-04-10 17:25:00");
         bhumikaAchievements.add(gloonyAboutVideos);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, bhumika.getId().toString(), gloonyAboutVideos.getId().toString(), AppConstants.PRIVATE, "2013-04-21 21:15:00");
         bhumika.setAchievements(bhumikaAchievements);
			
         bhumika.setAchievementsBasedScore(bhumikaAchievements.size()*10);
         bhumika.setVideoUploadBasedScore(50);   
         bhumika.setArticleBasedScore(250);                
         ArrayList<User> bhumikaFollowers=new ArrayList<>();       
         
         bhumikaFollowers.add(partho);
         
        
         
         bhumikaFollowers.add(swati);
         bhumika.setFollowedBy(bhumikaFollowers);
         bhumika.setUserFollowScore(bhumikaFollowers.size()*10);
         bhumika.setTotalScore(bhumika.getAchievementsBasedScore()+bhumika.getVideoUploadBasedScore()+bhumika.getUserFollowScore()+bhumika.getArticleBasedScore());
         userDAOInstance.save(bhumika);
         
         parthoFollows.add(bhumika);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, partho.getId().toString(), bhumika.getId().toString(), AppConstants.PUBLIC, "2013-01-02 19:15:00");
         partho.setFollowing(parthoFollows);
         userDAOInstance.save(partho);
         
         swatiFollows.add(bhumika);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, swati.getId().toString(), bhumika.getId().toString(), AppConstants.PUBLIC, "2013-01-02 19:16:00");
         swati.setFollowing(swatiFollows);
         userDAOInstance.save(swati);
        
       //user dada
         User dada = new User();
         dada.setEmail("prodipto.ghosh@gmail.com");
         dada.setEmailConfirmed(AppConstants.EMAIL_CONFIRMED);
         dada.setPassword("secret");
         dada.setFirstName("prodipto");         
         dada.setLastName("ghosh");
         dada.setUsername("jaguarpaw80");
         dada.setDay(19);
         dada.setMonth(7);
         dada.setYear(1980);
         dada.setAvatarPath("myAvatarPath");
         dada.setInsertTime(Utility.convertDateToString(new Date()));
         dada.setGameBio("Gaming had been and still a very large part of my life. Love to play every kind of video game...");
         userDAOInstance.save(dada);
         
         ArrayList<Achievement> dadaAchievements = new ArrayList<>();
         dadaAchievements.add(newGloonie);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, dada.getId().toString(), newGloonie.getId().toString(), AppConstants.PRIVATE, "2013-01-03 11:10:00");
         dadaAchievements.add(videoGlooniac);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, dada.getId().toString(), videoGlooniac.getId().toString(), AppConstants.PRIVATE, "2013-05-05 21:10:00");
         dadaAchievements.add(glooniacWriter);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, dada.getId().toString(), glooniacWriter.getId().toString(), AppConstants.PRIVATE, "2013-03-04 14:15:00");
         dadaAchievements.add(gloonyAboutgames);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, dada.getId().toString(), gloonyAboutgames.getId().toString(), AppConstants.PRIVATE, "2013-05-04 19:10:00");
         dadaAchievements.add(gloonyAboutVideos);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, dada.getId().toString(), gloonyAboutVideos.getId().toString(), AppConstants.PRIVATE, "2013-06-06 21:15:00");
         dada.setAchievements(dadaAchievements);
         
			
         dada.setAchievementsBasedScore(dadaAchievements.size()*10);
         dada.setVideoUploadBasedScore(50);   
         dada.setArticleBasedScore(400);                
         ArrayList<User> dadaFollowers=new ArrayList<>();
         dadaFollowers.add(partho);         
         dadaFollowers.add(swati);
         dadaFollowers.add(bhumika);
         dada.setFollowedBy(dadaFollowers);
         dada.setUserFollowScore(dadaFollowers.size()*10);
         dada.setTotalScore(dada.getAchievementsBasedScore()+dada.getVideoUploadBasedScore()+dada.getUserFollowScore()+dada.getArticleBasedScore());
         userDAOInstance.save(dada);
         
         ArrayList<User> bhumikaFollows = new ArrayList<>();
         ArrayList<User> dadaFollows = new ArrayList<>();
         
         parthoFollows.add(dada);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, partho.getId().toString(), dada.getId().toString(), AppConstants.PUBLIC, "2013-01-03 16:19:00");
         partho.setFollowing(parthoFollows);
         userDAOInstance.save(partho);
         
         swatiFollows.add(dada);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, swati.getId().toString(), dada.getId().toString(), AppConstants.PUBLIC, "2013-02-05 18:16:00");
         swati.setFollowing(swatiFollows);
         userDAOInstance.save(swati);
        
         bhumikaFollows.add(dada);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, bhumika.getId().toString(), dada.getId().toString(), AppConstants.PUBLIC, "2013-03-02 19:19:00");
         bhumika.setFollowing(bhumikaFollows);
         userDAOInstance.save(bhumika);
         
         dadaFollows.add(bhumika);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, dada.getId().toString(), bhumika.getId().toString(), AppConstants.PUBLIC, "2013-01-06 20:16:00");
         dada.setFollowing(dadaFollows);
         userDAOInstance.save(dada);
         
         bhumikaFollowers.add(dada);
         
         bhumika.setFollowedBy(bhumikaFollowers);
         bhumika.setUserFollowScore(bhumika.getFollowedBy().size()*10);
         bhumika.setTotalScore(bhumika.getAchievementsBasedScore()+bhumika.getVideoUploadBasedScore()+bhumika.getUserFollowScore()+bhumika.getArticleBasedScore());
         userDAOInstance.save(bhumika);
         
         
       //user buni
         User buni = new User();
         buni.setEmail("karleo84@gmail.com");
         buni.setEmailConfirmed(AppConstants.EMAIL_CONFIRMED);
         buni.setPassword("secret");
         buni.setFirstName("debashree");         
         buni.setLastName("ghosh");
         buni.setUsername("buno84");
         buni.setDay(13);
         buni.setMonth(8);
         buni.setYear(1984);
         buni.setAvatarPath("myAvatarPath");
         buni.setInsertTime(Utility.convertDateToString(new Date()));
         buni.setGameBio("I love mobile gaming. I go crazy and forget everything when i get an android or ios device in hand..");		
         userDAOInstance.save(buni);
         
         ArrayList<Achievement> buniAchievements = new ArrayList<>();
         buniAchievements.add(newGloonie);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, buni.getId().toString(), newGloonie.getId().toString(), AppConstants.PRIVATE, "2013-01-03 13:10:00");
         buniAchievements.add(gloonyAboutgames);                                               
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, buni.getId().toString(), gloonyAboutgames.getId().toString(), AppConstants.PRIVATE, "2013-06-03 19:10:00");
         buni.setAchievements(buniAchievements);
			
         buni.setAchievementsBasedScore(buniAchievements.size()*10);
         buni.setVideoUploadBasedScore(20);   
         buni.setArticleBasedScore(100);                
         ArrayList<User> buniFollowers=new ArrayList<>();
         buniFollowers.add(partho);
         buniFollowers.add(swati);
         buniFollowers.add(bhumika);
         buniFollowers.add(dada);
         buni.setFollowedBy(buniFollowers);
         buni.setUserFollowScore(buniFollowers.size()*10);
         buni.setTotalScore(buni.getAchievementsBasedScore()+buni.getVideoUploadBasedScore()+buni.getUserFollowScore()+buni.getArticleBasedScore());
         userDAOInstance.save(buni);
         
       
         
         parthoFollows.add(buni);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, partho.getId().toString(), buni.getId().toString(), AppConstants.PUBLIC, "2013-01-04 16:19:00");
         partho.setFollowing(parthoFollows);
         userDAOInstance.save(partho);
         
         swatiFollows.add(buni);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, swati.getId().toString(), buni.getId().toString(), AppConstants.PUBLIC, "2013-01-04 17:25:00");
         swati.setFollowing(swatiFollows);
         userDAOInstance.save(swati);
        
         bhumikaFollows.add(buni);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, bhumika.getId().toString(), buni.getId().toString(), AppConstants.PUBLIC, "2013-01-11 12:19:00");
         bhumika.setFollowing(bhumikaFollows);
         userDAOInstance.save(bhumika);
         
         dadaFollows.add(buni);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, dada.getId().toString(), buni.getId().toString(), AppConstants.PUBLIC, "2013-02-03 15:19:00");
         dada.setFollowing(dadaFollows);
         userDAOInstance.save(dada);
         
         ArrayList<User> buniFollows = new ArrayList<>();
         buniFollows.add(dada);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, buni.getId().toString(), dada.getId().toString(), AppConstants.PUBLIC, "2013-02-03 15:29:00");
         buni.setFollowing(buniFollows);
         userDAOInstance.save(buni);
         
         
         dadaFollowers.add(buni);
         dada.setFollowedBy(dadaFollowers);
         dada.setUserFollowScore(dada.getFollowedBy().size()*10);
         dada.setTotalScore(dada.getAchievementsBasedScore()+dada.getVideoUploadBasedScore()+dada.getUserFollowScore()+dada.getArticleBasedScore());
         userDAOInstance.save(dada);
         
         //User neo
         User neo = new User();
         neo.setEmail("neo.anderson@gmail.com");
         neo.setEmailConfirmed(AppConstants.EMAIL_CONFIRMED);
         neo.setPassword("secret");
         neo.setFirstName("neo");         
         neo.setLastName("anderson");
         neo.setUsername("theone90");
         neo.setDay(21);
         neo.setMonth(8);
         neo.setYear(1990);
			neo.setAvatarPath("myAvatarPath");
			neo.setInsertTime(Utility.convertDateToString(new Date()));
			neo.setGameBio("Badly crazy about video games... ");	
			userDAOInstance.save(neo);
         
         ArrayList<Achievement> neoAchievements = new ArrayList<>();
         neoAchievements.add(newGloonie);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, neo.getId().toString(), newGloonie.getId().toString(), AppConstants.PRIVATE, "2013-01-10 13:10:00");
         neoAchievements.add(videoGlooniac);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, neo.getId().toString(), videoGlooniac.getId().toString(), AppConstants.PRIVATE, "2013-03-03 23:15:00");
         neoAchievements.add(glooniacWriter);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, neo.getId().toString(), glooniacWriter.getId().toString(), AppConstants.PRIVATE, "2013-04-05 03:10:00");
         neoAchievements.add(gloonyAboutgames);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, neo.getId().toString(), gloonyAboutgames.getId().toString(), AppConstants.PRIVATE, "2013-04-11 16:10:00");
         neoAchievements.add(gloonyAboutVideos);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, neo.getId().toString(), gloonyAboutVideos.getId().toString(), AppConstants.PRIVATE, "2013-10-03 04:10:00");
         neo.setAchievements(neoAchievements);
			
         neo.setAchievementsBasedScore(neoAchievements.size()*10);
         neo.setVideoUploadBasedScore(100);   
         neo.setArticleBasedScore(450);                
         ArrayList<User> neoFollowers=new ArrayList<>();
         neoFollowers.add(partho);
         neoFollowers.add(swati);
         neoFollowers.add(bhumika);
         neoFollowers.add(dada);
         neo.setFollowedBy(neoFollowers);
         neo.setUserFollowScore(neoFollowers.size()*10);
         neo.setTotalScore(neo.getAchievementsBasedScore()+neo.getVideoUploadBasedScore()+neo.getUserFollowScore()+neo.getArticleBasedScore());
         userDAOInstance.save(neo);
         
         parthoFollows.add(neo);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, partho.getId().toString(), neo.getId().toString(), AppConstants.PUBLIC, "2013-01-10 16:00:00");
         partho.setFollowing(parthoFollows);
         userDAOInstance.save(partho);
         
         swatiFollows.add(neo);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, swati.getId().toString(), neo.getId().toString(), AppConstants.PUBLIC, "2013-01-11 16:00:01");
         swati.setFollowing(swatiFollows);
         userDAOInstance.save(swati);
        
         bhumikaFollows.add(neo);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, bhumika.getId().toString(), neo.getId().toString(), AppConstants.PUBLIC, "2013-02-03 17:59:00");
         bhumika.setFollowing(bhumikaFollows);
         userDAOInstance.save(bhumika);
         
         dadaFollows.add(neo);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, dada.getId().toString(), neo.getId().toString(), AppConstants.PUBLIC, "2013-02-09 14:20:00");
         dada.setFollowing(dadaFollows);
         userDAOInstance.save(dada);
         
         
         //User brian
         User brian = new User();
         brian.setEmail("brian.mcfury@gmail.com");
         brian.setEmailConfirmed(AppConstants.EMAIL_CONFIRMED);
         brian.setPassword("secret");
         brian.setFirstName("brian");         
         brian.setLastName("mcfury");
         brian.setUsername("brianzilla84");
         brian.setDay(5);
         brian.setMonth(1);
         brian.setYear(1984);
         brian.setAvatarPath("myAvatarPath");
			brian.setInsertTime(Utility.convertDateToString(new Date()));
			brian.setGameBio("Raised amongst nintendo, commodore 64, playstation...");		
			userDAOInstance.save(brian);
         
         ArrayList<Achievement> brianAchievements = new ArrayList<>();
         brianAchievements.add(newGloonie);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, brian.getId().toString(), newGloonie.getId().toString(), AppConstants.PRIVATE, "2013-01-11 16:10:00");
         brianAchievements.add(videoGlooniac);                
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, brian.getId().toString(), videoGlooniac.getId().toString(), AppConstants.PRIVATE, "2013-02-12 13:11:00");
         brianAchievements.add(gloonyAboutgames);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, brian.getId().toString(), gloonyAboutgames.getId().toString(), AppConstants.PRIVATE, "2013-03-14 10:09:00");
         brianAchievements.add(gloonyAboutVideos);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, brian.getId().toString(), gloonyAboutVideos.getId().toString(), AppConstants.PRIVATE, "2013-05-10 02:10:00");
         brian.setAchievements(brianAchievements);
			
         brian.setAchievementsBasedScore(brianAchievements.size()*10);
         brian.setVideoUploadBasedScore(150);   
         brian.setArticleBasedScore(250);                
         ArrayList<User> brianFollowers=new ArrayList<>();
         brianFollowers.add(partho);
         brianFollowers.add(swati);
         brianFollowers.add(bhumika);
         brianFollowers.add(neo);
         brian.setFollowedBy(brianFollowers);
         brian.setUserFollowScore(brianFollowers.size()*10);
         brian.setTotalScore(brian.getAchievementsBasedScore()+brian.getVideoUploadBasedScore()+brian.getUserFollowScore()+brian.getArticleBasedScore());
         userDAOInstance.save(brian);
         
         ArrayList<User> neoFollows = new ArrayList<>();
         
         parthoFollows.add(brian);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, partho.getId().toString(), brian.getId().toString(), AppConstants.PUBLIC, "2013-02-12 14:20:00");
         partho.setFollowing(parthoFollows);
         userDAOInstance.save(partho);
         
         swatiFollows.add(brian);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, swati.getId().toString(), brian.getId().toString(), AppConstants.PUBLIC, "2013-03-15 18:21:00");
         swati.setFollowing(swatiFollows);
         userDAOInstance.save(swati);
        
         bhumikaFollows.add(brian);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, bhumika.getId().toString(), brian.getId().toString(), AppConstants.PUBLIC, "2013-04-16 14:21:00");
         bhumika.setFollowing(bhumikaFollows);
         userDAOInstance.save(bhumika);
         
         neoFollows.add(brian);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, neo.getId().toString(), brian.getId().toString(), AppConstants.PUBLIC, "2013-02-09 14:20:00");
         neo.setFollowing(neoFollows);
         userDAOInstance.save(neo);
         
         
         //User ken
         User ken = new User();
         ken.setEmail("ken.smith@gmail.com");
         ken.setEmailConfirmed(AppConstants.EMAIL_CONFIRMED);
         ken.setPassword("secret");
         ken.setFirstName("ken");         
         ken.setLastName("smith");
         ken.setUsername("kenhaduken82");
         ken.setDay(5);
         ken.setMonth(6);
         ken.setYear(1982);
         ken.setAvatarPath("myAvatarPath");
         ken.setInsertTime(Utility.convertDateToString(new Date()));
         ken.setGameBio("I love gaming and my name resembles a famous character in gaming.");	
         userDAOInstance.save(ken);
         
         ArrayList<Achievement> kenAchievements = new ArrayList<>();
         kenAchievements.add(newGloonie);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, ken.getId().toString(), newGloonie.getId().toString(), AppConstants.PRIVATE, "2013-01-12 14:10:00");
         kenAchievements.add(videoGlooniac);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, ken.getId().toString(), videoGlooniac.getId().toString(), AppConstants.PRIVATE, "2013-05-10 13:10:00");
         kenAchievements.add(glooniacWriter);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, ken.getId().toString(), glooniacWriter.getId().toString(), AppConstants.PRIVATE, "2013-07-19 17:10:00");
         kenAchievements.add(gloonyAboutgames);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, ken.getId().toString(), gloonyAboutgames.getId().toString(), AppConstants.PRIVATE, "2013-09-18 16:19:00");
         kenAchievements.add(gloonyAboutVideos);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, ken.getId().toString(), gloonyAboutVideos.getId().toString(), AppConstants.PRIVATE, "2013-09-20 23:10:00");
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
         userDAOInstance.save(ken);
         
         parthoFollows.add(ken);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, partho.getId().toString(), ken.getId().toString(), AppConstants.PUBLIC, "2013-02-09 14:25:00");
         partho.setFollowing(parthoFollows);
         userDAOInstance.save(partho);
         
         swatiFollows.add(ken);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, swati.getId().toString(), ken.getId().toString(), AppConstants.PUBLIC, "2013-03-19 15:20:00");
         swati.setFollowing(swatiFollows);
         userDAOInstance.save(swati);
        
         bhumikaFollows.add(ken);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, bhumika.getId().toString(), ken.getId().toString(), AppConstants.PUBLIC, "2013-02-10 14:20:00");
         bhumika.setFollowing(bhumikaFollows);
         userDAOInstance.save(bhumika);
         
         neoFollows.add(ken);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, neo.getId().toString(), ken.getId().toString(), AppConstants.PUBLIC, "2013-02-05 11:20:00");
         neo.setFollowing(neoFollows);
         userDAOInstance.save(neo);
         
         dadaFollows.add(ken);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, dada.getId().toString(), ken.getId().toString(), AppConstants.PUBLIC, "2013-04-19 16:20:00");
         dada.setFollowing(dadaFollows);
         userDAOInstance.save(dada);
         
         buniFollows.add(ken);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, buni.getId().toString(), ken.getId().toString(), AppConstants.PUBLIC, "2013-04-20 04:20:00");
         buni.setFollowing(buniFollows);
         userDAOInstance.save(buni);
         
         ArrayList<User> brianFollows = new ArrayList<>(); 
         brianFollows.add(ken);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, brian.getId().toString(), ken.getId().toString(), AppConstants.PUBLIC, "2013-04-27 15:20:00");
         brian.setFollowing(brianFollows);
         userDAOInstance.save(brian);
         
         //User anand
         User anand = new User();
         anand.setEmail("anand.srinivas@gmail.com");
         anand.setEmailConfirmed(AppConstants.EMAIL_CONFIRMED);
         anand.setPassword("secret");
         anand.setFirstName("anand");         
         anand.setLastName("srinivas");
         anand.setUsername("anandcrazygamer89");
         anand.setDay(9);
         anand.setMonth(8);
         anand.setYear(1989);
         anand.setAvatarPath("myAvatarPath");
         anand.setInsertTime(Utility.convertDateToString(new Date()));
         anand.setGameBio("Had been always crazy about video games from my childhood. FIFA had been mine favorite series till now.");
         userDAOInstance.save(anand);
         
         ArrayList<Achievement> anandAchievements = new ArrayList<>();
         anandAchievements.add(newGloonie);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, anand.getId().toString(), newGloonie.getId().toString(), AppConstants.PRIVATE, "2013-01-13 15:10:00");
         anandAchievements.add(videoGlooniac);                
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, anand.getId().toString(), videoGlooniac.getId().toString(), AppConstants.PRIVATE, "2013-04-20 00:10:00");
         anandAchievements.add(gloonyAboutgames);                
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, anand.getId().toString(), gloonyAboutgames.getId().toString(), AppConstants.PRIVATE, "2013-06-10 03:10:00");
         anand.setAchievements(anandAchievements);
			
         anand.setAchievementsBasedScore(anandAchievements.size()*10);
         anand.setVideoUploadBasedScore(100);   
         anand.setArticleBasedScore(200);                
         ArrayList<User> anandFollowers=new ArrayList<>();
         anandFollowers.add(partho);
         anandFollowers.add(swati);
         anandFollowers.add(bhumika);
         anandFollowers.add(dada);
         anandFollowers.add(ken);
         anandFollowers.add(neo);
         anand.setFollowedBy(anandFollowers);
         anand.setUserFollowScore(dadaFollowers.size()*10);
         anand.setTotalScore(anand.getAchievementsBasedScore()+anand.getVideoUploadBasedScore()+anand.getUserFollowScore()+anand.getArticleBasedScore());
         userDAOInstance.save(anand);
         
         parthoFollows.add(anand);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, partho.getId().toString(), anand.getId().toString(), AppConstants.PUBLIC, "2013-01-27 15:20:00");
         partho.setFollowing(parthoFollows);
         userDAOInstance.save(partho);
         
         swatiFollows.add(anand);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, swati.getId().toString(), anand.getId().toString(), AppConstants.PUBLIC, "2013-02-19 15:29:00");
         swati.setFollowing(swatiFollows);
         userDAOInstance.save(swati);
        
         bhumikaFollows.add(anand);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, bhumika.getId().toString(), anand.getId().toString(), AppConstants.PUBLIC, "2013-02-01 00:20:00");
         bhumika.setFollowing(bhumikaFollows);
         userDAOInstance.save(bhumika);
         
         neoFollows.add(anand);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, neo.getId().toString(), anand.getId().toString(), AppConstants.PUBLIC, "2013-01-28 19:28:00");
         neo.setFollowing(neoFollows);
         userDAOInstance.save(neo);
         
         dadaFollows.add(anand);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, dada.getId().toString(), anand.getId().toString(), AppConstants.PUBLIC, "2013-01-29 17:20:00");
         dada.setFollowing(dadaFollows);
         userDAOInstance.save(dada);                  
         
         ArrayList<User> kenFollows = new ArrayList<>(); 
         kenFollows.add(anand);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, brian.getId().toString(), ken.getId().toString(), AppConstants.PUBLIC, "2013-04-27 15:20:00");
         brian.setFollowing(kenFollows);
         userDAOInstance.save(ken);
         
         //User radha
         User radha = new User();
         radha.setEmail("radha.gupta@gmail.com");
         radha.setEmailConfirmed(AppConstants.EMAIL_CONFIRMED);
         radha.setPassword("secret");
         radha.setFirstName("radha");         
         radha.setLastName("gupta");
         radha.setUsername("radhashooter95");
         radha.setDay(12);
         radha.setMonth(3);
         radha.setYear(1995);
         radha.setAvatarPath("myAvatarPath");
         radha.setInsertTime(Utility.convertDateToString(new Date()));
         radha.setGameBio("Proud to be a gamer. Won many awards in many gaming tournaments. Big fan of FPS and racing games.");			
         userDAOInstance.save(radha);
         
         ArrayList<Achievement> radhaAchievements = new ArrayList<>();
         radhaAchievements.add(newGloonie);                
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, radha.getId().toString(), newGloonie.getId().toString(), AppConstants.PRIVATE, "2013-01-15 15:10:00");
         radhaAchievements.add(gloonyAboutgames);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, radha.getId().toString(), gloonyAboutgames.getId().toString(), AppConstants.PRIVATE, "2013-03-13 16:20:00");
         radhaAchievements.add(gloonyAboutVideos);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, radha.getId().toString(), gloonyAboutVideos.getId().toString(), AppConstants.PRIVATE, "2013-07-23 11:19:00");
         radha.setAchievements(radhaAchievements);
			
         radha.setAchievementsBasedScore(radhaAchievements.size()*10);
         radha.setVideoUploadBasedScore(40);   
         radha.setArticleBasedScore(190);                
         ArrayList<User> radhaFollowers=new ArrayList<>();
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
         userDAOInstance.save(radha);
         
         parthoFollows.add(radha);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, partho.getId().toString(), radha.getId().toString(), AppConstants.PUBLIC, "2013-01-15 14:20:00");
         partho.setFollowing(parthoFollows);
         userDAOInstance.save(partho);
         
         swatiFollows.add(radha);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, swati.getId().toString(), radha.getId().toString(), AppConstants.PUBLIC, "2013-02-27 15:25:00");
         swati.setFollowing(swatiFollows);
         userDAOInstance.save(swati);
        
         bhumikaFollows.add(radha);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, bhumika.getId().toString(), radha.getId().toString(), AppConstants.PUBLIC, "2013-03-03 19:20:00");
         bhumika.setFollowing(bhumikaFollows);
         userDAOInstance.save(bhumika);
         
         neoFollows.add(radha);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, neo.getId().toString(), radha.getId().toString(), AppConstants.PUBLIC, "2013-03-05 11:40:00");
         neo.setFollowing(neoFollows);
         userDAOInstance.save(neo);
         
         buniFollows.add(radha);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, buni.getId().toString(), radha.getId().toString(), AppConstants.PUBLIC, "2013-03-05 15:20:00");
         buni.setFollowing(buniFollows);
         userDAOInstance.save(buni);      
         
         kenFollows.add(radha);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, ken.getId().toString(), radha.getId().toString(), AppConstants.PUBLIC, "2013-04-11 19:20:00");
         brian.setFollowing(kenFollows);
         userDAOInstance.save(ken);
         
         brianFollows.add(radha);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, brian.getId().toString(), radha.getId().toString(), AppConstants.PUBLIC, "2013-04-18 17:25:00");
         brian.setFollowing(brianFollows);
         userDAOInstance.save(brian);
         
         ArrayList<User> anandFollows = new ArrayList<>(); 
         anandFollows.add(radha);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, anand.getId().toString(), radha.getId().toString(), AppConstants.PUBLIC, "2013-05-01 05:20:00");
         anand.setFollowing(anandFollows);
         userDAOInstance.save(anand);
         
         //User tiffany
         User tiffany = new User();
         tiffany.setEmail("tiffany.parker@gmail.com");
         tiffany.setEmailConfirmed(AppConstants.EMAIL_CONFIRMED);
         tiffany.setPassword("secret");
         tiffany.setFirstName("tiffany");         
         tiffany.setLastName("parker");
         tiffany.setUsername("tparkerponny98");
         tiffany.setDay(5);
         tiffany.setMonth(6);
         tiffany.setYear(1998);
         tiffany.setAvatarPath("myAvatarPath");
         tiffany.setInsertTime(Utility.convertDateToString(new Date()));
         tiffany.setGameBio("Love video gaming.");			
         userDAOInstance.save(tiffany);
         
         ArrayList<Achievement> tiffanyAchievements = new ArrayList<>();
         tiffanyAchievements.add(newGloonie);                
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, tiffany.getId().toString(), newGloonie.getId().toString(), AppConstants.PRIVATE, "2013-01-16 15:10:00");
         tiffanyAchievements.add(glooniacWriter);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, tiffany.getId().toString(), glooniacWriter.getId().toString(), AppConstants.PRIVATE, "2013-04-15 14:15:00");
         tiffanyAchievements.add(gloonyAboutgames);                
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, tiffany.getId().toString(), gloonyAboutgames.getId().toString(), AppConstants.PRIVATE, "2013-09-25 04:00:00");
         tiffany.setAchievements(tiffanyAchievements);
			
         tiffany.setAchievementsBasedScore(tiffanyAchievements.size()*10);
         tiffany.setVideoUploadBasedScore(40);   
         tiffany.setArticleBasedScore(500);                
         ArrayList<User> tiffanyFollowers=new ArrayList<>();
         tiffanyFollowers.add(partho);
         tiffanyFollowers.add(swati);
         tiffanyFollowers.add(bhumika);
         tiffanyFollowers.add(radha);
         tiffanyFollowers.add(buni);
         tiffanyFollowers.add(ken);
         tiffany.setFollowedBy(tiffanyFollowers);
         tiffany.setUserFollowScore(tiffanyFollowers.size()*10);
         tiffany.setTotalScore(tiffany.getAchievementsBasedScore()+tiffany.getVideoUploadBasedScore()+tiffany.getUserFollowScore()+tiffany.getArticleBasedScore());
         userDAOInstance.save(tiffany);
         
         parthoFollows.add(tiffany);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, partho.getId().toString(), tiffany.getId().toString(), AppConstants.PUBLIC, "2013-01-17 15:20:00");
         partho.setFollowing(parthoFollows);
         userDAOInstance.save(partho);
         
         swatiFollows.add(tiffany);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, swati.getId().toString(), tiffany.getId().toString(), AppConstants.PUBLIC, "2013-03-01 20:20:00");
         swati.setFollowing(swatiFollows);
         userDAOInstance.save(swati);
        
         bhumikaFollows.add(tiffany);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, bhumika.getId().toString(), tiffany.getId().toString(), AppConstants.PUBLIC, "2013-04-05 21:20:00");
         bhumika.setFollowing(bhumikaFollows);
         userDAOInstance.save(bhumika);                  
         
         buniFollows.add(tiffany);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, buni.getId().toString(), tiffany.getId().toString(), AppConstants.PUBLIC, "2013-05-01 06:20:00");
         buni.setFollowing(buniFollows);
         userDAOInstance.save(buni);      
         
         kenFollows.add(tiffany);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, ken.getId().toString(), tiffany.getId().toString(), AppConstants.PUBLIC, "2013-05-06 15:21:00");
         brian.setFollowing(kenFollows);
         userDAOInstance.save(ken);                  
         
         ArrayList<User> radhaFollows = new ArrayList<>(); 
         radhaFollows.add(tiffany);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, radha.getId().toString(), tiffany.getId().toString(), AppConstants.PUBLIC, "2013-05-11 15:20:00");
         radha.setFollowing(radhaFollows);
         userDAOInstance.save(radha);
         
         //User xiang
         User xiang = new User();
         xiang.setEmail("xiang.hu@gmail.com");
         xiang.setEmailConfirmed(AppConstants.EMAIL_CONFIRMED);
         xiang.setPassword("secret");
         xiang.setFirstName("xiang");         
         xiang.setLastName("hu");
         xiang.setUsername("xwarior81");
         xiang.setDay(2);
         xiang.setMonth(5);
         xiang.setYear(1981);
         xiang.setAvatarPath("myAvatarPath");
         xiang.setInsertTime(Utility.convertDateToString(new Date()));
         xiang.setGameBio("Love video games");				
         userDAOInstance.save(xiang);
         
         ArrayList<Achievement> xiangAchievements = new ArrayList<>();
         xiangAchievements.add(newGloonie);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, xiang.getId().toString(), newGloonie.getId().toString(), AppConstants.PRIVATE, "2013-01-16 19:10:00");
         xiangAchievements.add(videoGlooniac);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, xiang.getId().toString(), videoGlooniac.getId().toString(), AppConstants.PRIVATE, "2013-02-16 00:10:00");
         xiangAchievements.add(glooniacWriter);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, xiang.getId().toString(), glooniacWriter.getId().toString(), AppConstants.PRIVATE, "2013-05-26 04:10:00");
         xiangAchievements.add(gloonyAboutgames);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, xiang.getId().toString(), gloonyAboutgames.getId().toString(), AppConstants.PRIVATE, "2013-05-28 01:10:00");
         xiangAchievements.add(gloonyAboutVideos);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, xiang.getId().toString(), gloonyAboutVideos.getId().toString(), AppConstants.PRIVATE, "2013-06-01 19:10:00");
         xiang.setAchievements(xiangAchievements);
			
         xiang.setAchievementsBasedScore(xiangAchievements.size()*10);
         xiang.setVideoUploadBasedScore(150);   
         xiang.setArticleBasedScore(650);                
         ArrayList<User> xiangFollowers=new ArrayList<>();
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
         userDAOInstance.save(xiang);
         
         
         parthoFollows.add(xiang);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, partho.getId().toString(), xiang.getId().toString(), AppConstants.PUBLIC, "2013-01-17 15:20:00");
         partho.setFollowing(parthoFollows);
         userDAOInstance.save(partho);
         
         swatiFollows.add(xiang);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, swati.getId().toString(), xiang.getId().toString(), AppConstants.PUBLIC, "2013-01-18 19:20:00");
         swati.setFollowing(swatiFollows);
         userDAOInstance.save(swati);
        
         bhumikaFollows.add(xiang);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, bhumika.getId().toString(), xiang.getId().toString(), AppConstants.PUBLIC, "2013-02-01 00:20:00");
         bhumika.setFollowing(bhumikaFollows);
         userDAOInstance.save(bhumika);                  
         
         dadaFollows.add(xiang);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, dada.getId().toString(), xiang.getId().toString(), AppConstants.PUBLIC, "2013-01-19 19:35:00");
         dada.setFollowing(dadaFollows);
         userDAOInstance.save(dada);                  
         
         ArrayList<User> tiffanyFollows = new ArrayList<>(); 
         tiffanyFollows.add(xiang);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, tiffany.getId().toString(), xiang.getId().toString(), AppConstants.PUBLIC, "2013-03-11 19:20:00");
         tiffany.setFollowing(tiffanyFollows);
         userDAOInstance.save(tiffany);
         
         neoFollows.add(xiang);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, neo.getId().toString(), xiang.getId().toString(), AppConstants.PUBLIC, "2013-03-21 16:20:00");
         neo.setFollowing(neoFollows);
         userDAOInstance.save(neo);         
         
         kenFollows.add(xiang);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, ken.getId().toString(), xiang.getId().toString(), AppConstants.PUBLIC, "2013-04-01 14:20:00");
         brian.setFollowing(kenFollows);
         userDAOInstance.save(ken);
         
         brianFollows.add(xiang);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, brian.getId().toString(), xiang.getId().toString(), AppConstants.PUBLIC, "2013-04-13 16:20:00");
         brian.setFollowing(brianFollows);
         userDAOInstance.save(brian);
         
         
         //User ted
         User ted = new User();
         ted.setEmail("ted.muchoo@gmail.com");
         ted.setEmailConfirmed(AppConstants.EMAIL_CONFIRMED);
         ted.setPassword("secret");
         ted.setFirstName("ted");         
         ted.setLastName("muchoo");
         ted.setUsername("muchooomg90");
         ted.setDay(10);
         ted.setMonth(1);
			ted.setYear(1990);
			ted.setAvatarPath("myAvatarPath");
			ted.setInsertTime(Utility.convertDateToString(new Date()));
			ted.setGameBio("blah blah blah.... nuff said, lemme play video games now... bye");		
			userDAOInstance.save(ted);
         
         ArrayList<Achievement> tedAchievements = new ArrayList<>();
         tedAchievements.add(newGloonie);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, ted.getId().toString(), newGloonie.getId().toString(), AppConstants.PRIVATE, "2013-01-17 18:05:00");
         tedAchievements.add(videoGlooniac);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, ted.getId().toString(), videoGlooniac.getId().toString(), AppConstants.PRIVATE, "2013-03-19 20:10:00");
         tedAchievements.add(glooniacWriter);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, ted.getId().toString(), glooniacWriter.getId().toString(), AppConstants.PRIVATE, "2013-03-26 16:10:00");
         tedAchievements.add(gloonyAboutgames);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, ted.getId().toString(), gloonyAboutgames.getId().toString(), AppConstants.PRIVATE, "2013-04-16 09:10:00");
         tedAchievements.add(gloonyAboutVideos);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, ted.getId().toString(), gloonyAboutVideos.getId().toString(), AppConstants.PRIVATE, "2013-05-16 00:10:00");
         ted.setAchievements(tedAchievements);
			
         ted.setAchievementsBasedScore(tedAchievements.size()*10);
         ted.setVideoUploadBasedScore(100);   
         ted.setArticleBasedScore(500);                
         ArrayList<User> tedFollowers=new ArrayList<>();
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
         userDAOInstance.save(ted);
         
         parthoFollows.add(ted);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, partho.getId().toString(), ted.getId().toString(), AppConstants.PUBLIC, "2013-01-18 15:20:00");
         partho.setFollowing(parthoFollows);
         userDAOInstance.save(partho);
         
         swatiFollows.add(ted);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, swati.getId().toString(), ted.getId().toString(), AppConstants.PUBLIC, "2013-02-18 16:20:00");
         swati.setFollowing(swatiFollows);
         userDAOInstance.save(swati);
        
         bhumikaFollows.add(ted);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, bhumika.getId().toString(), ted.getId().toString(), AppConstants.PUBLIC, "2013-01-17 05:20:00");
         bhumika.setFollowing(bhumikaFollows);
         userDAOInstance.save(bhumika);                  
         
         dadaFollows.add(ted);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, dada.getId().toString(), ted.getId().toString(), AppConstants.PUBLIC, "2013-01-20 19:20:00");
         dada.setFollowing(dadaFollows);
         userDAOInstance.save(dada);                  
         
         ArrayList<User> xiangFollows = new ArrayList<>(); 
         xiangFollows.add(ted);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, xiang.getId().toString(), ted.getId().toString(), AppConstants.PUBLIC, "2013-03-21 00:20:00");
         xiang.setFollowing(xiangFollows);
         userDAOInstance.save(xiang);
         
         neoFollows.add(ted);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, neo.getId().toString(), ted.getId().toString(), AppConstants.PUBLIC, "2013-04-16 02:20:00");
         neo.setFollowing(neoFollows);
         userDAOInstance.save(neo);         
         
         tiffanyFollows.add(ted);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, tiffany.getId().toString(), ted.getId().toString(), AppConstants.PUBLIC, "2013-04-17 16:20:00");
         tiffany.setFollowing(tiffanyFollows);
         userDAOInstance.save(tiffany);
         
         brianFollows.add(ted);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, brian.getId().toString(), ted.getId().toString(), AppConstants.PUBLIC, "2013-04-18 19:20:00");
         brian.setFollowing(brianFollows);
         userDAOInstance.save(brian);
         
         anandFollows.add(ted);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, anand.getId().toString(), ted.getId().toString(), AppConstants.PUBLIC, "2013-05-19 20:20:00");
         anand.setFollowing(anandFollows);
         userDAOInstance.save(anand);
         
         
         //User john
         User john = new User();
         john.setEmail("john.doe@gmail.com");
         john.setEmailConfirmed(AppConstants.EMAIL_CONFIRMED);
         john.setPassword("secret");
         john.setFirstName("john");         
         john.setLastName("doe");
         john.setUsername("rayray86");
         john.setDay(24);
         john.setMonth(3);
			john.setYear(1986);
			john.setAvatarPath("myAvatarPath");
			john.setInsertTime(Utility.convertDateToString(new Date()));
			john.setGameBio("Love doing pew pew...");				
			userDAOInstance.save(john);
         
         ArrayList<Achievement> johnAchievements =new ArrayList<>();
         johnAchievements.add(newGloonie);                      
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, john.getId().toString(), newGloonie.getId().toString(), AppConstants.PRIVATE, "2013-01-18 18:05:00");
         johnAchievements.add(gloonyAboutgames);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, john.getId().toString(), gloonyAboutgames.getId().toString(), AppConstants.PRIVATE, "2013-05-17 00:05:00");
         johnAchievements.add(gloonyAboutVideos);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, john.getId().toString(), gloonyAboutVideos.getId().toString(), AppConstants.PRIVATE, "2013-07-27 08:05:00");
         john.setAchievements(johnAchievements);
			
         john.setAchievementsBasedScore(johnAchievements.size()*10);
         john.setVideoUploadBasedScore(200);   
         john.setArticleBasedScore(300);                
         ArrayList<User> johnFollowers=new ArrayList<>();
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
         userDAOInstance.save(john);
         
         parthoFollows.add(john);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, partho.getId().toString(), john.getId().toString(), AppConstants.PUBLIC, "2013-01-19 15:20:00");
         partho.setFollowing(parthoFollows);
         userDAOInstance.save(partho);
         
         swatiFollows.add(john);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, swati.getId().toString(), john.getId().toString(), AppConstants.PUBLIC, "2013-01-28 17:20:00");
         swati.setFollowing(swatiFollows);
         userDAOInstance.save(swati);
        
         ArrayList<User> tedFollows = new ArrayList<>(); 
         tedFollows.add(john);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, ted.getId().toString(), john.getId().toString(), AppConstants.PUBLIC, "2013-02-18 00:20:00");
         ted.setFollowing(tedFollows);
         userDAOInstance.save(ted);                 
         
         dadaFollows.add(john);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, dada.getId().toString(), john.getId().toString(), AppConstants.PUBLIC, "2013-02-20 16:20:00");
         dada.setFollowing(dadaFollows);
         userDAOInstance.save(dada);                  
                  
         xiangFollows.add(john);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, xiang.getId().toString(), john.getId().toString(), AppConstants.PUBLIC, "2013-03-18 06:20:00");
         xiang.setFollowing(xiangFollows);
         userDAOInstance.save(xiang);
         
         radhaFollows.add(john);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, radha.getId().toString(), john.getId().toString(), AppConstants.PUBLIC, "2013-03-28 03:20:00");
         radha.setFollowing(radhaFollows);
         userDAOInstance.save(radha);         
         
         tiffanyFollows.add(john);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, tiffany.getId().toString(), john.getId().toString(), AppConstants.PUBLIC, "2013-04-04 19:20:00");
         tiffany.setFollowing(tiffanyFollows);
         userDAOInstance.save(tiffany);         
         
         anandFollows.add(john);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, anand.getId().toString(), john.getId().toString(), AppConstants.PUBLIC, "2013-05-17 22:20:00");
         anand.setFollowing(anandFollows);
         userDAOInstance.save(anand);
         
         
         //User jane
         User jane = new User();
         jane.setEmail("jane.doe@gmail.com");
         jane.setEmailConfirmed(AppConstants.EMAIL_CONFIRMED);
         jane.setPassword("secret");
         jane.setFirstName("jane");         
         jane.setLastName("doe");
         jane.setUsername("pookie87");
         jane.setDay(7);
         jane.setMonth(3);
			jane.setYear(1987);
			jane.setAvatarPath("myAvatarPath");
			jane.setInsertTime(Utility.convertDateToString(new Date()));
			jane.setGameBio("I find gaming fun, exciting and beautiful");
			 userDAOInstance.save(jane);
         
         ArrayList<Achievement> janeAchievements = new ArrayList<>();
         janeAchievements.add(newGloonie);               
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, jane.getId().toString(), newGloonie.getId().toString(), AppConstants.PRIVATE, "2013-01-20 18:05:00");
         janeAchievements.add(gloonyAboutgames);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, jane.getId().toString(), gloonyAboutgames.getId().toString(), AppConstants.PRIVATE, "2013-05-18 00:05:00");
         janeAchievements.add(gloonyAboutVideos);
         activityDAOInstance.create(Activity.ACTIVITY_NEW_ACHIEVMENT, jane.getId().toString(), gloonyAboutVideos.getId().toString(), AppConstants.PRIVATE, "2013-10-18 16:15:00");
         jane.setAchievements(janeAchievements);
			
         jane.setAchievementsBasedScore(janeAchievements.size()*10);
         jane.setVideoUploadBasedScore(30);   
         jane.setArticleBasedScore(120);                
         ArrayList<User> janeFollowers=new ArrayList<>();
         janeFollowers.add(partho);
         janeFollowers.add(swati);
         janeFollowers.add(bhumika);
         janeFollowers.add(dada);
         janeFollowers.add(buni);
         janeFollowers.add(neo);
         janeFollowers.add(john);
         jane.setFollowedBy(janeFollowers);
         jane.setUserFollowScore(janeFollowers.size()*10);
         jane.setTotalScore(jane.getAchievementsBasedScore()+jane.getVideoUploadBasedScore()+jane.getUserFollowScore()+jane.getArticleBasedScore());
         userDAOInstance.save(jane);
         
         
         parthoFollows.add(jane);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, partho.getId().toString(), jane.getId().toString(), AppConstants.PUBLIC, "2013-01-20 00:20:00");
         partho.setFollowing(parthoFollows);
         userDAOInstance.save(partho);
         
         swatiFollows.add(jane);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, swati.getId().toString(), jane.getId().toString(), AppConstants.PUBLIC, "2013-02-19 15:20:00");
         swati.setFollowing(swatiFollows);
         userDAOInstance.save(swati);
        
         ArrayList<User> johnFollows = new ArrayList<>(); 
         johnFollows.add(jane);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, john.getId().toString(), jane.getId().toString(), AppConstants.PUBLIC, "2013-02-15 23:20:00");
         ted.setFollowing(johnFollows);
         userDAOInstance.save(john);                 
         
         dadaFollows.add(jane);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, dada.getId().toString(), jane.getId().toString(), AppConstants.PUBLIC, "2013-03-19 11:20:00");
         dada.setFollowing(dadaFollows);
         userDAOInstance.save(dada); 
         
         bhumikaFollows.add(jane);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, bhumika.getId().toString(), jane.getId().toString(), AppConstants.PUBLIC, "2013-05-21 12:20:00");
         bhumika.setFollowing(bhumikaFollows);
         userDAOInstance.save(bhumika);    
         
         neoFollows.add(jane);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, neo.getId().toString(), jane.getId().toString(), AppConstants.PUBLIC, "2013-04-19 17:20:00");
         neo.setFollowing(neoFollows);
         userDAOInstance.save(neo);   
         
         buniFollows.add(jane);
         activityDAOInstance.create(Activity.ACTIVITY_USER_FOLLOWS, buni.getId().toString(), jane.getId().toString(), AppConstants.PUBLIC, "2013-06-19 16:20:00");
         buni.setFollowing(buniFollows);
         userDAOInstance.save(buni);  
         
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
			xbox360.setShortTitle("x360");
			xbox360.setDescription("Microsoft's 2nd generation beast");
			xbox360.setManufacturer("Microsoft Inc.");
			platformDAOInstance.save(xbox360);
			
			Platform xboxOne = new Platform();
			xboxOne.setTitle("Xbox One");
			xboxOne.setShortTitle("xone");
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
			n3ds.setShortTitle("3ds");
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
			
			
			Set<Platform> platforms = new HashSet<>();
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
			maxPayne3.setRating("Mature");
			maxPayne3.setReleaseDate("2012-05-15");
			maxPayne3.setScore(8.5);

			gameDAOInstance.save(maxPayne3);
			
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
			
			gameDAOInstance.save(farCry3);
			
			Game guacamelee = new Game();
			guacamelee.setTitle("Guacamelee!");
			guacamelee.setDescription("Guacamelee! is a Metroid-vania style action-platformer set in a magical Mexican inspired world. The game draws its inspiration from traditional Mexican culture and folklore, and features many interesting and unique characters.");
			guacamelee.setDeveloper("Drinkbox Studios");
			guacamelee.setPublisher("Drinkbox Studios");
			guacamelee.setGenere("Action");
			guacamelee.setPrice("12$");
			Set<Platform> guacameleePlatforms = new HashSet<>();
			guacameleePlatforms.add(ps3);
			guacameleePlatforms.add(x360);
			guacameleePlatforms.add(vita);
			guacamelee.setPlatforms(guacameleePlatforms);
			guacamelee.setRating("Everyone");
			guacamelee.setReleaseDate("2013-04-09");
			guacamelee.setScore(9.1);
			
			gameDAOInstance.save(guacamelee);
			
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
			
			gameDAOInstance.save(bioshockInfinite);
			
			
			Game halo4 = new Game();
			halo4.setTitle("Halo4");
			halo4.setDescription("The Master Chief returns to battle an ancient evil bent on vengeance and annihilation. Shipwrecked on a mysterious world, faced with new enemies and deadly technology, the universe will never be the same. ");
			halo4.setDeveloper("343 Industries");
			halo4.setPublisher("Microsoft Game Studios");
			halo4.setGenere("First Person Shooter");
			halo4.setPrice("60$");
			Set<Platform> halo4Platforms = new HashSet<>();
			halo4Platforms.add(x360);
			halo4.setPlatforms(halo4Platforms);
			halo4.setRating("Mature");
			halo4.setReleaseDate("2012-11-06");
			halo4.setScore(8.8);
			
			gameDAOInstance.save(halo4);
			
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
			
			gameDAOInstance.save(defiance);
			
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
			
			gameDAOInstance.save(terraria);
			
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
			
			gameDAOInstance.save(batmanArkhamCity);
			
			Game motocrossMadness = new Game();
			motocrossMadness.setTitle("Motocross Madness");
			motocrossMadness.setDescription("Motocross Madness is back! Take your bike freeriding across massive environments, from the deserts of Egypt to the snows of Iceland. Drift, trick and turbo boost your way across 9 expansive offroad tracks in single player events, ghost challenges and 8-player multiplayer races.");
			motocrossMadness.setDeveloper("Bongfish");
			motocrossMadness.setPublisher("Microsoft Game Studios");
			motocrossMadness.setGenere("Racing");
			motocrossMadness.setPrice("11$");
			Set<Platform> motocrossMadnessPlatforms = new HashSet<>();
			motocrossMadnessPlatforms.add(x360);
			motocrossMadness.setPlatforms(motocrossMadnessPlatforms);
			motocrossMadness.setRating("Everyone");
			motocrossMadness.setReleaseDate("2013-04-10");
			motocrossMadness.setScore(7.2);
			
			gameDAOInstance.save(motocrossMadness);
			
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
			
			gameDAOInstance.save(ageOfWushu);
			
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
			
			gameDAOInstance.save(splinterCellBlacklist);
			
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
			
			gameDAOInstance.save(gta5);
			
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
			
			gameDAOInstance.save(mgsRising);
			
		}
		
		private void createArticles()
		{	
			
			Platform ps3 = platformDAOInstance.findByTitle("Playstation 3");
			Platform x360 = platformDAOInstance.findByTitle("Xbox 360");
			Platform pc = platformDAOInstance.findByTitle("PC");
			Platform vita = platformDAOInstance.findByTitle("PS-VITA");
			
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
			
			final ArticleDAO daoInstance = ArticleDAO.instantiateDAO();			
			double totalPageHits = daoInstance.getTotalPageHits();
			RankAlgorithm rankAlgorithm = new RankAlgorithm();	
			double coolScore=0.0;
			double notCoolScore=0.0;
			double coolNotCoolWilsonScore = 0.0;
			double pageCount=0.0;
			double pageHitWilsonScore=0.0;
			ArrayList<Platform> platforms = new ArrayList<>();
			platforms.add(ps3);
			platforms.add(x360);
			platforms.add(pc);
			
			
			Article maxPayne3Review = new Article();
			maxPayne3Review.setTitle("max payne 3 review");
			maxPayne3Review.setSubtitle("How does Max fared third time?");						
			maxPayne3Review.setAuthor(loonatic86);
			maxPayne3Review.setBody("Max Payne 3 was an awesome followup and finish to an awesome series.\n Gun blazin max rocked.");
			maxPayne3Review.setCategory(Category.review);
			maxPayne3Review.setPublishDate("2012-05-16 22:05:59");			
			maxPayne3Review.setGame(maxPayne3);
			maxPayne3Review.setFeaturedImagePath("articleFeaturedImage");
			//maxPayne3Review.setInsertTime(Utility.convertDateToString(new Date()));
			maxPayne3Review.setInsertTime("2012-05-16 22:05:59");
			maxPayne3Review.setUpdateTime("2012-05-16 22:05:59");				
			maxPayne3Review.setPlatforms(platforms);						
			coolScore=50;
			notCoolScore=35;
			maxPayne3Review.setCoolScore(coolScore);
			maxPayne3Review.setNotCoolScore(notCoolScore);
			coolNotCoolWilsonScore=rankAlgorithm.wilsonScoreCalculator(coolScore, notCoolScore);
			maxPayne3Review.setCoolNotCoolwilsonScore(coolNotCoolWilsonScore);
			pageCount=500;
			maxPayne3Review.setPageHitCount(pageCount);
			totalPageHits = daoInstance.getTotalPageHits();
			pageHitWilsonScore=rankAlgorithm.wilsonScoreCalculator(pageCount, (totalPageHits-pageCount));
			maxPayne3Review.setPageHitWilsonScore(pageHitWilsonScore);
			maxPayne3Review.setTotalScore(coolNotCoolWilsonScore+pageHitWilsonScore); //not game score, its article score	
			maxPayne3Review.setState(Article.PUBLISH);
			articleDAOInstance.save(maxPayne3Review);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, loonatic86.getId().toString(), maxPayne3Review.getId().toString(), AppConstants.PUBLIC, "2012-05-16 22:05:59");
			
			Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			farCry3Review.setSubtitle("Return to the jungle, tropical style...");								
			farCry3Review.setAuthor(jaguarpaw80);
			farCry3Review.setBody("The third game in the excellent series excels in every manner. Solid entry in Far Cry series.");
			farCry3Review.setCategory(Category.review);
			farCry3Review.setPublishDate("2012-12-15 09:30:00");
			
			farCry3Review.setGame(farCry3);
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2012-12-15 09:30:00");
			farCry3Review.setUpdateTime("2012-12-15 09:30:00");		
			farCry3Review.setPlatforms(platforms);
			coolScore=60;
			notCoolScore=29;
			farCry3Review.setCoolScore(coolScore);
			farCry3Review.setNotCoolScore(notCoolScore);
			coolNotCoolWilsonScore=rankAlgorithm.wilsonScoreCalculator(coolScore, notCoolScore);
			farCry3Review.setCoolNotCoolwilsonScore(coolNotCoolWilsonScore);
			pageCount=100;
			farCry3Review.setPageHitCount(pageCount);
			totalPageHits = daoInstance.getTotalPageHits();
			pageHitWilsonScore=rankAlgorithm.wilsonScoreCalculator(pageCount, (totalPageHits-pageCount));
			farCry3Review.setPageHitWilsonScore(pageHitWilsonScore);
			farCry3Review.setTotalScore(coolNotCoolWilsonScore+pageHitWilsonScore); //not game score, its article score			
			farCry3Review.setState(Article.PUBLISH);
			articleDAOInstance.save(farCry3Review);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, jaguarpaw80.getId().toString(), farCry3Review.getId().toString(), AppConstants.PUBLIC, "2012-12-15 09:30:00");
			
			
			ArrayList<Platform> guacameleePlatforms = new ArrayList<>();
			guacameleePlatforms.add(ps3);
			guacameleePlatforms.add(x360);
			guacameleePlatforms.add(vita);
			
			
			Article guacameleeReview1 = new Article();
			guacameleeReview1.setTitle("Guacamelee! review");		
			guacameleeReview1.setSubtitle("Get ready for good old meelee fun.");
			guacameleeReview1.setAuthor(jaguarpaw80);
			guacameleeReview1.setBody("Guacamelee is an awseome metroid fueled mexican wave ride.");
			guacameleeReview1.setCategory(Category.review);
			guacameleeReview1.setPublishDate("2013-04-10 09:00:00");			
			guacameleeReview1.setGame(guacamelee);
			guacameleeReview1.setFeaturedImagePath("articleFeaturedImage");
			//guacameleeReview.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			guacameleeReview1.setInsertTime("2013-04-10 09:00:00");		
			guacameleeReview1.setUpdateTime("2013-04-10 09:00:00");
			guacameleeReview1.setPlatforms(guacameleePlatforms);
			coolScore=10;
			notCoolScore=4;
			guacameleeReview1.setCoolScore(coolScore);
			guacameleeReview1.setNotCoolScore(notCoolScore);
			coolNotCoolWilsonScore=rankAlgorithm.wilsonScoreCalculator(coolScore, notCoolScore);
			guacameleeReview1.setCoolNotCoolwilsonScore(coolNotCoolWilsonScore);
			pageCount=50;
			guacameleeReview1.setPageHitCount(pageCount);
			totalPageHits = daoInstance.getTotalPageHits();
			pageHitWilsonScore=rankAlgorithm.wilsonScoreCalculator(pageCount, (totalPageHits-pageCount));
			guacameleeReview1.setPageHitWilsonScore(pageHitWilsonScore);
			guacameleeReview1.setTotalScore(coolNotCoolWilsonScore+pageHitWilsonScore); //not game score, its article score			
			guacameleeReview1.setState(Article.PUBLISH);
			articleDAOInstance.save(guacameleeReview1);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, jaguarpaw80.getId().toString(), guacameleeReview1.getId().toString(), AppConstants.PUBLIC, "2013-04-10 09:00:00");
			
			
			Article guacameleeReview2 = new Article();
			guacameleeReview2.setTitle("Guacamelee! review");	
			guacameleeReview2.setSubtitle("Get ready for some nice mexicano side scrolling brawler");
			guacameleeReview2.setAuthor(kenhaduken82);
			guacameleeReview2.setBody("Guacamelee is mexican luchador beauty.");
			guacameleeReview2.setCategory(Category.review);
			guacameleeReview2.setPublishDate("2013-04-10 10:00:00");			
			guacameleeReview2.setGame(guacamelee);
			guacameleeReview2.setFeaturedImagePath("articleFeaturedImage");
			//guacameleeReview.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			guacameleeReview2.setInsertTime("2013-04-10 10:00:00");		
			guacameleeReview2.setUpdateTime("2013-04-10 10:00:00");
			guacameleeReview2.setPlatforms(guacameleePlatforms);
			coolScore=20;
			notCoolScore=5;
			guacameleeReview2.setCoolScore(coolScore);
			guacameleeReview2.setNotCoolScore(notCoolScore);
			coolNotCoolWilsonScore=rankAlgorithm.wilsonScoreCalculator(coolScore, notCoolScore);
			guacameleeReview2.setCoolNotCoolwilsonScore(coolNotCoolWilsonScore);
			pageCount=100;
			guacameleeReview2.setPageHitCount(pageCount);
			totalPageHits = daoInstance.getTotalPageHits();
			pageHitWilsonScore=rankAlgorithm.wilsonScoreCalculator(pageCount, (totalPageHits-pageCount));
			guacameleeReview2.setPageHitWilsonScore(pageHitWilsonScore);
			guacameleeReview2.setTotalScore(coolNotCoolWilsonScore+pageHitWilsonScore); //not game score, its article score	
			guacameleeReview2.setState(Article.PUBLISH);
			articleDAOInstance.save(guacameleeReview2);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, kenhaduken82.getId().toString(), guacameleeReview2.getId().toString(), AppConstants.PUBLIC, "2013-04-10 10:00:00");
			
			Article guacameleeReview3 = new Article();
			guacameleeReview3.setTitle("Guacamelee! review");	
			guacameleeReview3.setSubtitle("Guacamelee is fun pendejo...");
			guacameleeReview3.setAuthor(loonatic86);
			guacameleeReview3.setBody("Being a mexican chicken is so fun.");
			guacameleeReview3.setCategory(Category.review);
			guacameleeReview3.setPublishDate("2013-04-10 11:00:00");			
			guacameleeReview3.setGame(guacamelee);
			guacameleeReview3.setFeaturedImagePath("articleFeaturedImage");
			//guacameleeReview.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			guacameleeReview3.setInsertTime("2013-04-10 11:00:00");
			guacameleeReview3.setUpdateTime("2013-04-10 11:00:00");
			guacameleeReview3.setPlatforms(guacameleePlatforms);
			coolScore=20;
			notCoolScore=5;
			guacameleeReview3.setCoolScore(coolScore);
			guacameleeReview3.setNotCoolScore(notCoolScore);
			coolNotCoolWilsonScore=rankAlgorithm.wilsonScoreCalculator(coolScore, notCoolScore);
			guacameleeReview3.setCoolNotCoolwilsonScore(coolNotCoolWilsonScore);
			pageCount=100;
			guacameleeReview3.setPageHitCount(pageCount);
			totalPageHits = daoInstance.getTotalPageHits();
			pageHitWilsonScore=rankAlgorithm.wilsonScoreCalculator(pageCount, (totalPageHits-pageCount));
			guacameleeReview3.setPageHitWilsonScore(pageHitWilsonScore);
			guacameleeReview3.setTotalScore(coolNotCoolWilsonScore+pageHitWilsonScore); //not game score, its article score		
			guacameleeReview3.setState(Article.PUBLISH);
			articleDAOInstance.save(guacameleeReview3);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, loonatic86.getId().toString(), guacameleeReview3.getId().toString(), AppConstants.PUBLIC, "2013-04-10 11:00:00");
			
			Article ageOfWushuReview1 = new Article();
			ageOfWushuReview1.setTitle("Age of Wushu review");		
			guacameleeReview3.setSubtitle("How does this kung fu MMORPG romp fares?");
			ageOfWushuReview1.setAuthor(loonatic86);
			ageOfWushuReview1.setBody("Age of Wushu lets you enjoy the world of Martial Arts in its purest form.");
			ageOfWushuReview1.setCategory(Category.review);
			ageOfWushuReview1.setPublishDate("2013-04-11 09:00:00");			
			ageOfWushuReview1.setGame(ageOfWushu);
			ageOfWushuReview1.setFeaturedImagePath("articleFeaturedImage");
			//ageOfWushuReview.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			ageOfWushuReview1.setInsertTime("2013-04-11 09:00:00");
			ageOfWushuReview1.setUpdateTime("2013-04-11 09:00:00");
			ageOfWushuReview1.setPlatforms(platforms);
			coolScore=10;
			notCoolScore=5;
			ageOfWushuReview1.setCoolScore(coolScore);
			ageOfWushuReview1.setNotCoolScore(notCoolScore);
			coolNotCoolWilsonScore=rankAlgorithm.wilsonScoreCalculator(coolScore, notCoolScore);
			ageOfWushuReview1.setCoolNotCoolwilsonScore(coolNotCoolWilsonScore);
			pageCount=80;
			ageOfWushuReview1.setPageHitCount(pageCount);
			totalPageHits = daoInstance.getTotalPageHits();
			pageHitWilsonScore=rankAlgorithm.wilsonScoreCalculator(pageCount, (totalPageHits-pageCount));
			ageOfWushuReview1.setPageHitWilsonScore(pageHitWilsonScore);
			ageOfWushuReview1.setTotalScore(coolNotCoolWilsonScore+pageHitWilsonScore); //not game score, its article score		
			ageOfWushuReview1.setState(Article.PUBLISH);
			articleDAOInstance.save(ageOfWushuReview1);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, loonatic86.getId().toString(), ageOfWushuReview1.getId().toString(), AppConstants.PUBLIC, "2013-04-11 09:00:00");
			
			Article ageOfWushuReview2 = new Article();
			ageOfWushuReview2.setTitle("Age of Wushu review");				
			ageOfWushuReview2.setSubtitle("Get ready for some wall-running, flying dagger style action...");
			ageOfWushuReview2.setAuthor(xwarior81);
			ageOfWushuReview2.setBody("If you are fan of sugar hardcore martial arts movie, you will find your salvation in Age of Wushu.");
			ageOfWushuReview2.setCategory(Category.review);
			ageOfWushuReview2.setPublishDate("2013-04-11 12:20:00");			
			ageOfWushuReview2.setGame(ageOfWushu);
			ageOfWushuReview2.setFeaturedImagePath("articleFeaturedImage");
			//ageOfWushuReview.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			ageOfWushuReview2.setInsertTime("2013-04-11 12:20:00");
			ageOfWushuReview2.setUpdateTime("2013-04-11 12:20:00");
			ageOfWushuReview2.setPlatforms(platforms);
			coolScore=90;
			notCoolScore=15;
			ageOfWushuReview2.setCoolScore(coolScore);
			ageOfWushuReview2.setNotCoolScore(notCoolScore);
			coolNotCoolWilsonScore=rankAlgorithm.wilsonScoreCalculator(coolScore, notCoolScore);
			ageOfWushuReview2.setCoolNotCoolwilsonScore(coolNotCoolWilsonScore);
			pageCount=150;
			ageOfWushuReview2.setPageHitCount(pageCount);
			totalPageHits = daoInstance.getTotalPageHits();
			pageHitWilsonScore=rankAlgorithm.wilsonScoreCalculator(pageCount, (totalPageHits-pageCount));
			ageOfWushuReview2.setPageHitWilsonScore(pageHitWilsonScore);
			ageOfWushuReview2.setTotalScore(coolNotCoolWilsonScore+pageHitWilsonScore); //not game score, its article score
			ageOfWushuReview2.setState(Article.PUBLISH);
			articleDAOInstance.save(ageOfWushuReview2);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, xwarior81.getId().toString(), ageOfWushuReview2.getId().toString(), AppConstants.PUBLIC, "2013-04-11 12:20:00");
			
			
			ArrayList<Platform> motocrossPlatforms = new ArrayList<>();
			motocrossPlatforms.add(x360);
			
			
			Article motocrossMadnessReview1 = new Article();
			motocrossMadnessReview1.setTitle("Motocross Madness review");			
			ageOfWushuReview2.setSubtitle("Motocross Madness is back. But is it any good?");
			motocrossMadnessReview1.setAuthor(muchooomg90);
			motocrossMadnessReview1.setBody("This Motocross Madness is medicore at its best.");
			motocrossMadnessReview1.setCategory(Category.review);
			motocrossMadnessReview1.setPublishDate("2013-04-11 00:30:00");			
			motocrossMadnessReview1.setGame(motocrossMadness);
			motocrossMadnessReview1.setFeaturedImagePath("articleFeaturedImage");
			//motocrossMadnessReview1.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			motocrossMadnessReview1.setInsertTime("2013-04-11 00:30:00");
			motocrossMadnessReview1.setUpdateTime("2013-04-11 00:30:00");
			motocrossMadnessReview1.setPlatforms(motocrossPlatforms);
			coolScore=150;
			notCoolScore=140;
			motocrossMadnessReview1.setCoolScore(coolScore);
			motocrossMadnessReview1.setNotCoolScore(notCoolScore);
			coolNotCoolWilsonScore=rankAlgorithm.wilsonScoreCalculator(coolScore, notCoolScore);
			motocrossMadnessReview1.setCoolNotCoolwilsonScore(coolNotCoolWilsonScore);
			pageCount=200;
			motocrossMadnessReview1.setPageHitCount(pageCount);
			totalPageHits = daoInstance.getTotalPageHits();
			pageHitWilsonScore=rankAlgorithm.wilsonScoreCalculator(pageCount, (totalPageHits-pageCount));
			motocrossMadnessReview1.setPageHitWilsonScore(pageHitWilsonScore);
			motocrossMadnessReview1.setTotalScore(coolNotCoolWilsonScore+pageHitWilsonScore); //not game score, its article score
			motocrossMadnessReview1.setState(Article.PUBLISH);
			articleDAOInstance.save(motocrossMadnessReview1);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, muchooomg90.getId().toString(), motocrossMadnessReview1.getId().toString(), AppConstants.PUBLIC,"2013-04-11 00:30:00");
			
			Article motocrossMadnessReview2 = new Article();
			motocrossMadnessReview2.setTitle("Motocross Madness review");
			motocrossMadnessReview2.setSubtitle("Motocross Madness is bad. Period!");
			motocrossMadnessReview2.setAuthor(kenhaduken82);
			motocrossMadnessReview2.setBody("Common Microsoft, stop serving us enough garbage now!");
			motocrossMadnessReview2.setCategory(Category.review);
			motocrossMadnessReview2.setPublishDate("2013-04-11 09:30:00");			
			motocrossMadnessReview2.setGame(motocrossMadness);
			motocrossMadnessReview2.setFeaturedImagePath("articleFeaturedImage");
			//motocrossMadnessReview2.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			motocrossMadnessReview2.setInsertTime("2013-04-11 09:30:00");
			motocrossMadnessReview2.setUpdateTime("2013-04-11 09:30:00");
			motocrossMadnessReview2.setPlatforms(motocrossPlatforms);
			coolScore=100;
			notCoolScore=95;
			motocrossMadnessReview2.setCoolScore(coolScore);
			motocrossMadnessReview2.setNotCoolScore(notCoolScore);
			coolNotCoolWilsonScore=rankAlgorithm.wilsonScoreCalculator(coolScore, notCoolScore);
			motocrossMadnessReview2.setCoolNotCoolwilsonScore(coolNotCoolWilsonScore);
			pageCount=178;
			motocrossMadnessReview2.setPageHitCount(pageCount);
			totalPageHits = daoInstance.getTotalPageHits();
			pageHitWilsonScore=rankAlgorithm.wilsonScoreCalculator(pageCount, (totalPageHits-pageCount));
			motocrossMadnessReview2.setPageHitWilsonScore(pageHitWilsonScore);
			motocrossMadnessReview2.setTotalScore(coolNotCoolWilsonScore+pageHitWilsonScore); //not game score, its article score
			motocrossMadnessReview2.setState(Article.PUBLISH);
			articleDAOInstance.save(motocrossMadnessReview2);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, kenhaduken82.getId().toString(), motocrossMadnessReview2.getId().toString(), AppConstants.PUBLIC,"2013-04-11 09:30:00");
			
			Article terrariaReview1 = new Article();
			terrariaReview1.setTitle("Terraria review");				
			terrariaReview1.setSubtitle("Load runner gets EVERYTHING is toppings.");
			terrariaReview1.setAuthor(loonatic86);
			terrariaReview1.setBody("A game which lets you do EVERYTHING!!!");
			terrariaReview1.setCategory(Category.review);
			terrariaReview1.setPublishDate("2013-03-28 09:00:00");			
			terrariaReview1.setGame(terraria);
			terrariaReview1.setFeaturedImagePath("articleFeaturedImage");
			//terraria.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			terrariaReview1.setInsertTime("2013-03-28 09:00:00");
			terrariaReview1.setUpdateTime("2013-03-28 09:00:00");
			terrariaReview1.setPlatforms(platforms);
			coolScore=300;
			notCoolScore=17;
			terrariaReview1.setCoolScore(coolScore);
			terrariaReview1.setNotCoolScore(notCoolScore);
			coolNotCoolWilsonScore=rankAlgorithm.wilsonScoreCalculator(coolScore, notCoolScore);
			terrariaReview1.setCoolNotCoolwilsonScore(coolNotCoolWilsonScore);
			pageCount=200;
			terrariaReview1.setPageHitCount(pageCount);
			totalPageHits = daoInstance.getTotalPageHits();
			pageHitWilsonScore=rankAlgorithm.wilsonScoreCalculator(pageCount, (totalPageHits-pageCount));
			terrariaReview1.setPageHitWilsonScore(pageHitWilsonScore);
			terrariaReview1.setTotalScore(coolNotCoolWilsonScore+pageHitWilsonScore); //not game score, its article score
			terrariaReview1.setState(Article.PUBLISH);
			articleDAOInstance.save(terrariaReview1);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, loonatic86.getId().toString(), terrariaReview1.getId().toString(), AppConstants.PUBLIC,"2013-03-28 09:00:00");
			
			Article terrariaReview2= new Article();
			terrariaReview2.setTitle("Terraria review");						
			terrariaReview2.setSubtitle("Get many flavors at a price of one. And it is fun...");
			terrariaReview2.setAuthor(theone90);
			terrariaReview2.setBody("Its an action game, a rpg, an exploration game. In short, play it as any way possible. Its awesome in short. ");
			terrariaReview2.setCategory(Category.review);
			terrariaReview2.setPublishDate("2013-03-28 10:00:00");			
			terrariaReview2.setGame(terraria);
			terrariaReview2.setFeaturedImagePath("articleFeaturedImage");
			//terraria.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			terrariaReview2.setInsertTime("2013-03-28 10:00:00");			
			terrariaReview2.setUpdateTime("2013-03-28 10:00:00");
			terrariaReview2.setPlatforms(platforms);
			coolScore=565;
			notCoolScore=20;
			terrariaReview2.setCoolScore(coolScore);
			terrariaReview2.setNotCoolScore(notCoolScore);
			coolNotCoolWilsonScore=rankAlgorithm.wilsonScoreCalculator(coolScore, notCoolScore);
			terrariaReview2.setCoolNotCoolwilsonScore(coolNotCoolWilsonScore);
			pageCount=350;
			terrariaReview2.setPageHitCount(pageCount);
			totalPageHits = daoInstance.getTotalPageHits();
			pageHitWilsonScore=rankAlgorithm.wilsonScoreCalculator(pageCount, (totalPageHits-pageCount));
			terrariaReview2.setPageHitWilsonScore(pageHitWilsonScore);
			terrariaReview2.setTotalScore(coolNotCoolWilsonScore+pageHitWilsonScore); //not game score, its article score
			terrariaReview2.setState(Article.PUBLISH);
			articleDAOInstance.save(terrariaReview2);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, theone90.getId().toString(), terrariaReview2.getId().toString(), AppConstants.PUBLIC,"2013-03-28 10:00:00");
			
			Article terrariaReview3= new Article();
			terrariaReview3.setTitle("Terraria review");							
			terrariaReview3.setSubtitle("A true homage to sugar retro gaming.");
			terrariaReview3.setAuthor(kenhaduken82);
			terrariaReview3.setBody("This game is fun and take you back into 90s bomberman and load runner era... ");
			terrariaReview3.setCategory(Category.review);
			terrariaReview3.setPublishDate("2013-03-28 08:00:00");			
			terrariaReview3.setGame(terraria);
			terrariaReview3.setFeaturedImagePath("articleFeaturedImage");
			//terraria.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			terrariaReview3.setInsertTime("2013-03-28 08:00:00");
			terrariaReview3.setUpdateTime("2013-03-28 08:00:00");
			terrariaReview3.setPlatforms(platforms);
			coolScore=400;
			notCoolScore=75;
			terrariaReview3.setCoolScore(coolScore);
			terrariaReview3.setNotCoolScore(notCoolScore);
			coolNotCoolWilsonScore=rankAlgorithm.wilsonScoreCalculator(coolScore, notCoolScore);
			terrariaReview3.setCoolNotCoolwilsonScore(coolNotCoolWilsonScore);
			pageCount=258;
			terrariaReview3.setPageHitCount(pageCount);
			totalPageHits = daoInstance.getTotalPageHits();
			pageHitWilsonScore=rankAlgorithm.wilsonScoreCalculator(pageCount, (totalPageHits-pageCount));
			terrariaReview3.setPageHitWilsonScore(pageHitWilsonScore);
			terrariaReview3.setTotalScore(coolNotCoolWilsonScore+pageHitWilsonScore); //not game score, its article score
			terrariaReview3.setState(Article.PUBLISH);
			articleDAOInstance.save(terrariaReview3);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, kenhaduken82.getId().toString(), terrariaReview3.getId().toString(), AppConstants.PUBLIC,"2013-03-28 08:00:00");
			
			Article defianceReview1 = new Article();
			defianceReview1.setTitle("Defiance review");		
			defianceReview1.setSubtitle("TV to game tie-not that good");
			defianceReview1.setAuthor(xwarior81);
			defianceReview1.setBody("Seriously!!! What the heck was that?");
			defianceReview1.setCategory(Category.review);
			defianceReview1.setPublishDate("2013-04-03 00:30:00");			
			defianceReview1.setGame(defiance);
			defianceReview1.setFeaturedImagePath("articleFeaturedImage");
			//defianceReview1.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			defianceReview1.setInsertTime("2013-04-03 00:30:00");
			defianceReview1.setUpdateTime("2013-04-03 00:30:00");
			defianceReview1.setPlatforms(platforms);
			coolScore=900;
			notCoolScore=100;
			defianceReview1.setCoolScore(coolScore);
			defianceReview1.setNotCoolScore(notCoolScore);
			coolNotCoolWilsonScore=rankAlgorithm.wilsonScoreCalculator(coolScore, notCoolScore);
			defianceReview1.setCoolNotCoolwilsonScore(coolNotCoolWilsonScore);
			pageCount=1010;
			defianceReview1.setPageHitCount(pageCount);
			totalPageHits = daoInstance.getTotalPageHits();
			pageHitWilsonScore=rankAlgorithm.wilsonScoreCalculator(pageCount, (totalPageHits-pageCount));
			defianceReview1.setPageHitWilsonScore(pageHitWilsonScore);
			defianceReview1.setTotalScore(coolNotCoolWilsonScore+pageHitWilsonScore); //not game score, its article score
			defianceReview1.setState(Article.PUBLISH);
			articleDAOInstance.save(defianceReview1);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, xwarior81.getId().toString(), defianceReview1.getId().toString(), AppConstants.PUBLIC,"2013-04-03 00:30:00");
			
			Article defianceReview2 = new Article();
			defianceReview2.setTitle("Defiance review");		
			defianceReview2.setSubtitle("A great concept but a failed implentation");
			defianceReview2.setAuthor(loonatic86);
			defianceReview2.setBody("Concept was great on paper but the execution was not just right.");
			defianceReview2.setCategory(Category.review);
			defianceReview2.setPublishDate("2013-04-03 01:30:00");			
			defianceReview2.setGame(defiance);
			defianceReview2.setFeaturedImagePath("articleFeaturedImage");
			//defianceReview2.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			defianceReview2.setInsertTime("2013-04-03 01:30:00");			
			defianceReview2.setUpdateTime("2013-04-03 01:30:00");
			defianceReview2.setPlatforms(platforms);
			coolScore=800;
			notCoolScore=90;
			defianceReview2.setCoolScore(coolScore);
			defianceReview2.setNotCoolScore(notCoolScore);
			coolNotCoolWilsonScore=rankAlgorithm.wilsonScoreCalculator(coolScore, notCoolScore);
			defianceReview2.setCoolNotCoolwilsonScore(coolNotCoolWilsonScore);
			pageCount=978;
			defianceReview2.setPageHitCount(pageCount);
			totalPageHits = daoInstance.getTotalPageHits();
			pageHitWilsonScore=rankAlgorithm.wilsonScoreCalculator(pageCount, (totalPageHits-pageCount));
			defianceReview2.setPageHitWilsonScore(pageHitWilsonScore);
			defianceReview2.setTotalScore(coolNotCoolWilsonScore+pageHitWilsonScore); //not game score, its article score
			defianceReview2.setState(Article.PUBLISH);
			articleDAOInstance.save(defianceReview2);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, loonatic86.getId().toString(), defianceReview2.getId().toString(), AppConstants.PUBLIC,"2013-04-03 01:30:00");
			
			Article defianceReview3 = new Article();			
			defianceReview3.setTitle("Defiance review");
			defianceReview3.setSubtitle("Defiance tries hard to be cool.");
			defianceReview3.setAuthor(muchooomg90);
			defianceReview3.setBody("Defiance just doesnt work. Period.");
			defianceReview3.setCategory(Category.review);
			defianceReview3.setPublishDate("2013-04-03 01:30:00");			
			defianceReview3.setGame(defiance);
			defianceReview3.setFeaturedImagePath("articleFeaturedImage");
			//defianceReview3.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			defianceReview3.setInsertTime("2013-04-03 01:30:00");			
			defianceReview3.setUpdateTime("2013-04-03 01:30:00");
			defianceReview3.setPlatforms(platforms);
			coolScore=858;
			notCoolScore=103;
			defianceReview3.setCoolScore(coolScore);
			defianceReview3.setNotCoolScore(notCoolScore);
			coolNotCoolWilsonScore=rankAlgorithm.wilsonScoreCalculator(coolScore, notCoolScore);
			defianceReview3.setCoolNotCoolwilsonScore(coolNotCoolWilsonScore);
			pageCount=1024;
			defianceReview3.setPageHitCount(pageCount);
			totalPageHits = daoInstance.getTotalPageHits();
			pageHitWilsonScore=rankAlgorithm.wilsonScoreCalculator(pageCount, (totalPageHits-pageCount));
			defianceReview3.setPageHitWilsonScore(pageHitWilsonScore);
			defianceReview3.setTotalScore(coolNotCoolWilsonScore+pageHitWilsonScore); //not game score, its article score
			defianceReview3.setState(Article.PUBLISH);
			articleDAOInstance.save(defianceReview3);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, muchooomg90.getId().toString(), defianceReview3.getId().toString(), AppConstants.PUBLIC,"2013-04-03 01:30:00");
			
			Article maxPayne3Feature = new Article();
			maxPayne3Feature.setTitle("Max Payne 3 Feature: The pain of bullet time 3.0");	
			maxPayne3Feature.setSubtitle("We look into the bullet time feature of Max Payne 3.0");
			maxPayne3Feature.setAuthor(loonatic86);
			maxPayne3Feature.setBody("Bullet time 3.0 felt kind of fresh even though bullet time used in games like anything. The new bullet time in Max Payne 3 enhance the poetic nature of Max thanks to rage engine and Euphoria.");
			maxPayne3Feature.setCategory(Category.feature);
			maxPayne3Feature.setPublishDate("2013-04-13 11:05:00");			
			maxPayne3Feature.setGame(maxPayne3);
			maxPayne3Feature.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			maxPayne3Feature.setInsertTime("2013-04-13 11:05:00");			
			maxPayne3Feature.setUpdateTime("2013-04-13 11:05:00");
			maxPayne3Feature.setPlatforms(platforms);
			coolScore=654;
			notCoolScore=103;
			maxPayne3Feature.setCoolScore(coolScore);
			maxPayne3Feature.setNotCoolScore(notCoolScore);
			coolNotCoolWilsonScore=rankAlgorithm.wilsonScoreCalculator(coolScore, notCoolScore);
			maxPayne3Feature.setCoolNotCoolwilsonScore(coolNotCoolWilsonScore);
			pageCount=951;
			maxPayne3Feature.setPageHitCount(pageCount);
			totalPageHits = daoInstance.getTotalPageHits();
			pageHitWilsonScore=rankAlgorithm.wilsonScoreCalculator(pageCount, (totalPageHits-pageCount));
			maxPayne3Feature.setPageHitWilsonScore(pageHitWilsonScore);
			maxPayne3Feature.setTotalScore(coolNotCoolWilsonScore+pageHitWilsonScore); //not game score, its article score
			maxPayne3Feature.setState(Article.PUBLISH);
			articleDAOInstance.save(maxPayne3Feature);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, loonatic86.getId().toString(), maxPayne3Feature.getId().toString(), AppConstants.PUBLIC,"2013-04-13 11:05:00");
			
			Article farCry3Feature = new Article();
			farCry3Feature.setTitle("Far Cry 3 Feature: The sense of wild");	
			farCry3Feature.setSubtitle("Lets have a look into the open world of Far cry 3");
			farCry3Feature.setAuthor(loonatic86);
			farCry3Feature.setBody("Wild was really alive in the game. IMHO, this is the second game after Metal Gear Solid: Snake Eater in which I stay alerted while playing the game. Just that this game is more open and random in nature. Danger is lurking everywhere.");
			farCry3Feature.setCategory(Category.feature);
			farCry3Feature.setPublishDate("2013-04-11 15:00:00");			
			farCry3Feature.setGame(farCry3);
			farCry3Feature.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Feature.setInsertTime("2013-04-11 15:00:00");			
			farCry3Feature.setUpdateTime("2013-04-11 15:00:00");
			farCry3Feature.setPlatforms(platforms);
			coolScore=755;
			notCoolScore=16;
			farCry3Feature.setCoolScore(coolScore);
			farCry3Feature.setNotCoolScore(notCoolScore);
			coolNotCoolWilsonScore=rankAlgorithm.wilsonScoreCalculator(coolScore, notCoolScore);
			farCry3Feature.setCoolNotCoolwilsonScore(coolNotCoolWilsonScore);
			pageCount=800;
			farCry3Feature.setPageHitCount(pageCount);
			totalPageHits = daoInstance.getTotalPageHits();
			pageHitWilsonScore=rankAlgorithm.wilsonScoreCalculator(pageCount, (totalPageHits-pageCount));
			farCry3Feature.setPageHitWilsonScore(pageHitWilsonScore);
			farCry3Feature.setTotalScore(coolNotCoolWilsonScore+pageHitWilsonScore); //not game score, its article score
			farCry3Feature.setState(Article.PUBLISH);
			articleDAOInstance.save(farCry3Feature);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, loonatic86.getId().toString(), farCry3Feature.getId().toString(), AppConstants.PUBLIC,"2013-04-11 15:00:00");
			
			Article mgsRisingFeature = new Article();
			mgsRisingFeature.setTitle("MGS Rising Feature: The cut mechanics");		
			mgsRisingFeature.setSubtitle("A look into MGS Rising cut feature");
			mgsRisingFeature.setAuthor(kenhaduken82);
			mgsRisingFeature.setBody("Atleast, Konami made sure that we get that same cut experience which they demoed in E3 2011. I was overwhelmed by it.");
			mgsRisingFeature.setCategory(Category.feature);
			mgsRisingFeature.setPublishDate("2013-03-29 15:45:00");			
			mgsRisingFeature.setGame(mgsr);
			mgsRisingFeature.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			mgsRisingFeature.setInsertTime("2013-03-29 15:45:00");			
			mgsRisingFeature.setUpdateTime("2013-03-29 15:45:00");
			mgsRisingFeature.setPlatforms(platforms);
			coolScore=900;
			notCoolScore=25;
			mgsRisingFeature.setCoolScore(coolScore);
			mgsRisingFeature.setNotCoolScore(notCoolScore);
			coolNotCoolWilsonScore=rankAlgorithm.wilsonScoreCalculator(coolScore, notCoolScore);
			mgsRisingFeature.setCoolNotCoolwilsonScore(coolNotCoolWilsonScore);
			pageCount=929;
			mgsRisingFeature.setPageHitCount(pageCount);
			totalPageHits = daoInstance.getTotalPageHits();
			pageHitWilsonScore=rankAlgorithm.wilsonScoreCalculator(pageCount, (totalPageHits-pageCount));
			mgsRisingFeature.setPageHitWilsonScore(pageHitWilsonScore);
			mgsRisingFeature.setTotalScore(coolNotCoolWilsonScore+pageHitWilsonScore); //not game score, its article score
			mgsRisingFeature.setState(Article.PUBLISH);
			articleDAOInstance.save(mgsRisingFeature);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, kenhaduken82.getId().toString(), mgsRisingFeature.getId().toString(), AppConstants.PUBLIC,"2013-03-29 15:45:00");
			
			Article defianceFeature = new Article();
			defianceFeature.setTitle("Defiance Feature: Marrying TV with games");	
			defianceFeature.setSubtitle("A look into Defiance's unique integration with TV");
			defianceFeature.setAuthor(theone90);
			defianceFeature.setBody("Defiance tries to pull a brave move by clubbing tv with video gaming.");
			defianceFeature.setCategory(Category.feature);
			defianceFeature.setPublishDate("2013-03-30 16:45:00");			
			defianceFeature.setGame(defiance);
			defianceFeature.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			defianceFeature.setInsertTime("2013-03-30 16:45:00");			
			defianceFeature.setUpdateTime("2013-03-30 16:45:00");
			defianceFeature.setPlatforms(platforms);
			coolScore=900;
			notCoolScore=25;
			defianceFeature.setCoolScore(coolScore);
			defianceFeature.setNotCoolScore(notCoolScore);
			coolNotCoolWilsonScore=rankAlgorithm.wilsonScoreCalculator(coolScore, notCoolScore);
			defianceFeature.setCoolNotCoolwilsonScore(coolNotCoolWilsonScore);
			pageCount=929;
			defianceFeature.setPageHitCount(pageCount);
			totalPageHits = daoInstance.getTotalPageHits();
			pageHitWilsonScore=rankAlgorithm.wilsonScoreCalculator(pageCount, (totalPageHits-pageCount));
			defianceFeature.setPageHitWilsonScore(pageHitWilsonScore);
			defianceFeature.setTotalScore(coolNotCoolWilsonScore+pageHitWilsonScore); //not game score, its article score
			defianceFeature.setState(Article.PUBLISH);
			articleDAOInstance.save(defianceFeature);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, theone90.getId().toString(), defianceFeature.getId().toString(), AppConstants.PUBLIC,"2013-03-30 16:45:00");
			
			Article motocrossFeature = new Article();
			motocrossFeature.setTitle("Motocross Madness: Xbox live motocross");						
			motocrossFeature.setSubtitle("Hands-on Motocross Madness demo.");
			motocrossFeature.setAuthor(muchooomg90);
			motocrossFeature.setBody("This motocross madness looks the same");
			motocrossFeature.setCategory(Category.feature);
			motocrossFeature.setPublishDate("2013-04-10 15:45:00");			
			motocrossFeature.setGame(motocrossMadness);
			motocrossFeature.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			motocrossFeature.setInsertTime("2013-04-10 15:45:00");			
			motocrossFeature.setUpdateTime("2013-04-10 15:45:00");
			motocrossFeature.setPlatforms(platforms);
			coolScore=827;
			notCoolScore=25;
			motocrossFeature.setCoolScore(coolScore);
			motocrossFeature.setNotCoolScore(notCoolScore);
			coolNotCoolWilsonScore=rankAlgorithm.wilsonScoreCalculator(coolScore, notCoolScore);
			motocrossFeature.setCoolNotCoolwilsonScore(coolNotCoolWilsonScore);
			pageCount=1000;
			motocrossFeature.setPageHitCount(pageCount);
			totalPageHits = daoInstance.getTotalPageHits();
			pageHitWilsonScore=rankAlgorithm.wilsonScoreCalculator(pageCount, (totalPageHits-pageCount));
			motocrossFeature.setPageHitWilsonScore(pageHitWilsonScore);
			motocrossFeature.setTotalScore(coolNotCoolWilsonScore+pageHitWilsonScore); //not game score, its article score
			motocrossFeature.setState(Article.PUBLISH);
			articleDAOInstance.save(motocrossFeature);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, muchooomg90.getId().toString(), motocrossFeature.getId().toString(), AppConstants.PUBLIC,"2013-04-10 15:45:00");
			
			Article ageOfWushuFeature = new Article();
			ageOfWushuFeature.setTitle("Age of Wushu Feature: The rise of asian gaming out of Japan");	
			ageOfWushuFeature.setSubtitle("A look into Age of Wushu as well as gaming outside Japan");
			ageOfWushuFeature.setAuthor(xwarior81);
			ageOfWushuFeature.setBody("Age of wushu brings asian gaming out of japan and puts on face of the planet. There is hope for other asian countries.");
			ageOfWushuFeature.setCategory(Category.feature);
			ageOfWushuFeature.setPublishDate("2013-04-14 15:45:00");			
			ageOfWushuFeature.setGame(ageOfWushu);
			ageOfWushuFeature.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			ageOfWushuFeature.setInsertTime("2013-04-14 15:45:00");			
			ageOfWushuFeature.setUpdateTime("2013-04-14 15:45:00");
			ageOfWushuFeature.setPlatforms(platforms);
			coolScore=830;
			notCoolScore=29;
			ageOfWushuFeature.setCoolScore(coolScore);
			ageOfWushuFeature.setNotCoolScore(notCoolScore);
			coolNotCoolWilsonScore=rankAlgorithm.wilsonScoreCalculator(coolScore, notCoolScore);
			ageOfWushuFeature.setCoolNotCoolwilsonScore(coolNotCoolWilsonScore);
			pageCount=1002;
			ageOfWushuFeature.setPageHitCount(pageCount);
			totalPageHits = daoInstance.getTotalPageHits();
			pageHitWilsonScore=rankAlgorithm.wilsonScoreCalculator(pageCount, (totalPageHits-pageCount));
			ageOfWushuFeature.setPageHitWilsonScore(pageHitWilsonScore);
			ageOfWushuFeature.setTotalScore(coolNotCoolWilsonScore+pageHitWilsonScore); //not game score, its article score
			ageOfWushuFeature.setState(Article.PUBLISH);
			articleDAOInstance.save(ageOfWushuFeature);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, xwarior81.getId().toString(), ageOfWushuFeature.getId().toString(), AppConstants.PUBLIC,"2013-04-14 15:45:00");
			
			Article cricketGloonicle = new Article();
			cricketGloonicle.setTitle("Why do we have to starve for a good cricket game?");		
			cricketGloonicle.setSubtitle("A look into history and future of cricket video games.");
			cricketGloonicle.setAuthor(loonatic86);
			cricketGloonicle.setBody("When on earth we are going to get next decent cricket game? Come on big great gaming companies, give us our next great cricket game");
			cricketGloonicle.setCategory(Category.gloonicle);
			cricketGloonicle.setPublishDate("2013-04-15 09:45:00");						
			cricketGloonicle.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			cricketGloonicle.setInsertTime("2013-04-15 09:45:00");			
			cricketGloonicle.setUpdateTime("2013-04-15 09:45:00");
			cricketGloonicle.setPlatforms(platforms);
			coolScore=910;
			notCoolScore=20;
			cricketGloonicle.setCoolScore(coolScore);
			cricketGloonicle.setNotCoolScore(notCoolScore);
			coolNotCoolWilsonScore=rankAlgorithm.wilsonScoreCalculator(coolScore, notCoolScore);
			cricketGloonicle.setCoolNotCoolwilsonScore(coolNotCoolWilsonScore);
			pageCount=1000;
			cricketGloonicle.setPageHitCount(pageCount);
			totalPageHits = daoInstance.getTotalPageHits();
			pageHitWilsonScore=rankAlgorithm.wilsonScoreCalculator(pageCount, (totalPageHits-pageCount));
			cricketGloonicle.setPageHitWilsonScore(pageHitWilsonScore);
			cricketGloonicle.setTotalScore(coolNotCoolWilsonScore+pageHitWilsonScore); //not game score, its article score
			cricketGloonicle.setState(Article.PUBLISH);
			articleDAOInstance.save(cricketGloonicle);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, loonatic86.getId().toString(), cricketGloonicle.getId().toString(), AppConstants.PUBLIC,"2013-04-15 09:45:00");
			
			Article freemiumGloonicle = new Article();
			freemiumGloonicle.setTitle("The birth of \"Free\" gaming");				
			freemiumGloonicle.setSubtitle("A look into Freemium model fo gaming.");
			freemiumGloonicle.setAuthor(kenhaduken82);
			freemiumGloonicle.setBody("Freemium gaming is the new way to go");
			freemiumGloonicle.setCategory(Category.gloonicle);
			freemiumGloonicle.setPublishDate("2013-01-15 10:45:00");						
			freemiumGloonicle.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			freemiumGloonicle.setInsertTime("2013-01-15 10:45:00");			
			freemiumGloonicle.setUpdateTime("2013-01-15 10:45:00");
			freemiumGloonicle.setPlatforms(platforms);
			coolScore=877;
			notCoolScore=356;
			freemiumGloonicle.setCoolScore(coolScore);
			freemiumGloonicle.setNotCoolScore(notCoolScore);
			coolNotCoolWilsonScore=rankAlgorithm.wilsonScoreCalculator(coolScore, notCoolScore);
			freemiumGloonicle.setCoolNotCoolwilsonScore(coolNotCoolWilsonScore);
			pageCount=1500;
			freemiumGloonicle.setPageHitCount(pageCount);
			totalPageHits = daoInstance.getTotalPageHits();
			pageHitWilsonScore=rankAlgorithm.wilsonScoreCalculator(pageCount, (totalPageHits-pageCount));
			freemiumGloonicle.setPageHitWilsonScore(pageHitWilsonScore);
			freemiumGloonicle.setTotalScore(coolNotCoolWilsonScore+pageHitWilsonScore); //not game score, its article score
			freemiumGloonicle.setState(Article.PUBLISH);
			articleDAOInstance.save(freemiumGloonicle);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, kenhaduken82.getId().toString(), freemiumGloonicle.getId().toString(), AppConstants.PUBLIC,"2013-01-15 10:45:00");
			
			Article top10horrorGloonicle = new Article();
			top10horrorGloonicle.setTitle("Top 10 horror games");			
			top10horrorGloonicle.setSubtitle("My Top 10 horror games");
			top10horrorGloonicle.setAuthor(xwarior81);
			top10horrorGloonicle.setBody("My top 10 horrors are: 1) Silent hill 2) Resident Evil...");
			top10horrorGloonicle.setCategory(Category.gloonicle);
			top10horrorGloonicle.setPublishDate("2013-04-15 21:45:00");						
			top10horrorGloonicle.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			top10horrorGloonicle.setInsertTime("2013-04-15 21:45:00");			
			top10horrorGloonicle.setUpdateTime("2013-04-15 21:45:00");
			top10horrorGloonicle.setPlatforms(platforms);
			coolScore=800;
			notCoolScore=25;
			top10horrorGloonicle.setCoolScore(coolScore);
			top10horrorGloonicle.setNotCoolScore(notCoolScore);
			coolNotCoolWilsonScore=rankAlgorithm.wilsonScoreCalculator(coolScore, notCoolScore);
			top10horrorGloonicle.setCoolNotCoolwilsonScore(coolNotCoolWilsonScore);
			pageCount=1000;
			top10horrorGloonicle.setPageHitCount(pageCount);
			totalPageHits = daoInstance.getTotalPageHits();
			pageHitWilsonScore=rankAlgorithm.wilsonScoreCalculator(pageCount, (totalPageHits-pageCount));
			top10horrorGloonicle.setPageHitWilsonScore(pageHitWilsonScore);
			top10horrorGloonicle.setTotalScore(coolNotCoolWilsonScore+pageHitWilsonScore); //not game score, its article score
			top10horrorGloonicle.setState(Article.PUBLISH);
			articleDAOInstance.save(top10horrorGloonicle);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, xwarior81.getId().toString(), top10horrorGloonicle.getId().toString(), AppConstants.PUBLIC,"2013-04-15 21:45:00");
			
			Article top10ActionGloonicle = new Article();
			top10ActionGloonicle.setTitle("Top 10 action games");	
			top10ActionGloonicle.setSubtitle("My Top 10 action games");
			top10ActionGloonicle.setAuthor(muchooomg90);
			top10ActionGloonicle.setBody("My top 10 action games: 1)Max Payne 3 2) Halo 4 3)Splinter cell series");
			top10ActionGloonicle.setCategory(Category.gloonicle);
			top10ActionGloonicle.setPublishDate("2013-01-13 20:02:00");						
			top10ActionGloonicle.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			top10ActionGloonicle.setInsertTime("2013-01-13 20:02:00");			
			top10ActionGloonicle.setUpdateTime("2013-01-13 20:02:00");
			top10ActionGloonicle.setPlatforms(platforms);
			coolScore=900;
			notCoolScore=25;
			top10ActionGloonicle.setCoolScore(coolScore);
			top10ActionGloonicle.setNotCoolScore(notCoolScore);
			coolNotCoolWilsonScore=rankAlgorithm.wilsonScoreCalculator(coolScore, notCoolScore);
			top10ActionGloonicle.setCoolNotCoolwilsonScore(coolNotCoolWilsonScore);
			pageCount=1500;
			top10ActionGloonicle.setPageHitCount(pageCount);
			totalPageHits = daoInstance.getTotalPageHits();
			pageHitWilsonScore=rankAlgorithm.wilsonScoreCalculator(pageCount, (totalPageHits-pageCount));
			top10ActionGloonicle.setPageHitWilsonScore(pageHitWilsonScore);
			top10ActionGloonicle.setTotalScore(coolNotCoolWilsonScore+pageHitWilsonScore); //not game score, its article score
			top10ActionGloonicle.setState(Article.PUBLISH);
			articleDAOInstance.save(top10ActionGloonicle);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, muchooomg90.getId().toString(), top10ActionGloonicle.getId().toString(), AppConstants.PUBLIC,"2013-01-13 20:02:00");
			
			Article myWeeklyShow = new Article();
			myWeeklyShow.setTitle("My weekly gaming adventure");
			myWeeklyShow.setSubtitle("This week we are looking into funny things in gaming.");
			top10ActionGloonicle.setSubtitle("Episode 10");
			myWeeklyShow.setAuthor(theone90);
			myWeeklyShow.setBody("I will share my daily gaming adventure in this series.");
			myWeeklyShow.setCategory(Category.gloonicle);
			myWeeklyShow.setPublishDate("2013-01-14 00:02:00");						
			myWeeklyShow.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			myWeeklyShow.setInsertTime("2013-01-14 00:02:00");			
			myWeeklyShow.setUpdateTime("2013-01-14 00:02:00");
			myWeeklyShow.setPlatforms(platforms);
			coolScore=1500;
			notCoolScore=25;
			myWeeklyShow.setCoolScore(coolScore);
			myWeeklyShow.setNotCoolScore(notCoolScore);
			coolNotCoolWilsonScore=rankAlgorithm.wilsonScoreCalculator(coolScore, notCoolScore);
			myWeeklyShow.setCoolNotCoolwilsonScore(coolNotCoolWilsonScore);
			pageCount=4000;
			myWeeklyShow.setPageHitCount(pageCount);
			totalPageHits = daoInstance.getTotalPageHits();
			pageHitWilsonScore=rankAlgorithm.wilsonScoreCalculator(pageCount, (totalPageHits-pageCount));
			myWeeklyShow.setPageHitWilsonScore(pageHitWilsonScore);
			myWeeklyShow.setTotalScore(coolNotCoolWilsonScore+pageHitWilsonScore); //not game score, its article score
			myWeeklyShow.setState(Article.PUBLISH);
			articleDAOInstance.save(myWeeklyShow);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, theone90.getId().toString(), myWeeklyShow.getId().toString(), AppConstants.PUBLIC,"2013-01-14 00:02:00");
			
			Article eaDisasterNews = new Article();
			eaDisasterNews.setTitle("EA has been again ranked as worst company in America");				
			eaDisasterNews.setSubtitle("EA does it again");
			eaDisasterNews.setAuthor(theone90);
			eaDisasterNews.setBody("EA has been again ranked as worst company in America. This is the second time they have been rated like this.");
			eaDisasterNews.setCategory(Category.news);
			eaDisasterNews.setPublishDate("2013-01-12 10:02:00");						
			eaDisasterNews.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			eaDisasterNews.setInsertTime("2013-01-12 10:02:00");			
			eaDisasterNews.setUpdateTime("2013-01-12 10:02:00");
			eaDisasterNews.setPlatforms(platforms);
			coolScore=800;
			notCoolScore=56;
			eaDisasterNews.setCoolScore(coolScore);
			eaDisasterNews.setNotCoolScore(notCoolScore);
			coolNotCoolWilsonScore=rankAlgorithm.wilsonScoreCalculator(coolScore, notCoolScore);
			eaDisasterNews.setCoolNotCoolwilsonScore(coolNotCoolWilsonScore);
			pageCount=1000;
			eaDisasterNews.setPageHitCount(pageCount);
			totalPageHits = daoInstance.getTotalPageHits();
			pageHitWilsonScore=rankAlgorithm.wilsonScoreCalculator(pageCount, (totalPageHits-pageCount));
			eaDisasterNews.setPageHitWilsonScore(pageHitWilsonScore);
			eaDisasterNews.setTotalScore(coolNotCoolWilsonScore+pageHitWilsonScore); //not game score, its article score
			eaDisasterNews.setState(Article.PUBLISH);
			articleDAOInstance.save(eaDisasterNews);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, theone90.getId().toString(), eaDisasterNews.getId().toString(), AppConstants.PUBLIC,"2013-01-12 10:02:00");
			
			Article ps4News = new Article();
			ps4News.setTitle("PS4 launch price revealed");		
			ps4News.setSubtitle("About the launch way below $500");
			ps4News.setAuthor(muchooomg90);
			ps4News.setBody("PS4 is said to launch at a starting price of $430");
			ps4News.setCategory(Category.news);
			ps4News.setPublishDate("2013-04-02 10:02:00");						
			ps4News.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			ps4News.setInsertTime("2013-04-02 10:02:00");
			ps4News.setUpdateTime("2013-04-02 10:02:00");
			ps4News.setPlatforms(platforms);
			coolScore=870;
			notCoolScore=10;
			ps4News.setCoolScore(coolScore);
			ps4News.setNotCoolScore(notCoolScore);
			coolNotCoolWilsonScore=rankAlgorithm.wilsonScoreCalculator(coolScore, notCoolScore);
			ps4News.setCoolNotCoolwilsonScore(coolNotCoolWilsonScore);
			pageCount=5600;
			ps4News.setPageHitCount(pageCount);
			totalPageHits = daoInstance.getTotalPageHits();
			pageHitWilsonScore=rankAlgorithm.wilsonScoreCalculator(pageCount, (totalPageHits-pageCount));
			ps4News.setPageHitWilsonScore(pageHitWilsonScore);
			ps4News.setTotalScore(coolNotCoolWilsonScore+pageHitWilsonScore); //not game score, its article score
			ps4News.setState(Article.PUBLISH);
			articleDAOInstance.save(ps4News);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, muchooomg90.getId().toString(), ps4News.getId().toString(), AppConstants.PUBLIC,"2013-04-02 10:02:00");
			
			Article warnerBrothersNews = new Article();
			warnerBrothersNews.setTitle("Warner Brothers registers Mad Max domains");		
			warnerBrothersNews.setSubtitle("Can this be a new game?");
			warnerBrothersNews.setAuthor(xwarior81);
			warnerBrothersNews.setBody("Warner Brothers registers multiple domains based on Mad Max");
			warnerBrothersNews.setCategory(Category.news);
			warnerBrothersNews.setPublishDate("2013-04-14 10:02:00");						
			warnerBrothersNews.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			warnerBrothersNews.setInsertTime("2013-04-14 10:02:00");
			warnerBrothersNews.setUpdateTime("2013-04-14 10:02:00");
			warnerBrothersNews.setPlatforms(platforms);
			coolScore=500;
			notCoolScore=10;
			warnerBrothersNews.setCoolScore(coolScore);
			warnerBrothersNews.setNotCoolScore(notCoolScore);
			coolNotCoolWilsonScore=rankAlgorithm.wilsonScoreCalculator(coolScore, notCoolScore);
			warnerBrothersNews.setCoolNotCoolwilsonScore(coolNotCoolWilsonScore);
			pageCount=5000;
			warnerBrothersNews.setPageHitCount(pageCount);
			totalPageHits = daoInstance.getTotalPageHits();
			pageHitWilsonScore=rankAlgorithm.wilsonScoreCalculator(pageCount, (totalPageHits-pageCount));
			warnerBrothersNews.setPageHitWilsonScore(pageHitWilsonScore);
			warnerBrothersNews.setTotalScore(coolNotCoolWilsonScore+pageHitWilsonScore); //not game score, its article score
			warnerBrothersNews.setState(Article.PUBLISH);
			articleDAOInstance.save(warnerBrothersNews);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, xwarior81.getId().toString(), warnerBrothersNews.getId().toString(), AppConstants.PUBLIC,"2013-04-14 10:02:00");
			
			Article batmanOriginsNews = new Article();
			batmanOriginsNews.setTitle("Batman Origins game revealed");		
			batmanOriginsNews.setSubtitle("A new Batman game is about to arrive");
			batmanOriginsNews.setAuthor(kenhaduken82);
			batmanOriginsNews.setBody("A new batman arkham orignins game revealed. The important thing is rocksteady is not working on it.");
			batmanOriginsNews.setCategory(Category.news);
			batmanOriginsNews.setPublishDate("2013-04-10 10:02:00");						
			batmanOriginsNews.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			batmanOriginsNews.setInsertTime("2013-04-10 10:02:00");
			batmanOriginsNews.setUpdateTime("2013-04-10 10:02:00");
			batmanOriginsNews.setPlatforms(platforms);
			coolScore=1678;
			notCoolScore=12;
			batmanOriginsNews.setCoolScore(coolScore);
			batmanOriginsNews.setNotCoolScore(notCoolScore);
			coolNotCoolWilsonScore=rankAlgorithm.wilsonScoreCalculator(coolScore, notCoolScore);
			batmanOriginsNews.setCoolNotCoolwilsonScore(coolNotCoolWilsonScore);
			pageCount=7985;
			batmanOriginsNews.setPageHitCount(pageCount);
			totalPageHits = daoInstance.getTotalPageHits();
			pageHitWilsonScore=rankAlgorithm.wilsonScoreCalculator(pageCount, (totalPageHits-pageCount));
			batmanOriginsNews.setPageHitWilsonScore(pageHitWilsonScore);
			batmanOriginsNews.setTotalScore(coolNotCoolWilsonScore+pageHitWilsonScore); //not game score, its article score
			batmanOriginsNews.setState(Article.PUBLISH);
			articleDAOInstance.save(batmanOriginsNews);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, kenhaduken82.getId().toString(), batmanOriginsNews.getId().toString(), AppConstants.PUBLIC,"2013-04-10 10:02:00");
			
			Article supermanGameNews = new Article();
			supermanGameNews.setTitle("Rocksteady is working on supposedly a Superman game.");						
			supermanGameNews.setSubtitle("We can recently get a new superman game.");
			supermanGameNews.setAuthor(loonatic86);
			supermanGameNews.setBody("Rocksteady studios is most probably working on a Superman game. No details are available till now");
			supermanGameNews.setCategory(Category.news);
			supermanGameNews.setPublishDate("2013-04-20 10:02:00");						
			supermanGameNews.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			supermanGameNews.setInsertTime("2013-04-20 10:02:00");
			supermanGameNews.setUpdateTime("2013-04-20 10:02:00");
			supermanGameNews.setPlatforms(platforms);
			coolScore=2526;
			notCoolScore=12;
			supermanGameNews.setCoolScore(coolScore);
			supermanGameNews.setNotCoolScore(notCoolScore);
			coolNotCoolWilsonScore=rankAlgorithm.wilsonScoreCalculator(coolScore, notCoolScore);
			supermanGameNews.setCoolNotCoolwilsonScore(coolNotCoolWilsonScore);
			pageCount=8995;
			supermanGameNews.setPageHitCount(pageCount);
			totalPageHits = daoInstance.getTotalPageHits();
			pageHitWilsonScore=rankAlgorithm.wilsonScoreCalculator(pageCount, (totalPageHits-pageCount));
			supermanGameNews.setPageHitWilsonScore(pageHitWilsonScore);
			supermanGameNews.setTotalScore(coolNotCoolWilsonScore+pageHitWilsonScore); //not game score, its article score
			supermanGameNews.setState(Article.PUBLISH);
			articleDAOInstance.save(supermanGameNews);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, loonatic86.getId().toString(), supermanGameNews.getId().toString(), AppConstants.PUBLIC,"2013-04-20 10:02:00");
			
			Article newPrinceOfPersiaGame = new Article();
			newPrinceOfPersiaGame.setTitle("New Prince of Persia Game surfaced");			
			newPrinceOfPersiaGame.setSubtitle("Another Prince of Persia Game surfaced.");
			newPrinceOfPersiaGame.setAuthor(loonatic87);
			newPrinceOfPersiaGame.setBody("Climax Studio is supposedly working on a new prince of persia game.");
			newPrinceOfPersiaGame.setCategory(Category.news);
			newPrinceOfPersiaGame.setPublishDate(Utility.convertDateToString(new Date()));			
			newPrinceOfPersiaGame.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			newPrinceOfPersiaGame.setInsertTime(Utility.convertDateToString(new Date()));
			newPrinceOfPersiaGame.setUpdateTime(Utility.convertDateToString(new Date()));
			newPrinceOfPersiaGame.setPlatforms(platforms);
			coolScore=1026;
			notCoolScore=17;
			newPrinceOfPersiaGame.setCoolScore(coolScore);
			newPrinceOfPersiaGame.setNotCoolScore(notCoolScore);
			coolNotCoolWilsonScore=rankAlgorithm.wilsonScoreCalculator(coolScore, notCoolScore);
			newPrinceOfPersiaGame.setCoolNotCoolwilsonScore(coolNotCoolWilsonScore);
			pageCount=9656;
			newPrinceOfPersiaGame.setPageHitCount(pageCount);
			totalPageHits = daoInstance.getTotalPageHits();
			pageHitWilsonScore=rankAlgorithm.wilsonScoreCalculator(pageCount, (totalPageHits-pageCount));
			newPrinceOfPersiaGame.setPageHitWilsonScore(pageHitWilsonScore);
			newPrinceOfPersiaGame.setTotalScore(coolNotCoolWilsonScore+pageHitWilsonScore); //not game score, its article score
			newPrinceOfPersiaGame.setState(Article.PUBLISH);
			articleDAOInstance.save(newPrinceOfPersiaGame);
			activityDAOInstance.create(Activity.ACTIVITY_POST_PUBLISH, loonatic87.getId().toString(), newPrinceOfPersiaGame.getId().toString(), AppConstants.PUBLIC,Utility.convertDateToString(new Date()));
			
			/*Article farCry3Review = new Article();
			farCry3Review.setTitle("far cry 3 review");
			User jaguarpaw80Author = jaguarpaw80;						
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
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
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
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
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
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
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
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
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
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
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
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
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
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
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
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
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
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
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
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
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
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
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
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
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
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
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
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
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
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
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
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
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
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
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
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
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
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
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
			farCry3Review.setFeaturedImagePath("articleFeaturedImage");
			//farCry3Review.setInsertTime(Utility.convertDateToString(new Date())); //"2013-04-08 00:05:59"
			farCry3Review.setInsertTime("2013-04-11 09:00:00");
			farCry3Review.setScore(0.78);
			farCry3Review.setPlatforms(platforms);
			gloonDatastore.save(farCry3Review);*/
				
		}
}

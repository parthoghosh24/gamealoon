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
import com.gamealoon.database.daos.MediaDAO;
import com.gamealoon.database.daos.PlatformDAO;
import com.gamealoon.database.daos.UserDAO;
//import com.gamealoon.database.daos.UserGameScoreMapDAO;
import com.gamealoon.models.Achievement;
import com.gamealoon.models.Activity;
//import com.gamealoon.models.Article;
//import com.gamealoon.models.Category;
import com.gamealoon.models.Game;
import com.gamealoon.models.Genre;
import com.gamealoon.models.Media;
import com.gamealoon.models.Platform;
import com.gamealoon.models.User;
import com.gamealoon.utility.AppConstants;
import com.gamealoon.utility.Utility;
import play.Application;
import play.GlobalSettings;
import play.mvc.Http.MultipartFormData.FilePart;

public class GloonGlobal extends GlobalSettings {

	final AchievementDAO achievementDAOInstance = AchievementDAO.instantiateDAO();	
	final GameDAO gameDAOInstance = GameDAO.instantiateDAO();	
	final PlatformDAO platformDAOInstance = PlatformDAO.instantiateDAO();	
	final MediaDAO mediaDAOInstance = MediaDAO.instantiateDAO(); 
	
	 @Override
	public void onStart(Application app) {
		 if (Achievement.getAllAchievementCount()==0) {
				System.out.println("Data getting created.............");				
				createAchievements();				
				createPlatforms();					
				createGames();							
				

			}
	}


	private void createAchievements()
		{
			/**
			 * Temporary way to save achievement...
			 * 
			 */
		   			
				Achievement newGloonie = new Achievement();
				newGloonie.setTitle("New Gloonie!");
				newGloonie.setDescription("Welcome to gamealoon!!!");
				newGloonie.setAchievementImage("");
			
			achievementDAOInstance.save(newGloonie);			
		}
		
		

		
		
		private void createPlatforms()
		{
			Date time = new Date();
			Platform playstation3 = new Platform();
			playstation3.setTitle("Playstation 3");
			playstation3.setShortTitle("ps3");
			playstation3.setDescription("The PlayStation 3 (PS3) is a home video game console produced by Sony Computer Entertainment. It is the successor to the PlayStation 2, as part of the PlayStation series.");
			playstation3.setManufacturer("Sony EMCS,Foxconn,ASUSTeK");
			playstation3.setDeveloper("SCEI");
			playstation3.setInsertTime(Utility.convertDateToString(time));
			playstation3.setTimestamp(time.getTime());
			platformDAOInstance.save(playstation3);
			
			Platform playstation4 = new Platform();
			playstation4.setTitle("Playstation 4");
			playstation4.setShortTitle("ps4");
			playstation4.setDescription("PlayStation 4 (PS4) is an upcoming video game console from Sony Computer Entertainment. Announced as the successor to PlayStation 3 during a press conference on February 20, 2013, it will launch on November 15, 2013, in North America, and November 29, 2013, in Europe and Australia.");
			playstation4.setManufacturer("Sony Computer Entertainment");			
			playstation4.setDeveloper("Sony Computer Entertainment");
			playstation4.setInsertTime(Utility.convertDateToString(time));
			playstation4.setTimestamp(time.getTime());
			platformDAOInstance.save(playstation4);
			
			Platform xbox360 = new Platform();
			xbox360.setTitle("Xbox 360");
			xbox360.setShortTitle("xbox360");
			xbox360.setDescription("The Xbox 360 is the second video game console developed by and produced for Microsoft and the successor to the Xbox.");
			xbox360.setManufacturer("Flextronics,Wistron,Celestica,Foxconn");
			xbox360.setDeveloper("Microsoft");
			xbox360.setInsertTime(Utility.convertDateToString(time));
			xbox360.setTimestamp(time.getTime());
			platformDAOInstance.save(xbox360);
			
			Platform xboxOne = new Platform();
			xboxOne.setTitle("Xbox One");
			xboxOne.setShortTitle("xboxOne");
			xboxOne.setDescription("The Xbox One is an upcoming video game console from Microsoft. Announced on May 21, 2013, it is the successor to the Xbox 360 and the third console in the Xbox family of consoles.");
			xboxOne.setManufacturer("Microsoft");
			xboxOne.setDeveloper("Microsoft");
			xboxOne.setInsertTime(Utility.convertDateToString(time));
			xboxOne.setTimestamp(time.getTime());
			platformDAOInstance.save(xboxOne);
			
			Platform pc = new Platform();
			pc.setTitle("PC");
			pc.setShortTitle("pc");
			pc.setDescription("AMD and Intel are the bigget CPU makers.");
			pc.setManufacturer("AMD,Intel");
			pc.setDeveloper("AMD,Intel");
			pc.setInsertTime(Utility.convertDateToString(time));
			pc.setTimestamp(time.getTime());
			platformDAOInstance.save(pc);
			
			Platform ios = new Platform();
			ios.setTitle("IOS");
			ios.setShortTitle("ios");
			ios.setDescription("iOS (previously iPhone OS) is a mobile operating system developed and distributed by Apple Inc.");
			ios.setManufacturer("Apple");
			ios.setDeveloper("Apple");
			ios.setInsertTime(Utility.convertDateToString(time));
			ios.setTimestamp(time.getTime());
			platformDAOInstance.save(ios);
			
			Platform android = new Platform();
			android.setTitle("Android");
			android.setShortTitle("android");
			android.setDescription("Android is an operating system based on the Linux kernel,[11] and designed primarily for touchscreen mobile devices such as smartphones and tablet computers. Initially developed by Android, Inc., which Google backed financially and later bought in 2005.");
			android.setManufacturer("Google");
			android.setDeveloper("Google");
			android.setInsertTime(Utility.convertDateToString(time));
			android.setTimestamp(time.getTime());
			platformDAOInstance.save(android);
			
			Platform wiiu = new Platform();
			wiiu.setTitle("WII-U");
			wiiu.setShortTitle("wiiu");
			wiiu.setDescription("The Wii U is a video game console from Nintendo and the successor to the Wii. The system was released on November 18, 2012, in North America; November 30, 2012, in the PAL regions; and on December 8, 2012, in Japan. It is the first entry in the eighth generation of video game home consoles, and will compete with Sony's PlayStation 4 and Microsoft's Xbox One.");
			wiiu.setManufacturer("Nintendo,Foxconn,Mitsumi");
			wiiu.setDeveloper("Nintendo");
			wiiu.setInsertTime(Utility.convertDateToString(time));
			wiiu.setTimestamp(time.getTime());
			platformDAOInstance.save(wiiu);
			
			Platform n3ds = new Platform();
			n3ds.setTitle("3DS");
			n3ds.setShortTitle("n3ds");
			n3ds.setDescription("The Nintendo 3DS is a portable game console produced by Nintendo. It is an autostereoscopic device capable of projecting stereoscopic 3D effects without the use of 3D glasses or additional accessories.");
			n3ds.setManufacturer("Nintendo,Foxconn");
			n3ds.setDeveloper("Nintendo");
			n3ds.setInsertTime(Utility.convertDateToString(time));
			n3ds.setTimestamp(time.getTime());
			platformDAOInstance.save(n3ds);
			
			Platform vita = new Platform();
			vita.setTitle("Playstation Vita");
			vita.setShortTitle("vita");
			vita.setDescription("The PlayStation Vita is a handheld game console manufactured and marketed by Sony Computer Entertainment. It is the successor to the PlayStation Portable as part of the PlayStation brand of gaming devices.");
			vita.setManufacturer("Sony Computer Entertainment");
			vita.setDeveloper("Sony Computer Entertainment");
			vita.setInsertTime(Utility.convertDateToString(time));
			vita.setTimestamp(time.getTime());
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
			
			Date time = new Date();

			Game maxPayne3 = new Game();
			maxPayne3.setTitle("Max Payne 3");			
			maxPayne3.setDescription("In Max Payne 3, the player controls Max Payne, a former NYPD detective who had became a vigilante after his wife and daughter were brutally murdered. Nine years after the events of the second game, Max meets Raul Passos, who gets him the job as a private security contractor in Brazil. He quickly finds himself constantly encountering difficult situations, which leads him on a search for the culprits of deaths and betrayals");
			maxPayne3.setDeveloper("Rockstar Vancouver");
			maxPayne3.setPublisher("Rockstar Games");
			maxPayne3.setGenere(Genre.Action); 
			maxPayne3.setPrice("");
			String[] maxPayne3Platforms = {"ps3","xbox360","pc"};
			maxPayne3.setPlatforms(maxPayne3Platforms);			
			maxPayne3.setRating(Game.MATURE);
			maxPayne3.setReleaseDate("2012-05-15");
			maxPayne3.setGameReleaseStatus(Game.RELEASED);			
			Media media = new Media();
			media.setFileName("mp_cover.jpg");
			media.setImmediateOwner(Utility.shortenString(maxPayne3.getTitle()));
			media.setOwner(Media.GAME);
			media.setUrl("https://s3.amazonaws.com/gloonuploadsdev/game/maxpayne3/uploads/mp_cover.jpg");
			media.setInsertTime(Utility.convertDateToString(time));
			media.setTimestamp(time.getTime());
			mediaDAOInstance.save(media);
			maxPayne3.setGameBoxShot(media.getId().toString());			
			gameDAOInstance.save(maxPayne3);			
			
			/*Game farCry3 = new Game();
			farCry3.setTitle("Far Cry 3");
			farCry3.setDescription("With Far Cry 3, players step into the shoes of Jason Brody, a man alone at the edge of the world, stranded on a mysterious tropical island. In this savage paradise where lawlessness and violence are the only sure thing, players dictate how the story unfolds, from the battles they choose to fight to the allies or enemies they make along the way. As Jason Brody, players will slash, sneak, detonate and shoot their way across the island in a world that has lost all sense of right and wrong.");
			farCry3.setDeveloper("Ubisoft Montreal");
			farCry3.setPublisher("Ubisoft");
			farCry3.setGenere(Genre.Fps);
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
			mgsRising.setReleaseDate("2013-02-19");		
			mgsRising.setGameBoxShotPath("mgsr_cover.jpg");			
			gameDAOInstance.save(mgsRising);
			uptoGamename =AppConstants.APP_ABSOLUTE_IMAGE_GAME_PATH+Utility.shortenString(mgsRising.getTitle())+"\\uploads\\boxshot\\";
			makeGamenameDir = new File(uptoGamename);
			makeGamenameDir.mkdirs();
			gameDAOInstance.save(mgsRising);*/
			
		}
		
		
}

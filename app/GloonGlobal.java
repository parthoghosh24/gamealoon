import java.text.ParseException;
import java.util.Date;
import java.util.List;
//import java.util.List;

import com.gamealoon.algorithm.RankAlgorithm;
import com.gamealoon.database.GloonDatabase;
import com.gamealoon.database.daos.AchievementDAO;
import com.gamealoon.database.daos.GameDAO;
import com.gamealoon.database.daos.MediaDAO;
import com.gamealoon.database.daos.PlatformDAO;
import com.gamealoon.database.daos.UserDAO;
import com.gamealoon.models.Achievement;
import com.gamealoon.models.Game;
import com.gamealoon.models.Genre;
import com.gamealoon.models.Media;
import com.gamealoon.models.Platform;
import com.gamealoon.models.User;
import com.gamealoon.utility.Utility;
import com.mongodb.Mongo;

import play.Application;
import play.GlobalSettings;
import play.Play;

public class GloonGlobal extends GlobalSettings {

	final AchievementDAO achievementDAOInstance = AchievementDAO.instantiateDAO();	
	final GameDAO gameDAOInstance = GameDAO.instantiateDAO();	
	final PlatformDAO platformDAOInstance = PlatformDAO.instantiateDAO();	
	final MediaDAO mediaDAOInstance = MediaDAO.instantiateDAO(); 
	
	 @Override
	public void onStart(Application app) {
		 
		 //temp update
		 UserDAO userDAOInstance = UserDAO.instantiateDAO();
		List<User> users = userDAOInstance.getTopUsers(0);
		Mongo instance = GloonDatabase.instantiate().getMongoInstance();
		for(User user: users)
		{
			Double userFollowedByScore = user.getFollowedBy().size()/(userDAOInstance.count()*1.0);				
			user.setUserFollowScore(userFollowedByScore);
			Double articlePublishRateRatio=RankAlgorithm.calculateUserArticlePublishRateRatio(user.getArticlePublishRate(), instance);		
			Double articleScoreRatio = RankAlgorithm.calculateUserArticleScoreRatio(user.getUserArticleScore(), instance);		
			Double userTotalScore = RankAlgorithm.calculateUserScore(articlePublishRateRatio, userFollowedByScore, articleScoreRatio);
			user.setTotalScore(userTotalScore);
			userDAOInstance.save(user);
		}
		 if (Achievement.getAllAchievementCount()==0) {
				System.out.println("Data getting created.............");				
				createAchievements();				
				createPlatforms();					
				try {
					createGames();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}							
				

			}
	}


	private void createAchievements()
		{
			/**
			 * Temporary way to save achievement...
			 * 
			 */
				Date time = new Date();
		   			
				Achievement newGloonie = new Achievement();
				newGloonie.setTitle("New Gloonie!");
				newGloonie.setDescription("Welcome to gamealoon!!!");
				newGloonie.setAchievementImage("");
				newGloonie.setInsertTime(Utility.convertDateToString(time));
				newGloonie.setTimestamp(time.getTime());
			
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
		
		private void createGames() throws ParseException
		{					
			
			Date time = new Date();

			Game maxPayne3 = new Game();
			maxPayne3.setTitle("Max Payne 3");			
			maxPayne3.setDescription("In Max Payne 3, the player controls Max Payne, a former NYPD detective who had became a vigilante after his wife and daughter were brutally murdered. Nine years after the events of the second game, Max meets Raul Passos, who gets him the job as a private security contractor in Brazil. He quickly finds himself constantly encountering difficult situations, which leads him on a search for the culprits of deaths and betrayals");
			maxPayne3.setDeveloper("Rockstar Vancouver");
			maxPayne3.setPublisher("Rockstar Games");
			maxPayne3.setGenre(Genre.Tps); 
			maxPayne3.setPrice("");
			String[] maxPayne3Platforms = {"ps3","xbox360","pc"};
			maxPayne3.setPlatforms(maxPayne3Platforms);			
			maxPayne3.setRating(Game.MATURE);
			maxPayne3.setInsertTime(Utility.convertDateToString(time));
			maxPayne3.setTimestamp(time.getTime());
			maxPayne3.setUpdateTime(Utility.convertDateToString(time));
			maxPayne3.setReleaseDate("2012-05-15");
			maxPayne3.setReleaseTimeStamp(Utility.convertFromStringToDateFormat2("2012-05-15").getTime());
			maxPayne3.setGameReleaseStatus(Game.RELEASED);			
			Media media = new Media();
			media.setFileName("mp_cover.jpg");
			media.setMediaType(Media.IMAGE);
			media.setImmediateOwner(Utility.shortenString(maxPayne3.getTitle()));
			media.setOwner(Media.GAME);
			if(Play.isDev() || Play.isTest())
			{
				media.setUrl("https://s3.amazonaws.com/gloonuploadsdev/game/maxpayne3/uploads/mp_cover.jpg");
			}
			if(Play.isProd())
			{
				media.setUrl("https://s3.amazonaws.com/gloonuploads/game/maxpayne3/uploads/mp_cover.jpg");
			}
			media.setInsertTime(Utility.convertDateToString(time));
			media.setTimestamp(time.getTime());
			mediaDAOInstance.save(media);
			maxPayne3.setGameBoxShot(media.getId().toString());			
			gameDAOInstance.save(maxPayne3);			
			
			time = new Date();
			Game farCry3 = new Game();
			farCry3.setTitle("Far Cry 3");
			farCry3.setDescription("Far Cry 3 is set on a tropical island between the Indian and Pacific Oceans.After a vacation goes awry, protagonist Jason Brody must save his friends, who have been kidnapped by pirates and escape from the island and its unhinged inhabitants.");
			farCry3.setDeveloper("Ubisoft Montreal");
			farCry3.setPublisher("Ubisoft");
			farCry3.setGenre(Genre.Fps);
			farCry3.setPrice("");
			String[] farCry3Platforms = {"ps3","xbox360","pc"};
			farCry3.setPlatforms(farCry3Platforms);
			farCry3.setRating(Game.MATURE);
			farCry3.setInsertTime(Utility.convertDateToString(time));
			farCry3.setTimestamp(time.getTime());
			farCry3.setUpdateTime(Utility.convertDateToString(time));
			farCry3.setReleaseDate("2012-12-04");
			farCry3.setReleaseTimeStamp(Utility.convertFromStringToDateFormat2("2012-12-04").getTime());
			farCry3.setGameReleaseStatus(Game.RELEASED);
			media = new Media();
			media.setFileName("fc3_cover.jpg");
			media.setMediaType(Media.IMAGE);
			media.setImmediateOwner(Utility.shortenString(farCry3.getTitle()));
			media.setOwner(Media.GAME);
			if(Play.isDev() || Play.isTest())
			{
				media.setUrl("https://s3.amazonaws.com/gloonuploadsdev/game/farcry3/uploads/fc3_cover.jpg");
			}
			if(Play.isProd())
			{
				media.setUrl("https://s3.amazonaws.com/gloonuploads/game/farcry3/uploads/fc3_cover.jpg");
			}				
			media.setInsertTime(Utility.convertDateToString(time));
			media.setTimestamp(time.getTime());
			mediaDAOInstance.save(media);
			farCry3.setGameBoxShot(media.getId().toString());			
			gameDAOInstance.save(farCry3);
			
			time = new Date();
			Game batmanArkhamCity = new Game();
			batmanArkhamCity.setTitle("Batman: Arkham City");
			batmanArkhamCity.setDescription("Written by veteran Batman writer Paul Dini with Paul Crocker and Sefton Hill, Arkham City is based on the franchise's long-running comic book mythos. In the game's main storyline, Batman is incarcerated in Arkham City, a massive new super-prison enclosing the decaying urban slums of fictional Gotham City. He must uncover the secret behind the sinister scheme, \"Protocol 10\", orchestrated by the facilities warden, Hugo Strange.");
			batmanArkhamCity.setDeveloper("Warner Bros. Interactive, Eidos Interactive");
			batmanArkhamCity.setPublisher(" Rocksteady Studios");
			batmanArkhamCity.setGenre(Genre.ActionAdventure);
			batmanArkhamCity.setPrice("");
			String[] batmanArkhamCityPlatforms = {"ps3","xbox360","pc","wiiu"};
			batmanArkhamCity.setPlatforms(batmanArkhamCityPlatforms);
			batmanArkhamCity.setRating(Game.TEEN);
			batmanArkhamCity.setInsertTime(Utility.convertDateToString(time));
			batmanArkhamCity.setTimestamp(time.getTime());
			batmanArkhamCity.setUpdateTime(Utility.convertDateToString(time));
			batmanArkhamCity.setReleaseDate("2011-10-21");
			batmanArkhamCity.setReleaseTimeStamp(Utility.convertFromStringToDateFormat2("2011-10-21").getTime());
			batmanArkhamCity.setGameReleaseStatus(Game.RELEASED);
			media = new Media();
			media.setFileName("bac_cover.jpg");
			media.setMediaType(Media.IMAGE);
			media.setImmediateOwner(Utility.shortenString(batmanArkhamCity.getTitle()));
			media.setOwner(Media.GAME);
			if(Play.isDev() || Play.isTest())
			{
				media.setUrl("https://s3.amazonaws.com/gloonuploadsdev/game/batmanarkhamcity/uploads/bac_cover.jpg");
			}
			if(Play.isProd())
			{
				media.setUrl("https://s3.amazonaws.com/gloonuploads/game/batmanarkhamcity/uploads/bac_cover.jpg");
			}				
			media.setInsertTime(Utility.convertDateToString(time));
			media.setTimestamp(time.getTime());
			mediaDAOInstance.save(media);
			batmanArkhamCity.setGameBoxShot(media.getId().toString());			
			gameDAOInstance.save(batmanArkhamCity);
			
			time = new Date();
			Game batmanArkhamOrigins = new Game();
			batmanArkhamOrigins.setTitle("Batman: Arkham Origins");
			batmanArkhamOrigins.setDescription("Arkham Origins moved development away from series creators Rocksteady Studios, and is written by Corey May and Dooma Wendschuh. The game's main storyline is set five years before that of 2009's Batman: Arkham Asylum and follows a younger and less refined Batman who has a bounty placed on his head by crime lord Black Mask, drawing eight of the world's greatest assassins to Gotham City on Christmas Eve.");
			batmanArkhamOrigins.setDeveloper("WB Games Montreal");
			batmanArkhamOrigins.setPublisher("Warner Bros. Interactive Entertainment");
			batmanArkhamOrigins.setGenre(Genre.ActionAdventure);
			batmanArkhamOrigins.setPrice("");			
			batmanArkhamOrigins.setPlatforms(batmanArkhamCityPlatforms);
			batmanArkhamOrigins.setRating(Game.TEEN);
			batmanArkhamOrigins.setInsertTime(Utility.convertDateToString(time));
			batmanArkhamOrigins.setTimestamp(time.getTime());
			batmanArkhamOrigins.setUpdateTime(Utility.convertDateToString(time));
			batmanArkhamOrigins.setGameReleaseStatus(Game.RELEASED);
			batmanArkhamOrigins.setReleaseDate("2013-10-25");	
			batmanArkhamOrigins.setReleaseTimeStamp(Utility.convertFromStringToDateFormat2("2013-10-25").getTime());
			media = new Media();
			media.setFileName("bao_cover.jpg");
			media.setMediaType(Media.IMAGE);
			media.setImmediateOwner(Utility.shortenString(batmanArkhamOrigins.getTitle()));
			media.setOwner(Media.GAME);
			if(Play.isDev() || Play.isTest())
			{
				media.setUrl("https://s3.amazonaws.com/gloonuploadsdev/game/batmanarkhamorigins/uploads/bao_cover.jpg");
			}
			if(Play.isProd())
			{
				media.setUrl("https://s3.amazonaws.com/gloonuploads/game/batmanarkhamorigins/uploads/bao_cover.jpg");
			}				
			media.setInsertTime(Utility.convertDateToString(time));
			media.setTimestamp(time.getTime());
			mediaDAOInstance.save(media);
			batmanArkhamOrigins.setGameBoxShot(media.getId().toString());			
			gameDAOInstance.save(batmanArkhamOrigins);
			
			time = new Date();
			Game gta5 = new Game();
			gta5.setTitle("Grand Theft Auto V");
			gta5.setDescription("Grand Theft Auto V is played from a third-person perspective in an open world environment, allowing the player to interact with the game world at their leisure. The game is set mainly within the fictional state of San Andreas (based on Southern California) and affords the player the ability to freely roam the world's countryside and the fictional city of Los Santos (based on Los Angeles). The single-player story is told through three player-controlled protagonists whom the player switches between, and it follows their efforts to plan and execute six large heists to accrue wealth for themselves.");
			gta5.setDeveloper("Rockstar North");
			gta5.setPublisher("Rockstar Games");
			gta5.setGenre(Genre.ActionAdventure);
			gta5.setPrice("");
			String[] grandtheftautov = {"ps3","xbox360"};
			gta5.setPlatforms(grandtheftautov);
			gta5.setRating(Game.MATURE);
			gta5.setInsertTime(Utility.convertDateToString(time));
			gta5.setTimestamp(time.getTime());
			gta5.setUpdateTime(Utility.convertDateToString(time));
			gta5.setGameReleaseStatus(Game.RELEASED);
			gta5.setReleaseDate("2013-09-17");	
			gta5.setReleaseTimeStamp(Utility.convertFromStringToDateFormat2("2013-09-17").getTime());
			media = new Media();
			media.setFileName("gta5_cover.jpg");
			media.setMediaType(Media.IMAGE);
			media.setImmediateOwner(Utility.shortenString(gta5.getTitle()));
			media.setOwner(Media.GAME);
			if(Play.isDev() || Play.isTest())
			{
				media.setUrl("https://s3.amazonaws.com/gloonuploadsdev/game/grandtheftautov/uploads/gta5_cover.jpg");
			}
			if(Play.isProd())
			{
				media.setUrl("https://s3.amazonaws.com/gloonuploads/game/grandtheftautov/uploads/gta5_cover.jpg");
			}				
			media.setInsertTime(Utility.convertDateToString(time));
			media.setTimestamp(time.getTime());
			mediaDAOInstance.save(media);
			gta5.setGameBoxShot(media.getId().toString());			
			gameDAOInstance.save(gta5);
			
			time = new Date();
			Game mgsRising = new Game();
			mgsRising.setTitle("Metal Gear Rising: Revengence");
			mgsRising.setDescription("METAL GEAR RISING: REVENGEANCE takes the renowned METAL GEAR franchise into exciting new territory by focusing on delivering an all-new action experience unlike anything that has come before. Combining world-class development teams at Kojima Productions and PlatinumGames, METAL GEAR RISING: REVENGEANCE brings two of the world's most respected teams together with a common goal of providing players with a fresh synergetic experience that combines the best elements of pure action and epic storytelling, all within the expansive MG universe");
			mgsRising.setDeveloper("Platinum Games");
			mgsRising.setPublisher("Kojima Productions");
			mgsRising.setGenre(Genre.Action);
			mgsRising.setPrice("");
			String[] mgsrPlatforms = {"ps3","xbox360","pc"};
			mgsRising.setPlatforms(mgsrPlatforms);
			mgsRising.setRating(Game.MATURE);
			mgsRising.setInsertTime(Utility.convertDateToString(time));
			mgsRising.setTimestamp(time.getTime());
			mgsRising.setUpdateTime(Utility.convertDateToString(time));
			mgsRising.setGameReleaseStatus(Game.RELEASED);
			mgsRising.setReleaseDate("2013-02-19");		
			mgsRising.setReleaseTimeStamp(Utility.convertFromStringToDateFormat2("2013-02-19").getTime());
			media = new Media();
			media.setFileName("mgsr_cover.jpg");
			media.setMediaType(Media.IMAGE);
			media.setImmediateOwner(Utility.shortenString(mgsRising.getTitle()));
			media.setOwner(Media.GAME);
			if(Play.isDev() || Play.isTest())
			{
				media.setUrl("https://s3.amazonaws.com/gloonuploadsdev/game/metalgearrisingrevengence/uploads/mgsr_cover.jpg");
			}
			if(Play.isProd())
			{
				media.setUrl("https://s3.amazonaws.com/gloonuploads/game/metalgearrisingrevengence/uploads/mgsr_cover.jpg");
			}				
			media.setInsertTime(Utility.convertDateToString(time));
			media.setTimestamp(time.getTime());
			mediaDAOInstance.save(media);
			mgsRising.setGameBoxShot(media.getId().toString());			
			gameDAOInstance.save(mgsRising);
			
			time = new Date();
			Game evilWithin = new Game();
			evilWithin.setTitle("The Evil Within");
			evilWithin.setDescription("The Evil Within, known in Japan as Psychobreak, is an upcoming survival horror video game, being developed by Japanese studio Tango Gameworks and published by Bethesda Softworks.When Detective Sebastian Castellanos and his partner rush to the scene of a gruesome mass murder, a mysterious, powerful force is lying in wait for them.");
			evilWithin.setDeveloper("Tango Gameworks");
			evilWithin.setPublisher("Bethesda Softworks");
			evilWithin.setGenre(Genre.Action);
			evilWithin.setPrice("");
			String[] evilWithinPlatforms = {"ps3","xbox360","pc","xboxOne","ps4"};
			evilWithin.setPlatforms(evilWithinPlatforms);
			evilWithin.setRating(Game.RATING_PENDING);
			evilWithin.setInsertTime(Utility.convertDateToString(time));
			evilWithin.setTimestamp(time.getTime());
			evilWithin.setUpdateTime(Utility.convertDateToString(time));
			evilWithin.setGameReleaseStatus(Game.NOT_RELEASED);
			evilWithin.setReleaseDate("2014-12-31");		
			evilWithin.setReleaseTimeStamp(Utility.convertFromStringToDateFormat2("2014-12-31").getTime());
			media = new Media();
			media.setFileName("tew_cover.jpg");
			media.setMediaType(Media.IMAGE);
			media.setImmediateOwner(Utility.shortenString(evilWithin.getTitle()));
			media.setOwner(Media.GAME);
			if(Play.isDev() || Play.isTest())
			{
				media.setUrl("https://s3.amazonaws.com/gloonuploadsdev/game/theevilwithin/uploads/tew_cover.jpg");
			}
			if(Play.isProd())
			{
				media.setUrl("https://s3.amazonaws.com/gloonuploads/game/theevilwithin/uploads/tew_cover.jpg");
			}				
			media.setInsertTime(Utility.convertDateToString(time));
			media.setTimestamp(time.getTime());
			mediaDAOInstance.save(media);
			evilWithin.setGameBoxShot(media.getId().toString());			
			gameDAOInstance.save(evilWithin);
			
			time = new Date();
			Game battlefield4 = new Game();
			battlefield4.setTitle("Battlefield 4");
			battlefield4.setDescription("Battlefield 4's single-player Campaign takes place in 2020, six years after the events of its predecessor. Tensions between Russia and the United States are running at a record high, due to a conflict that has been running for the past six years. On top of all this, China is also on the brink of war as Admiral Cheng, plans to overthrow China's current government; and, if successful, the Russians will have full support from the Chinese, bringing China into a war with the United States");
			battlefield4.setDeveloper("EA Digital Illusions CE");
			battlefield4.setPublisher("Electronic Arts");
			battlefield4.setGenre(Genre.Fps);
			battlefield4.setPrice("");
			String[] battlefield4Platforms = {"ps3","xbox360","pc","xboxOne","ps4"};
			battlefield4.setPlatforms(battlefield4Platforms);
			battlefield4.setRating(Game.MATURE);
			battlefield4.setInsertTime(Utility.convertDateToString(time));
			battlefield4.setTimestamp(time.getTime());
			battlefield4.setUpdateTime(Utility.convertDateToString(time));
			battlefield4.setGameReleaseStatus(Game.RELEASED);
			battlefield4.setReleaseDate("2013-10-29");		
			battlefield4.setReleaseTimeStamp(Utility.convertFromStringToDateFormat2("2013-10-29").getTime());
			media = new Media();
			media.setFileName("b4_cover.jpg");
			media.setMediaType(Media.IMAGE);
			media.setImmediateOwner(Utility.shortenString(battlefield4.getTitle()));
			media.setOwner(Media.GAME);
			if(Play.isDev() || Play.isTest())
			{
				media.setUrl("https://s3.amazonaws.com/gloonuploadsdev/game/battlefield4/uploads/b4_cover.jpg");
			}
			if(Play.isProd())
			{
				media.setUrl("https://s3.amazonaws.com/gloonuploads/game/battlefield4/uploads/b4_cover.jpg");
			}				
			media.setInsertTime(Utility.convertDateToString(time));
			media.setTimestamp(time.getTime());
			mediaDAOInstance.save(media);
			battlefield4.setGameBoxShot(media.getId().toString());			
			gameDAOInstance.save(battlefield4);
			
			time = new Date();
			Game destiny = new Game();
			destiny.setTitle("Destiny");
			destiny.setDescription("Destiny is an upcoming action role-playing first-person shooter video game in a \"mythic science fiction\" open world setting. It is developed by Bungie and published by Activision as part of a ten-year publishing deal.Destiny is set seven hundred years into the future in a post-apocalyptic setting following a prosperous period of exploration, peace and technological advancement known as the Golden Age.");
			destiny.setDeveloper("Bungie");
			destiny.setPublisher("Activision");
			destiny.setGenre(Genre.Fps);
			destiny.setPrice("");
			String[] destinyPlatforms = {"ps3","xbox360","xboxOne","ps4"};
			destiny.setPlatforms(destinyPlatforms);
			destiny.setRating(Game.RATING_PENDING);
			destiny.setInsertTime(Utility.convertDateToString(time));
			destiny.setTimestamp(time.getTime());
			destiny.setUpdateTime(Utility.convertDateToString(time));
			destiny.setGameReleaseStatus(Game.NOT_RELEASED);
			destiny.setReleaseDate("TBA");		
			destiny.setReleaseTimeStamp(Long.MAX_VALUE);			
			media = new Media();
			media.setFileName("destiny_cover.jpg");
			media.setMediaType(Media.IMAGE);
			media.setImmediateOwner(Utility.shortenString(destiny.getTitle()));
			media.setOwner(Media.GAME);
			if(Play.isDev() || Play.isTest())
			{
				media.setUrl("https://s3.amazonaws.com/gloonuploadsdev/game/destiny/uploads/destiny_cover.jpg");
			}
			if(Play.isProd())
			{
				media.setUrl("https://s3.amazonaws.com/gloonuploads/game/destiny/uploads/destiny_cover.jpg");
			}				
			media.setInsertTime(Utility.convertDateToString(time));
			media.setTimestamp(time.getTime());
			mediaDAOInstance.save(media);
			destiny.setGameBoxShot(media.getId().toString());			
			gameDAOInstance.save(destiny);
			
			
		}
		
		
}

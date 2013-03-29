package utility;

import java.net.UnknownHostException;

import com.mongodb.Mongo;
import com.typesafe.config.ConfigFactory;

/**
 * 
 * This initiates the Mongo DB connection. This is a singleton class which only comprises of 
 * one method (till now), which initiates a MongoDb connection.
 * @author Partho
 *
 */
public class GloonDatabase {

	public static synchronized Mongo instantiate() throws UnknownHostException {
		Mongo result = null;

		if (result == null) {
			String host = ConfigFactory.load().getString("mongo.host"); 
			int port = (int) Long.parseLong(ConfigFactory.load().getString("mongo.port"));
			result = new Mongo(host,port);			
		}
		return result;
	}
}

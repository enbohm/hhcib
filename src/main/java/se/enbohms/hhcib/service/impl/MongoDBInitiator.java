package se.enbohms.hhcib.service.impl;

import java.net.UnknownHostException;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

import com.mongodb.DB;
import com.mongodb.MongoClient;

@Singleton
public class MongoDBInitiator {

	private final static Logger LOG = Logger.getLogger(MongoDBInitiator.class
			.getName());

	private static final String DB_USER = "enbohm";
	private static final int MONGO_DB_PORT = 10041;
	private static final String MONGO_DB_URL = "paulo.mongohq.com";
	private static final String HHCIB_DB = "RdbIrCfawkRwew7GqwZYw";// MongoHQ(generated)
	private static final String DB_PASSWORD = "enbohm";

	private DB db = null;

	/**
	 * Initiates/setups the database connection
	 * 
	 * @throws RuntimeException
	 *             if anything fails when initiating the database
	 */
	@PostConstruct
	public void initDB() {
		MongoClient mongoClient;
		try {
			mongoClient = new MongoClient(MONGO_DB_URL, MONGO_DB_PORT);
			db = mongoClient.getDB(HHCIB_DB);
			db.authenticate(DB_USER, DB_PASSWORD.toCharArray());

		} catch (UnknownHostException e) {
			LOG.severe("Failed to init mongodb" + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * @return a {@link DB} instance (only one per VM)
	 */
	public DB getMongoDB() {
		return db;
	}
}
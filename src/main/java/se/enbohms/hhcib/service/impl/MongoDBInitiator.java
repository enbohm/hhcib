package se.enbohms.hhcib.service.impl;

import java.net.UnknownHostException;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

import com.mongodb.DB;
import com.mongodb.MongoClient;

@Singleton
public class MongoDBInitiator {

	private DB db = null;
	private static final int MONGO_DB_PORT = 10041;
	private static final String MONGO_DB_URL = "paulo.mongohq.com";
	private static final String HHCIB_DB = "RdbIrCfawkRwew7GqwZYw";// name from
																	// MongoHQ
																	// DaPAAS

	@PostConstruct
	public void initDB() {
		MongoClient mongoClient;
		try {
			mongoClient = new MongoClient(MONGO_DB_URL, MONGO_DB_PORT);
			db = mongoClient.getDB(HHCIB_DB);
			db.authenticate("enbohm", "enbohm".toCharArray());

		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public DB getMongoDB() {
		return db;
	}
}
package se.enbohms.hhcib.service.impl;

import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.inject.Inject;

import se.enbohms.hhcib.common.PerformanceMonitored;
import se.enbohms.hhcib.service.api.LoginService;
import se.enbohms.hhcib.service.api.UserAuthenticationException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

@Singleton
@DependsOn("MongoDBInitiator")
public class MongoDBLoginService implements LoginService {

	private static final String USER_COLLECTION_NAME = "user";

	@Inject
	private MongoDBInitiator dbInitiator;

	@PerformanceMonitored
	public void login(String userName, String password)
			throws UserAuthenticationException {
		DBCollection collection = dbInitiator.getMongoDB().getCollection(
				USER_COLLECTION_NAME);
		BasicDBObject query = new BasicDBObject();
		query.put("username", userName);
		DBObject dbObj = collection.findOne(query);

		if (dbObj != null) {
			
			if (!dbObj.get("password").toString().equals(password)) {
				
			}

		}
	}

	public void logout() {

	}

}

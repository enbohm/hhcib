package se.enbohms.hhcib.service.impl;

import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.inject.Inject;

import se.enbohms.hhcib.common.PerformanceMonitored;
import se.enbohms.hhcib.entity.Email;
import se.enbohms.hhcib.entity.User;
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
	public User login(String userName, String password)
			throws UserAuthenticationException {
		DBCollection collection = dbInitiator.getMongoDB().getCollection(
				USER_COLLECTION_NAME);
		BasicDBObject query = new BasicDBObject();
		query.put("username", userName);
		DBObject dbObj = collection.findOne(query);

		if (dbObj != null) {
			// TODO: Validato pwd
			return User.creteUser(userName,
					Email.of((String) dbObj.get("email")));

		}
		throw new UserAuthenticationException();
	}

	public void logout() {

	}

}

package se.enbohms.hhcib.service.impl;

import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.inject.Inject;

import org.bson.types.ObjectId;

import se.enbohms.hhcib.common.PerformanceMonitored;
import se.enbohms.hhcib.entity.Email;
import se.enbohms.hhcib.entity.User;
import se.enbohms.hhcib.service.api.UserAuthenticationException;
import se.enbohms.hhcib.service.api.UserNotFoundException;
import se.enbohms.hhcib.service.api.UserService;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * Handles the various DB operations for a user
 */
@Singleton
@DependsOn("MongoDBInitiator")
public class MongoDBUserService implements UserService {

	private static final String USER_COLLECTION_NAME = "user";

	private MongoDBInitiator dbInitiator;

	@Inject
	public void setDBInitiator(MongoDBInitiator initiator) {
		this.dbInitiator = initiator;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation uses MongoDB to authenticate (login) the user
	 */
	@PerformanceMonitored
	public User login(String userName, String password)
			throws UserAuthenticationException {
		DBCollection collection = dbInitiator.getMongoDB().getCollection(
				USER_COLLECTION_NAME);
		BasicDBObject query = new BasicDBObject();
		query.put(User.USERNAME, userName);
		DBObject dbObj = collection.findOne(query);

		if (dbObj != null
				&& (password.equals(passwordFromDB(dbObj)))) {
			return User.creteUser(dbObj.get(User.ID).toString(), userName,
					Email.of((String) dbObj.get("email")));
		}

		throw new UserAuthenticationException();
	}

	private String passwordFromDB(DBObject dbObj) {
		return dbObj.get(User.PASSWORD).toString();
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation uses MongoDB store the new user
	 */
	@PerformanceMonitored
	public User createUser(String userName, Email email, String password) {
		DBCollection collection = dbInitiator.getMongoDB().getCollection(
				USER_COLLECTION_NAME);

		BasicDBObject doc = new BasicDBObject(User.USERNAME, userName).append(
				User.EMAIL, email.getEmail()).append(User.PASSWORD, password);

		collection.insert(doc);

		return User.creteUser(doc.get(User.ID).toString(), userName, email);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation deletes a user from the MongoDB
	 */
	public void delete(User user) throws UserNotFoundException {
		DBCollection collection = dbInitiator.getMongoDB().getCollection(
				USER_COLLECTION_NAME);

		BasicDBObject dbObj = new BasicDBObject();
		dbObj.put(User.ID, new ObjectId(user.getId()));
		collection.remove(dbObj);
	}
}
package se.enbohms.hhcib.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.bson.types.ObjectId;

import se.enbohms.hhcib.common.PerformanceMonitored;
import se.enbohms.hhcib.entity.Email;
import se.enbohms.hhcib.entity.User;
import se.enbohms.hhcib.service.api.UserAuthenticationException;
import se.enbohms.hhcib.service.api.UserCreatedEvent;
import se.enbohms.hhcib.service.api.UserNotFoundException;
import se.enbohms.hhcib.service.api.UserService;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * Handles the various DB operations for a user
 */
@Singleton
@DependsOn("MongoDBInitiator")
public class MongoUserService implements UserService {

	private static final String USER_COLLECTION_NAME = "user";

	private MongoDBInitiator dbInitiator;

	@Inject
	private Event<UserCreatedEvent> events;

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

		if (dbObj != null && (password.equals(passwordFromDB(dbObj)))) {
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
		events.fire(UserCreatedEvent.of(userName, email));
		return User.creteUser(doc.get(User.ID).toString(), userName, email);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation deletes a user from the MongoDB
	 */
	@PerformanceMonitored
	public void delete(User user) throws UserNotFoundException {
		DBCollection collection = dbInitiator.getMongoDB().getCollection(
				USER_COLLECTION_NAME);

		BasicDBObject dbObj = new BasicDBObject();
		dbObj.put(User.ID, new ObjectId(user.getId()));
		collection.remove(dbObj);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation fetches all user names from MongoDB
	 */
	@PerformanceMonitored
	public List<String> getUserNames() {
		DBCollection collection = dbInitiator.getMongoDB().getCollection(
				USER_COLLECTION_NAME);
		DBCursor cursor = collection.find();
		try {
			return fetchUserNames(cursor);
		} finally {
			cursor.close();
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation fetches all user names from MongoDB
	 */
	@PerformanceMonitored
	public List<Email> getEmails() {
		DBCollection collection = dbInitiator.getMongoDB().getCollection(
				USER_COLLECTION_NAME);
		DBCursor cursor = collection.find();
		try {
			return fetchEmails(cursor);
		} finally {
			cursor.close();
		}
	}

	private List<String> fetchUserNames(DBCursor cursor) {
		List<String> result = new ArrayList<>();
		while (cursor.hasNext()) {
			DBObject dbObj = cursor.next();
			result.add(dbObj.get(User.USERNAME).toString());
		}
		return result;
	}

	private List<Email> fetchEmails(DBCursor cursor) {
		List<Email> result = new ArrayList<>();
		while (cursor.hasNext()) {
			DBObject dbObj = cursor.next();
			result.add(Email.of(dbObj.get(User.EMAIL).toString()));
		}
		return result;
	}
}
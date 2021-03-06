package se.enbohms.hhcib.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.DependsOn;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.bson.types.ObjectId;

import se.enbohms.hhcib.common.PerformanceMonitored;
import se.enbohms.hhcib.entity.Email;
import se.enbohms.hhcib.entity.Password;
import se.enbohms.hhcib.entity.User;
import se.enbohms.hhcib.service.api.UserAuthenticationException;
import se.enbohms.hhcib.service.api.UserCreatedEvent;
import se.enbohms.hhcib.service.api.UserNotFoundException;
import se.enbohms.hhcib.service.api.UserService;
import se.enbohms.hhcib.service.api.UserUpdatedEvent;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * Handles the various database operations for a user using MongoDB as database
 */
@Stateless
@DependsOn("MongoDBInitiator")
public class MongoUserService implements UserService {

	private static final String USER_COLLECTION_NAME = "user";

	private MongoDBInitiator dbInitiator;

	@Inject
	private Event<UserCreatedEvent> userCreatedEvent;

	@Inject
	private Event<UserUpdatedEvent> userUpdatedEvent;

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

		DBObject dbObj = collection.findOne(new BasicDBObject(User.USERNAME,
				userName));

		if (credentialsValid(password, dbObj)) {
			return User.creteUser(dbObj.get(User.ID).toString(), userName,
					Email.of((String) dbObj.get("email")));
		}
		throw new UserAuthenticationException();
	}

	private boolean credentialsValid(String password, DBObject dbObj) {
		return dbObj != null && (password.equals(passwordFromDB(dbObj)));
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
		userCreatedEvent.fire(UserCreatedEvent.of(userName, email));
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
		collection
				.remove(new BasicDBObject(User.ID, new ObjectId(user.getId())));
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

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation fetches all user names from MongoDB
	 */
	@PerformanceMonitored
	public void updateUserPassword(Password newPassword, User user) {
		DBCollection collection = dbInitiator.getMongoDB().getCollection(
				USER_COLLECTION_NAME);
		BasicDBObject newDocument = new BasicDBObject();

		newDocument.append(
				"$set",
				new BasicDBObject().append(User.PASSWORD,
						newPassword.getPassword()));

		collection.update(
				new BasicDBObject(User.ID, new ObjectId(user.getId())),
				newDocument);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation fetches all user names from MongoDB
	 */
	@PerformanceMonitored
	public void updateUserEmail(Email newEmail, User user) {
		DBCollection collection = dbInitiator.getMongoDB().getCollection(
				USER_COLLECTION_NAME);
		BasicDBObject newDocument = new BasicDBObject();

		newDocument.append("$set",
				new BasicDBObject().append(User.EMAIL, newEmail.getEmail()));

		collection.update(
				new BasicDBObject(User.ID, new ObjectId(user.getId())),
				newDocument);
		this.userUpdatedEvent.fire(UserUpdatedEvent.of(user, newEmail));
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@PerformanceMonitored
	public void updateNewPasswordFor(Email email, Password newPassword) {
		DBCollection collection = dbInitiator.getMongoDB().getCollection(
				USER_COLLECTION_NAME);
		BasicDBObject newDocument = new BasicDBObject().append("$set",
				new BasicDBObject(User.PASSWORD, newPassword.getPassword()));

		collection.update(new BasicDBObject(User.EMAIL, email.getEmail()),
				newDocument);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	public String getUsernameFrom(Email email) throws UserNotFoundException {
		DBCollection collection = dbInitiator.getMongoDB().getCollection(
				USER_COLLECTION_NAME);

		BasicDBObject dbObj = (BasicDBObject) collection
				.findOne(new BasicDBObject(User.EMAIL, email.getEmail()));

		if (match(dbObj)) {
			return dbObj.getString(User.USERNAME);
		}

		throw new UserNotFoundException();
	}

	private boolean match(BasicDBObject dbObj) {
		return dbObj != null;
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

	private String passwordFromDB(DBObject dbObj) {
		return dbObj.get(User.PASSWORD).toString();
	}
}
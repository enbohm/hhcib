package se.enbohms.hhcib.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.DependsOn;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

import org.bson.BasicBSONObject;
import org.bson.types.ObjectId;

import se.enbohms.hhcib.common.PerformanceMonitored;
import se.enbohms.hhcib.entity.Category;
import se.enbohms.hhcib.entity.Subject;
import se.enbohms.hhcib.entity.User;
import se.enbohms.hhcib.entity.Vote;
import se.enbohms.hhcib.service.api.CrudService;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * The MongoDB implementation of the HHCIB {@link CrudService}
 * 
 */
@Stateless
@DependsOn("MongoDBInitiator")
public class MongoCrudService implements CrudService {

	private static final String SUBJECT_COLLECTION_NAME = "subject";

	private MongoDBInitiator mongoDBInitiator;

	@Inject
	public void setDBInitiator(MongoDBInitiator initiator) {
		this.mongoDBInitiator = initiator;

	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation uses MongoDB as database
	 */
	@PerformanceMonitored
	public Subject createSubject(String heading, String description,
			Category category, User user) {
		DBCollection coll = mongoDBInitiator.getMongoDB().getCollection(
				SUBJECT_COLLECTION_NAME);
		BasicDBObject doc = new BasicDBObject(Subject.CATEGORY,
				category.toString()).append(Subject.HEADING, heading)
				.append(Subject.DESCRIPTION, description)
				.append(Subject.CREATED_BY, user.getUserName());

		coll.insert(doc);

		return new Subject.Builder(doc.get(Subject.ID).toString())
				.heading(heading).description(description).category(category)
				.createdBy(user.getUserName()).build();
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation uses MongoDB as database
	 */
	@PerformanceMonitored
	public List<Subject> getSubjectsFor(Category category) {
		DBCollection coll = mongoDBInitiator.getMongoDB().getCollection(
				SUBJECT_COLLECTION_NAME);

		BasicDBObject searchQuery = new BasicDBObject(Subject.CATEGORY,
				category.toString());

		DBObject descendingOrder = new BasicDBObject("rating", -1d);
		DBCursor cursor = coll.find(searchQuery).sort(descendingOrder);
		try {
			return fetchResults(cursor);
		} finally {
			cursor.close();
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation uses MongoDB as database
	 */
	@PerformanceMonitored
	public void update(Subject subject) {
		DBCollection collection = mongoDBInitiator.getMongoDB().getCollection(
				SUBJECT_COLLECTION_NAME);
		BasicDBObject newDocument = new BasicDBObject();

		List<BasicDBObject> voters = new ArrayList<>();

		for (Map.Entry<String, Double> entry : subject.getVoters().entrySet()) {
			voters.add(new BasicDBObject().append(Vote.USER_NAME,
					entry.getKey()).append(Vote.SCIRE, entry.getValue()));
		}

		newDocument.append(
				"$set",
				new BasicDBObject().append(Subject.RATING, voters).append(
						Subject.DESCRIPTION, subject.getDescription()));

		ObjectId objectId = new ObjectId(subject.getId());
		BasicDBObject searchQuery = new BasicDBObject(Subject.ID, objectId);

		collection.update(searchQuery, newDocument);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation uses MongoDB as database
	 */
	@PerformanceMonitored
	public Subject find(String objectID) {
		DBCollection collection = mongoDBInitiator.getMongoDB().getCollection(
				SUBJECT_COLLECTION_NAME);
		BasicDBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(objectID));
		DBObject dbObj = collection.findOne(query);

		if (dbObj != null) {
			return buildSubject(dbObj);
		}
		throw new EntityNotFoundException("Could not find object with id "
				+ objectID + " in DB");
	}

	private Subject buildSubject(DBObject dbObj) {
		Subject.Builder builder = new Subject.Builder(dbObj.get(Subject.ID)
				.toString());
		builder.heading(dbObj.get(Subject.HEADING).toString())
				.description(dbObj.get(Subject.DESCRIPTION).toString())
				.category(getCategoryFrom(dbObj))
				.createdBy(dbObj.get(Subject.CREATED_BY).toString());

		Set<Vote> voters = getVotersFrom(dbObj);
		for (Vote vote : voters) {
			builder.voter(vote);
		}
		return builder.build();
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation uses MongoDB as database
	 */
	@PerformanceMonitored
	public void delete(String objectID) {
		DBCollection collection = mongoDBInitiator.getMongoDB().getCollection(
				SUBJECT_COLLECTION_NAME);
		BasicDBObject dbObj = new BasicDBObject();
		dbObj.put("_id", new ObjectId(objectID));
		collection.remove(dbObj);
	}

	private List<Subject> fetchResults(DBCursor cursor) {
		List<Subject> result = new ArrayList<>();
		while (cursor.hasNext()) {
			DBObject dbObj = cursor.next();
			result.add(buildSubject(dbObj));
		}
		return result;
	}

	private Category getCategoryFrom(DBObject dbObj) {
		return Category.valueOf(dbObj.get(Subject.CATEGORY).toString());
	}

	private Set<Vote> getVotersFrom(DBObject dbObj) {
		if (dbObj.get(Subject.RATING) == null)
			return Collections.emptySet();

		BasicDBList ratings = (BasicDBList) dbObj.get(Subject.RATING);

		Set<Vote> result = new HashSet<>();
		if (ratings != null) {
			for (int i = 0; i < ratings.size(); i++) {
				String userName = ((BasicBSONObject) ratings.get(i))
						.getString(Vote.USER_NAME);
				Double score = ((BasicBSONObject) ratings.get(i))
						.getDouble(Vote.SCIRE);
				result.add(Vote.of(userName, score));
			}
		}
		return result;
	}

}
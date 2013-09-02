package se.enbohms.hhcib.service.impl;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.persistence.EntityNotFoundException;

import org.bson.types.ObjectId;

import se.enbohms.hhcib.common.PerformanceMonitored;
import se.enbohms.hhcib.entity.Category;
import se.enbohms.hhcib.entity.Subject;
import se.enbohms.hhcib.service.api.CrudService;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

/**
 * The MongoDB implementation of the HHCIB {@link CrudService}
 * 
 */
@Singleton
public class MongoCrudService implements CrudService {

	private static final int MONGO_DB_PORT = 10041;
	private static final String MONGO_DB_URL = "paulo.mongohq.com";
	private static final String HHCIB_DB = "RdbIrCfawkRwew7GqwZYw";// name from
																	// MongoHQ
																	// DaPAAS
	private static final String SUBJECT_COLLECTION_NAME = "subject";
	private DB db = null;

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

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation uses MongoDB as database
	 */
	@PerformanceMonitored
	public Subject insertSubject(String heading, String description,
			Category category) {
		DBCollection coll = db.getCollection(SUBJECT_COLLECTION_NAME);
		BasicDBObject doc = new BasicDBObject(Subject.CATEGORY,
				category.toString()).append(Subject.HEADING, heading)
				.append(Subject.DESCRIPTION, description)
				.append(Subject.CREATED_BY, "enbohm");

		coll.insert(doc);

		return Subject.of(doc.get(Subject.ID).toString(), heading, description,
				Double.valueOf(0d), category);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation uses MongoDB as database
	 */
	@PerformanceMonitored
	public List<Subject> getSubjectsFor(Category category) {
		DBCollection coll = db.getCollection(SUBJECT_COLLECTION_NAME);

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
		DBCollection collection = db.getCollection(SUBJECT_COLLECTION_NAME);
		BasicDBObject newDocument = new BasicDBObject();

		newDocument.append("$set",
				new BasicDBObject().append(Subject.RATING, subject.getRating())
						.append(Subject.DESCRIPTION, subject.getDescription()));

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
		DBCollection collection = db.getCollection(SUBJECT_COLLECTION_NAME);
		BasicDBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(objectID));
		DBObject dbObj = collection.findOne(query);

		if (dbObj != null) {
			return Subject.of(dbObj.get(Subject.ID).toString(),
					dbObj.get(Subject.HEADING).toString(),
					dbObj.get(Subject.DESCRIPTION).toString(),
					getRatingFrom(dbObj), getCategoryFrom(dbObj));
		}
		throw new EntityNotFoundException("Could not find object with id "
				+ objectID + " in DB");
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation uses MongoDB as database
	 */
	@PerformanceMonitored
	public void delete(String objectID) {
		DBCollection collection = db.getCollection(SUBJECT_COLLECTION_NAME);
		BasicDBObject dbObj = new BasicDBObject();
		dbObj.put("_id", new ObjectId(objectID));
		collection.remove(dbObj);
	}

	private List<Subject> fetchResults(DBCursor cursor) {
		List<Subject> result = new ArrayList<>();
		while (cursor.hasNext()) {
			DBObject dbObj = cursor.next();
			result.add(Subject.of(dbObj.get(Subject.ID).toString(),
					dbObj.get(Subject.HEADING).toString(),
					dbObj.get(Subject.DESCRIPTION).toString(),
					getRatingFrom(dbObj), getCategoryFrom(dbObj)));
		}
		return result;
	}

	private Category getCategoryFrom(DBObject dbObj) {
		return Category.valueOf(dbObj.get(Subject.CATEGORY).toString());
	}

	// TODO: remove hard codes 2.0 string, just for demo
	private Double getRatingFrom(DBObject dbObj) {
		Double rating = dbObj.get(Subject.RATING) != null ? Double
				.valueOf((Double) dbObj.get(Subject.RATING)) : 2.0d;
		return rating;
	}

}
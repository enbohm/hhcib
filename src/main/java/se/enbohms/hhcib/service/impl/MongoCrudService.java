package se.enbohms.hhcib.service.impl;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.persistence.EntityNotFoundException;

import org.bson.types.ObjectId;

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

	private static final String HHCIB_DB = "hhcib";
	private DB db = null;

	@PostConstruct
	public void initDB() {
		MongoClient mongoClient;
		try {
			mongoClient = new MongoClient();
			db = mongoClient.getDB(HHCIB_DB);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * {@inheritDoc} This implementation uses MongoDB as database
	 */
	public void insertSubject(String heading, String description,
			Category category) {
		DBCollection coll = db.getCollection(category.name());

		BasicDBObject doc = new BasicDBObject(Subject.HEADING, heading).append(
				Subject.DESCRIPTION, description).append("creator", "enbohm");

		coll.insert(doc);
	}

	/**
	 * {@inheritDoc} This implementation uses MongoDB as database
	 */
	public List<Subject> getSubjectsFor(Category category) {
		DBCollection coll = db.getCollection(category.name());

		DBCursor cursor = coll.find();
		try {
			return fetchResults(cursor, category);
		} finally {
			cursor.close();
		}
	}

	private List<Subject> fetchResults(DBCursor cursor, Category category) {
		List<Subject> result = new ArrayList<>();
		while (cursor.hasNext()) {
			DBObject dbObj = cursor.next();
			result.add(Subject.of(dbObj.get(Subject.ID).toString(),
					dbObj.get(Subject.HEADING).toString(),
					dbObj.get(Subject.DESCRIPTION).toString(),
					getRating(dbObj), category));
		}
		return result;
	}

	//TODO: remove hard codes 2.0 string, just for demo 
	private Double getRating(DBObject dbObj) {
		Double rating = dbObj.get(Subject.RATING) != null ? Double
				.valueOf((Double) dbObj.get(Subject.RATING)) : 2.0d;
		return rating;
	}

	public void update(Subject subject) {
		DBCollection collection = db
				.getCollection(subject.getCategory().name());
		BasicDBObject newDocument = new BasicDBObject();

		newDocument.append("$set",
				new BasicDBObject().append(Subject.RATING, subject.getRating())
						.append(Subject.DESCRIPTION, subject.getDescription()));

		ObjectId objectId = new ObjectId(subject.getId());
		BasicDBObject searchQuery = new BasicDBObject(Subject.ID, objectId);

		collection.update(searchQuery, newDocument);
	}

	public Subject find(String objectID, Category category) {
		DBCollection collection = db.getCollection(category.name());
		BasicDBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(objectID));
		DBObject dbObj = collection.findOne(query);

		if (dbObj != null) {
			return Subject.of(dbObj.get(Subject.ID).toString(),
					dbObj.get(Subject.HEADING).toString(),
					dbObj.get(Subject.DESCRIPTION).toString(),
					getRating(dbObj), category);
		}
		throw new EntityNotFoundException("Could not find object with id "
				+ objectID + " and category " + category + " in DB");
	}
}
package se.enbohms.hhcib.service.impl;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

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
	public void createSubject(String heading, String description,
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
			return fetchResults(cursor);
		} finally {
			cursor.close();
		}
	}

	private List<Subject> fetchResults(DBCursor cursor) {
		List<Subject> result = new ArrayList<>();
		while (cursor.hasNext()) {
			DBObject dbObj = cursor.next();
			result.add(Subject.of(dbObj.get(Subject.ID).toString(),
					dbObj.get(Subject.HEADING).toString(),
					dbObj.get(Subject.DESCRIPTION).toString()));
		}
		return result;
	}
}
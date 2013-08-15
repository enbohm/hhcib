package se.enbohms.hhcib.service.impl;

import static org.fest.assertions.Assertions.assertThat;

import java.net.UnknownHostException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import se.enbohms.hhcib.entity.Category;
import se.enbohms.hhcib.entity.Subject;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoCrudServiceTest {

	@Before
	public void setUp() throws UnknownHostException {
		MongoClient mongoClient = new MongoClient();
		DB db = mongoClient.getDB("hhcib");
		db.getCollection(Category.FOOD.name()).drop();
	}

	@Test
	public void should_insert_and_return_object_in_db() throws Exception {
		// given
		MongoCrudService service = new MongoCrudService();
		service.initDB();
		service.createSubject("a heading goes here",
				"a description goes here...", Category.FOOD);

		// when
		List<Subject> result = service.getSubjectsFor(Category.FOOD);

		// then
		assertThat(result).isNotEmpty();
	}
}

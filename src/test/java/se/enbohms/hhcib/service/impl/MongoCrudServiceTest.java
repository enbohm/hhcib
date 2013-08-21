package se.enbohms.hhcib.service.impl;

import static org.fest.assertions.Assertions.assertThat;

import java.net.UnknownHostException;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Test;

import se.enbohms.hhcib.entity.Category;
import se.enbohms.hhcib.entity.Subject;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoCrudServiceTest {

	private static final String UPDATED_DESCRIPTION = "Updated Description";

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
		service.insertSubject("a heading goes here",
				"a description goes here...", Category.FOOD);

		// when
		List<Subject> result = service.getSubjectsFor(Category.FOOD);

		// then
		assertThat(result).isNotEmpty();
		assertThat(result.get(0).getCategory()).isEqualTo(Category.FOOD);
	}

	@Test
	public void should_update_object_in_db() throws Exception {
		// given
		MongoCrudService service = new MongoCrudService();
		service.initDB();
		service.insertSubject("a heading goes here",
				"a description goes here...", Category.FOOD);

		Subject existingSubject = service.getSubjectsFor(Category.FOOD).get(0);

		// when
		existingSubject.setRating(5d);
		existingSubject.setDescription(UPDATED_DESCRIPTION);

		service.update(existingSubject, Category.FOOD);

		// then
		Subject result = service.find(existingSubject.getId(), Category.FOOD);
		assertThat(result).isNotNull();
		assertThat(result.getRating().doubleValue()).isEqualTo(5d);
		assertThat(result.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
		assertThat(result.getCategory()).isEqualTo(Category.FOOD);
	}

	@Test(expected = EntityNotFoundException.class)
	public void should_throw_exception_when_object_not_found_in_db()
			throws Exception {
		// given
		MongoCrudService service = new MongoCrudService();
		service.initDB();

		// when
		service.find("000000000000000000000099", Category.ECONOMY);

		// then
		// exception should be thrown

	}
}
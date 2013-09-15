package se.enbohms.hhcib.service.impl;

import static org.fest.assertions.Assertions.assertThat;

import java.net.UnknownHostException;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Test;

import se.enbohms.hhcib.entity.Category;
import se.enbohms.hhcib.entity.IntegrationTest;
import se.enbohms.hhcib.entity.Subject;
import se.enbohms.hhcib.entity.User;
import se.enbohms.hhcib.entity.Vote;

/**
 * Test client for the {@link MongoCrudService}
 */
@org.junit.experimental.categories.Category(IntegrationTest.class)
public class MongoCrudServiceTest {

	private static final String DESCRIPTION = "a description goes here...";
	private static final String HEADING = "a heading goes here";
	private static final String UPDATED_DESCRIPTION = "Updated Description";
	private static final User USER = User.creteUser("id", "chuck", null);
	private static final String USER_NAME_1 = "userName1";
	private static final String USER_NAME_2 = "userName2";
	private MongoCrudService crudService;

	@Before
	public void setUp() throws UnknownHostException {
		crudService = new MongoCrudService();
		MongoDBInitiator dbInitiator = new MongoDBInitiator();
		dbInitiator.initDB();
		crudService.setDBInitiator(dbInitiator);
	}

	@Test
	public void should_insert_and_return_all_subject_for_a_category_in_db()
			throws Exception {
		Subject subject = null;
		try {
			// given
			subject = crudService.createSubject(HEADING,
					DESCRIPTION, Category.FOOD, USER);

			// when
			List<Subject> result = crudService.getSubjectsFor(Category.FOOD);

			// then
			assertThat(result).isNotEmpty();
			assertThat(subject.getId()).isNotNull();
			assertThat(result.get(0).getCategory()).isEqualTo(Category.FOOD);
			assertThat(subject.getCreatedBy()).isNotEmpty();
		} finally {
			if (subject != null) {
				crudService.delete(subject.getId());
			}
		}
	}

	@Test
	public void should_insert_and_find_subject_in_db() throws Exception {
		Subject subject = null;
		try {
			// given
			subject = crudService.createSubject(HEADING,
					DESCRIPTION, Category.FOOD, USER);

			// when
			Subject result = crudService.find(subject.getId());

			// then
			assertThat(result).isNotNull();
			assertThat(subject.getId()).isNotNull();
			assertThat(subject.getCategory()).isEqualTo(Category.FOOD);
			assertThat(subject.getCreatedBy()).isEqualTo(USER.getUserName());
			assertThat(subject.getDescription()).isEqualTo(DESCRIPTION);
			assertThat(subject.getHeading()).isEqualTo(HEADING);
			assertThat(subject.getRating()).isEqualTo(0d);
		} finally {
			if (subject != null) {
				crudService.delete(subject.getId());
			}
		}
	}

	@Test
	public void should_update_object_in_db() throws Exception {
		Subject existingSubject = null;
		try {
			// given
			existingSubject = crudService.createSubject(HEADING,
					DESCRIPTION, Category.FOOD, USER);

			// when
			existingSubject.addVote(Vote.of(USER_NAME_1, 2.0d));
			existingSubject.addVote(Vote.of(USER_NAME_2, 4.0d));
			existingSubject.addVote(Vote.of(USER_NAME_1, 5.0d));
			//result should be 4.5 
			
			
			existingSubject.setDescription(UPDATED_DESCRIPTION);

			crudService.update(existingSubject);

			// then
			Subject result = crudService.find(existingSubject.getId());
			assertThat(result).isNotNull();
			assertThat(result.getRating()).isEqualTo(4.5d);
			assertThat(result.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
			assertThat(result.getCategory()).isEqualTo(Category.FOOD);
			assertThat(result.getCreatedBy()).isEqualTo(USER.getUserName());
		} finally {
			if (existingSubject != null) {
				crudService.delete(existingSubject.getId());
			}
		}
	}

	@Test(expected = EntityNotFoundException.class)
	public void should_throw_exception_when_object_not_found_in_db()
			throws Exception {
		// when
		crudService.find("000000000000000000000099");

		// then
		// exception should be thrown
	}

	@Test
	public void should_delete_object_in_db() throws Exception {
		// given
		Subject subject = crudService.createSubject(HEADING,
				DESCRIPTION, Category.FOOD, USER);

		int sizeBefore = crudService.getSubjectsFor(Category.FOOD).size();

		// when
		crudService.delete(subject.getId());
		int sizeAfter = crudService.getSubjectsFor(Category.FOOD).size();

		// then
		assertThat(sizeAfter).isLessThan(sizeBefore);
	}
}
package se.enbohms.hhcib.service.impl;

import static org.fest.assertions.Assertions.assertThat;

import java.net.UnknownHostException;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Test;

import se.enbohms.hhcib.entity.Category;
import se.enbohms.hhcib.entity.Subject;

public class MongoCrudServiceTest {

	private static final String UPDATED_DESCRIPTION = "Updated Description";
	private MongoCrudService crudService;

	@Before
	public void setUp() throws UnknownHostException {
		crudService = new MongoCrudService();
		crudService.initDB();
	}

	@Test
	public void should_insert_and_return_object_in_db() throws Exception {
		// given
		Subject subject = crudService.insertSubject("a heading goes here",
				"a description goes here...", Category.FOOD);

		// when
		List<Subject> result = crudService.getSubjectsFor(Category.FOOD);

		// then
		assertThat(result).isNotEmpty();
		assertThat(subject.getId()).isNotNull();
		// assertThat(result.get(0).getCategory()).isEqualTo(Category.FOOD);
	}

	@Test
	public void should_update_object_in_db() throws Exception {
		// given
		Subject existingSubject = crudService.insertSubject(
				"a heading goes here", "a description goes here...",
				Category.FOOD);

		// when
		existingSubject.setRating(5d);
		existingSubject.setDescription(UPDATED_DESCRIPTION);

		crudService.update(existingSubject);

		// then
		Subject result = crudService.find(existingSubject.getId());
		assertThat(result).isNotNull();
		assertThat(result.getRating().doubleValue()).isEqualTo(5d);
		assertThat(result.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
		assertThat(result.getCategory()).isEqualTo(Category.FOOD);
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
		Subject subject = crudService.insertSubject("a heading goes here",
				"a description goes here...", Category.FOOD);

		int sizeBefore = crudService.getSubjectsFor(Category.FOOD).size();

		// when
		crudService.delete(subject.getId());
		int sizeAfter = crudService.getSubjectsFor(Category.FOOD).size();

		// then
		assertThat(sizeAfter).isLessThan(sizeBefore);
	}
}
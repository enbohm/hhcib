package se.enbohms.hhcib.facade;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import se.enbohms.hhcib.entity.Category;
import se.enbohms.hhcib.entity.Subject;

public class SubjectFacadeTest {

	@Test
	@Ignore
	public void should_return_list_with_subjects_for_economy() throws Exception {
		// given
		CategoryFacade facade = new CategoryFacade();

		// when
		List<Subject> result = facade.getSubjectsFor(Category.ECONOMY);

		// then
		assertThat(result).isNotEmpty();
	}
}

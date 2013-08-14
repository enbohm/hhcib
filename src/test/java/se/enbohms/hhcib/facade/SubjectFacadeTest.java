package se.enbohms.hhcib.facade;

import java.util.List;

import org.junit.Test;

import se.enbohms.hhcib.entity.Category;
import se.enbohms.hhcib.entity.Subject;

public class SubjectFacadeTest {

	@Test
	public void should_return_list_with_subjects_for_economy() throws Exception {
		//given
		SubjectFacade facade = new SubjectFacade();
		
		//when
		List<Subject> result = facade.getSubjectFor(Category.ECONOMY);
		
		//then
		
	}
}

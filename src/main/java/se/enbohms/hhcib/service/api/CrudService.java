package se.enbohms.hhcib.service.api;

import java.util.List;

import javax.ejb.Local;

import se.enbohms.hhcib.entity.Category;
import se.enbohms.hhcib.entity.Subject;

@Local
public interface CrudService {

	/**
	 * 
	 * @param category
	 * @return a list of subject for the supplied category from the database or
	 *         empty list of no subjects are found
	 */
	List<Subject> getSubjectsFor(Category category);

	void createSubject(String heading, String description, Category category);
}

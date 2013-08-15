package se.enbohms.hhcib.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import se.enbohms.hhcib.entity.Category;
import se.enbohms.hhcib.entity.Subject;
import se.enbohms.hhcib.service.api.CrudService;

/**
 * Represent the facade client can use when interaction with {@link Subject}
 * 
 */
@Stateless
@Named
public class SubjectFacade {

	@Inject
	private CrudService service;

	/**
	 * 
	 * @param category
	 * @return a list of subject for the corresponding {@link Category} or empty
	 *         list if no subjects exist for the supplied category
	 */
	public List<Subject> getSubjectsFor(Category category) {
		return service.getSubjectsFor(category);
	}
}

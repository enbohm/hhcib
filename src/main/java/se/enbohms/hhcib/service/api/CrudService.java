package se.enbohms.hhcib.service.api;

import java.util.List;

import javax.ejb.Local;
import javax.persistence.EntityNotFoundException;

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

	/**
	 * Inserts (creates) a new object in the data base
	 * 
	 * @param heading
	 * @param description
	 * @param category
	 */
	void insertSubject(String heading, String description, Category category);

	/**
	 * Updates a current subject in the DB
	 * 
	 * @param existingSubject
	 */
	void update(Subject existingSubject);

	/**
	 * Find a subject in the database from the supplied objectId or throws
	 * {@link EntityNotFoundException} of no match is found
	 * 
	 * @param objectID
	 * @return a new subject
	 * @throws EntityNotFoundException
	 */
	Subject find(String objectID) throws EntityNotFoundException;
}
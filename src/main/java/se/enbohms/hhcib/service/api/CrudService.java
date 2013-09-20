package se.enbohms.hhcib.service.api;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import se.enbohms.hhcib.entity.Category;
import se.enbohms.hhcib.entity.Subject;
import se.enbohms.hhcib.entity.User;

/**
 * Defines the contract for operations on the database
 */
public interface CrudService {

	/**
	 * 
	 * @param category
	 * @return a list of subject for the supplied category from the database or
	 *         empty list of no subjects are found
	 */
	List<Subject> getSubjectsFor(Category category);

	/**
	 * Inserts (creates) a new object in the database
	 * 
	 * @param heading
	 * @param description
	 * @param category
	 * @param user
	 * @return the newly created subject
	 */
	Subject createSubject(String heading, String description,
			Category category, User user);

	/**
	 * Updates a current subject in the database
	 * 
	 * @param existingSubject
	 */
	void update(Subject existingSubject);

	/**
	 * Find a subject in the database from the supplied objectId or throws
	 * {@link EntityNotFoundException} of no match is found
	 * 
	 * @param objectId
	 * @return a new subject
	 * @throws EntityNotFoundException
	 *             if no subject is found with the supplied objectId
	 */
	Subject find(String objectId) throws EntityNotFoundException;

	/**
	 * Deletes a subject from the database
	 * 
	 * @param objectId
	 */
	void delete(String objectId);
}
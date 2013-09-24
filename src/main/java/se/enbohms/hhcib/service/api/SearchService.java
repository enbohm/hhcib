package se.enbohms.hhcib.service.api;

import java.util.List;

import se.enbohms.hhcib.entity.Subject;

/**
 * Defines the methods for searching the the database
 */
public interface SearchService {

	/**
	 * Returns a list of {@link Subject} matching the supplied search text
	 * 
	 * @param searchText
	 * @return a list of subject or empty list if not matching subjects are
	 *         found
	 */
	List<Subject> search(String searchText);
}

package se.enbohms.hhcib.facade;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;

import se.enbohms.hhcib.entity.Category;
import se.enbohms.hhcib.entity.Subject;

/**
 * Represent the facade client can use when interaction with {@link Subject}
 * 
 */
@Stateless
@Named
public class SubjectFacade {

	/**
	 * 
	 * @param category
	 * @return a list of subject for the corresponding {@link Category} or empty
	 *         list if no subjects exist for the supplied category
	 */
	public List<Subject> getSubjectsFor(Category category) {

		switch (category) {
		case ECONOMY:
			return Arrays.asList(Subject.of(1, "Reseavdrag",
					"Så här gör du avdrag för din resa"));
		case FOOD:
			return Arrays.asList(Subject.of(1, "Förbereda hummer",
					"Så här tillagar du himmer"));
		case HOUSE_AND_GARDEN:
			return Arrays.asList(Subject.of(1, "Lägga sten",
					"Så här lägger du sten"));
		default:
			return Collections.emptyList();
		}
	}
}

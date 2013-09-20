package se.enbohms.hhcib.facade;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import se.enbohms.hhcib.entity.Category;
import se.enbohms.hhcib.entity.Subject;
import se.enbohms.hhcib.service.api.CrudService;

/**
 * JSF facade which handles fetching subject in categories
 * 
 */
@RequestScoped
@Named
public class CategoryFacade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CrudService service;

	private String categoryValue;

	public String getCategoryValue() {
		return categoryValue;
	}

	public void setCategoryValue(String categoryValue) {
		this.categoryValue = categoryValue;
	}

	public Category[] getCategories() {
		return Category.values();
	}

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
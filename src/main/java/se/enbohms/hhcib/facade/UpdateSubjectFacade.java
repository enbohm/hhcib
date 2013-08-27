package se.enbohms.hhcib.facade;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import se.enbohms.hhcib.entity.Subject;
import se.enbohms.hhcib.service.api.CrudService;

/**
 * Facade which is responsible for loading/fetching existing subject and
 * updating them in the DB
 * 
 * <p>
 * Note: All facades (incl this) should only act as a facade and delegate all
 * business logic to the existing services. Validation and coordination between
 * service is the facade main responsibility
 */
@Named
@ViewScoped
public class UpdateSubjectFacade implements Serializable {

	private static final long serialVersionUID = -1712331748877385330L;

	private String subjectId;
	private Subject subject;
	private String category;
	private Double rating = Double.valueOf(0d);

	@Inject
	private CrudService service;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public Double getRating() {
		return rating;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setRating(Double rating) {
		this.rating = rating;

	}

	/**
	 * Fetches the subject from the database using the supplied subjectId and
	 * category (parameters from the Http Request)
	 */
	public void fetchSubject() {
		this.subject = service.find(subjectId);
	}

	/**
	 * Updates the current subject in the database
	 */
	public void update() {
		service.update(subject);
	}
}
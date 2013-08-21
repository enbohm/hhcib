package se.enbohms.hhcib.facade;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import se.enbohms.hhcib.entity.Category;
import se.enbohms.hhcib.entity.Subject;
import se.enbohms.hhcib.service.api.CrudService;

@Named
@ViewScoped
public class UpdateSubjectFacade implements Serializable {
	private static final long serialVersionUID = -1712331748877385330L;
	private String subjectId;
	private Subject subject;

	public String getSubjectId() {
		return subjectId;
	}

	@Inject
	private CrudService service;

	public void fetchSubject() {
		this.subject = service.find(subjectId, Category.FOOD);
	}

	private Double rating = Double.valueOf(0d);

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
}
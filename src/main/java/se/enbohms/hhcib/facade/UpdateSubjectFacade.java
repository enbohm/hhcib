package se.enbohms.hhcib.facade;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
 * Note: All facades (incl. this) should only act as a facade and delegate all
 * business logic to the existing services. Validation and coordination between
 * service is the facade main responsibility
 */
@Named
@ViewScoped
public class UpdateSubjectFacade implements Serializable {

	private static final String SUBJECTS_IN_CATEGORY_URL = "/categories/subjects_in_category.xhtml?category=";

	private static final long serialVersionUID = -1712331748877385330L;

	private String subjectId;
	private Subject subject;

	private String category;

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
		return subject.getRating();
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public Subject getSubject() {
		return subject;
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
		addUpdateSuccessMessage();
	}

	/**
	 * Deletes the current subject in the database
	 * 
	 * @throws IOException
	 */
	public void delete() throws IOException {
		service.delete(subject.getId());
		addDeleteSuccessMessage();
		redirectToShowAllSubject();
	}

	private void addUpdateSuccessMessage() {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Uppdatering lyckades", "Uppdatering lyckades"));
	}

	private void addDeleteSuccessMessage() {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Inlägget togs bort", "Inlägget togs bort"));
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.setKeepMessages(true);
	}

	private void redirectToShowAllSubject() throws IOException {
		String contextPath = ((javax.servlet.http.HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest())
				.getContextPath();
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect(contextPath + SUBJECTS_IN_CATEGORY_URL + category);
	}
}
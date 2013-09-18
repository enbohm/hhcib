package se.enbohms.hhcib.facade;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Size;

import se.enbohms.hhcib.entity.Category;
import se.enbohms.hhcib.entity.User;
import se.enbohms.hhcib.entity.validator.NotNullOrEmpty;
import se.enbohms.hhcib.service.api.CrudService;

/**
 * JSF facade which handles client interaction when new subjects are created
 * 
 */
@Named
@ViewScoped
public class NewSubjectFacade implements Serializable {

	private static final String SUBJECTS_IN_CATEGORY_URL = "/categories/subjects_in_category.xhtml?category=";

	private static final long serialVersionUID = -3633333461394775021L;

	private String category;

	@NotNullOrEmpty(message = "Beskrivingen kan inte vara tom")
	@Size(min = 10)
	private String description;

	@Inject
	private CrudService service;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Saves the new subject description in the database
	 * 
	 * @throws IOException
	 */
	public void save(User user) throws IOException {
		service.createSubject("a heading", this.description,
				Category.valueOf(this.category), user);
		addCreateSubjectMessage();
		redirectToShowAllSubject();
	}

	private void addCreateSubjectMessage() {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Ny beskrivning har skapats, tack för din medverkan!",
						"Ny beskrivning har skapats, tack för din medverkan!"));
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

	/**
	 * Clears the new subscription
	 */
	public void clear() {
		description = null;
	}
}
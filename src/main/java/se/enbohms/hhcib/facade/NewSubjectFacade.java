package se.enbohms.hhcib.facade;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import se.enbohms.hhcib.entity.Category;
import se.enbohms.hhcib.entity.Subject;
import se.enbohms.hhcib.service.api.CrudService;

/**
 * Represent the facade client can use when interaction with {@link Subject}
 * 
 */
@Named
@ViewScoped
public class NewSubjectFacade implements Serializable {

	private static final long serialVersionUID = -3633333461394775021L;

	private String category;

	@NotNull
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
	 */
	public void save() {
		service.insertSubject("a heading", this.description,
				Category.valueOf(this.category));
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Ny beskrivning har skapats",
						"Ny beskrivning har skapats"));
	}

	/**
	 * Clears the new subscription form
	 */
	public void clear() {
		description = null;
	}
}
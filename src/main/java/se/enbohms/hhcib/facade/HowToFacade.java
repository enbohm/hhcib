package se.enbohms.hhcib.facade;

import java.io.Serializable;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import se.enbohms.hhcib.entity.Category;
import se.enbohms.hhcib.service.api.CrudService;

@Named
@RequestScoped
public class HowToFacade implements Serializable {

	private static final long serialVersionUID = -3633333461394775021L;

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

	public void save() {
		System.out.println("submitted value  " + description);
		service.createSubject("a heading", description, Category.FOOD);
	}

	public void clear() {
		description = null;
	}
}
package se.enbohms.hhcib.facade;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import se.enbohms.hhcib.entity.Category;
import se.enbohms.hhcib.service.api.CrudService;

@Named
@ViewScoped
public class HowToFacade implements Serializable {

	private static final long serialVersionUID = -3633333461394775021L;

	private String description;
	private String category;

	@Inject
	private CrudService service;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String save() {
		System.out.println("submitted value  " + description);
		service.createSubject("a heading", description, Category.FOOD);
		return "/hhcib/index.xhtml";
	}

	public void clear() {
		description = null;
	}
}
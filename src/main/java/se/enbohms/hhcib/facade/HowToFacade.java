package se.enbohms.hhcib.facade;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class HowToFacade implements Serializable {

	private static final long serialVersionUID = -3633333461394775021L;

	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String save() {
		System.out.println("submitted value  " + description);
		return "/hhcib/index.xhtml";
	}
	public void clear() {
		description = null;
	}
}
package se.enbohms.hhcib.facade;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class HowToFacade implements Serializable {

	private static final long serialVersionUID = -3633333461394775021L;

	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void save() {

		System.out.println("submitted value  " + description);
	}
}
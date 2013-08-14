package se.enbohms.hhcib.facade;

import javax.inject.Named;

@Named
public class HowToDescriptionFacade {
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}

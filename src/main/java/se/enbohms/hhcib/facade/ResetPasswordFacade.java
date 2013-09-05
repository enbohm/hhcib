package se.enbohms.hhcib.facade;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import se.enbohms.hhcib.entity.Email;
import se.enbohms.hhcib.service.impl.UserService;

/**
 * Facade which handles the case when uses has forgotten his password
 */
@Named
@RequestScoped
public class ResetPasswordFacade implements Serializable {

	private static final long serialVersionUID = -1712331748877385330L;

	@NotNull(message = "E-post får inte vara tomt")
	private String email;

	private boolean valid = true;

	@Inject
	private UserService userService;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void emailChanged(AjaxBehaviorEvent event) {
		try {
			setValid(userService.existing(Email.of(getEmail())));
		} catch (IllegalArgumentException e) {
			setValid(false);
		}
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public void resetPassword() {
		if (userService.existing(Email.of(getEmail()))) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Ett nytt lösenord har skickas med E-post",
							"Ett nytt lösenord har skickas med E-post"));
		} else {
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Angivet lösenord kan inte hittas, vänligen kontrollera.",
									"Angivet lösenord kan inte hittas, vänligen kontrollera."));
		}
	}
}
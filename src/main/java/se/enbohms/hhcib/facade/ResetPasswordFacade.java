package se.enbohms.hhcib.facade;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import se.enbohms.hhcib.entity.Email;
import se.enbohms.hhcib.service.impl.UserServiceUtil;

/**
 * Facade which handles the case when uses has forgotten his password
 */
@Named
@ViewScoped
public class ResetPasswordFacade implements Serializable {

	private static final long serialVersionUID = -1712331748877385330L;

	@NotNull(message = "E-post får inte vara tomt")
	private String email;

	private boolean valid = true;

	@Inject
	private UserServiceUtil userService;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Handles JSF ajax event
	 * 
	 * @param event
	 */
	public void emailChanged(AjaxBehaviorEvent event) {
		if (correctEmail()) {
			setValid(userService.existing(Email.of(getEmail())));
		} else {
			setValid(false);
		}
	}

	private boolean correctEmail() {
		return getEmail() != null && getEmail().contains("@");
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public void resetPassword() {
		try {
			if (userService.existing(Email.of(getEmail()))) {
				resetEmail();
			} else {
				handleEmailNotFound();
			}
		} catch (IllegalArgumentException e) {
			handleEmailNotFound();
		}
	}

	private void handleEmailNotFound() {
		FacesContext
				.getCurrentInstance()
				.addMessage(
						null,
						new FacesMessage(
								FacesMessage.SEVERITY_ERROR,
								"Angivet lösenord kan inte hittas, vänligen kontrollera.",
								"Angivet lösenord kan inte hittas, vänligen kontrollera."));
	}

	private void resetEmail() {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Ett nytt lösenord har skickas med E-post",
						"Ett nytt lösenord har skickas med E-post"));
	}
}
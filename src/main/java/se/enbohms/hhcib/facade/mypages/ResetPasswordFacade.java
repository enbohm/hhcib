package se.enbohms.hhcib.facade.mypages;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import se.enbohms.hhcib.common.Utils;
import se.enbohms.hhcib.entity.Email;
import se.enbohms.hhcib.entity.Password;
import se.enbohms.hhcib.service.api.NotificationException;
import se.enbohms.hhcib.service.api.NotificationService;
import se.enbohms.hhcib.service.api.UserService;
import se.enbohms.hhcib.service.impl.UserServiceUtil;

/**
 * JSF facade which handles the case when uses has forgotten his password
 * 
 */
@Named
@RequestScoped
public class ResetPasswordFacade implements Serializable {

	private static final long serialVersionUID = -1712331748877385330L;

	@NotNull(message = "E-post får inte vara tomt")
	@se.enbohms.hhcib.entity.validator.Email(message = "E-post är inte giltigt")
	private String email;

	private boolean valid = true;

	@Inject
	private UserServiceUtil userServiceUtil;

	@Inject
	private NotificationService notificationService;

	@Inject
	private UserService userService;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Handles JSF ajax event for determine if supplied email exist
	 * 
	 * @param event
	 */
	public void emailChanged(AjaxBehaviorEvent event) {
		if (correctEmail()) {
			setValid(userServiceUtil.existing(Email.of(getEmail())));
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

	/**
	 * Resets the password for a user (i.e. send a new one to the supplied email
	 * address)
	 * 
	 * @throws NotificationException
	 */
	public void resetPassword() throws NotificationException {
		if (userServiceUtil.existing(Email.of(getEmail()))) {
			resetEmail();
		} else {
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

	private void resetEmail() throws NotificationException {
		Password generatedPwd = Utils.generatePassword();
		userService.updateNewPasswordFor(Email.of(getEmail()), generatedPwd);
		notificationService.sendMessageTo(Email.of(getEmail()), generatedPwd);	
		addNewPasswordSendMessage();
	}

	private void addNewPasswordSendMessage() {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Ett nytt lösenord har skickas med E-post",
						"Ett nytt lösenord har skickas med E-post"));
	}
}
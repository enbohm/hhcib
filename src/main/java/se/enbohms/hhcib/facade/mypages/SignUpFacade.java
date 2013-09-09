package se.enbohms.hhcib.facade.mypages;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Size;

import se.enbohms.hhcib.entity.Email;
import se.enbohms.hhcib.entity.validator.NotNullOrEmpty;
import se.enbohms.hhcib.service.api.UserService;
import se.enbohms.hhcib.service.impl.UserServiceUtil;

/**
 * JSF Facade handling user sign up
 */
@Named
@RequestScoped
public class SignUpFacade implements Serializable {

	private static final long serialVersionUID = -2711087335089608672L;

	@NotNullOrEmpty(message = "Användarnamn kan inte vara tomt")
	private String userName;

	@se.enbohms.hhcib.entity.validator.Email(message = "Ange en giltig E-post adress")
	private String email;

	@NotNullOrEmpty(message = "Lösenord kan inte vara tomt (minst 4 tecken)")
	@Size(min = 4, max = 50, message = "Lösenord måste var minst 4 tecken långt")
	private String password;

	private String repeatedPassword;

	@Inject
	private UserService loginService;

	@Inject
	private UserServiceUtil userServiceUtil;

	/**
	 * Creates a new user with the supplied username, email and password
	 */
	public void signUp() {
		checkUniqueUserName();

		if (!password.equals(repeatedPassword)) {
			addPasswordDiffersMessage();
		} else {
			loginService.createUser(userName, Email.of(email), password);
			addSuccessMesssage();
		}
	}

	/**
	 * Handles JSF ajax event for determine if supplied email exist
	 * 
	 * @param event
	 */
	public void userNameExist(AjaxBehaviorEvent event) {
		checkUniqueUserName();
	}

	protected void checkUniqueUserName() {
		if (!userServiceUtil.unique(getUserName())) {
			addMessageUserNameNotUnique();
		}
	}

	protected void addMessageUserNameNotUnique() {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Användarnamnet är upptaget", null));
	}

	private void addPasswordDiffersMessage() {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Lösenorden skiljer sig åt", null));
	}

	private void addSuccessMesssage() {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"En ny användare har skapats", null));
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepeatedPassword() {
		return repeatedPassword;
	}

	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}
}
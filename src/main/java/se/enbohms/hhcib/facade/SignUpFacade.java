package se.enbohms.hhcib.facade;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Size;

import se.enbohms.hhcib.entity.Email;
import se.enbohms.hhcib.entity.validator.NotNullOrEmpty;
import se.enbohms.hhcib.service.api.UserService;

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

	@Inject
	private UserService loginService;

	/**
	 * Creates a new user with the supplied username, email and password
	 */
	public void signUp() {
		try {
			Email validEmail = Email.of(email);
			loginService.createUser(userName, validEmail, password);
			addSuccessMesssage();
		} catch (IllegalArgumentException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Ogiltig e-post", null));
		}
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
}
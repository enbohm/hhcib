package se.enbohms.hhcib.facade.mypages;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import se.enbohms.hhcib.entity.User;
import se.enbohms.hhcib.entity.validator.Password;
import se.enbohms.hhcib.service.api.UserService;

/**
 * JSF facade handling user password changes
 * 
 */
@Named
@RequestScoped
public class UpdatePasswordFacade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Password(message = "Lösenord måste var minst 4 tecken långt")
	private String password;

	private String repeatedPassword;

	@Inject
	private UserService userService;

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

	public void updatePassword(User user) {
		if (!password.equals(repeatedPassword)) {
			addPasswordDiffersMessage();
		} else {
			userService.updateUserPassword(
					se.enbohms.hhcib.entity.Password.of(password), user);
			addPasswordChangedMessage();
		}
	}

	private void addPasswordChangedMessage() {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Ditt lösenord har uppdaterats",
						"Ditt lösenord har uppdaterats"));
	}

	private void addPasswordDiffersMessage() {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Lösenorden skiljer sig åt",
						"Lösenorden skiljer sig åt"));
	}
}

package se.enbohms.hhcib.facade.mypages;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import se.enbohms.hhcib.common.Constants;
import se.enbohms.hhcib.entity.User;
import se.enbohms.hhcib.entity.validator.Email;
import se.enbohms.hhcib.service.api.UserService;

/**
 * JSF facade handling user password changes
 * 
 */
@Named
@RequestScoped
public class UpdateEmailFacade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Email(message = "Ange en giltig E-post adress")
	private String email;

	@Inject
	private UserService userService;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@PostConstruct
	public void init() {
		User user = getUserFromSession();
		this.email = user.getEmail().getEmail();
	}

	private User getUserFromSession() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext()
				.getSession(false);
		User user = (User) session.getAttribute(Constants.USER);
		return user;
	}

	public void updateEmail(User user) {
		userService.updateUserEmail(se.enbohms.hhcib.entity.Email.of(email),
				user);
		addEmailChangedMessage();
	}

	private void addEmailChangedMessage() {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Din e-postadress har uppdaterats",
						"Din e-postadress har uppdaterats"));
	}
}
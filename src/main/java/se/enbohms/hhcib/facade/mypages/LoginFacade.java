package se.enbohms.hhcib.facade.mypages;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import se.enbohms.hhcib.common.Constants;
import se.enbohms.hhcib.entity.User;
import se.enbohms.hhcib.entity.validator.NotNullOrEmpty;
import se.enbohms.hhcib.service.api.UserAuthenticationException;
import se.enbohms.hhcib.service.api.UserService;

@Named
@RequestScoped
public class LoginFacade implements Serializable {

	private static final long serialVersionUID = -1712331748877385330L;

	private static Logger LOG = Logger.getLogger(LoginFacade.class.getName());

	@NotNullOrEmpty(message = "Fyll i ett användarnamn")
	private String username;

	@NotNull(message = "Fyll i ett lösenord")
	private String password;

	@Inject
	private UserService loginService;

	/**
	 * Invalidated the HttpSession and redirect to the start page
	 * 
	 * @return the start page url
	 */
	public String logout() {
		invalidateSession();
		return "/index?faces-redirect=true";
	}

	/**
	 * Logs in a user and redirect to the original request page
	 * 
	 * @return the url
	 */
	public String login() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext()
				.getSession(true);
		try {
			return loginAndRedirectUser(session);
		} catch (UserAuthenticationException e) {
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_WARN,
							"Inloggning misslyckades, kontrollera användarnamn/lösenord",
							null));
			return "login.xhtml";
		}
	}

	private String loginAndRedirectUser(HttpSession session)
			throws UserAuthenticationException {
		User user = loginService.login(username, password);
		session.setAttribute(Constants.USER, user);

		return redirectUser(session);
	}

	private String redirectUser(HttpSession session) {
		String targetUrl = (String) session.getAttribute(Constants.TARGET_URL);
		if (targetUrl != null) {
			LOG.info("Target url " + targetUrl);
			session.removeAttribute(Constants.TARGET_URL);
			return targetUrl + "?faces-redirect=true";
		} else {
			return "/index?faces-redirect=true";
		}
	}

	private void invalidateSession() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();

		request.getSession().invalidate();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
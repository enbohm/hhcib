package se.enbohms.hhcib.facade;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import se.enbohms.hhcib.service.api.UserAuthenticationException;
import se.enbohms.hhcib.service.api.LoginService;

@Named
@ViewScoped
public class LoginFacade {

	private static Logger log = Logger.getLogger(LoginFacade.class.getName());

	private String username;
	private String password;

	@Inject
	private LoginService loginService;

	public String login() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			loginService.login(username, password);
		} catch (UserAuthenticationException e) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "Login failed!", null));
			return "login.xhtml";
		}

		return "/index?faces-redirect=true";
	}

	public String logout() {
		String result = "/index?faces-redirect=true";

		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();

		try {
			request.logout();
		} catch (ServletException e) {
			log.log(Level.SEVERE, "Failed to logout user!", e);
			result = "/loginError?faces-redirect=true";
		}

		return result;
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
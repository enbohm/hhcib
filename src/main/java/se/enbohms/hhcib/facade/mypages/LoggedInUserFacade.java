package se.enbohms.hhcib.facade.mypages;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import se.enbohms.hhcib.common.Constants;
import se.enbohms.hhcib.entity.User;

/**
 * JSF Facade handling user login scenario
 */
@Named
@RequestScoped
public class LoggedInUserFacade {

	/**
	 * 
	 * @return the logged in user fetched from the {@link HttpSession}
	 */
	public User getLoggedInUser() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext()
				.getSession(false);
		return (User) session.getAttribute(Constants.USER);
	}

	/**
	 * 
	 * @return {@code true} is there is an logged in user, {@code false}
	 *         otherwise
	 */
	public boolean isUserLoggedIn() {
		return getLoggedInUser() != null;
	}

}

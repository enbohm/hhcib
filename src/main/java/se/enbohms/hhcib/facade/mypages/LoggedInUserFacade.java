package se.enbohms.hhcib.facade.mypages;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import se.enbohms.hhcib.common.Constants;
import se.enbohms.hhcib.entity.Subject;
import se.enbohms.hhcib.entity.User;
import se.enbohms.hhcib.service.api.SubjectCrudService;

/**
 * JSF facade which contains methods for handling a logged in user
 * 
 */
@Named
@RequestScoped
public class LoggedInUserFacade {

	@Inject
	private SubjectCrudService crudService;

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

	/**
	 * 
	 * @param user
	 * @return a list with subjected created by the supplied user
	 */
	public List<Subject> getSubjectsCreatedByUser() {
		return crudService.getSubjectsCreatedBy(getLoggedInUser());
	}
}
package se.enbohms.hhcib.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Observes;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import se.enbohms.hhcib.common.Constants;
import se.enbohms.hhcib.entity.Email;
import se.enbohms.hhcib.entity.Subject;
import se.enbohms.hhcib.entity.User;
import se.enbohms.hhcib.service.api.UserCreatedEvent;
import se.enbohms.hhcib.service.api.UserService;
import se.enbohms.hhcib.service.api.UserUpdatedEvent;

/**
 * Contains various util methods for handling users
 */
@Singleton
@Startup
@Named
public class UserServiceUtil {

	private final static Logger LOG = Logger.getLogger(UserServiceUtil.class
			.getName());

	private List<Email> userEmails = new ArrayList<>();
	private List<String> userNames = new ArrayList<>();

	@Inject
	private UserService userService;

	@PostConstruct
	private void init() {
		userEmails.addAll(userService.getEmails());
		userNames.addAll(userService.getUserNames());
	}

	/**
	 * 
	 * @param email
	 * @return {@code true} if the supplied email exist, {@code false} otherwise
	 */
	public boolean existing(Email email) {
		return userEmails.contains(email);
	}

	/**
	 * 
	 * @param userName
	 * @return {@code true} if the supplied userName is unique, {@code false}
	 *         otherwise
	 */
	public boolean unique(String userName) {
		return !userNames.contains(userName);
	}

	/**
	 * 
	 * @param user
	 * @param subject
	 * @return {@code true} if the user can modify the subject, {@code false}
	 *         otherwise
	 */
	public boolean canModify(User user, Subject subject) {
		return existingUserAndSubject(user, subject)
				&& createdByUser(user, subject);
	}

	/**
	 * 
	 * @param user
	 * @param subject
	 * @return {@code true} if the user is allowed to rate the supplied subject,
	 *         {@code false} otherwise
	 */
	public boolean canRate(User user, Subject subject) {
		return existingUserAndSubject(user, subject)
				&& createdByAnotherUser(user, subject);
	}

	private boolean createdByUser(User user, Subject subject) {
		return user.getUserName().equals(subject.getCreatedBy());
	}

	private boolean existingUserAndSubject(User user, Subject subject) {
		return user != null && subject != null;
	}

	private boolean createdByAnotherUser(User user, Subject subject) {
		return !createdByUser(user, subject);
	}

	/**
	 * Adds a new username and email to the exiting ones when a
	 * {@link UserCreatedEvent} occurs
	 * 
	 * @param userCreatedEvent
	 */
	public void addUserInformation(@Observes UserCreatedEvent userCreatedEvent) {
		LOG.info("New user created with username "
				+ userCreatedEvent.getUserName());
		this.userNames.add(userCreatedEvent.getUserName());
		this.userEmails.add(userCreatedEvent.getEmail());
	}

	/**
	 * Updates user emails when a {@link UserUpdatedEvent} occurs
	 * 
	 * @param userUpdatedEvent
	 */
	public void updateUserInformation(
			@Observes UserUpdatedEvent userUpdatedEvent) {
		LOG.info("User " + userUpdatedEvent.getUser() + " has updated email");
		this.userEmails.remove(userUpdatedEvent.getUser().getEmail());
		this.userEmails.add(userUpdatedEvent.getEmail());
		
		updateUserInSession(userUpdatedEvent);
	}

	private void updateUserInSession(UserUpdatedEvent userUpdatedEvent) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext()
				.getSession(false);
		session.removeAttribute(Constants.USER);
		session.setAttribute(Constants.USER, User.creteUser(userUpdatedEvent.getUser().getId(),
				userUpdatedEvent.getUser().getUserName(), userUpdatedEvent.getEmail()));
	}
}
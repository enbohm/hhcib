package se.enbohms.hhcib.service.api;

import se.enbohms.hhcib.entity.Email;
import se.enbohms.hhcib.entity.User;

/**
 * Represents event information which is broadcasted to listening consumers when
 * a user is updated.
 */
public final class UserUpdatedEvent {

	private User user;
	private Email email;

	private UserUpdatedEvent(User user, Email email) {
		this.user = user;
		this.email = email;
	}

	/**
	 * Factory method which creates new instances of this class
	 * 
	 * @param userName
	 * @param email
	 * @return a new instance of this class
	 */
	public static UserUpdatedEvent of(User user, Email updatedEmailAddress) {
		return new UserUpdatedEvent(user, updatedEmailAddress);
	}

	public User getUser() {
		return user;
	}

	public Email getEmail() {
		return email;
	}
}
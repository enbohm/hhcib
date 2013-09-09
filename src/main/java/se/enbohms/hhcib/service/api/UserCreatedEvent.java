package se.enbohms.hhcib.service.api;

import se.enbohms.hhcib.entity.Email;

/**
 * Represents event information which is broadcasted to listening consumers when
 * a new user is created
 */
public class UserCreatedEvent {
	
	private String userName;
	private Email email;

	private UserCreatedEvent(String userName, Email email) {
		this.userName = userName;
		this.email = email;
	}

	/**
	 * Factory method which creates new instances of this class
	 * 
	 * @param userName
	 * @param email
	 * @return a new instance of this class
	 */
	public static UserCreatedEvent of(String userName, Email email) {
		return new UserCreatedEvent(userName, email);
	}

	public String getUserName() {
		return userName;
	}

	public Email getEmail() {
		return email;
	}
}
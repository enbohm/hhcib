package se.enbohms.hhcib.service.api;

/**
 * Represents event information which is broadcasted to listening consumers when
 * a new user is created
 */
public class UserCreatedEvent {
	private String userName;

	private UserCreatedEvent(String userName) {
		this.userName = userName;
	}

	/**
	 * Factory method which creates new instances of this class
	 * 
	 * @param userName
	 * @return a new instance of this class
	 */
	public static UserCreatedEvent of(String userName) {
		return new UserCreatedEvent(userName);
	}

	public String getUserName() {
		return userName;
	}
}
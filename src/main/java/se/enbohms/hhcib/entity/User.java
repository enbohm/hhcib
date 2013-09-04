package se.enbohms.hhcib.entity;

/**
 * Represents a logged in user
 * 
 * 
 */
public class User {

	private String userName;
	private Email email;
	private Group group;

	private User(String userName, Email email, Group group) {
		this.userName = userName;
		this.email = email;
		this.group = group;
	}

	/**
	 * Factory method which create administrator users
	 * 
	 * @param userName
	 * @param email
	 * @return a new user with administrator privileges
	 */
	public static User creteAdminUser(String userName, Email email) {
		return new User(userName, email, Group.ADMINISTRATOR);
	}

	/**
	 * Factory method which create default/ordinary users
	 * 
	 * @param userName
	 * @param email
	 * @return a new user
	 */
	public static User creteUser(String userName, Email email) {
		return new User(userName, email, Group.DEFAULT_USER);
	}

	public String getUserName() {
		return userName;
	}

	public Email getEmail() {
		return email;
	}

	public Group getGroup() {
		return group;
	}
}
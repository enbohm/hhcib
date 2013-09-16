package se.enbohms.hhcib.entity;

/**
 * Represents a in user in HHCIB domain.
 * 
 */
public final class User {

	public static final String ID = "_id";
	public static final String EMAIL = "email";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";

	private String id;
	private String userName;
	private Email email;
	private Group group;

	private User(String id, String userName, Email email, Group group) {
		this.id = id;
		this.userName = userName;
		this.email = email;
		this.group = group;
	}

	/**
	 * Factory method which create administrator users
	 * 
	 * @param id
	 * @param userName
	 * @param email
	 * 
	 * @return a new user with administrator privileges
	 */
	public static User creteAdminUser(String id, String userName, Email email) {
		return new User(id, userName, email, Group.ADMINISTRATOR);
	}

	/**
	 * Factory method which create default/ordinary users
	 * 
	 * @param id
	 * @param userName
	 * @param email
	 * 
	 * @return a new user
	 */
	public static User creteUser(String id, String userName, Email email) {
		return new User(id, userName, email, Group.DEFAULT_USER);
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

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", email=" + email
				+ ", group=" + group + "]";
	}
}
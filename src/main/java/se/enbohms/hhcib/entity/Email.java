package se.enbohms.hhcib.entity;

public class Email {

	private String email;

	private Email(String email) {
		this.email = email;
	}

	/**
	 * Factory method which create new instance of {@link Email}
	 * 
	 * @param email
	 * @return a new instance of this class
	 */
	public static Email of(String email) {
		if (email != null && email.contains("@")) {
			return new Email(email);
		} else {
			throw new IllegalArgumentException(
					"The supplied email is not valid " + email);
		}
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return email;
	}
}

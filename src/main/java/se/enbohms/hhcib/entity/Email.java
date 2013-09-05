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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Email other = (Email) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}
}

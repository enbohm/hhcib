package se.enbohms.hhcib.entity;

import javax.validation.constraints.Size;

import se.enbohms.hhcib.entity.validator.NotNullOrEmpty;

/**
 * Represents a users password. Must not be {@code null} or less than 4
 * characters or more that 50 characters.
 */
public class Password {

	@NotNullOrEmpty(message = "Lösenord kan inte vara tomt (minst 4 tecken)")
	@Size(min = 4, max = 50, message = "Lösenord måste var minst 4 tecken långt")
	private String password;

	private Password(String password) {
		this.password = password;
	}

	public static Password of(String password) {
		if (password == null || password.length() < 4 || password.length() > 50) {
			throw new IllegalArgumentException(
					"Password must not be null and between 4-50 characters");
		}
		return new Password(password);
	}

	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}
}
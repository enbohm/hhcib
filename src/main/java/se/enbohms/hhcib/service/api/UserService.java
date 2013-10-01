package se.enbohms.hhcib.service.api;

import java.util.List;

import se.enbohms.hhcib.entity.Email;
import se.enbohms.hhcib.entity.Password;
import se.enbohms.hhcib.entity.User;

/**
 * Describes the various method needed for logging in and out users
 * 
 */
public interface UserService {

	/**
	 * Logs in a user.
	 * 
	 * @param userName
	 * @param password
	 * @return the logged in user
	 * @throws UserAuthenticationException
	 *             if authentication fails
	 */
	User login(String userName, String password)
			throws UserAuthenticationException;

	/**
	 * Creates a new user with the supplied information
	 * 
	 * @param userName
	 * @param email
	 * @param password
	 * @return a new user instance
	 */
	User createUser(String userName, Email email, String password);

	/**
	 * Removes and existing user from the repository
	 * 
	 * @param user
	 * @throws UserNotFoundException
	 */
	void delete(User user) throws UserNotFoundException;

	/**
	 * 
	 * @return all usernames that are found in the repository
	 */
	List<String> getUserNames();

	/**
	 * 
	 * @return all email that are found in the repository
	 */
	List<Email> getEmails();

	/**
	 * Updated the users password
	 * 
	 * @param newPassword
	 * @param user
	 */
	void updateUserPassword(Password newPassword, User user);

	/**
	 * Updated the users E-mail
	 * 
	 * @param newPassword
	 * @param user
	 */
	void updateUserEmail(Email newEmail, User user);
}
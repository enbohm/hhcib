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

	/**
	 * Updated the password for the suppled email address. This method uses the
	 * supplied email to identify the 'user'. The use case for this method is
	 * when a user has forgotten his password and need to reset it
	 * 
	 * @param email
	 * @return a new generated password
	 */
	void updateNewPasswordFor(Email email, Password newPassword);

	/**
	 * 
	 * @param email
	 * @return the user name corresponding the the {@link Email}
	 * @throws UserNotFoundException
	 *             if the supplied email can't be found in the database
	 */
	String getUsernameFrom(Email email) throws UserNotFoundException;

}
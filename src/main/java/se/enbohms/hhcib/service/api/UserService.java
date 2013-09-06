package se.enbohms.hhcib.service.api;

import se.enbohms.hhcib.entity.Email;
import se.enbohms.hhcib.entity.User;

/**
 * Describes the various method needed for logging in/out users
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

	void delete(User user) throws UserNotFoundException;
}
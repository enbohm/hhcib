package se.enbohms.hhcib.service.api;

import se.enbohms.hhcib.entity.User;

/**
 * Describes the various method needed for logging in/out users
 * 
 */
public interface LoginService {

	/**
	 * Logs in a user.
	 * 
	 * @param userName
	 * @param password
	 * @return the logged in user
	 * @throws UserAuthenticationException
	 *             if authentication fails
	 */
	public User login(String userName, String password)
			throws UserAuthenticationException;

	/**
	 * Logs out the current logged in user
	 */
	public void logout();

}
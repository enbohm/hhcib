package se.enbohms.hhcib.service.api;

/**
 * Describes the various method needed for logging in/out users
 * 
 */
public interface LoginService {

	/**
	 * Logs in a user
	 * 
	 * @param userName
	 * @param password
	 * @throws UserAuthenticationException
	 *             if authentication fails
	 */
	public void login(String userName, String password)
			throws UserAuthenticationException;

	/**
	 * Logs out the current logged in user
	 */
	public void logout();

}
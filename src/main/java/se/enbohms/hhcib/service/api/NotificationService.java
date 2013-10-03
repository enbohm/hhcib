package se.enbohms.hhcib.service.api;

import se.enbohms.hhcib.entity.Email;
import se.enbohms.hhcib.entity.Password;

/**
 * Defines the contract for notifying users
 */
public interface NotificationService {

	/**
	 * Send a notification to the supplied email address contains the supplied
	 * password
	 * 
	 * @param email
	 * @param password
	 * @throws NotificationException
	 *             if sending the notification fails
	 */
	void sendMessageTo(Email email, Password password)
			throws NotificationException;
}

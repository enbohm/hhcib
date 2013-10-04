package se.enbohms.hhcib.service.impl;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;

import org.junit.Test;

import se.enbohms.hhcib.entity.Email;
import se.enbohms.hhcib.entity.Password;
import se.enbohms.hhcib.service.api.NotificationService;

/**
 * Test client for the {@link EmailNotificationService}
 */
public class EmailNotificationServiceTest {

	@Test
	public void should_send_email() throws Exception {
		NotificationService notificationService = new TestableEmailNotoficationService();
		notificationService.sendMessageTo(Email.of("andreas@enbohms.se"),
				Password.of("123456"));

	}

	private class TestableEmailNotoficationService extends
			EmailNotificationService {

		@Override
		protected void sendMessage(Transport transport, Message message)
				throws MessagingException {
			// do nothing here, i.e. don't send the actual email
		}
	}
}
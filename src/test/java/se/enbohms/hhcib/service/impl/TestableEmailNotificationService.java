package se.enbohms.hhcib.service.impl;

import javax.ejb.Singleton;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;

@Singleton
public class TestableEmailNotificationService extends EmailNotificationService {

	@Override
	protected void sendMessage(Transport transport, Message message)
			throws MessagingException {
		// do nothing here (i.e don't send the acctual email)
	}
}

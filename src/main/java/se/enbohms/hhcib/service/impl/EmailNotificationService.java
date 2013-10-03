package se.enbohms.hhcib.service.impl;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import se.enbohms.hhcib.entity.Email;
import se.enbohms.hhcib.entity.Password;
import se.enbohms.hhcib.service.api.NotificationException;
import se.enbohms.hhcib.service.api.NotificationService;

/**
 * This implementation uses mail as notification service
 * 
 */
@Singleton
public class EmailNotificationService implements NotificationService {

	private static final String EMAIL_SUBJECT = "HHCIB - lösenordinfo";
	private static final String FROM_ADDRESS = "noreply@hhcib.se";

	private static final String SMTP_HOST_NAME = "smtp.sendgrid.net";
	private static final String SMTP_AUTH_USER = "cloudbees_enbohm";
	private static final String SMTP_AUTH_PWD = "cloudbees_enbohm";
	private static final String NEW_LINE = "\n\r";
	private Session mailSession;

	@PostConstruct
	private void setUp() {
		Properties sessionPropterties = createProperties();
		mailSession = Session.getDefaultInstance(sessionPropterties,
				new SMTPAuthenticator());
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	public void sendMessageTo(Email email, Password password)
			throws NotificationException {

		try {
			Transport transport = mailSession.getTransport();

			Message message = new MimeMessage(mailSession);
			message.setFrom(new InternetAddress(FROM_ADDRESS));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					email.getEmail()));

			message.setSubject(EMAIL_SUBJECT);

			message.setText("Hej!" + NEW_LINE
					+ " Här kommer ditt nya lösenord: " + NEW_LINE
					+ password.getPassword() + NEW_LINE
					+ " Du kan byta lösenord på Mina Sidor");

			sendMessage(transport, message);

		} catch (MessagingException e) {
			throw new NotificationException(e);
		}
	}

	private void sendMessage(Transport transport, Message message)
			throws MessagingException {
		transport.connect();
		transport.sendMessage(message,
				message.getRecipients(Message.RecipientType.TO));

		transport.close();
	}

	private Properties createProperties() {
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		props.put("mail.smtp.port", 587);
		props.put("mail.smtp.auth", "true");
		return props;
	}

	private class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			String username = SMTP_AUTH_USER;
			String password = SMTP_AUTH_PWD;
			return new PasswordAuthentication(username, password);
		}
	}
}

package se.enbohms.hhcib.service.impl;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import se.enbohms.hhcib.entity.Email;
import se.enbohms.hhcib.entity.Password;
import se.enbohms.hhcib.service.api.NotificationService;
import se.enbohms.hhcib.web.util.IntegrationTest;

/**
 * Test client for the {@link EmailNotificationService}
 */
@org.junit.experimental.categories.Category(IntegrationTest.class)
@RunWith(Arquillian.class)
public class EmailNotificationServiceTest {

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)
				.addClass(NotificationService.class)
				.addClass(TestableEmailNotificationService.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Inject
	private NotificationService notificationService;

	@Test
	public void should_send_email() throws Exception {
		notificationService.sendMessageTo(Email.of("andreas@enbohms.se"),
				Password.of("123456"));

	}
}
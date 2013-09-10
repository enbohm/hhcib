package se.enbohms.hhcib.service.impl;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import se.enbohms.hhcib.entity.Email;
import se.enbohms.hhcib.entity.IntegrationTest;
import se.enbohms.hhcib.entity.User;
import se.enbohms.hhcib.service.api.UserAuthenticationException;
import se.enbohms.hhcib.service.api.UserService;

/**
 * Test client for the {@link MongoUserService}
 */
@Category(IntegrationTest.class)
@RunWith(Arquillian.class)
public class MongoUserServiceTest {

	private static final String EMAIL_STRING = "test@test.com";
	private static final String TEST_USER = "junit_test_user";

	@Inject
	private UserService userService;

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)
				.addClass(MongoUserService.class)
				.addClass(MongoDBInitiator.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Test(expected = UserAuthenticationException.class)
	public void should_throw_excsption_when_user_does_not_exist()
			throws Exception {
		userService.login("userShouldNotAuthenticate", "no");
	}

	@Test
	public void should_create_and_delete_a_user() throws Exception {
		User user = null;
		try {
			// when
			user = userService.createUser(TEST_USER, Email.of(EMAIL_STRING),
					"password");
			// then
			assertThat(user.getId()).isNotEmpty();
			assertThat(user.getId().length()).isGreaterThan(10);

		} finally {
			if (user != null) {
				userService.delete(user);
			}
		}
	}

	@Test
	public void should_create_login_and_delete_a_user() throws Exception {
		User user = null;
		try {
			// when
			user = userService.createUser(TEST_USER, Email.of(EMAIL_STRING),
					"password");

			User loggedInUser = userService.login(TEST_USER, "password");

			// then
			assertThat(loggedInUser).isNotNull();
			assertThat(loggedInUser.getEmail().getEmail()).isEqualTo(
					EMAIL_STRING);
			assertThat(loggedInUser.getId()).isEqualTo(user.getId());

		} finally {
			if (user != null) {
				userService.delete(user);
			}
		}
	}

	@Test
	public void should_return_several_user_names() throws Exception {
		User user = null;
		try {
			// when
			user = userService.createUser(TEST_USER, Email.of(EMAIL_STRING),
					"password");

			List<String> userNames = userService.getUserNames();

			// then
			assertThat(userNames).isNotEmpty();

		} finally {
			if (user != null) {
				userService.delete(user);
			}
		}
	}

	@Test
	public void should_return_several_emails() throws Exception {
		User user = null;
		try {
			// when
			user = userService.createUser(TEST_USER, Email.of(EMAIL_STRING),
					"password");

			List<Email> emails = userService.getEmails();

			// then
			assertThat(emails).isNotEmpty();

		} finally {
			if (user != null) {
				userService.delete(user);
			}
		}
	}
}
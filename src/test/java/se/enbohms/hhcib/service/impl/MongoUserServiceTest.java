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
import se.enbohms.hhcib.entity.Password;
import se.enbohms.hhcib.entity.User;
import se.enbohms.hhcib.service.api.UserAuthenticationException;
import se.enbohms.hhcib.service.api.UserNotFoundException;
import se.enbohms.hhcib.service.api.UserService;
import se.enbohms.hhcib.web.util.IntegrationTest;

/**
 * Test client for the {@link MongoUserService}
 */
@Category(IntegrationTest.class)
@RunWith(Arquillian.class)
public class MongoUserServiceTest {

	private static final String PASSWORD = "password";
	private static final String EMAIL_STRING = "junit@test.com";
	private static final String TEST_USER = "junit_test_user";
	private static final String UPDATED_EMAIL = "updated@email.com";

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
	public void should_throw_exception_when_user_does_not_exist()
			throws Exception {
		userService.login("userShouldNotAuthenticate", "no");
	}

	@Test
	public void should_create_and_delete_a_user() throws Exception {
		User user = null;
		try {
			// when
			user = userService.createUser(TEST_USER, Email.of(EMAIL_STRING),
					PASSWORD);
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
					PASSWORD);

			User loggedInUser = userService.login(TEST_USER, PASSWORD);

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
					PASSWORD);

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
					PASSWORD);

			List<Email> emails = userService.getEmails();

			// then
			assertThat(emails).isNotEmpty();

		} finally {
			if (user != null) {
				userService.delete(user);
			}
		}
	}

	@Test
	public void should_update_users_password() throws Exception {
		User user = null;
		try {

			// given
			user = userService.createUser(TEST_USER, Email.of(EMAIL_STRING),
					PASSWORD);

			// when
			userService.updateUserPassword(Password.of("password2"), user);

			// then
			User result = userService.login(TEST_USER, "password2");
			assertThat(result).isNotNull();

		} finally {
			if (user != null) {
				userService.delete(user);
			}
		}
	}

	@Test
	public void should_update_users_email() throws Exception {
		User user = null;
		try {

			// given
			user = userService.createUser(TEST_USER, Email.of(EMAIL_STRING),
					PASSWORD);

			// when
			userService.updateUserEmail(Email.of(UPDATED_EMAIL), user);

			// then
			User result = userService.login(TEST_USER, PASSWORD);
			assertThat(result.getEmail().getEmail()).isEqualTo(UPDATED_EMAIL);

		} finally {
			if (user != null) {
				userService.delete(user);
			}
		}
	}

	@Test
	public void should_update_new_password_for_users_email() throws Exception {
		User user = null;
		try {

			// given
			user = userService.createUser(TEST_USER, Email.of(EMAIL_STRING),
					PASSWORD);

			// when
			userService.updateNewPasswordFor(Email.of(EMAIL_STRING),
					Password.of("newPassword"));

			// then
			User result = userService.login(TEST_USER, "newPassword");
			assertThat(result).isNotNull();

		} finally {
			if (user != null) {
				userService.delete(user);
			}
		}
	}

	@Test
	public void should_return_user_name_from_users_email() throws Exception {
		User user = null;
		try {

			// given
			user = userService.createUser(TEST_USER, Email.of(EMAIL_STRING),
					PASSWORD);

			// when
			String result = userService.getUsernameFrom(Email.of(EMAIL_STRING));

			assertThat(result).isEqualTo(TEST_USER);

		} finally {
			if (user != null) {
				userService.delete(user);
			}
		}
	}

	@Test(expected = UserNotFoundException.class)
	public void should_throw_execption_when_users_email_is_not_found()
			throws Exception {
		userService.getUsernameFrom(Email
				.of("this_email_should_not_exist@dummy.com"));
	}
}
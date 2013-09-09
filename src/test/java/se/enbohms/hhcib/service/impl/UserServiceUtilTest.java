package se.enbohms.hhcib.service.impl;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import se.enbohms.hhcib.entity.Email;
import se.enbohms.hhcib.service.api.UserCreatedEvent;

/**
 * Test client for the {@link UserServiceUtil}
 */
public class UserServiceUtilTest {

	private static final String EMAIL = "test@test.com";
	private static final String USERNAME = "test";
	private static final String ANOTHER_USERNAME = "anotherUserName";

	@Test
	public void should_inform_that_email_does_not_exist() throws Exception {
		// given
		UserServiceUtil service = new UserServiceUtil();

		// when
		boolean result = service.existing(Email.of(EMAIL));

		// then
		assertThat(result).isFalse();
	}

	@Test
	public void should_inform_that_email_exist() throws Exception {
		// given
		UserServiceUtil service = new UserServiceUtil();
		service.addUserInformation(UserCreatedEvent.of(USERNAME,
				Email.of(EMAIL)));

		// when
		boolean result = service.existing(Email.of(EMAIL));

		// then
		assertThat(result).isTrue();
	}

	@Test
	public void should_inform_when_username_is_not_unique() throws Exception {
		// given
		UserServiceUtil service = new UserServiceUtil();
		service.addUserInformation(UserCreatedEvent.of(USERNAME,
				Email.of(EMAIL)));

		// when
		boolean result = service.unique(USERNAME);

		// then
		assertThat(result).isFalse();
	}

	@Test
	public void should_inform_when_username_is_unique() throws Exception {
		// given
		UserServiceUtil service = new UserServiceUtil();
		service.addUserInformation(UserCreatedEvent.of(USERNAME,
				Email.of(EMAIL)));

		// when
		boolean result = service.unique(ANOTHER_USERNAME);

		// then
		assertThat(result).isTrue();
	}
}

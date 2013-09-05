package se.enbohms.hhcib.service.impl;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;

import se.enbohms.hhcib.entity.Email;

/**
 * Test client for the {@link UserService}
 */
public class UserServiceTest {

	private static final String EMAIL = "test@test.com";

	@Test
	public void should_inform_that_email_does_not_exist() throws Exception {
		// given
		UserService service = new UserService();

		// when
		boolean result = service.existing(Email.of(EMAIL));

		// then
		assertThat(result).isFalse();
	}

	@Test
	public void should_inform_that_email_exist() throws Exception {
		// given
		UserService service = new UserService();
		service.setUserEmails(Arrays.asList(Email.of(EMAIL)));

		// when
		boolean result = service.existing(Email.of(EMAIL));

		// then
		assertThat(result).isTrue();
	}
}

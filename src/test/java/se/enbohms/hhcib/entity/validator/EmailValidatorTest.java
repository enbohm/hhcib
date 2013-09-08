package se.enbohms.hhcib.entity.validator;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

/**
 * Test client for the {@link EmailValidator}
 */
public class EmailValidatorTest {

	@Test
	public void should_inform_that_email_is_invalid() throws Exception {
		String emailString = "test.com";
		EmailValidator emailValidator = new EmailValidator();
		assertThat(emailValidator.isValid(emailString, null)).isFalse();
	}

	@Test
	public void should_inform_that_email_is_valid() throws Exception {
		String emailString = "test@testcom";
		EmailValidator emailValidator = new EmailValidator();
		assertThat(emailValidator.isValid(emailString, null)).isTrue();
	}
}

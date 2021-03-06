package se.enbohms.hhcib.entity;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

/**
 * Test client for the {@link Email} class
 * 
 */
public class EmailTest {

	private static final String VALID_EMAIL = "a@b.com";

	@Test(expected = IllegalArgumentException.class)
	public void should_throw_exception_when_no_at_sign_is_supplied()
			throws Exception {
		Email.of("dasd.com");
	}

	@Test(expected = IllegalArgumentException.class)
	public void should_throw_exception_null_email_is_supplied()
			throws Exception {
		Email.of(null);
	}

	@Test
	public void should_create_email_when_correct_email_is_supplied()
			throws Exception {
		Email email = Email.of(VALID_EMAIL);
		assertThat(email.getEmail()).isEqualTo(VALID_EMAIL);
	}

	@Test
	public void should_return_email_when_calling_toString() throws Exception {
		assertThat(Email.of(VALID_EMAIL).toString()).isEqualTo(VALID_EMAIL);
	}
}

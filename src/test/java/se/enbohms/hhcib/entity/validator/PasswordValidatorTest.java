package se.enbohms.hhcib.entity.validator;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

/**
 * Test client for the {@link PasswordValidator}
 */
public class PasswordValidatorTest {

	private static final String NULL_PWD = null;
	private static final String EMPTY_PWD = "";
	private static final String SHORT_PWD = "abc";
	private static final String LONG_PWD = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
			+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
	private static final String VALID_PWD = "abcd";
	private PasswordValidator validator;

	@Before
	public void setUp() {
		validator = new PasswordValidator();
	}

	@Test
	public void should_return_not_valid_when_password_is_null()
			throws Exception {
		assertThat(validator.isValid(NULL_PWD, null)).isFalse();
	}

	@Test
	public void should_return_not_valid_when_password_is_empty()
			throws Exception {
		assertThat(validator.isValid(EMPTY_PWD, null)).isFalse();
	}

	@Test
	public void should_return_not_valid_when_password_is_less_than_three_characters()
			throws Exception {
		assertThat(validator.isValid(SHORT_PWD, null)).isFalse();
	}

	@Test
	public void should_return_not_valid_when_password_is_more_than_fifty_characters()
			throws Exception {
		assertThat(validator.isValid(LONG_PWD, null)).isFalse();
	}

	@Test
	public void should_return_valid_password() throws Exception {
		assertThat(validator.isValid(VALID_PWD, null)).isTrue();
	}
}

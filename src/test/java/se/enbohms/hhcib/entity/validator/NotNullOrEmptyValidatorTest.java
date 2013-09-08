package se.enbohms.hhcib.entity.validator;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

/**
 * Test client for the {@link NotNullOrEmptyValidator} class
 */
public class NotNullOrEmptyValidatorTest {

	@Test
	public void should_inform_invalid_when_string_is_empty() throws Exception {
		String emptyString = "";
		NotNullOrEmptyValidator validator = new NotNullOrEmptyValidator();
		assertThat(validator.isValid(emptyString, null)).isFalse();
	}

	@Test
	public void should_inform_invalid_when_string_contains_spaces()
			throws Exception {
		String emptyString = "   ";
		NotNullOrEmptyValidator validator = new NotNullOrEmptyValidator();
		assertThat(validator.isValid(emptyString, null)).isFalse();
	}

	@Test
	public void should_inform_invalid_when_string_is_null() throws Exception {
		String nullString = null;
		NotNullOrEmptyValidator validator = new NotNullOrEmptyValidator();
		assertThat(validator.isValid(nullString, null)).isFalse();
	}
	
	@Test
	public void should_inform_valid() throws Exception {
		String string = "jk dd";
		NotNullOrEmptyValidator validator = new NotNullOrEmptyValidator();
		assertThat(validator.isValid(string, null)).isTrue();
	}

}

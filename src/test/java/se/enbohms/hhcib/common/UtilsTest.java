package se.enbohms.hhcib.common;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import se.enbohms.hhcib.entity.Password;

/**
 * Test client for the {@link Utils} class
 */
public class UtilsTest {

	@Test
	public void should_remove_html_from_string() throws Exception {
		// Given
		String htmlString = "<b>This is a string</b>";

		// when
		String result = Utils.removeHtmlFrom(htmlString);

		// then
		assertThat(result).isEqualTo("This is a string");
	}

	@Test
	public void should_remove_html_and_replace_br_with_space_from_string()
			throws Exception {
		// Given
		String htmlString = "<b>This is a string<br /></b>";

		// when
		String result = Utils.removeHtmlFrom(htmlString);

		// then
		assertThat(result).isEqualTo("This is a string ");
	}

	@Test
	public void should_remove_html_and_replace_new_lines_from_string()
			throws Exception {
		// Given
		String htmlString = "<b>This is a string\r\n</b>Containing tags\r\n";

		// when
		String result = Utils.removeHtmlFrom(htmlString);

		// then
		assertThat(result).isEqualTo("This is a string Containing tags ");
	}

	@Test
	public void should_generate_a_valid_password() throws Exception {
		//
		Password pwd = Utils.generatePassword();
		assertThat(pwd).isNotNull();
		assertThat(pwd.getPassword().length()).isGreaterThan(4);
		assertThat(pwd.getPassword().length()).isLessThan(50);
	}
}
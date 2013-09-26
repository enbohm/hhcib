package se.enbohms.hhcib.common;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

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
	public void should_remove_html_and_replace_br_with_space_from_string() throws Exception {
		// Given
		String htmlString = "<b>This is a string<br /></b>";

		// when
		String result = Utils.removeHtmlFrom(htmlString);

		// then
		assertThat(result).isEqualTo("This is a string ");
	}
	
	@Test
	public void should_remove_html_and_replace_r_n_from_string() throws Exception {
		// Given
		String htmlString = "<b>This is a string\r\n</b>";

		// when
		String result = Utils.removeHtmlFrom(htmlString);

		// then
		assertThat(result).isEqualTo("This is a string ");
	}
}
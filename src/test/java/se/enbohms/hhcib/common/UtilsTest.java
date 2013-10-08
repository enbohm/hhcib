package se.enbohms.hhcib.common;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import se.enbohms.hhcib.entity.Password;
import se.enbohms.hhcib.entity.Subject;
import se.enbohms.hhcib.entity.Vote;

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

	@Test
	public void should_sort_list_descending() throws Exception {
		Subject subject1 = new Subject.Builder("1").voter(Vote.of("a", 2d))
				.build();
		Subject subject2 = new Subject.Builder("1").voter(Vote.of("a", 3d))
				.build();
		Subject subject3 = new Subject.Builder("1").build();

		List<Subject> unsortedList = Arrays
				.asList(subject1, subject2, subject3);

		List<Subject> sortedList = Utils.sortDesceding(unsortedList);
		
		assertThat(sortedList.get(0)).isEqualTo(subject2);
		assertThat(sortedList.get(1)).isEqualTo(subject1);
		assertThat(sortedList.get(2)).isEqualTo(subject3);
		
		
		
	}
}
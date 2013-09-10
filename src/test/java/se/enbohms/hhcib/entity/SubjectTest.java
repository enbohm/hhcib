package se.enbohms.hhcib.entity;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

/**
 * Test client for the {@link Subject} class
 */
public class SubjectTest {

	private static final String ID = "id";
	private static final String HEADING = "heading";
	private static final String DESCRIPTION = "desc";
	private static final Double RATING = Double.valueOf(2.0d);
	private static final String USER = "admin";

	@Test
	public void should_create_valid_subject() throws Exception {
		Subject subject = new Subject.Builder(ID).description(DESCRIPTION)
				.heading(HEADING).rating(RATING).category(Category.ECONOMY)
				.createdBy(USER).build();

		assertThat(subject.getId()).isEqualTo(ID);
		assertThat(subject.getDescription()).isEqualTo(DESCRIPTION);
		assertThat(subject.getHeading()).isEqualTo(HEADING);
		assertThat(subject.getRating()).isEqualTo(RATING);
		assertThat(subject.getCategory()).isEqualTo(Category.ECONOMY);
		assertThat(subject.getCreatedBy()).isEqualTo(USER);
	}
}
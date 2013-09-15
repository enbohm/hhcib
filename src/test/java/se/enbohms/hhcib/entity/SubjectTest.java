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
	private static final Double SCORE_1 = Double.valueOf(2.0d);
	private static final Double SCORE_2 = Double.valueOf(4.0d);

	private static final String USER_NAME_1 = "admin";
	private static final String USER_NAME_2 = "admn2";

	@Test
	public void should_create_valid_subject() throws Exception {
		Subject subject = new Subject.Builder(ID).description(DESCRIPTION)
				.heading(HEADING).voter(Vote.of(USER_NAME_1, SCORE_1))
				.voter(Vote.of(USER_NAME_2, SCORE_2))
				.category(Category.ECONOMY).createdBy(USER_NAME_1).build();

		assertThat(subject.getId()).isEqualTo(ID);
		assertThat(subject.getDescription()).isEqualTo(DESCRIPTION);
		assertThat(subject.getHeading()).isEqualTo(HEADING);
		assertThat(subject.getRating()).isEqualTo((SCORE_1+SCORE_2)/2);
		assertThat(subject.getCategory()).isEqualTo(Category.ECONOMY);
		assertThat(subject.getCreatedBy()).isEqualTo(USER_NAME_1);
	}
}
package se.enbohms.hhcib.entity;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

/**
 * Test client fot the {@link Vote} class
 */
public class VoteTest {

	private static final Double SCOREt = 0.5d;
	private static final String USER_NAME = "username";

	@Test(expected = IllegalArgumentException.class)
	public void should_throw_exception_when_username_is_null() throws Exception {
		Vote.of(null, 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void should_throw_exception_when_score_is_zero() throws Exception {
		Vote.of(USER_NAME, 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void should_throw_exception_when_score_is_null() throws Exception {
		Vote.of(USER_NAME, null);
	}
	
	@Test()
	public void should_create_vote_instance() throws Exception {
		Vote vote = Vote.of(USER_NAME, SCOREt);
		assertThat(vote.getScore()).isEqualTo(0.5);
		assertThat(vote.getUserName()).isEqualTo(USER_NAME);
	}
}
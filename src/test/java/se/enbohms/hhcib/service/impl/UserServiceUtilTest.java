package se.enbohms.hhcib.service.impl;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import se.enbohms.hhcib.entity.Email;
import se.enbohms.hhcib.entity.Subject;
import se.enbohms.hhcib.entity.User;
import se.enbohms.hhcib.service.api.UserCreatedEvent;

/**
 * Test client for the {@link UserServiceUtil}
 */
public class UserServiceUtilTest {

	private static final String EMAIL = "test@test.com";
	private static final String USERNAME = "test";
	private static final String ANOTHER_USERNAME = "anotherUserName";
	private static final User NOT_EXISTING_USER = null;
	private UserServiceUtil userServiceUtil;

	@Before
	public void setup() {
		userServiceUtil = new UserServiceUtil();
	}

	@Test
	public void should_inform_that_email_does_not_exist() throws Exception {
		// when
		boolean result = userServiceUtil.existing(Email.of(EMAIL));

		// then
		assertThat(result).isFalse();
	}

	@Test
	public void should_inform_that_email_exist() throws Exception {
		// given
		userServiceUtil.addUserInformation(UserCreatedEvent.of(USERNAME,
				Email.of(EMAIL)));

		// when
		boolean result = userServiceUtil.existing(Email.of(EMAIL));

		// then
		assertThat(result).isTrue();
	}

	@Test
	public void should_inform_when_username_is_not_unique() throws Exception {
		// given
		userServiceUtil.addUserInformation(UserCreatedEvent.of(USERNAME,
				Email.of(EMAIL)));

		// when
		boolean result = userServiceUtil.unique(USERNAME);

		// then
		assertThat(result).isFalse();
	}

	@Test
	public void should_inform_when_username_is_unique() throws Exception {
		// given
		userServiceUtil.addUserInformation(UserCreatedEvent.of(USERNAME,
				Email.of(EMAIL)));

		// when
		boolean result = userServiceUtil.unique(ANOTHER_USERNAME);

		// then
		assertThat(result).isTrue();
	}

	@Test
	public void should_inform_when_existring_user_is_allowed_to_vote()
			throws Exception {
		// given
		Subject subject = new Subject.Builder("12").build();
		User user = User.creteUser("id", USERNAME, null);

		// when
		boolean result = userServiceUtil.canRate(user, subject);

		// then
		assertThat(result).isTrue();
	}

	@Test
	public void should_inform_that_no_user_is_not_allowed_to_vote()
			throws Exception {
		// given
		Subject subject = new Subject.Builder("12").build();

		// when
		boolean result = userServiceUtil.canRate(NOT_EXISTING_USER, subject);

		// then
		assertThat(result).isFalse();
	}

	@Test
	public void should_inform_when_user_is_not_allowed_to_vote()
			throws Exception {
		// given
		Subject subject = new Subject.Builder("12").createdBy(USERNAME).build();
		User user = User.creteUser("id", USERNAME, null);

		// when
		boolean result = userServiceUtil.canRate(user, subject);

		// then
		assertThat(result).isFalse();
	}

	@Test
	public void should_inform_when_user_can_not_modify_subject()
			throws Exception {
		// given
		Subject subject = new Subject.Builder("12").createdBy(USERNAME).build();
		User user = User.creteUser("id", "", null);

		// when
		boolean result = userServiceUtil.canModify(user, subject);

		// then
		assertThat(result).isFalse();
	}

	@Test
	public void should_inform_when_user_can_modify_subject() throws Exception {
		// given
		Subject subject = new Subject.Builder("12").createdBy(USERNAME).build();
		User user = User.creteUser("id", USERNAME, null);

		// when
		boolean result = userServiceUtil.canModify(user, subject);

		// then
		assertThat(result).isTrue();
	}
}

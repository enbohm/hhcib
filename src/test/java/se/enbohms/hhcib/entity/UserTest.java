package se.enbohms.hhcib.entity;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

/**
 * Test client for the {@link User} class
 */
public class UserTest {

	private static final String USER_NAME = "username";
	private static final Email EMAIL = Email.of("email@email.com");

	@Test
	public void should_create_admin_user() throws Exception {
		User adminUser = User.creteAdminUser(USER_NAME, EMAIL);

		assertThat(adminUser.getGroup()).isEqualTo(Group.ADMINISTRATOR);
		assertThat(adminUser.getUserName()).isEqualTo(USER_NAME);
		assertThat(adminUser.getEmail()).isEqualTo(EMAIL);
	}

	@Test
	public void should_create_default_user() throws Exception {
		User user = User.creteUser(USER_NAME, EMAIL);

		assertThat(user.getGroup()).isEqualTo(Group.DEFAULT_USER);
		assertThat(user.getUserName()).isEqualTo(USER_NAME);
		assertThat(user.getEmail()).isEqualTo(EMAIL);

	}
}

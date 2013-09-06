package se.enbohms.hhcib.service.impl;

import static org.fest.assertions.Assertions.assertThat;

import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.Test;

import se.enbohms.hhcib.entity.Email;
import se.enbohms.hhcib.entity.User;

/**
 * Test client for the {@link MongoDBUserService}
 */
public class MongoDBUserServiceTest {

	private MongoDBUserService userService;

	@Before
	public void setUp() throws UnknownHostException {
		userService = new MongoDBUserService();
		MongoDBInitiator dbInitiator = new MongoDBInitiator();
		dbInitiator.initDB();
		userService.setDBInitiator(dbInitiator);
	}

	@Test
	public void should_create_and_delete_a_user() throws Exception {
		User user = null;
		try {
			// when
			user = userService.createUser("test", Email.of("test@test.com"),
					"password");
			// then
			assertThat(user.getId()).isNotEmpty();
			assertThat(user.getId().length()).isGreaterThan(10);
			
		} finally {
			if (user != null) {
				userService.delete(user);
			}
		}
	}
	
	@Test
	public void should_create_login_and_delete_a_user() throws Exception {
		User user = null;
		try {
			// when
			user = userService.createUser("test", Email.of("test@test.com"),
					"password");
			
			User loggedInUser = userService.login("test","password");
			
			// then
			assertThat(loggedInUser).isNotNull();
			assertThat(loggedInUser.getEmail().getEmail()).isEqualTo("test@test.com");
			assertThat(loggedInUser.getId()).isEqualTo(user.getId());
			
		} finally {
			if (user != null) {
				userService.delete(user);
			}
		}
	}
}

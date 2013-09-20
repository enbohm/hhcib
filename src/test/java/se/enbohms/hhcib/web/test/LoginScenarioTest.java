package se.enbohms.hhcib.web.test;

import static org.fest.assertions.Assertions.assertThat;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import se.enbohms.hhcib.web.page.LoginPage;
import se.enbohms.hhcib.web.page.MyPagesPage;
import se.enbohms.hhcib.web.util.Deployments;
import se.enbohms.hhcib.web.util.IntegrationTest;

/**
 * Contains test cases for the login scenarios
 */
@Category(IntegrationTest.class)
@RunWith(Arquillian.class)
public class LoginScenarioTest {

	private static final String USER_NAME = "enbohm";
	private static final String PASSWORD = "enbohm";
	private static final String BAD_PASSWORD = "_1";
	private static final String WELCOME_TEXT = "Välkommen enbohm!";
	private static final String ERROR_MESSAGE = "Inloggning misslyckades, kontrollera användarnamn/lösenord";

	@Deployment(testable = false)
	public static WebArchive createDeployment() {
		return Deployments.createLoginDeployment();
	}

	@Drone
	private WebDriver driver;

	@ArquillianResource
	private URL deploymentUrl;

	@Test
	@InSequence(1)
	public void should_login_successfully() {
		LoginPage loginPage = new LoginPage(driver, deploymentUrl);
		MyPagesPage myPagesPage = (MyPagesPage) loginPage.login(USER_NAME,
				PASSWORD);
		assertThat(myPagesPage.getWelcomeText()).isEqualTo(WELCOME_TEXT);
	}

	@Test
	@InSequence(2)
	public void should_logout_successfully() {
		LoginPage loginPage = new LoginPage(driver, deploymentUrl);
		MyPagesPage myPagesPage = (MyPagesPage) loginPage.login(USER_NAME,
				PASSWORD);

		myPagesPage.logout();
		assertThat(myPagesPage.isLoggedOut()).isTrue();
	}

	@Test
	public void should_show_error_message_with_invalid_credentials() {
		LoginPage myPagesPage = (LoginPage) new LoginPage(driver, deploymentUrl)
				.login(USER_NAME, BAD_PASSWORD);
		assertThat(myPagesPage.getErrorMessage()).isEqualTo(ERROR_MESSAGE);
	}
}
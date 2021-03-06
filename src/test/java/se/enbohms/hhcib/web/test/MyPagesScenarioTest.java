package se.enbohms.hhcib.web.test;

import static org.fest.assertions.Assertions.assertThat;

import java.net.MalformedURLException;
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
import se.enbohms.hhcib.web.page.UpdateEmailPage;
import se.enbohms.hhcib.web.page.UpdatePasswordPage;
import se.enbohms.hhcib.web.util.Deployments;
import se.enbohms.hhcib.web.util.IntegrationTest;

/**
 * Contains integration test cases for the MyPages scenarios such as login,
 * update password, change email address, etc.
 */
@Category(IntegrationTest.class)
@RunWith(Arquillian.class)
public class MyPagesScenarioTest {

	private static final String USER_NAME = "enbohm";
	private static final String PASSWORD = "enbohm";
	private static final String BAD_PASSWORD = "_1";
	private static final String WELCOME_TEXT = "Välkommen enbohm!";
	private static final String ERROR_MESSAGE = "Inloggning misslyckades, kontrollera användarnamn/lösenord";

	private static final String PASSWORD_HAS_BEEN_UPDATED = "Ditt lösenord har uppdaterats";
	private static final String PASSWORD_MUST_BE_FOUR_CHARACTERS = "Lösenord måste var minst 4 tecken långt";
	private static final String PASSWORDS_NOT_EQUAL = "Lösenorden skiljer sig åt";

	private static final String VALID_EMAIL = "test@test.com";
	private static final String INVALID_EMAIL = "test";

	private static final String UPDATE_EMAIL_ERROR = "Ange en giltig E-post adress";
	private static final String EMAIL_UPDATED = "Din e-postadress har uppdaterats";

	@Deployment(testable = false)
	public static WebArchive createDeployment() throws MalformedURLException {
		return Deployments.createMyPagesDeployment();
	}

	@Drone
	private WebDriver driver;

	@ArquillianResource
	private URL deploymentUrl;

	@Test
	@InSequence(1)
	public void should_show_error_message_with_invalid_credentials() {
		LoginPage myPagesPage = (LoginPage) new LoginPage(driver, deploymentUrl)
				.login(USER_NAME, BAD_PASSWORD);
		assertThat(myPagesPage.getErrorMessage()).isEqualTo(ERROR_MESSAGE);
	}

	@Test
	@InSequence(2)
	public void should_login_successfully() {
		LoginPage loginPage = new LoginPage(driver, deploymentUrl);
		MyPagesPage myPagesPage = (MyPagesPage) loginPage.login(USER_NAME,
				PASSWORD);
		assertThat(myPagesPage.getWelcomeText()).isEqualTo(WELCOME_TEXT);
	}

	@Test
	@InSequence(3)
	public void should_not_update_passwords_due_to_short_password()
			throws Exception {
		UpdatePasswordPage updatePwdPage = new MyPagesPage(driver,
				deploymentUrl).clickUpdatePasswordLink();
		updatePwdPage.updatePassword("e", "e");
		assertThat(updatePwdPage.getInfoMessage().trim()).isEqualTo(
				PASSWORD_MUST_BE_FOUR_CHARACTERS);
	}

	@Test
	@InSequence(4)
	public void should_not_update_password_due_to_unequal_passwords()
			throws Exception {
		UpdatePasswordPage updatePwdPage = new MyPagesPage(driver,
				deploymentUrl).clickUpdatePasswordLink();
		updatePwdPage.updatePassword("enbohm1", "enbohm2");
		assertThat(updatePwdPage.getInfoMessage().trim()).isEqualTo(
				PASSWORDS_NOT_EQUAL);
	}

	@Test
	@InSequence(5)
	public void should_update_password() throws Exception {
		UpdatePasswordPage updatePwdPage = new MyPagesPage(driver,
				deploymentUrl).clickUpdatePasswordLink();
		updatePwdPage.updatePassword("enbohm", "enbohm");
		assertThat(updatePwdPage.getInfoMessage()).isEqualTo(
				PASSWORD_HAS_BEEN_UPDATED);
	}

	@Test
	@InSequence(6)
	public void should_not_update_email_due_to_incorrect_email()
			throws Exception {
		MyPagesPage myPagesPage = new MyPagesPage(driver, deploymentUrl);
		UpdateEmailPage updateEmailPage = myPagesPage.clickChangeEmailLink();
		updateEmailPage.updateEmail(INVALID_EMAIL);

		assertThat(updateEmailPage.getInfoMessage()).isEqualTo(
				UPDATE_EMAIL_ERROR);
	}

	@Test
	@InSequence(7)
	public void should_update_email() throws Exception {
		MyPagesPage myPagesPage = new MyPagesPage(driver, deploymentUrl);
		UpdateEmailPage updateEmailPage = myPagesPage.clickChangeEmailLink();
		updateEmailPage.updateEmail(VALID_EMAIL);

		assertThat(updateEmailPage.getInfoMessage().trim()).isEqualTo(
				EMAIL_UPDATED);
	}

	@Test
	@InSequence(8)
	public void should_logout_successfully() {
		MyPagesPage myPagesPage = new MyPagesPage(driver, deploymentUrl);

		myPagesPage.logout();
		assertThat(myPagesPage.isLoggedOut()).isTrue();
	}
}
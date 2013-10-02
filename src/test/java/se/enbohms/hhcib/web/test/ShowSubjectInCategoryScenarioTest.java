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
import se.enbohms.hhcib.web.page.ShowSubjectsInCategoryPage;
import se.enbohms.hhcib.web.util.Deployments;
import se.enbohms.hhcib.web.util.IntegrationTest;

/**
 * Contains integration test cases for the 'Show Subjects' scenario
 * 
 */
@Category(IntegrationTest.class)
@RunWith(Arquillian.class)
public class ShowSubjectInCategoryScenarioTest {

	private static final String USER_NAME = "enbohm";
	private static final String PASSWORD = "enbohm";
	private static final String WELCOME_TEXT = "Välkommen enbohm!";

	@Deployment(testable = false)
	public static WebArchive createDeployment() throws MalformedURLException {
		return Deployments.createShowSubjectsInCategoryDeployment();
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
	public void should_display_add_subject_link_when_user_is_logged_in() {
		ShowSubjectsInCategoryPage showSubjectInCategoryPage = new ShowSubjectsInCategoryPage(
				driver, deploymentUrl, se.enbohms.hhcib.entity.Category.FOOD);
		assertThat(showSubjectInCategoryPage.isAddSubjectLinkVisible())
				.isTrue();
	}

	@Test
	@InSequence(3)
	public void should_logout_successfully() {
		LoginPage loginPage = new LoginPage(driver, deploymentUrl);
		MyPagesPage myPagesPage = (MyPagesPage) loginPage.login(USER_NAME,
				PASSWORD);
		myPagesPage.logout();
		assertThat(myPagesPage.isLoggedOut()).isTrue();
	}

	@Test
	@InSequence(4)
	public void should_display_login_link_when_user_is_not_logged_in()
			throws InterruptedException {
		ShowSubjectsInCategoryPage showSubjectInCategoryPage = new ShowSubjectsInCategoryPage(
				driver, deploymentUrl, se.enbohms.hhcib.entity.Category.FOOD);
		assertThat(showSubjectInCategoryPage.isLoginLinkVisible()).isTrue();
	}
}
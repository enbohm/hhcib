package se.enbohms.hhcib.web.test;

import static org.fest.assertions.Assertions.assertThat;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import se.enbohms.hhcib.web.page.IndexPage;
import se.enbohms.hhcib.web.util.Deployments;
import se.enbohms.hhcib.web.util.IntegrationTest;

/**
 * Contains test cases for the index page scenarios
 */
@Category(IntegrationTest.class)
@RunWith(Arquillian.class)
public class IndexScenarioTest {
	private static final String HEADING_TEXT = "Välkommen till HurSvårtKanDetVara.se";

	@Deployment(testable = false)
	public static WebArchive createDeployment() {
		return Deployments.createIndexPageDeployment();
	}

	@Drone
	private WebDriver driver;

	@ArquillianResource
	private URL deploymentUrl;

	@Test
	public void should_contain_header_text() {
		IndexPage indexPage = new IndexPage(driver, deploymentUrl);
		assertThat(indexPage.getHeader()).isEqualTo(HEADING_TEXT);
	}

	@Test
	public void should_contain_menu_area() {
		IndexPage indexPage = new IndexPage(driver, deploymentUrl);
		assertThat(indexPage.hasMenuArea()).isTrue();
	}

	@Test
	public void should_contain_content_area() {
		IndexPage indexPage = new IndexPage(driver, deploymentUrl);
		assertThat(indexPage.hasContentArea()).isTrue();
	}
	
	@Test
	public void should_contain_search_result_after_successful_search() throws InterruptedException {
		IndexPage indexPage = new IndexPage(driver, deploymentUrl);
		indexPage.searchFor("Hummer");
		assertThat(indexPage.searchDoneInfoTextPresent()).isTrue();
	}
}
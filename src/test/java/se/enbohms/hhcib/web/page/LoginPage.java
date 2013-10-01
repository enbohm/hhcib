package se.enbohms.hhcib.web.page;

import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.id;

import java.net.URL;

import org.openqa.selenium.WebDriver;

public class LoginPage extends AbstractPage {

	public LoginPage(WebDriver driver, URL contextPath) {
		super(driver, contextPath);
		driver.get(contextPath + "login/login.xhtml");
	}

	/**
	 * 
	 * @param userName
	 * @param password
	 * @return {@link MyPagesPage} if user login is successful, otherwise
	 *         {@link LoginPage}
	 */
	public AbstractPage login(String userName, String password) {
		driver.findElement(id("hhcib-form:username")).sendKeys(userName);
		driver.findElement(id("hhcib-form:password")).sendKeys(password);
		driver.findElement(id("hhcib-form:loginButton")).click();

		if (driver.getCurrentUrl().endsWith("my_pages.xhtml")) {
			return new MyPagesPage(driver, contextPath);
		} else {
			return this;
		}
	}

	public String getErrorMessage() {
		return driver.findElement(cssSelector("#login-message li")).getText();
	}
}
package se.enbohms.hhcib.web.page;

import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.cssSelector;

import java.net.URL;

import org.openqa.selenium.WebDriver;

public class MyPagesPage extends AbstractPage {

	public MyPagesPage(WebDriver driver, URL contextPath) {
		super(driver, contextPath);
		driver.get(contextPath + "secured/my_pages.xhtml");
	}

	public String getWelcomeText() {
		return driver.findElement(id("welcome-user")).getText();
	}

	public void logout() {
		driver.findElement(id("hhcib-form:logout-button")).click();
	}

	public boolean isLoggedOut() {
		return driver.getCurrentUrl().endsWith("login.xhtml");
	}

	public UpdatePasswordPage clickUpdatePasswordLink() {
		driver.findElement(cssSelector("a[data-change-password-link]")).click();
		UpdatePasswordPage page = new UpdatePasswordPage(driver, contextPath);
		return page;
	}
	
	public UpdateEmailPage clickChangeEmailLink() {
		driver.findElement(cssSelector("a[data-change-email-link]")).click();
		UpdateEmailPage page = new UpdateEmailPage(driver, contextPath);
		return page;
	}
}
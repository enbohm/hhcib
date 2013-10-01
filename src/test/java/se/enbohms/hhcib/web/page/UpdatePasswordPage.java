package se.enbohms.hhcib.web.page;

import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.id;

import java.net.URL;

import org.openqa.selenium.WebDriver;

public class UpdatePasswordPage extends AbstractPage {

	public UpdatePasswordPage(WebDriver driver, URL contextPath) {
		super(driver, contextPath);
	}

	public void updatePassword(String password, String repeatedPassword) {
		driver.findElement(id("hhcib-form:password")).sendKeys(password);
		driver.findElement(id("hhcib-form:repeated-password")).sendKeys(repeatedPassword);
		driver.findElement(id("hhcib-form:update-password")).click();
	}

	public String getInfoMessage() {
		return driver.findElement(cssSelector("#update-password-message li")).getText();
	}
}

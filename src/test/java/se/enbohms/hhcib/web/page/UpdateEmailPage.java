package se.enbohms.hhcib.web.page;

import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.id;

import java.net.URL;

import org.openqa.selenium.WebDriver;

public class UpdateEmailPage extends AbstractPage {

	public UpdateEmailPage(WebDriver driver, URL contextPath) {
		super(driver, contextPath);
	}

	public void updateEmail(String newEmail) {

		driver.findElement(id("hhcib-form:email-input")).clear();
		driver.findElement(id("hhcib-form:email-input")).sendKeys(newEmail);
		driver.findElement(id("hhcib-form:update-email")).click();
	}

	public String getInfoMessage() {
		return driver.findElement(cssSelector("#update-email-message li"))
				.getText();
	}
}
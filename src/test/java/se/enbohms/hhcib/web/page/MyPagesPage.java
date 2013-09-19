package se.enbohms.hhcib.web.page;

import static org.openqa.selenium.By.id;

import java.net.URL;

import org.openqa.selenium.WebDriver;

public class MyPagesPage extends AbstractPage {

	public MyPagesPage(WebDriver driver, URL contextPath) {
		super(driver, contextPath);
	}

	public String getWelcomeText() {
		return driver.findElement(id("welcome-user")).getText();
	}

}
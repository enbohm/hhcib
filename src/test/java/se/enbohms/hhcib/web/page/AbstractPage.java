package se.enbohms.hhcib.web.page;

import java.net.URL;

import org.openqa.selenium.WebDriver;

public abstract class AbstractPage {

	final WebDriver driver;
	final URL contextPath;

	public AbstractPage(WebDriver driver, URL contextPath) {
		this.driver = driver;
		this.contextPath = contextPath;
	}
}

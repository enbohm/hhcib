package se.enbohms.hhcib.web.page;

import static org.openqa.selenium.By.cssSelector;

import java.net.URL;

import org.openqa.selenium.WebDriver;

public class IndexPage extends AbstractPage {

	public IndexPage(WebDriver driver, URL contextPath) {
		super(driver, contextPath);
		driver.get(contextPath + "index.xhtml");
	}

	public String getHeader() {
		return driver.findElement(cssSelector("#header h3")).getText();
	}

	public boolean hasMenuArea() {
		return driver.findElement(cssSelector("#left")) != null;
	}

	public boolean hasContentArea() {
		return driver.findElement(cssSelector("#content")) != null;
	}
}
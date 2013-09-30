package se.enbohms.hhcib.web.page;

import static org.openqa.selenium.By.cssSelector;

import java.net.URL;

import org.openqa.selenium.WebDriver;

public class IndexPage extends AbstractPage {

	private static final String SEARCH_DONE_INFO_TEXT = "Sökningen är klar. Du kan fortsätta skriva om du vill begränsa sökningen.";

	public IndexPage(WebDriver driver, URL contextPath) {
		super(driver, contextPath);
		driver.get(contextPath + "index.xhtml");
	}

	public String getHeader() {
		return driver.findElement(cssSelector("#header h3")).getText();
	}

	public void searchFor(String query) {
		driver.findElement(
				org.openqa.selenium.By
						.cssSelector("#search-form\\:search-input")).sendKeys(
				query);
		try {
			Thread.sleep(2500);//let the AJAX search complete
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean searchDoneInfoTextPresent() {
		return SEARCH_DONE_INFO_TEXT.equals(driver.findElement(
				org.openqa.selenium.By.cssSelector("#search-form\\:info"))
				.getText());
	}

	public boolean hasMenuArea() {
		return driver.findElement(cssSelector("#left")) != null;
	}

	public boolean hasContentArea() {
		return driver.findElement(cssSelector("#content")) != null;
	}
}
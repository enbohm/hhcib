package se.enbohms.hhcib.web.page;

import static org.openqa.selenium.By.cssSelector;

import java.net.URL;

import org.openqa.selenium.WebDriver;

import se.enbohms.hhcib.entity.Category;

public class ShowSubjectsInCategoryPage extends AbstractPage {

	private static final String SHOW_SUBJECTS_IN_CATEGORY_BASE_URL = "categories/subjects_in_category.xhtml?category=";

	public ShowSubjectsInCategoryPage(WebDriver driver, URL contextPath,
			Category category) {
		super(driver, contextPath);
		driver.get(contextPath + SHOW_SUBJECTS_IN_CATEGORY_BASE_URL + category);
	}

	public boolean isAddSubjectLinkVisible() {
		return driver
				.findElement(cssSelector("a[data-add-subject=add-subject-link]")) != null;
	}
	
	public boolean isLoginLinkVisible() {
		return driver
				.findElement(cssSelector("a[data-add-subject=login-link]")) != null;
	}
}
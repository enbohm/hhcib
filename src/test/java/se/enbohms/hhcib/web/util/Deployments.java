package se.enbohms.hhcib.web.util;

import java.io.File;

import javax.faces.event.AjaxBehaviorEvent;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import se.enbohms.hhcib.entity.Subject;
import se.enbohms.hhcib.facade.CategoryFacade;
import se.enbohms.hhcib.facade.SearchFacade;
import se.enbohms.hhcib.facade.mypages.LoggedInUserFacade;
import se.enbohms.hhcib.facade.mypages.LoginFacade;
import se.enbohms.hhcib.facade.mypages.UpdatePasswordFacade;
import se.enbohms.hhcib.service.api.SearchService;
import se.enbohms.hhcib.service.impl.MongoDBInitiator;
import se.enbohms.hhcib.service.impl.MongoSubjectCrudService;
import se.enbohms.hhcib.service.impl.MongoUserService;
import se.enbohms.hhcib.service.impl.UserServiceUtil;

/**
 * Contains static util method for creating various arquillian/selenium
 * deployments
 * 
 */
public final class Deployments {

	private static final String WEBAPP_SRC = "src/main/webapp";

	private Deployments() {
		// Suppresses default constructor, ensuring non-instantiability.
	}

	public static WebArchive createIndexPageDeployment() {
		WebArchive war = ShrinkWrap
				.create(WebArchive.class, "index.war")
				.addClasses(SearchFacade.class, Subject.class,
						MongoSubjectCrudService.class, AjaxBehaviorEvent.class,
						SearchService.class, MongoDBInitiator.class)
				.addAsWebResource(new File(WEBAPP_SRC, "hhcib_template.xhtml"))
				.addAsWebResource(new File(WEBAPP_SRC, "menu_template.xhtml"))
				.addAsWebResource(new File(WEBAPP_SRC, "index.xhtml"))
				.addAsWebResource(
						new File(WEBAPP_SRC,
								"search/search_result_template.xhtml"),
						"search/search_result_template.xhtml");
		
		addDefaultWebInfResources(war);
		addJavascriptResources(war);
		return war;
	}

	public static WebArchive createLoginDeployment() {
		WebArchive war = ShrinkWrap
				.create(WebArchive.class, "login.war")
				.addClasses(addLoginRequiredClasses())
				.addAsWebResource(new File(WEBAPP_SRC, "hhcib_template.xhtml"))
				.addAsWebResource(new File(WEBAPP_SRC, "menu_template.xhtml"))
				.addAsWebResource(new File(WEBAPP_SRC, "login/login.xhtml"),
						"login/login.xhtml")
				.addAsWebResource(
						new File(WEBAPP_SRC, "secured/my_pages.xhtml"),
						"secured/my_pages.xhtml")
				.addAsWebResource(
						new File(WEBAPP_SRC, "secured/update_password.xhtml"),
						"secured/update_password.xhtml")
				.addAsWebResource(new File(WEBAPP_SRC, "index.xhtml"));
		addDefaultWebInfResources(war);
		addJavascriptResources(war);
		return war;
	}

	public static WebArchive createShowSubjectsInCategoryDeployment() {
		WebArchive war = ShrinkWrap
				.create(WebArchive.class, "createSubject.war")
				.addClasses(addLoginRequiredClasses())
				.addClasses(CategoryFacade.class, UserServiceUtil.class,
						Subject.class)
				.addAsWebResource(new File(WEBAPP_SRC, "hhcib_template.xhtml"))
				.addAsWebResource(new File(WEBAPP_SRC, "menu_template.xhtml"))
				.addAsWebResource(new File(WEBAPP_SRC, "login/login.xhtml"),
						"login/login.xhtml")
				.addAsWebResource(
						new File(WEBAPP_SRC,
								"categories/subjects_in_category.xhtml"),
						"categories/subjects_in_category.xhtml")
				.addAsWebResource(
						new File(WEBAPP_SRC,
								"subject/show_subjects_in_category.xhtml"),
						"subject/show_subjects_in_category.xhtml")
				.addAsWebResource(
						new File(WEBAPP_SRC, "secured/my_pages.xhtml"),
						"secured/my_pages.xhtml")
				.addAsWebResource(new File(WEBAPP_SRC, "index.xhtml"));
		addDefaultWebInfResources(war);
		addJavascriptResources(war);
		return war;
	}

	private static Class<?>[] addLoginRequiredClasses() {
		return new Class[] { LoginFacade.class, MongoUserService.class,
				MongoDBInitiator.class, LoggedInUserFacade.class,
				MongoSubjectCrudService.class, UpdatePasswordFacade.class };
	}

	private static WebArchive addDefaultWebInfResources(WebArchive war) {
		return war
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource(new File(WEBAPP_SRC, "WEB-INF/web.xml"))
				.addAsWebInfResource(
						new File(WEBAPP_SRC, "WEB-INF/glassfish-web.xml"))
				.addAsWebInfResource(
						new StringAsset("<faces-config version=\"2.0\"/>"),
						"faces-config.xml");
	}

	private static WebArchive addJavascriptResources(WebArchive war) {
		return war
				.addAsWebResource(
						new File(WEBAPP_SRC,
								"resources/javascript/jquery-v1.10.2.min.js"),
						"resources/javascript/jquery-v1.10.2.min.js")
				.addAsWebResource(
						new File(WEBAPP_SRC,
								"resources/javascript/tinymce.4.0.6.min.js"),
						"resources/javascript/tinymce.4.0.6.min.js")
				.addAsWebResource(
						new File(WEBAPP_SRC, "resources/javascript/tinymce.js"),
						"resources/javascript/tinymce.js")
				.addAsWebResource(
						new File(WEBAPP_SRC, "resources/javascript/slider.js"),
						"resources/javascript/slider.js")
				.addAsWebResource(
						new File(WEBAPP_SRC,
								"resources/javascript/websocket.js"),
						"resources/javascript/websocket.js")
				.addAsWebResource(
						new File(WEBAPP_SRC, "resources/javascript/menu.js"),
						"resources/javascript/menu.js")
				.addAsWebResource(
						new File(WEBAPP_SRC,
								"resources/javascript/unoSlider.min.js"),
						"resources/javascript/unoSlider.min.js");
	}
}
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
 * Contains static util method for creating various arquillian deployments
 * 
 */
public final class Deployments {

	private static final String WEBAPP_SRC = "src/main/webapp";

	private Deployments() {
		// Suppresses default constructor, ensuring non-instantiability.
	}

	public static WebArchive createIndexPageDeployment() {
		return ShrinkWrap
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
						"search/search_result_template.xhtml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource(new File(WEBAPP_SRC, "WEB-INF/web.xml"))
				.addAsWebInfResource(
						new File(WEBAPP_SRC, "WEB-INF/glassfish-web.xml"))
				.addAsWebInfResource(
						new StringAsset("<faces-config version=\"2.0\"/>"),
						"faces-config.xml");
	}

	public static WebArchive createLoginDeployment() {
		return ShrinkWrap
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
				.addAsWebResource(new File(WEBAPP_SRC, "index.xhtml"))
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource(new File(WEBAPP_SRC, "WEB-INF/web.xml"))
				.addAsWebInfResource(
						new File(WEBAPP_SRC, "WEB-INF/glassfish-web.xml"))
				.addAsWebInfResource(
						new StringAsset("<faces-config version=\"2.0\"/>"),
						"faces-config.xml");
	}

	public static WebArchive createShowSubjectsInCategoryDeployment() {
		return ShrinkWrap
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
				.addAsWebResource(new File(WEBAPP_SRC, "index.xhtml"))
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource(new File(WEBAPP_SRC, "WEB-INF/web.xml"))
				.addAsWebInfResource(
						new File(WEBAPP_SRC, "WEB-INF/glassfish-web.xml"))
				.addAsWebInfResource(
						new StringAsset("<faces-config version=\"2.0\"/>"),
						"faces-config.xml");
	}

	private static Class<?>[] addLoginRequiredClasses() {
		return new Class[] { LoginFacade.class, MongoUserService.class,
				MongoDBInitiator.class, LoggedInUserFacade.class,
				MongoSubjectCrudService.class, UpdatePasswordFacade.class };
	}
}

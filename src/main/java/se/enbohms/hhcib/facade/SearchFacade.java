package se.enbohms.hhcib.facade;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import se.enbohms.hhcib.entity.Subject;
import se.enbohms.hhcib.service.api.SearchService;

/**
 * JSF facade which handles client interaction when new subjects are created
 * 
 */
@Named
@RequestScoped
public class SearchFacade {

	private final static String INFO_TEXT = "Sökningen är klar. Du kan fortsätta skriva om du vill begränsa sökningen.";
	private String query;
	private List<Subject> searchResult = new ArrayList<>();
	private String info;

	@Inject
	private SearchService searchService;

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public List<Subject> getSearchResult() {
		return searchResult;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	/**
	 * Ajax (JSF) callback method. Handles searching without full page submit
	 * 
	 * @param actionEvent
	 */
	public void search(AjaxBehaviorEvent actionEvent) {
		setInfo(null);
		if (query.length() < 3)
			return;
		searchResult.addAll(searchService.search(query));
		setInfo(INFO_TEXT);
	}
}
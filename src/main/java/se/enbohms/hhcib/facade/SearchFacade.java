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

	private String query;
	private List<Subject> searchResult = new ArrayList<>();

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

	/**
	 * Ajax (JSF) callback method. Handles voting without full page sumbit
	 * 
	 * @param actionEvent
	 */
	public void search(AjaxBehaviorEvent actionEvent) {
		if (query.length() < 3)
			return;
		List<Subject> subjects = searchService.search(query);
		searchResult.addAll(subjects);
	}
}
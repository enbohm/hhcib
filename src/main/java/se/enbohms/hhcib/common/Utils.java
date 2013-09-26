package se.enbohms.hhcib.common;

/**
 * Contains various util methods
 */
public final class Utils {

	private Utils() {
		// Suppresses default constructor, ensuring non-instantiability.
	}

	public static final String removeHtmlFrom(String htmlString) {
		return htmlString.replaceAll("<br/>|<br />", " ").replaceAll("\\<.*?\\>", "").replace("\r\n", " ");
	}
}

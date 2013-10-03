package se.enbohms.hhcib.common;

import java.security.SecureRandom;
import java.util.Random;

import se.enbohms.hhcib.entity.Password;

/**
 * Contains various util methods
 */
public final class Utils {

	private static final Random RANDOM = new SecureRandom();
	private static final String LETTERS = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ2345678";
	private static final int PASSWORD_LENGTH = 8;

	private Utils() {
		// Suppresses default constructor, ensuring non-instantiability.
	}

	/**
	 * 
	 * @param htmlString
	 * @return a string where all html-tags are removed
	 */
	public static final String removeHtmlFrom(String htmlString) {
		return htmlString.replaceAll("<br/>|<br />", " ")
				.replaceAll("\\<.*?\\>", "").replace("\r\n", " ");
	}

	/**
	 * Generates a {@link Password}
	 * 
	 * @return a 8 letters generated password
	 */
	public static Password generatePassword() {
		StringBuilder pwdBuilder = new StringBuilder();

		for (int i = 0; i < PASSWORD_LENGTH; i++) {
			int index = (int) (RANDOM.nextDouble() * LETTERS.length());
			pwdBuilder.append(LETTERS.substring(index, index + 1));
		}

		return Password.of(pwdBuilder.toString());
	}
}

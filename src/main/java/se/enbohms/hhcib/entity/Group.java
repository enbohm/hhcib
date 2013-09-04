package se.enbohms.hhcib.entity;

/**
 * Represents the different user groups
 * 
 */
public enum Group {

	/**
	 * Represents administrators with full privileges
	 */
	ADMINISTRATOR,

	/**
	 * Represents an ordinary user which can vote, delete own content and create
	 * new content.
	 */
	DEFAULT_USER;
}

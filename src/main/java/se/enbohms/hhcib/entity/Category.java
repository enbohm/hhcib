package se.enbohms.hhcib.entity;

/**
 * Represents the various categories
 * 
 */
public enum Category {

	ECONOMY, FOOD, HOUSE_AND_GARDEN;

	/**
	 * 
	 * @return a random category
	 */
	public static Category getRandom() {
		return values()[(int) (Math.random() * values().length)];
	}
}

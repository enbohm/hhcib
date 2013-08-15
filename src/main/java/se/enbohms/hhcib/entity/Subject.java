package se.enbohms.hhcib.entity;


/**
 * Represents an actual subject with its corresponding 'How-to'-description
 * 
 * 
 */
public class Subject {
	private String id;
	private String heading;
	private String description;

	public static final String ID = "_id";
	public static final String HEADING = "heading";
	public static final String DESCRIPTION = "heading";

	private Subject(String id, String heading, String description) {
		this.id = id;
		this.heading = heading;
		this.description = description;
	}

	public static Subject of(String id, String heading, String description) {
		return new Subject(id, heading, description);
	}

	public String getId() {
		return id;
	}

	public String getHeading() {
		return heading;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "Subject [heading=" + heading + ", description=" + description
				+ "]";
	}
}
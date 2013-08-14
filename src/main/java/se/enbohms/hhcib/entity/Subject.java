package se.enbohms.hhcib.entity;

import javax.persistence.Entity;

/**
 * Represents an actual subject with its corresponding 'How-to'-description
 * 
 * 
 */
@Entity
public class Subject {
	private int id;
	private String heading;
	private String description;

	private Subject(int id, String heading, String description) {
		this.id = id;
		this.heading = heading;
		this.description = description;
	}

	public static Subject of(int id, String heading, String description) {
		return new Subject(id, heading, description);
	}

	public int getId() {
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

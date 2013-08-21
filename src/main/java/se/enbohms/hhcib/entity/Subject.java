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
	private Double rating = new Double(0d);

	public static final String ID = "_id";
	public static final String HEADING = "heading";
	public static final String DESCRIPTION = "description";
	public static final String RATING = "rating";
	

	private Subject(String id, String heading, String description, Double rating) {
		this.id = id;
		this.heading = heading;
		this.description = description;
		this.rating = rating;

	}

	public static Subject of(String id, String heading, String description,
			Double rating) {
		return new Subject(id, heading, description, rating);
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

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Subject [id=" + id + ", heading=" + heading + ", description="
				+ description + ", rating=" + rating + "]";
	}
}
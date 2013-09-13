package se.enbohms.hhcib.entity;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Size;

import se.enbohms.hhcib.entity.validator.NotNullOrEmpty;

/**
 * Represents an actual subject with its corresponding 'How-to'-description
 * 
 * 
 */
public class Subject {

	public static final String ID = "_id";
	public static final String HEADING = "heading";
	public static final String DESCRIPTION = "description";
	public static final String RATING = "rating";
	public static final String CATEGORY = "category";
	public static final String CREATED_BY = "created_by";

	private String id;
	private String heading;

	@NotNullOrEmpty(message = "Beskrivning kan inte vara tom")
	@Size(min = 10)
	private String description;

	private Double rating = new Double(0d);
	private Set<User> voters = new HashSet<>();
	private Category category;

	private String createdBy;

	public Category getCategory() {
		return category;
	}

	private Subject(String id, String heading, String description,
			Double rating, Category category) {
		this.id = id;
		this.heading = heading;
		this.description = description;
		this.rating = rating;
		this.category = category;
	}

	private Subject() {
	}

	public static class Builder {
		private Subject subject;

		public Builder(String id) {
			subject = new Subject();
			subject.id = id;
		}

		public Builder heading(String heading) {
			subject.heading = heading;
			return this;
		}

		public Builder description(String description) {
			subject.description = description;
			return this;
		}

		public Builder rating(Double rating) {
			subject.rating = rating;
			return this;
		}

		public Builder category(Category category) {
			subject.category = category;
			return this;
		}

		public Builder createdBy(String userName) {
			subject.createdBy = userName;
			return this;
		}

		/**
		 * Build the new {@link Subject} object
		 * 
		 * @return a new instance of {@link Subject}
		 */
		public Subject build() {
			return subject;
		}
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

	public String getCreatedBy() {
		return createdBy;
	}

	public Integer getNumberOfVoters() {
		return voters.size();
	}

	@Override
	public String toString() {
		return "Subject [id=" + id + ", heading=" + heading + ", description="
				+ description + ", rating=" + rating + ", category=" + category
				+ ", createdBy=" + createdBy + "]";
	}
}
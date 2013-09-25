package se.enbohms.hhcib.entity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.Size;

import se.enbohms.hhcib.entity.validator.NotNullOrEmpty;

/**
 * Represents an actual subject with its corresponding 'How-to'-description
 * 
 */
public class Subject {

	public static final String ID = "_id";
	public static final String HEADING = "heading";
	public static final String DESCRIPTION = "description";
	public static final String DESCRIPTION_NO_HTML = "descriptionWithoutHtml";
	public static final String RATING = "rating";
	public static final String CATEGORY = "category";
	public static final String CREATED_BY = "created_by";

	private String id;
	private String heading;

	@NotNullOrEmpty(message = "Beskrivning kan inte vara tom")
	@Size(min = 10)
	private String description;

	private Map<String, Double> voters = new HashMap<>();

	private Category category;
	private String createdBy;

	private Subject() {
		// user builder to create instances of this class
	}

	public Category getCategory() {
		return category;
	}

	/**
	 * Builder which created instances of {@link Subject}
	 */
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

		public Builder voter(Vote vote) {
			subject.voters.put(vote.getUserName(), vote.getScore());
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
	
	/**
	 * Return the rating score of this subject.
	 * <p>
	 * The rating score is calculated as the average value of the votes a
	 * subject has
	 * 
	 * @return the rating score of this subject or 0 if no votes exists for this
	 *         object
	 */
	public Double getRating() {
		double totalScore = 0d;
		if (voters.isEmpty()) {
			return 0d;
		}

		totalScore = calculateTotalScore(totalScore);

		return averageScore(totalScore);
	}

	private double averageScore(double totalScore) {
		return totalScore / voters.size();
	}

	private double calculateTotalScore(double totalScore) {
		for (Map.Entry<String, Double> entry : voters.entrySet()) {
			totalScore += entry.getValue();
		}
		return totalScore;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void addVote(Vote vote) {
		voters.put(vote.getUserName(), vote.getScore());
	}

	/**
	 * 
	 * @return an unmodifiable set of the voters
	 */
	public Map<String, Double> getVoters() {
		return Collections.unmodifiableMap(voters);
	}

	public Integer getNumberOfVoters() {
		return voters.size();
	}

	@Override
	public String toString() {
		return "Subject [id=" + id + ", heading=" + heading + ", description="
				+ description + ", voters=" + voters + ", category=" + category
				+ ", createdBy=" + createdBy + "]";
	}
}
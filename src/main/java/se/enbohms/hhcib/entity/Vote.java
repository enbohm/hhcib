package se.enbohms.hhcib.entity;

/**
 * Represents a vote for a specific subject
 */
public class Vote {

	public static final String USER_NAME = "username";
	public static final String SCIRE = "score";
	
	
	private String userName;
	private Double score;

	private Vote(String userName, Double score) {
		this.userName = userName;
		this.score = score;
	}

	/**
	 * Factory method which created new instanced of {@link Vote}
	 * 
	 * @param userName
	 * @param score
	 * @return a new instance of this class
	 */
	public static Vote of(String userName, Double score) {
		if (userName == null || score == null || !(score > 0d)) {
			throw new IllegalArgumentException(
					"Arguments must not be null or less than 0");
		}
		return new Vote(userName, score);
	}

	public String getUserName() {
		return userName;
	}

	public Double getScore() {
		return score;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vote other = (Vote) obj;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vote [userName=" + userName + ", score=" + score + "]";
	}
}
package hr.fer.oprpp1.hw04.db;

public class StudentRecord {
	
	private String jmbag;
	private String lastName;
	private String firstName;
	private String finalGrade;
	
	public StudentRecord(String jmbag, String lastName, String firstName, String finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * @return jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * @param jmbag jmbag to set
	 */
	public void setJmbag(String jmbag) {
		this.jmbag = jmbag;
	}
	
	/**
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return finalGrade
	 */
	public String getFinalGrade() {
		return finalGrade;
	}

	/**
	 * @param finalGrade finalGrade to set
	 */
	public void setFinalGrade(String finalGrade) {
		this.finalGrade = finalGrade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
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
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}
	
	

	
	
	
	
	
	
	
}

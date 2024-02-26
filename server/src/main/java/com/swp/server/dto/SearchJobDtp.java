package com.swp.server.dto;

public class SearchJobDtp {

	private String text;
	private int category;
	private int branch;
	private String career_level;
	private int experience;
	private String salary;
	private String qualification;

	public SearchJobDtp(String text, int category, int branch, String career_level, int experience, String salary,
			String qualification) {
		super();
		this.text = text;
		this.category = category;
		this.branch = branch;
		this.career_level = career_level;
		this.experience = experience;
		this.salary = salary;
		this.qualification = qualification;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getBranch() {
		return branch;
	}

	public void setBranch(int branch) {
		this.branch = branch;
	}

	public String getCareer_level() {
		return career_level;
	}

	public void setCareer_level(String career_level) {
		this.career_level = career_level;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	@Override
	public String toString() {
		return "SearchJobDtp [text=" + text + ", category=" + category + ", branch=" + branch + ", career_level="
				+ career_level + ", experience=" + experience + ", salary=" + salary + ", qualification="
				+ qualification + "]";
	}

}

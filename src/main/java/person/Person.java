package person;

import java.util.UUID;

public class Person {
	public String firstName;
	public String lastName;
	public String notes;
	public String servingUrl;
	private String subjectId;
	
	public Person() {}

	public Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.servingUrl = null;
		this.notes = null;
		this.subjectId = makeSubjectId();
	}
	
	public Person(String firstName, String lastName, String servingUrl) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.servingUrl = servingUrl;
		this.notes = null;
		this.subjectId = makeSubjectId();
	}
	
	public String getSubjectId() {
		return subjectId;
	}
	
	private String makeSubjectId() {
		return UUID.randomUUID().toString();
	}
}

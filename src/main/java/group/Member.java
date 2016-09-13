package group;

import java.util.Date;
import java.util.UUID;

public class Member {
	public static String firstName;
	public static String lastName;
	public String notes;
	public String servingUrl;
	public Date timeLastSeen;
	private String subjectId;
	
	public Member() {}

	public Member(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.servingUrl = null;
		this.notes = null;
		this.subjectId = makeSubjectId();
		this.timeLastSeen = new Date();
	}
	
	public Member(String firstName, String lastName, String servingUrl) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.servingUrl = servingUrl;
		this.notes = null;
		this.subjectId = makeSubjectId();
		this.timeLastSeen = new Date();
	}
	
	
	public static String getFullName() {
		return firstName + " " + lastName;
	}
	public static String getFirst()
	{
		return firstName;
	}
	public static String getLast()
	{
		return lastName;
	}
	
	public String getSubjectId() {
		return subjectId;
	}
	
	private String makeSubjectId() {
		return UUID.randomUUID().toString();
	}
}

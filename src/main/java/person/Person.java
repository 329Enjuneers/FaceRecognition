package person;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Person {
	// TODO add attributes and functions that we need
	@Id public long id;
	private String name;

	public Person(String name) {
		this.name = name;
	}
}

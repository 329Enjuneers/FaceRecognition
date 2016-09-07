package group;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import person.Person;

@Entity
public class Group {
	@Id private Long id;
	@Index private String ownerEmail;
	@Index public String name;
	
	public ArrayList<Person> children;
	
	public Group() {}
	
	public Group(String name, String ownerEmail) {
		this.name = name;
		this.ownerEmail = ownerEmail;
		this.children = new ArrayList<Person>();
	}
	
	public static Group getOrInsert(String name, String ownerEmail) {
		Group group = ofy().load().type(Group.class).filter("ownerEmail", ownerEmail).filter("name", name).first().now();
		if (group == null) {
			System.out.println("Created new group!");
			group = new Group(name, ownerEmail);
			ofy().save().entity(group).now();
		}
		else {
			System.out.println("Fetched old user!");
		}
		return group;
	}
	
	public static Iterable<Group> fetchByUser(String ownerEmail) {
		return ofy().load().type(Group.class).filter("ownerEmail", ownerEmail).iterable();
	}
}

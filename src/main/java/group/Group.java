package group;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import person.Person;
import user.User;

@Entity
public class Group {
	@Id private Long id;
	@Index public String ownerEmail;
	@Index public String name;
	
	public ArrayList<Person> children;
	
	public Group() {}
	
	public Group(String name, String ownerEmail) {
		this.name = name;
		this.ownerEmail = ownerEmail;
		this.children = new ArrayList<Person>();
	}
	
	public static Iterable<Group> fetchByUser(String ownerEmail) {
		return ofy().load().type(Group.class).filter("ownerEmail", ownerEmail).iterable();
	}
	
	public static Group getOrInsert(String name, String ownerEmail) {
		User user = User.get(ownerEmail);
		if (user == null) {
			return null;
		}
		
		Group group = Group.get(name, ownerEmail);
		if (group == null) {
			group = new Group(name, ownerEmail);
			group.save();
		}
		else {
			System.out.println("Fetched existing group!");
		}
		return group;
	}
	
	public static Group get(String name, String ownerEmail) {
		return ofy().load().type(Group.class).filter("ownerEmail", ownerEmail).filter("name", name).first().now();
	}
	
	public void save() {
		ofy().save().entity(this).now();
	}
}

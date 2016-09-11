package user;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class User{
	@Id private Long id;
	@Index public String email;
	public String nickname;

	public User() {
		this.email = null;
		this.nickname = null;
	}

	public User(String email) {
		this.email = email;
		this.nickname = null;
	}
	public User(String email, String nickname) {
		this.email = email;
		this.nickname = nickname;
	}

	public static User getCurrentUser() {
		com.google.appengine.api.users.User googleUser = getCurrentGoogleUser();
		if (googleUser == null) {
			return null;
		}

		User user = getOrInsert(googleUser.getEmail());
		if (user.nickname == null) {
			user.nickname = googleUser.getNickname();
			ofy().save().entity(user).now();
		}

		return user;
	}

	public static User getOrInsert(String email) {
		User user = ofy().load().type(User.class).filter("email", email).first().now();
		if (user == null) {
			user = new User(email);
			ofy().save().entity(user).now();
		}
		return user;
	}

	public static User get(String email) {
		return ofy().load().type(User.class).filter("email", email).first().now();
	}

	private static com.google.appengine.api.users.User getCurrentGoogleUser() {
		UserService userService = UserServiceFactory.getUserService();
		return userService.getCurrentUser();
	}
}

package pages;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import group.Group;
import pages.html_builder.HTMLBuilder;
import user.User;

public class Page {
	protected HTMLBuilder htmlBuilder;
	protected User user;
	protected Group group;
	protected String baseUrl;
	
	public Page() {
		htmlBuilder = new HTMLBuilder(baseUrl);
		htmlBuilder.includeAppHeader = true;
		user = User.getCurrentUser();
		this.group = null;
		this.baseUrl = null;
	}
	
	public Page(String baseUrl) {
		htmlBuilder = new HTMLBuilder(baseUrl);
		htmlBuilder.includeAppHeader = true;
		user = User.getCurrentUser();
		this.baseUrl = baseUrl;
	}
	
	protected void addLogout() {
		UserService userService = UserServiceFactory.getUserService();
		htmlBuilder.addToBody("You are not logged in!");
    	htmlBuilder.addToBody("Login <a href='" + userService.createLoginURL(baseUrl) + "'> here </a>");
	}
	
	protected void addHorizontalRule() {
		htmlBuilder.addToBody("<hr>");
	}
}

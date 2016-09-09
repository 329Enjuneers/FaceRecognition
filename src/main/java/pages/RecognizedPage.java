package pages;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import group.Group;

import pages.html_builder.HTMLBuilder;
import user.User;

public class RecognizedPage {


	private HTMLBuilder htmlBuilder;
	private User user;
	private String baseUrl;


	public RecognizedPage(String baseUrl) {
		htmlBuilder = new HTMLBuilder(baseUrl);
		htmlBuilder.includeAppHeader = true;
		user = User.getCurrentUser();
		this.baseUrl = baseUrl;

	}

	public RecognizedPage(User user, String baseUrl) {
		htmlBuilder = new HTMLBuilder(baseUrl);
		htmlBuilder.includeAppHeader = true;
		this.user = user;
		this.baseUrl = baseUrl;
	}

	public void uploadPicture()
	{
		
		//TODO upload a picture to the database
		
		
		
	}
	public String make() {
	    setTitle();
	    if (user == null) {
	    	addLogout();
	    	return htmlBuilder.build();
	    }
	    addHorizontalRule();
	    addOwnedGroups();
	    return htmlBuilder.build();
	}

	private void addLogout() {
		UserService userService = UserServiceFactory.getUserService();
		htmlBuilder.addToBody("You are not logged in!");
    	htmlBuilder.addToBody("Login <a href='" + userService.createLoginURL("/") + "'> here </a>");
	}

	private void setTitle() {
		try {
			htmlBuilder.setTitle("Recognized");
		} catch (Exception e) {}
	}


	private void addHorizontalRule() {
		htmlBuilder.addToBody("<hr>");
	}

	private void addOwnedGroups() {
		htmlBuilder.addToBody("<ul>");
	    for(Group group : Group.fetchByUser(user.email)) {
	    	try {
				String emailQuery = URLEncoder.encode(user.email, "UTF-8");
				String groupQuery = URLEncoder.encode(group.name, "UTF-8");
				htmlBuilder.addToBody("<li><a href='/group?email=" + emailQuery + "&name=" + groupQuery + "'>" + group.name + "</a></li>");
			} catch (UnsupportedEncodingException e) {}
	    }
	    htmlBuilder.addToBody("</ul>");
	}

}

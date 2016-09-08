package pages;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import group.Group;
import pages.html_builder.Form;
import pages.html_builder.HTMLBuilder;
import user.User;
import kairos.KairosApp;
public class HomePage {

	private HTMLBuilder htmlBuilder;
	private User user;

	public HomePage() {
		htmlBuilder = new HTMLBuilder();
		htmlBuilder.includeAppHeader = true;
		user = User.getCurrentUser();

		// Test Kairos APIs on homepage.
		// TODO: Following lines associated with kairos will be removed from here.
		KairosApp k = new KairosApp();
		k.listGalleries();
	}

	public HomePage(User user) {
		htmlBuilder = new HTMLBuilder();
		htmlBuilder.includeAppHeader = true;
		this.user = user;
	}

	public String make() {
	    setTitle();
	    if (user == null) {
	    	addLogout();
	    	return htmlBuilder.build();
	    }
	    addNewGroupForm();
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
			htmlBuilder.setTitle("Home");
		} catch (Exception e) {}
	}

	private void addNewGroupForm() {
		Form newGroupForm = new Form();
	    newGroupForm.addProperty("action", "/group");
	    newGroupForm.addProperty("method", "POST");
	    newGroupForm.addProperty("style", "margin-bottom:2em");
	    newGroupForm.addElement("<div style='margin-bottom: 1em'><label><b>New Group</b></label></div>");
	    newGroupForm.addElement("<input name='group-name' placeholder='Group Name'>");
	    newGroupForm.addElement("<button type='submit'>Add Group</button>");
	    htmlBuilder.addToBody(newGroupForm.toString());
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
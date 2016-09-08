package pages;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import group.Group;
import pages.html_builder.Div;
import pages.html_builder.HTMLBuilder;
import user.User;

public class GroupPage {
	private HTMLBuilder htmlBuilder;
	private User user;
	private Group group;
	private String baseUrl;
	
	public GroupPage(String baseUrl, Group group) {
		htmlBuilder = new HTMLBuilder(baseUrl);
		htmlBuilder.includeAppHeader = true;
		user = User.getCurrentUser();
		this.group = group;
		this.baseUrl = baseUrl;
	}
	
	public String make() {
		setTitle();
		if (user == null) {
			addLogout();
			return htmlBuilder.build();
		}
		
		addHeader();
		addHorizontalRule();
		addChildren();
		return htmlBuilder.build();
	}
	
	private void setTitle() {
		try {
			htmlBuilder.setTitle(group.name);
		} catch (Exception e) {}
	}
	
	private void addLogout() {
		UserService userService = UserServiceFactory.getUserService();
		htmlBuilder.addToBody("You are not logged in!");
    	htmlBuilder.addToBody("Login <a href='" + userService.createLoginURL(baseUrl) + "'> here </a>");
	}
	
	private void addHeader() {
		htmlBuilder.addToBody("<h4>" + group.name + "</h4>");
		Div tabs = new Div(); 
		try {
			// TODO build servlets
			// TODO build tab class or something
			String groupQuery = URLEncoder.encode(group.name, "UTF-8");
			String emailQuery = URLEncoder.encode(user.email, "UTF-8");
			tabs.addElement("<a href='/enroll?email=" + emailQuery + "&groupName=" + groupQuery + "'>Enroll</a>");
			tabs.addElement("<span style='border-right: 1px solid black; margin-left: .2em; margin-right: .3em;'></span>");
			tabs.addElement("<a href='/recognize?email=" + emailQuery + "&groupName=" + groupQuery + "'>Recognize</a>");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		htmlBuilder.addToBody(tabs.toString());
	}
	
	private void addHorizontalRule() {
		htmlBuilder.addToBody("<hr>");
	}
	
	private void addChildren() {
		// TODO add children to the page
	}
 }

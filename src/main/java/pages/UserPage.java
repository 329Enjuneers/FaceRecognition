package pages;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import group.Group;
import pages.html_builder.Div;

public class UserPage extends Page{
	protected Group group;
	
	public UserPage() {
		super();
	}
	
	public UserPage(String baseUrl, Group group) {
		super(baseUrl);
		htmlBuilder.includeAppHeader = true;
		this.group = group;
	}
	
	public String make() {
		if (user == null) {
			addLogout();
			return htmlBuilder.build();
		}
		addHeader();
		return htmlBuilder.build();
	}
	
	public void setTitle(String title) {
		try {
			htmlBuilder.setTitle(title);
		} catch (Exception e) {}
	}
	
	protected void addLogout() {
		UserService userService = UserServiceFactory.getUserService();
		htmlBuilder.addToBody("You are not logged in!");
    	htmlBuilder.addToBody("Login <a href='" + userService.createLoginURL(baseUrl) + "'> here </a>");
	}
	
	protected void addHeader() {
		addGroupTitle();
		Div tabs = new Div(); 
		try {
			String groupQuery = URLEncoder.encode(group.name, "UTF-8");
			tabs.addElement("<a href='/enroll?groupName=" + groupQuery + "'>Enroll</a>");
			
			if(group.getNumMembers() != 0)
			{
				tabs.addElement("<span style='border-right: 1px solid black; margin-left: .2em; margin-right: .3em;'></span>");
				tabs.addElement("<a href='/recognize?groupName=" + groupQuery + "'>Recognize</a>");
			}
			

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		htmlBuilder.addToBody(tabs.toString());
		addHorizontalRule();
	}
	
	protected void addGroupTitle() {
		try {
			String groupQuery = URLEncoder.encode(group.name, "UTF-8");
			htmlBuilder.addToBody("<h4><a href='/group?name=" + groupQuery + "'>" + group.name + "</a></h4>");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	protected void addHorizontalRule() {
		htmlBuilder.addToBody("<hr>");
	}

}

package pages;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import group.Group;
import group.Member;
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
		addGroupTitle();
		Div tabs = new Div(); 
		try {
			String groupQuery = URLEncoder.encode(group.name, "UTF-8");
			tabs.addElement("<a href='/enroll?groupName=" + groupQuery + "'>Enroll</a>");
			tabs.addElement("<span style='border-right: 1px solid black; margin-left: .2em; margin-right: .3em;'></span>");
			tabs.addElement("<a href='/recognize?groupName=" + groupQuery + "'>Recognize</a>");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		htmlBuilder.addToBody(tabs.toString());
	}
	
	private void addGroupTitle() {
		try {
			String groupQuery = URLEncoder.encode(group.name, "UTF-8");
			htmlBuilder.addToBody("<h4><a href='/group?name=" + groupQuery + "'>" + group.name + "</a></h4>");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	private void addHorizontalRule() {
		htmlBuilder.addToBody("<hr>");
	}
	
	private void addChildren() {
		htmlBuilder.addToBody("<ul>");
		for (Member member : group.getMembers()) {
			htmlBuilder.addToBody(getMemberLink(member));
		}
		htmlBuilder.addToBody("</ul>");
	}
	
	private String getMemberLink(Member member) {
		return "<li><a href='/member" + getMemberLinkQuery(member) + "'>" + member.getFullName() + "</a></li>";
	}
	
	private String getMemberLinkQuery(Member member) {
		try {
			String groupQuery = "?groupName=" + URLEncoder.encode(group.name, "UTF-8");
			String subjectIdQuery = "&subjectId=" + URLEncoder.encode(member.getSubjectId(), "UTF-8");
			return groupQuery + subjectIdQuery;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
 }

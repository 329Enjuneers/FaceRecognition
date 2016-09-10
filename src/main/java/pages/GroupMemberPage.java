package pages;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import group.Group;
import group.Member;
import pages.html_builder.Div;
import pages.html_builder.Form;
import pages.html_builder.HTMLBuilder;
import user.User;

public class GroupMemberPage {
	private HTMLBuilder htmlBuilder;
	private User user;
	private Group group;
	private String baseUrl;
	private Member groupMember;
	
	public GroupMemberPage(String baseUrl, Group group, Member groupMember) {
		htmlBuilder = new HTMLBuilder(baseUrl);
		htmlBuilder.includeAppHeader = true;
		user = User.getCurrentUser();
		this.group = group;
		this.baseUrl = baseUrl;
		this.groupMember = groupMember;
	}
	
	public String make() {
		setTitle();
		if (user == null) {
			addLogout();
			return htmlBuilder.build();
		}
		addHeader();
		addHorizontalRule();
		addMemberInfo();
		addMemberForm();
		return htmlBuilder.build();
	}
	
	private void setTitle() {
		try {
			htmlBuilder.setTitle(groupMember.getFullName() + " - " + group.name);
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
	
	private void addMemberInfo() {
		Div div = new Div();
		div.addElement("<h3>" + groupMember.getFullName() +"</h3>");
		htmlBuilder.addToBody(div.toString());
	}
	
	private void addMemberForm() {
		Form form = new Form();
		
		form.addProperty("method", "POST");
		form.addProperty("action", "/member");
		
		form.addElement(getHiddenSubjectIdDiv());
		form.addElement(getHiddenGroupNameDiv());
		form.addElement(getImageDiv());
		form.addElement(getNotesDiv());
		form.addElement(getSubmitDiv());
		htmlBuilder.addToBody(form.toString());
	}
	
	private String getHiddenSubjectIdDiv() {
		Div hiddenSubjectIdDiv = new Div();
		hiddenSubjectIdDiv.addElement("<input type='text' name='subjectId' value='" + groupMember.getSubjectId() + "' hidden>");
		return hiddenSubjectIdDiv.toString();
	}
	
	private String getHiddenGroupNameDiv() {
		Div hiddenGroupNameDiv = new Div();
		hiddenGroupNameDiv.addElement("<input type='text' name='groupName' value='" + group.name + "' hidden>");
		return hiddenGroupNameDiv.toString();
	}
	
	private String getImageDiv() {
		Div imageDiv = new Div();
		imageDiv.addProperty("style", "margin-top: 1em");
		imageDiv.addElement("<label><b>Image</b></label><br>");
		imageDiv.addElement("<img src='" + groupMember.servingUrl + "' style='height: 40%'>");
		return imageDiv.toString();
	}
	
	private String getNotesDiv() {
		Div notesDiv = new Div();
		notesDiv.addProperty("style", "margin-top: 1em");
		notesDiv.addElement("<label><b>Notes</b></label><br>");
		String notes = "";
		if (groupMember.notes != null) {
			notes = groupMember.notes;
		}
		notesDiv.addElement("<textarea name='notes' placeholder='Birthday is February 8' style='width: 23.4em; height: 6em;'>" + notes + "</textarea>");
		return notesDiv.toString();
	}
	
	private String getSubmitDiv() {
		Div submitDiv = new Div();
		submitDiv.addProperty("style", "margin-top: 1em");
		submitDiv.addElement("<input type='submit'>");
		return submitDiv.toString();
	}

}

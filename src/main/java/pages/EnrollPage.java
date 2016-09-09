package pages;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import group.Group;
import pages.html_builder.Div;
import pages.html_builder.Form;
import pages.html_builder.HTMLBuilder;
import user.User;

public class EnrollPage {
	private HTMLBuilder htmlBuilder;
	private User user;
	private Group group;
	private String baseUrl;
	
	public EnrollPage(String baseUrl, Group group) {
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
		addEnrolleeForm();
		
		return htmlBuilder.build();
	}
	
	private void setTitle() {
		try {
			htmlBuilder.setTitle("Enroll in " + group.name);
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
			tabs.addElement("<a href='/recognizeFace?email=" + emailQuery + "&groupName=" + groupQuery + "'>Recognize</a>");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		htmlBuilder.addToBody(tabs.toString());
	}
	
	private void addHorizontalRule() {
		htmlBuilder.addToBody("<hr>");
	}
	
	private void addEnrolleeForm() {
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		Form form = new Form();
		
		form.addProperty("method", "POST");
		form.addProperty("enctype", "multipart/form-data");
		form.addProperty("action", blobstoreService.createUploadUrl("/enroll"));
		
		form.addElement(getHiddenEmailDiv());
		form.addElement(getHiddenGroupNameDiv());
		form.addElement(getFirstNameDiv());
		form.addElement(getLastNameDiv());
		form.addElement(getImageDiv());
		form.addElement(getSubmitDiv());
		htmlBuilder.addToBody(form.toString());
	}
	
	private String getHiddenEmailDiv() {
		Div hiddenEmailDiv = new Div();
		hiddenEmailDiv.addElement("<input type='text' name='email' value='" + user.email + "' placeholder='John' hidden>");
		return hiddenEmailDiv.toString();
	}
	
	private String getHiddenGroupNameDiv() {
		Div hiddenGroupNameDiv = new Div();
		hiddenGroupNameDiv.addElement("<input type='text' name='group-name' value='" + group.name + "' placeholder='John' hidden>");
		return hiddenGroupNameDiv.toString();
	}
	
	private String getFirstNameDiv() {
		Div firstNameDiv = new Div();
		firstNameDiv.addProperty("style", "margin-top: 1em");
		firstNameDiv.addElement("<label><b>First Name</b></label><br>");
		firstNameDiv.addElement("<input type='text' name='first-name' placeholder='John' required>");
		return firstNameDiv.toString();
	}
	
	private String getLastNameDiv() {
		Div lastNameDiv = new Div();
		lastNameDiv.addProperty("style", "margin-top: 1em");
		lastNameDiv.addElement("<label><b>Last Name</b></label><br>");
		lastNameDiv.addElement("<input type='text' name='last-name' placeholder='Doe' required>");
		return lastNameDiv.toString();
	}
	
	private String getImageDiv() {
		Div imageDiv = new Div();
		imageDiv.addProperty("style", "margin-top: 1em");
		imageDiv.addElement("<label><b>Image</b></label><br>");
		imageDiv.addElement("<input type='file' name='image' accept='image/*' required>");
		return imageDiv.toString();
	}
	
	private String getSubmitDiv() {
		Div submitDiv = new Div();
		submitDiv.addProperty("style", "margin-top: 1em");
		submitDiv.addElement("<input type='submit'>");
		return submitDiv.toString();
	}
}

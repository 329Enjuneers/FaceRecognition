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

/**
 * For when group leader wants to input a picture and see who they are
 * aka "Recognize" tab
 * 
 * servlet is /recognizeFace
 * 
 * Should just contain a form to send to the /face servlet. 
 * It should send over the current user's email, the group name, and the image uploaded. 
 * I have an example of this in the /group servlet. It should have some text saying something like: 
 * "Recognize a member of your group" with those inputs. It should then send a request to the /face servlet, 
 * which should send that image to kairos. Kairos will tell us who is in that image. 
 * If we recognize them, we will send back a page that has the member's first name, last name, notes, and image. 
 * If we do not recognize them, we will send back a page that says something like "Sorry, we could not recognize that person."
 * 
 * User has access to the group page, enroll page, and recognize page? 
 * You can check out the enrollee page for examples on how to do this (specifically, addHeader). 
 * I think you will need to change the name of a form input
 */
public class RecognizeFacePage {

	private HTMLBuilder htmlBuilder;
	private User user;
	private String baseUrl;
	private Group group;


	public RecognizeFacePage(String baseUrl) {
		htmlBuilder = new HTMLBuilder(baseUrl);
		htmlBuilder.includeAppHeader = true;
		user = User.getCurrentUser();
		this.baseUrl = baseUrl;

	}

	public RecognizeFacePage(User user, String baseUrl, Group group) {
		htmlBuilder = new HTMLBuilder(baseUrl);
		htmlBuilder.includeAppHeader = true;
		this.user = user;
		this.baseUrl = baseUrl;
		this.group = group;
	}

	public String make() {
	    setTitle();
	   
	    if (user == null) {
	    	addLogout();
	    	return htmlBuilder.build();
	    }
	    addHeader();
	    addSubmitDiv();
	    addRecognizeFaceForm();
	    return htmlBuilder.build();
	}
	
	private String addImageDiv() {
		Div imageDiv = new Div();
		imageDiv.addProperty("style", "margin-top: 1em");
		imageDiv.addElement("<label><b>Image</b></label><br>");
		imageDiv.addElement("<input type='file' name='image' accept='image/*' required>");
		return imageDiv.toString();
	}
	
	private String addSubmitDiv() {
		Div submitDiv = new Div();
		submitDiv.addProperty("style", "margin-top: 1em");
		submitDiv.addElement("<input type='submit'>");
		return submitDiv.toString();
	}
	
	private void addRecognizeFaceForm() {
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		htmlBuilder.addToBody("<h3>Recognize a member of your group</h3>");
		htmlBuilder.addToBody("<p>Please choose a picture<p>");

		Form form = new Form();
		form.addProperty("action", blobstoreService.createUploadUrl("/face"));
		form.addProperty("method", "POST");
		form.addProperty("enctype", "multipart/form-data");

	    form.addProperty("style", "margin-bottom:2em");
		form.addElement(addImageDiv());
		form.addElement("<br>");
		form.addElement("<span style='border-right: 1px solid black; margin-left: .2em; margin-right: .3em;'></span>");
		form.addElement("<input type='submit'>");
		htmlBuilder.addToBody(form.toString());
	}
	
	private void addHeader() {
		addGroupTitle();
		Div tabs = new Div(); 
		try {
			String groupQuery = URLEncoder.encode(group.name, "UTF-8");
			tabs.addElement("<a href='/enroll?groupName=" + groupQuery + "'>Enroll</a>");
			tabs.addElement("<span style='border-right: 1px solid black; margin-left: .2em; margin-right: .3em;'></span>");
			tabs.addElement("<a href='/recognizeFace?groupName=" + groupQuery + "'>Recognize</a>");
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


	private void addLogout() {
		UserService userService = UserServiceFactory.getUserService();
		htmlBuilder.addToBody("You are not logged in!");
    	htmlBuilder.addToBody("Login <a href='" + userService.createLoginURL("/") + "'> here </a>");
	}

	private void setTitle() {
		try {
			htmlBuilder.setTitle("Recognize Face");
		} catch (Exception e) {}
	}



}

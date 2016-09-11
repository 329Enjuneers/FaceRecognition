package pages;


import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import group.Group;
import pages.html_builder.Div;
import pages.html_builder.Form;

/**
 * For when group leader wants to input a picture and see who they are
 * aka "Recognize" tab
 *
 * servlet is /recognize
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
public class RecognizePage extends UserPage {

	public RecognizePage(String baseUrl, Group group) {
		super(baseUrl, group);
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
		form.addProperty("action", blobstoreService.createUploadUrl("/recognize"));
		form.addProperty("method", "POST");
		form.addProperty("enctype", "multipart/form-data");
	    form.addProperty("style", "margin-bottom:2em");
		
	    form.addElement(getHiddenGroupDiv());
	    form.addElement(addImageDiv());
		form.addElement("<br>");
		form.addElement("<input type='submit'>");
		htmlBuilder.addToBody(form.toString());
	}
	
	private String getHiddenGroupDiv() {
		Div hiddenGroupNameDiv = new Div();
		hiddenGroupNameDiv.addElement("<input type='text' name='groupName' value='" + group.name + "' hidden>");
		return hiddenGroupNameDiv.toString();
	}

	private void setTitle() {
		try {
			htmlBuilder.setTitle("Recognize Face");
		} catch (Exception e) {}
	}
}

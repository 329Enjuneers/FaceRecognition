package pages;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import group.Group;
import pages.html_builder.Div;
import pages.html_builder.Form;

public class EnrollPage extends UserPage {

	public EnrollPage(String baseUrl, Group group) {
		super(baseUrl, group);
	}

	public String make() {
		setTitle();
		if (user == null) {
			addLogout();
			return htmlBuilder.build();
		}
		addHeader();
		addEnrolleeForm();
		return htmlBuilder.build();
	}

	private void setTitle() {
		try {
			htmlBuilder.setTitle("Enroll in " + group.name);
		} catch (Exception e) {}
	}

	private void addEnrolleeForm() {
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		Form form = new Form();

		form.addProperty("method", "POST");
		form.addProperty("enctype", "multipart/form-data");
		form.addProperty("action", blobstoreService.createUploadUrl("/enroll"));

		form.addElement(getHiddenGroupNameDiv());
		form.addElement(getFirstNameDiv());
		form.addElement(getLastNameDiv());
		form.addElement(getImageDiv());
		form.addElement(getSubmitDiv());
		htmlBuilder.addToBody(form.toString());
	}

	private String getHiddenGroupNameDiv() {
		Div hiddenGroupNameDiv = new Div();
		hiddenGroupNameDiv.addElement("<input type='text' name='group-name' value='" + group.name + "' hidden>");
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

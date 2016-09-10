package pages;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import pages.html_builder.Div;
import pages.html_builder.Form;
import pages.html_builder.HTMLBuilder;

/**
 * Shows this page when you try to upload an image and we cannot recognize the face(s) 
 * @author hopescheffert
 *
 */
public class NotRecognizedPage 
{
	
	private HTMLBuilder htmlBuilder;
	private String baseUrl;

	
	public NotRecognizedPage(String baseUrl) 
	{
		htmlBuilder = new HTMLBuilder(baseUrl);
		htmlBuilder.includeAppHeader = true;
		this.baseUrl = baseUrl;
	}
	
	public String make() {
	    setTitle();
	   
	    addNewFaceForm();
	    return htmlBuilder.build();
	}
	
	private void setTitle() {
		try {
			htmlBuilder.setTitle("New Face");
		} catch (Exception e) {}
	}
	
	
	private void addNewFaceForm() {
		htmlBuilder.addToBody("<h3>I'm sorry, we don't recognize this face!</h3>");
		htmlBuilder.addToBody("<p>Would you like to add this face to our database?</p>");
		Form form = new Form();
		form.addProperty("action", "/face"); //servlet for adding a new face to database
		form.addProperty("method", "POST");
	    form.addProperty("style", "margin-bottom:2em");
		form.addElement("<span style='border-right: 1px solid black; margin-left: .2em; margin-right: .3em;'></span>");
		form.addElement("<input type='radio' name='choice' value='yes' checked>Yes<br>");
		form.addElement("<input type='radio' name='choice' value='no'>No<br>");
		form.addElement("<input type='submit'>");
		htmlBuilder.addToBody(form.toString());
	}
	
	
	
	
	
	
	
	
	
}

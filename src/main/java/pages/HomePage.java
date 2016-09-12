package pages;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import group.Group;
import pages.html_builder.Form;

public class HomePage extends Page {

	public HomePage(String baseUrl) {
		super(baseUrl);
		htmlBuilder.includeAppHeader = true;
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
	    newGroupForm.addElement("<input name='group-name' placeholder='Group Name' required>");
	    newGroupForm.addElement("<button type='submit'>Add Group</button>");
	    htmlBuilder.addToBody(newGroupForm.toString());
	}

	private void addOwnedGroups() {
		htmlBuilder.addToBody("<ul>");
	    for(Group group : Group.fetchByUser(user.email)) {
	    	try {
				String groupQuery = URLEncoder.encode(group.name, "UTF-8");
				htmlBuilder.addToBody("<li id='"+group.name+"'><a href='/group?name=" + groupQuery + "'>" + group.name + "</a>&nbsp;&nbsp;<a href='/?action=delete&amp;name="+group.name+"'><button>DELETE</button></a></li><br/>");
			} catch (UnsupportedEncodingException e) {}
	    }
    htmlBuilder.addToBody("</ul>");
	}


}

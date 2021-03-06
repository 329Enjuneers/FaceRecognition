package pages;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import group.Group;
import group.Member;
import pages.html_builder.Div;
import pages.html_builder.Form;

public class GroupMemberPage extends UserPage {
	private Member groupMember;

	public GroupMemberPage(String baseUrl, Group group, Member groupMember) {
		super(baseUrl, group);
		this.groupMember = groupMember;
	}

	public String make() {
		setTitle();
		if (user == null) {
			addLogout();
			return htmlBuilder.build();
		}
		addHeader();
		addMemberInfo();
		addMemberForm();
		return htmlBuilder.build();
	}

	private void setTitle() {
		try {
			htmlBuilder.setTitle(groupMember.getFullName() + " - " + group.name);
		} catch (Exception e) {}
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
		imageDiv.addElement("<br/>Last seen on: <label>" + getTimeLastSeenString() +"</label>");
		return imageDiv.toString();
	}
	
	private String getTimeLastSeenString() {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		return df.format(groupMember.timeLastSeen);
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

package pages;

import group.Group;
import group.Member;
import pages.html_builder.Div;

public class EnrolleeAddedPage extends UserPage {
	private Member enrollee;

	public EnrolleeAddedPage(String baseUrl, Group group, Member enrollee) {
		super(baseUrl, group);
		this.enrollee = enrollee;
	}

	public String make() {
		setTitle();
		if (user == null) {
			addLogout();
			return htmlBuilder.build();
		}
		addHeader();
		addEnrolleeDiv();

		return htmlBuilder.build();
	}

	private void setTitle() {
		try {
			htmlBuilder.setTitle("Enroll in " + group.name);
		} catch (Exception e) {}
	}

	private void addEnrolleeDiv() {
		Div div = new Div();
		div.addElement(getSuccessMessage());
		div.addElement(getImageDiv());
		htmlBuilder.addToBody(div.toString());
	}

	private String getSuccessMessage() {
		Div div = new Div();
		div.addElement("<h4>" + enrollee.firstName + " " + enrollee.lastName + " successfully added to group \"" + group.name + "\"</h4>");
		return div.toString();
	}

	private String getImageDiv() {
		Div div = new Div();
		div.addElement("<img src='" + enrollee.servingUrl + "'>");
		return div.toString();
	}
}

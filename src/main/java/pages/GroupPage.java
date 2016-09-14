package pages;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import group.Group;
import group.Member;

public class GroupPage extends UserPage {

	public GroupPage(String baseUrl, Group group) {
		super(baseUrl, group);
	}

	public String make() {
		setTitle();
		if (user == null) {
			addLogout();
			return htmlBuilder.build();
		}

		addHeader();
		addChildren();
		return htmlBuilder.build();
	}

	private void setTitle() {
		try {
			htmlBuilder.setTitle(group.name);
		} catch (Exception e) {}
	}

	private void addChildren() {
		htmlBuilder.addToBody("<ul>");
		for (Member member : group.getMembers()) {
			htmlBuilder.addToBody(getMemberLink(member));
		}
		htmlBuilder.addToBody("</ul>");
	}

	private String getMemberLink(Member member) {
		return "<li><a href='/member" + getMemberLinkQuery(member) + "'>" + member.getFullName() + "</a>&nbsp;&nbsp;<a href='/group?name=" + encodeGroupName() + "&action=delete"+ getSubjectIdQuery(member) +"'><button>DELETE</button></a></li>";
	}

	private String getMemberLinkQuery(Member member) {
		String groupQuery = "?groupName=" + encodeGroupName();
		return groupQuery + getSubjectIdQuery(member);
	}
	
	private String encodeGroupName() {
		try {
			return URLEncoder.encode(group.name, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private String getSubjectIdQuery(Member member) {
		try {
			String subjectIdQuery = "&subjectId=" + URLEncoder.encode(member.getSubjectId(), "UTF-8");
			return subjectIdQuery;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
 }

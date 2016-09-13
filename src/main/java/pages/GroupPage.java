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
		return "<li><a href='/member" + getMemberLinkQuery(member) + "'>" + member.getFullName() + "</a>&nbsp;&nbsp;<a href='/?action=delete&amp;lastName="+ member.getLast()+"'><button>DELETE</button></a></li>";
	}

	private String getMemberLinkQuery(Member member) {
		try {
			String groupQuery = "?groupName=" + URLEncoder.encode(group.name, "UTF-8");
			String subjectIdQuery = "&subjectId=" + URLEncoder.encode(member.getSubjectId(), "UTF-8");
			return groupQuery + subjectIdQuery;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
 }

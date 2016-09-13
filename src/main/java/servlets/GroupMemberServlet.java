package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import group.Group;
import group.Member;
import pages.GroupMemberPage;
import user.User;

public class GroupMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String groupName = req.getParameter("groupName");
		String subjectId = req.getParameter("subjectId");
		User user = User.getCurrentUser();
		
		
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		
		
		if (groupName == null || subjectId == null) {
			out.write("Insufficient data");
			resp.setStatus(400);
			return;
		}
		
		Group group = Group.get(groupName, user.email);
		if (group == null) {
			out.write("Group not found");
			resp.setStatus(404);
			return;
		}
		
		Member member = group.getMember(subjectId);
		if (member == null) {
			out.write("Member not found");
			resp.setStatus(404);
			return;
		}
		
		out.write(new GroupMemberPage(req.getRequestURI(), group, member).make());
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String groupName = req.getParameter("groupName");
		String subjectId = req.getParameter("subjectId");
		String notes = req.getParameter("notes");
		User user = User.getCurrentUser();

		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		
		if (groupName == null || subjectId == null) {
			out.write("Insufficient data");
			resp.setStatus(400);
			return;
		}
		
		Group group = Group.get(groupName, user.email);
		if (group == null) {
			out.write("Group not found");
			resp.setStatus(404);
			return;
		}
		
		Member member = group.getMember(subjectId);
		if (member == null) {
			out.write("Member not found");
			resp.setStatus(404);
			return;
		}
		
		
		member.notes = notes;
		group.save();
		resp.sendRedirect("/member?groupName=" + group.name + "&subjectId=" + member.getSubjectId());
	}

}

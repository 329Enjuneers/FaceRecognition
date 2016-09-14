package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import group.Group;
import group.Member;
import pages.GroupPage;
import user.User;

public class GroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private HttpServletRequest request;
	private Group group;
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		request = req;
		String groupName = req.getParameter("name");
		User user = User.getCurrentUser();
		
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		
		group = Group.get(groupName, user.email);
		if(req.getParameterMap().containsKey("action")){
			String action = req.getParameter("action");
			performAction(action);
		}
		out.write(new GroupPage(req.getRequestURI(), group).make());

	}
	
	private void performAction(String action) {
		if (action.toLowerCase().equals("delete")) {
			deleteMember();
		}
		group.save();
	}
	
	private void deleteMember() {
		if (request.getParameterMap().containsKey("subjectId")) {
			String subjectId = request.getParameter("subjectId");
			if (group != null) {
				Member member = group.getMember(subjectId);
				group.deleteMember(member);
			}
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		
		String groupName = req.getParameter("group-name");
		User user = User.getCurrentUser();
		Group group = Group.getOrInsert(groupName, user.email);
		
		out.write(new GroupPage(req.getRequestURI(), group).make());
	}
}

package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import group.Group;
import group.Member;
import pages.GroupPage;
import pages.HomePage;
import user.User;

public class GroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String groupName = req.getParameter("name");
		User user = User.getCurrentUser();
		
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		
		Group group = Group.getOrInsert(groupName, user.email);
		out.write(new GroupPage(req.getRequestURI(), group).make());
		if(req.getParameterMap().containsKey("lastName") && req.getParameterMap().containsKey("action")){
			User u = User.getCurrentUser();
			Member m = new Member(req.getParameter("firstName"), req.getParameter("lastName"));
			Group g = Group.get(req.getParameter("groupName"), u.email);
			if( g != null){
				g.deleteMember(m);
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

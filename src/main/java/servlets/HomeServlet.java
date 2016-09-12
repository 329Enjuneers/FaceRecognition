package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import group.Group;
import user.User;
import pages.HomePage;

public class HomeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		// Delete user if prompted.
		if(req.getParameterMap().containsKey("name") && req.getParameterMap().containsKey("action")){
			User user = User.getCurrentUser();
			Group group = Group.get(req.getParameter("name"),user.email);
			if(group != null){
				group.delete();
			}
		}
		out.write(new HomePage(req.getRequestURI()).make());
	}

}

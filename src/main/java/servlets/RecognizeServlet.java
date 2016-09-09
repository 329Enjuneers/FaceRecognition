package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pages.RecognizedPage;
import pages.html_builder.HTMLBuilder;

/**
 * for when user inputs a picture and the face is recognized
 * @author hopescheffert
 *
 */
public class RecognizeServlet extends HttpServlet { 


	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
	    out.write(new RecognizedPage(req.getRequestURI()).make());
	}

}

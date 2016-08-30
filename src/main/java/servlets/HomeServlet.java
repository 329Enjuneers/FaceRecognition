package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import html_builder.Form;
import html_builder.HTMLBuilder;
import user.User;

public class HomeServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		UserService userService = UserServiceFactory.getUserService();
		User user = User.getCurrentUser();
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		HTMLBuilder htmlBuilder = new HTMLBuilder();
	    try {
			htmlBuilder.setTitle("Face Recognition");
		} catch (Exception e) {}
	    
	    if (user == null) {
	    	htmlBuilder.addToBody("You are not logged in!");
	    	htmlBuilder.addToBody("Login <a href='" + userService.createLoginURL(req.getRequestURI()) + "'> here </a>");
	    	out.write(htmlBuilder.build());
	    	return;
	    }

	    // TODO objectify all this to something like: HTML.buildHomePage();
		htmlBuilder.addToBody("<p>Hello, " + user.nickname + "</p>");
		htmlBuilder.addToBody("<p>Logout <a href='" + userService.createLogoutURL(req.getRequestURI()) + "'> here </a></p>");
		Form form = htmlBuilder.getNewForm();
		form.addProperty("action", blobstoreService.createUploadUrl("/face"));
		form.addProperty("method", "POST");
		form.addProperty("enctype", "multipart/form-data");
		form.addElement("<input type='file' accept='image/*' name='image'>");
		form.addElement("<input type='submit'>");
		htmlBuilder.addForm(form);
		out.write(htmlBuilder.build());
	}
	
}

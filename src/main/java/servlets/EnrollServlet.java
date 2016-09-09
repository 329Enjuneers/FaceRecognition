package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;

import group.Group;
import kairos.KairosApp;
import pages.EnrollPage;
import person.Person;

public class EnrollServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String email = req.getParameter("email");
		String groupName = req.getParameter("groupName");
		System.out.println("email: " + email);
		System.out.println("group: " + groupName);
		
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		
		Group group = Group.getOrInsert(groupName, email);
		out.write(new EnrollPage(req.getRequestURI(), group).make());
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		String firstName = req.getParameter("first-name");
		String lastName = req.getParameter("last-name");
		String email = req.getParameter("email");
		String groupName = req.getParameter("group-name");
		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
		List<BlobKey> blobKeys = blobs.get("image");
		
		if (firstName == null || lastName == null || email == null || groupName == null || blobKeys.size() == 0) {
			out.write("Insufficient data");
			resp.setStatus(400);
			return;
		}
		
		Group group = Group.get(groupName, email);
		if (group == null) {
			out.write("Could not find group");
			resp.setStatus(400);
			return;
		}
		
		String servingUrl = getServingUrl(blobKeys.get(0));
		Person person = new Person(firstName, lastName, servingUrl);
		group.addChild(person);
		System.out.println("first:" + firstName);
		System.out.println("last: " + lastName);
		System.out.println("num group children: " + group.getNumChildren());
		System.out.println("serving url: " + servingUrl);
		System.out.println("id " + person.getSubjectId());

		// TODO create page 
		
//		KairosApp kairos = new KairosApp();
//		kairos.enroll(person.servingUrl, person.getSubjectId(), groupName);
		
//		PrintWriter out = resp.getWriter();
//		resp.setContentType("text/html");
//		
//		Group group = Group.getOrInsert(groupName, email);
//		out.write(new EnrollPage(req.getRequestURI(), group).make());
	}
	
	private String getServingUrl(BlobKey blob) {
		return ImagesServiceFactory.getImagesService().getServingUrl(ServingUrlOptions.Builder.withBlobKey(blob));
	}
}

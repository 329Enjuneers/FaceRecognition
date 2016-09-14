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
import group.Member;
import kairos.KairosApp;
import pages.EnrollPage;
import pages.EnrolleeAddedPage;
import user.User;

public class EnrollServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		User user = User.getCurrentUser();
		String groupName = req.getParameter("groupName");
		System.out.println("email: " + user.email);
		System.out.println("group: " + groupName);

		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");

		Group group = Group.get(groupName, user.email);
		out.write(new EnrollPage(req.getRequestURI(), group).make());
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");

		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		String firstName = req.getParameter("first-name");
		String lastName = req.getParameter("last-name");
		String groupName = req.getParameter("group-name");
		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
		List<BlobKey> blobKeys = blobs.get("image");
		User user = User.getCurrentUser();

		if (firstName == null || lastName == null || user == null || groupName == null || blobKeys.size() == 0) {
			out.write("Insufficient data");
			resp.setStatus(400);
			return;
		}

		Group group = Group.get(groupName, user.email);
		if (group == null) {
			out.write("Could not find group");
			resp.setStatus(400);
			return;
		}

		String servingUrl = getServingUrl(blobKeys.get(0));
		Member member = new Member(firstName, lastName, servingUrl);
		group.addMember(member);

		KairosApp kairos = new KairosApp();
		kairos.enroll(member.servingUrl, member.getSubjectId(), groupName);

		out.write(new EnrolleeAddedPage(req.getRequestURI(), group, member).make());
	}

	private String getServingUrl(BlobKey blob) {
		return ImagesServiceFactory.getImagesService().getServingUrl(ServingUrlOptions.Builder.withBlobKey(blob));
	}
}

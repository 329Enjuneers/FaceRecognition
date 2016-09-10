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
import pages.GroupPage;
import pages.RecognizeFacePage;
import user.User;

/**
 * For when group leader wants to input a picture and see who they are
 */
public class RecognizeFaceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String email = req.getParameter("email");
		String groupName = req.getParameter("name");
		
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		User user = User.getCurrentUser();

		Group group = Group.getOrInsert(groupName, email);
		out.write(new RecognizeFacePage(user, req.getRequestURI(), group).make());
	}
	
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
		List<BlobKey> blobKeys = blobs.get("image");
		
		if (blobKeys.size() != 1) {
			out.write("Must have only one image");
			resp.setStatus(400);
			return;
		}
		
		BlobKey blob = blobKeys.get(0);
		String servingUrl = ImagesServiceFactory.getImagesService().getServingUrl(ServingUrlOptions.Builder.withBlobKey(blob));
		System.out.println(servingUrl);
		String groupName = req.getParameter("group-name");
		User user = User.getCurrentUser();
		Group group = Group.getOrInsert(groupName, user.email);
		
		out.write(new RecognizeFacePage(user, req.getRequestURI(), group).make());
	}

}

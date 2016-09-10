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

public class FaceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

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

		// TODO
		// 1. Get group name from this request
		// 2. Get first name and last name from this request
		// 3. Fetch person
		// 4. Get persons subject id
		// 5. Recognize face

//		KairosApp kairos = new KairosApp();
//		String subjectId = kairos.recognize(servingUrl, "test-gallery");
//		System.out.println("Subject id: " + subjectId);
		// TODO uncomment following line once we have group
//		resp.sendRedirect("/member?groupName=" + group.name + "&subjectId=" + subjectId);
	}

}

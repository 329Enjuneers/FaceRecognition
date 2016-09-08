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

import kairos.KairosApp;
import pages.html_builder.HTMLBuilder;

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
		// 3. Create new Person
		// 4. Generate subject id based on person
		// 5. Enroll user
		
		KairosApp kairos = new KairosApp();
//		kairos.enroll(servingUrl, "joe-testing", "test-gallery");
		String subjectId = kairos.recognize(servingUrl, "test-gallery");
		System.out.println("Subject id: " + subjectId);
		HTMLBuilder htmlBuilder = new HTMLBuilder(req.getRequestURI());
		htmlBuilder.addToBody("We're working on it");
		out.write(htmlBuilder.build());
	}

}

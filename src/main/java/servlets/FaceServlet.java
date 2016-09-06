package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.services.vision.v1.model.FaceAnnotation;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import face_detector.FaceDetectApp;
import html_builder.HTMLBuilder;
import user.User;

public class FaceServlet extends HttpServlet {

	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		//confirmation
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		HTMLBuilder htmlBuilder = new HTMLBuilder();
		htmlBuilder.addToBody("We're working on it");
		out.write(htmlBuilder.build());
		
		User user = User.getCurrentUser();
		System.out.println(user);
		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
		List<BlobKey> blobKeys = blobs.get("image");
		System.out.println(blobKeys);

		if (blobKeys == null || blobKeys.isEmpty()) {
			resp.sendRedirect("/");
		} else {
			
			resp.sendRedirect("/serve?blob-key=" + blobKeys.get(0).getKeyString());
		}


		//TODO if face is recognized, go to the success page

		//TODO if the face is not recognized, go to the failed page, ask user if they want to upload to our datastore



		//		TODO implement this detection portion
		//		FaceDetectApp app = new FaceDetectApp(getVisionService());
		//	    List<FaceAnnotation> faces = app.detectFaces(inputPath, MAX_RESULTS);
		//	    System.out.printf("Found %d face%s\n", faces.size(), faces.size() == 1 ? "" : "s");
		//	    System.out.printf("Writing to file %s\n", outputPath);
		//	    app.writeWithFaces(inputPath, outputPath, faces);
	}

}

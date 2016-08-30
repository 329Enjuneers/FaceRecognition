package servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.services.vision.v1.model.FaceAnnotation;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import face_detector.FaceDetectApp;
import user.User;

public class FaceServlet extends HttpServlet {
	
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		User user = User.getCurrentUser();
		System.out.println(user);
		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
		List<BlobKey> blobKeys = blobs.get("image");
		System.out.println(blobKeys);
//		TODO implement this detection portion
//		FaceDetectApp app = new FaceDetectApp(getVisionService());
//	    List<FaceAnnotation> faces = app.detectFaces(inputPath, MAX_RESULTS);
//	    System.out.printf("Found %d face%s\n", faces.size(), faces.size() == 1 ? "" : "s");
//	    System.out.printf("Writing to file %s\n", outputPath);
//	    app.writeWithFaces(inputPath, outputPath, faces);
	}
}

package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import pages.RecognizePage;
import pages.UserPage;
import user.User;

/**
 * For when group leader wants to input a picture and see who they are
 */
public class RecognizeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String groupName = req.getParameter("groupName");
		
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		User user = User.getCurrentUser();

		Group group = Group.getOrInsert(groupName, user.email);
		out.write(new RecognizePage(req.getRequestURI(), group).make());
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
		String groupName = req.getParameter("groupName");
		User user = User.getCurrentUser();
		Group group = Group.get(groupName, user.email);
		if (group == null) {
			out.write("Group not found");
			resp.setStatus(404);
			return;
		}
		
		UserPage basicPage = new UserPage(req.getRequestURI(), group);
		KairosApp kairos = new KairosApp();
		String subjectId = kairos.recognize(servingUrl, group.name);
		String extraHtml;
		if (subjectId == null) {
			basicPage.setTitle("Unrecognized");
			extraHtml = "Sorry, we did not recognize this person.";
			/**
			 * Add "Would you like to add this person to your group/our database?
			 */
		}
		else {
			basicPage.setTitle("Recognized");
			Member member = group.getMember(subjectId);
			if (member == null) {
				extraHtml = "Sorry, we did not recognize this person.";
			}
			else {
				extraHtml = "This person was recognized as " + getMemberLink(group, member);
			}
		}
		
		String html = basicPage.make() + extraHtml;
		out.write(html);
	}
	
	private String getMemberLink(Group group, Member member) {
		return "<a href='/member" + getMemberLinkQuery(group, member) + "'>" + member.getFullName() + "</a>";
	}

	private String getMemberLinkQuery(Group group, Member member) {
		try {
			String groupQuery = "?groupName=" + URLEncoder.encode(group.name, "UTF-8");
			String subjectIdQuery = "&subjectId=" + URLEncoder.encode(member.getSubjectId(), "UTF-8");
			return groupQuery + subjectIdQuery;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

}

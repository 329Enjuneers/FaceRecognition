package kairos;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

import kairos.requests.EnrollRequest;
import kairos.requests.RecognizeRequest;

public class KairosApp {
	private static final String BASE_URL = "https://api.kairos.com";
	private static final String APP_ID = "36d96c12";
	private static final String APP_KEY = "883a07fe98616020343472d0753e4c09";
	private static final Logger log = Logger.getLogger(KairosApp.class.getName());

	public KairosApp() {}

	/**
	 * This function is used to enroll a person in a group / gallery.
	 * @param b64Image - String : Image converted to String in base64 format that is used to enroll person with kairos.
	 * @param subjectID - String : id that identifies this person.
	 * @param gallery - String : name of the gallery this person belongs to. Can be case-sensitive. Please ensure the gallery_name is supplied correctly.
	 */
	public void enroll(String imageUrl, String subjectId,  String gallery) {
		EnrollRequest request = new EnrollRequest();
		request.imageUrl = imageUrl;
		request.subjectId = subjectId;
		request.gallery = gallery;
		try {
			JSONObject json = request.send();
			if (json != null) {
				log.warning(json.toString());	
			}
			else {
				log.warning("Bad json");
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.warning("Something went wrong while processing the request please try again.");
		}
	} // End function enroll.

	/**
	 * Detects whether given image is in Kairos db.
	 * @param imageUrl - String : Public image url for kairos to recognize faces from.
	 * @param gallery - String : The gallery_id of the gallery that contains this person.
	 */
	public void detectFaces(String imageUrl, String gallery) {
		RecognizeRequest request = new RecognizeRequest();
		request.imageUrl = imageUrl;
		request.gallery = gallery;
		try {
			JSONObject json = request.send();
			if (json != null) {
				log.warning(json.toString());	
			}
			else {
				log.warning("Bad json");
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.warning("Something went wrong while processing the request please try again.");
		}
	} // End function detectFaces.

	/**
	 * This function lists all galleries that are already created in Kairos database.
	 * Can be used to provide list of groups to user.
	 * @return JSONArray - Returns a JSONArray containg all groups as stored in Kairos database.
	 */
	public org.json.JSONArray listGalleries() {
		// TODO implement ListGalleryRequest
		
		boolean error = false;
		// Kairos URL to make the request to.
		String requestURL = BASE_URL + "/gallery/list_all";
		// Start generating the requeust here.
		HTTPRequest request;
		JSONArray galleries = null;
		try {
			// Set headers for the request.
			request = new HTTPRequest(new URL(requestURL), HTTPMethod.POST);
	    request.setHeader(new HTTPHeader("app_id", APP_ID));
	    request.setHeader(new HTTPHeader("app_key", APP_KEY));
			URLFetchService url_service = URLFetchServiceFactory.getURLFetchService();
	    HTTPResponse response = url_service.fetch(request);
	    if (response.getResponseCode() != 200) {
	        throw new IOException(new String(response.getContent()));
	    }
	    String content = new String(response.getContent());
			JSONObject result = new JSONObject(content);
			galleries = (JSONArray) result.get("gallery_ids");
			// System.out.println(galleries);
			// Return JSONArray of all galleries fetched from Kairos.
			return galleries;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.warning(e.getMessage());
			error = true;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.warning(e.getMessage());
			error = true;
		} catch (IOException e) {
			e.printStackTrace();
			log.warning(e.getMessage());
			error = true;
		} catch(org.json.JSONException e) {
			e.printStackTrace();
			log.warning(e.getMessage());
			error = true;
		}

		// Check if an error occured while making Kairos database request.
		if(error){
			log.warning("Something went wrong while processing the request please try again.");
			return null;
		}else{
			return galleries;
		}
	}
}

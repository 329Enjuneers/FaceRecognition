package kairos;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import org.json.JSONObject;

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

public class KairosApp {
	private static final String BASE_URL = "https://api.kairos.com";
	private static final String APP_ID = "36d96c12";
	private static final String APP_KEY = "883a07fe98616020343472d0753e4c09";
	private static final Logger log = Logger.getLogger(KairosApp.class.getName());
	
	public KairosApp() {}
	
	public void enroll(String b64Image, String subjectID,  String gallery) {
		// TODO clean this up!!!!
			URLFetchService url_service = URLFetchServiceFactory.getURLFetchService();
		    HTTPRequest request;
			try {
				JSONObject obj = new JSONObject();
				obj.put("image", b64Image);
				obj.put("url", b64Image);
				obj.put("subject_id", subjectID);
				obj.put("gallery_name", gallery);
				System.out.println(BASE_URL + "/enroll");
				request = new HTTPRequest(new URL(BASE_URL + "/enroll"), HTTPMethod.POST);
				request.setHeader(new HTTPHeader("Content-Type", "application/json; charset=UTF-8"));
			    request.setHeader(new HTTPHeader("app_id", APP_ID));
			    request.setHeader(new HTTPHeader("app_key", APP_KEY));
			    request.setPayload(obj.toString().getBytes("utf8"));
			    HTTPResponse response = url_service.fetch(request);
			    if (response.getResponseCode() != 200) {
			        throw new IOException(new String(response.getContent()));
			    }
			    String content = new String(response.getContent());
			    log.warning(content);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.warning(e.getMessage());
			}
			catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.warning(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				log.warning(e.getMessage());}
			catch(org.json.JSONException e) {
				e.printStackTrace();
				log.warning(e.getMessage());
			}
	}
	
	public void detectFaces(String b64Image, String gallery) {
//		try {
//			HttpResponse<JsonNode> jsonResponse = Unirest.post(BASE_URL + "/recognize")
//					  .header("accept", "application/json")
//					  .header("app_id", APP_ID)
//					  .header("app_key", APP_KEY)
//					  .field("image", b64Image)
//					  .field("gallery", gallery)
//					  .asJson();
//			System.out.println(jsonResponse.getBody().toString());
//		} catch (UnirestException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}

package kairos.requests;

import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

import kairos.KairosApp;

public class RecognizeRequest extends KairosRequest{
	private static final Logger log = Logger.getLogger(KairosApp.class.getName());
	public String imageUrl;
	public String gallery;
	
	private HTTPRequest request;
	
	private static final String requestUrl = BASE_URL + "/recognize";
	
	public RecognizeRequest() {
		imageUrl = null;
		gallery = null;
		initRequest();
	}
	
	public JSONObject send() throws IOException {
		if(!allDataSet()) {
			throw new IllegalStateException();
		}
		setPayload();
		HTTPResponse response = sendRequest();
		if (response.getResponseCode() != 200) {
			throw new IOException(new String(response.getContent()));
		}
		log.info("Received the following content from Kairos:");
		log.info(new String(response.getContent()));
		
		try {
			return new JSONObject(new String(response.getContent()));
		} catch (JSONException e) {
			return null;
		}
	}
	
	private boolean allDataSet() {
		log.warning(imageUrl);
		log.warning(gallery);
		return imageUrl != null && gallery != null;
	}
	
	private void initRequest() {
		try {
			request = new HTTPRequest(new URL(requestUrl), HTTPMethod.POST);
			request = setHeaders(request);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	private void setPayload() {
		request.setPayload(getJson());
	}
	
	private byte[] getJson() {
		try {
			JSONObject obj = new JSONObject();
			obj.put("image", imageUrl);
			obj.put("gallery_name", gallery);
			return obj.toString().getBytes("utf8");
		} catch (JSONException e) {
			return null;
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	private HTTPResponse sendRequest() {
		try {
			URLFetchService url_service = URLFetchServiceFactory.getURLFetchService();
			return url_service.fetch(request);
		} catch (IOException e) {
			return null;
		}
	}

}

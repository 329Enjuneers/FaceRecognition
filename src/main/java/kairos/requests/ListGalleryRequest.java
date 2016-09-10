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

// Simple ListGallery class that provides list of all galleries registered with Kairos.
public class ListGalleryRequest extends KairosRequest {

	private HTTPRequest request;
	private static final String requestUrl = BASE_URL + "/gallery/list_all";
	private static final Logger log = Logger.getLogger(ListGalleryRequest.class.getName());

	public ListGalleryRequest() {
		initRequest();
	}

	// Returns JSONObject that includes time taken for request, request status, and gallery_ids registered with Kairos db.
	public JSONObject send() throws IOException {
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

	// Initialize request.
	private void initRequest() {
		try {
			request = new HTTPRequest(new URL(requestUrl), HTTPMethod.POST);
			request = setHeaders(request);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	// Make the request to Kairos.
	private HTTPResponse sendRequest() {
		try {
			URLFetchService url_service = URLFetchServiceFactory.getURLFetchService();
			return url_service.fetch(request);
		} catch (IOException e) {
			return null;
		}
	}
}

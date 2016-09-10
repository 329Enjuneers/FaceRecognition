package kairos.requests;

import java.io.IOException;
import java.util.logging.Logger;

import org.json.JSONObject;

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPRequest;

public class KairosRequest {
	protected static final String BASE_URL = "https://api.kairos.com";
	protected static final String APP_ID = "36d96c12";
	protected static final String APP_KEY = "883a07fe98616020343472d0753e4c09";
	protected static final Logger log = Logger.getLogger(KairosRequest.class.getName());
	
	public KairosRequest() {
		
	}
	
	public JSONObject send() throws IOException {
		throw new UnsupportedOperationException();
	}
	
	protected HTTPRequest setHeaders(HTTPRequest request) {
		request.setHeader(new HTTPHeader("Content-Type", "application/json; charset=UTF-8"));
		request.setHeader(new HTTPHeader("app_id", APP_ID));
		request.setHeader(new HTTPHeader("app_key", APP_KEY));
		return request;
	}

}

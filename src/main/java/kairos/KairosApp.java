package kairos;

import java.io.IOException;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kairos.requests.EnrollRequest;
import kairos.requests.RecognizeRequest;

public class KairosApp {
	private static final Logger log = Logger.getLogger(KairosApp.class.getName());

	public KairosApp() {}

	/**
	 * This function is used to enroll a person in a group / gallery.
	 * @param b64Image - String : Image converted to String in base64 format that is used to enroll person with kairos.
	 * @param subjectID - String : id that identifies this person.
	 * @param gallery - String : name of the gallery this person belongs to. Can be case-sensitive. Please ensure the gallery_name is supplied correctly.
	 */
	public void enroll(String imageUrl, String subjectId,  String gallery) {
		log.warning("Enrolling!");
		EnrollRequest request = new EnrollRequest();
		request.imageUrl = imageUrl;
		request.subjectId = subjectId;
		request.gallery = gallery;
		System.out.println("Enrolling into gallery: " + gallery);
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
	public String recognize(String imageUrl, String gallery) {
		RecognizeRequest request = new RecognizeRequest();
		request.imageUrl = imageUrl;
		request.gallery = gallery;
		log.warning("set");
		try {
			JSONObject json = request.send();
			log.warning("request sent");
			if (json != null) {
				return getSubjectId(json);
			}
		} catch (IOException | JSONException e) {
			log.warning("Something went wrong while processing the request please try again.");
		}
		return null;
	} // End function detectFaces.

	private String getSubjectId(JSONObject json) throws JSONException {
		JSONArray images = new JSONArray();
		images = json.getJSONArray("images");
		JSONObject image = images.getJSONObject(0);
		JSONObject transaction = image.getJSONObject("transaction");
		return transaction.getString("subject");
	}
}

package com.testjson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.os.Bundle;
import android.util.Log;
import android.app.Activity;

public class MainActivity extends Activity {

	private String url = "http://sipacontact-sky86.rhcloud.com/contact.php/";

	// JSON node name
	private String TAG_CONTACT = "contact";
	private String TAG_TITLE = "title";
	private String TAG_FIRSTNAME = "firstname";
	private String TAG_LASTNAME = "lastname";
	private String TAG_NICKNAME = "nickname";
	private String TAG_DEPARTMENT = "department";
	private String TAG_POSITION = "position";
	private String TAG_EMAIL = "email";
	private String TAG_MOBILE = "mobile";
	private String TAG_TELEPHONE = "telephone";

	// contacts JSONArray
	JSONArray contact = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Creating JSONParser
		JsonPaser jparser = new JsonPaser();

		// get JSONObject from url
		JSONObject json = jparser.getJSONFromUrl(url);

		try {

			contact = json.getJSONArray(TAG_CONTACT);

			for (int i = 0; i < contact.length(); i++) {

				JSONObject c = contact.getJSONObject(i);

				String title = c.getString(TAG_TITLE);
				String firstname = c.getString(TAG_FIRSTNAME);
				String lastname = c.getString(TAG_LASTNAME);
				String nickname = c.getString(TAG_NICKNAME);
				String department = c.getString(TAG_DEPARTMENT);
				String position = c.getString(TAG_POSITION);
				String email = c.getString(TAG_EMAIL);
				String mobile = c.getString(TAG_MOBILE);
				String telephone = c.getString(TAG_TELEPHONE);

				Log.d("Log Contact", "Data: \n" + title + " " + firstname + " "
						+ lastname + " " + nickname + " " + department + " "
						+ position + " " + email + " " + mobile + " "
						+ telephone);

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}

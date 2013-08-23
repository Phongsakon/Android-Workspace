package com.ggplus.sky86.sipaphonebook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ggplus.sky86.helper.ConnectionDetector;
import com.ggplus.sky86.helper.JSONParser;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ContactActivity extends ListActivity {

	// Connection detector
	ConnectionDetector cd;

	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();

	// Progress Dialog
	private ProgressDialog Dialog;

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();

	ArrayList<HashMap<String, String>> contactList;

	JSONArray departments = null;

	// department id
	String department_id, department_name;

	private static final String URL_DEPARTMENT = "http://sipacontact-sky86.rhcloud.com/department_contact.php";

	// ALL JSON node names
	private static final String TAG_CONTACT = "contact";
	private static final String TAG_ID = "id";
	private static final String TAG_DEPARTMENTNAME = "departmentname";
	private static final String TAG_TITLE = "title";
	private static final String TAG_FIRSTNAME = "firstname";
	private static final String TAG_LASTNAME = "lastname";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_list);

		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(ContactActivity.this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}

		// Get department id
		Intent i = getIntent();
		department_id = i.getStringExtra("department_id");

		// Hashmap for ListView
		contactList = new ArrayList<HashMap<String, String>>();

		// Loading contact in Background Thread
		new LoadContact().execute();

		// get listview
		ListView lv = getListView();

		lv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {

				Intent i = new Intent(getApplicationContext(),
						SingleContactActivity.class);

				String department_id = ((TextView) view
						.findViewById(R.id.department_id)).getText().toString();
				String contact_id = ((TextView) view
						.findViewById(R.id.contact_id)).getText().toString();

				i.putExtra("department_id", department_id);
				i.putExtra("contact_id", contact_id);
				Log.d("Contact ID", contact_id);
				startActivity(i);

			}
		});
	}

	// Background Async Task
	class LoadContact extends AsyncTask<String, String, String> {

		// Show dialog
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Dialog = new ProgressDialog(ContactActivity.this);
			Dialog.setMessage("Loading contact ...");
			Dialog.setIndeterminate(false);
			Dialog.setCancelable(false);
			Dialog.show();
		}

		// JSON parsing
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			// post department id as GET parameter
			params.add(new BasicNameValuePair(TAG_ID, department_id));

			// getting JSON string from URL
			String json = jsonParser.makeHttpRequest(URL_DEPARTMENT, "GET",
					params);

			// Check log cat for JSON reponse
			Log.d("Contact List JSON: ", json);

			try {
				JSONObject jObj = new JSONObject(json);
				if (jObj != null) {
					String department_id = jObj.getString(TAG_ID);
					department_name = jObj.getString(TAG_DEPARTMENTNAME);
					departments = jObj.getJSONArray(TAG_CONTACT);

					if (departments != null) {
						// looping through All data
						for (int i = 0; i < departments.length(); i++) {
							JSONObject c = departments.getJSONObject(i);

							// Storing each json item in variable
							String contact_id = c.getString(TAG_ID);
							// track no - increment i value
							String title = c.getString(TAG_TITLE);
							String firstname = c.getString(TAG_FIRSTNAME);
							String lastname = c.getString(TAG_LASTNAME);

							// creating new HashMap
							HashMap<String, String> map = new HashMap<String, String>();

							// adding each child node to HashMap key => value
							map.put("department_id", department_id);
							map.put(TAG_ID, contact_id);
							map.put(TAG_TITLE, title);
							map.put(TAG_FIRSTNAME, firstname);
							map.put(TAG_LASTNAME, lastname);

							// adding HashList to ArrayList
							contactList.add(map);
						}
					} else {
						Log.d("Departments: ", "null");
					}
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		// Dismiss dialog
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all tracks
			Dialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					// Update JSON into listview
					ListAdapter adapter = new SimpleAdapter(
							ContactActivity.this, contactList,
							R.layout.contact_list_item, new String[] {
									"department_id", TAG_ID, TAG_TITLE,
									TAG_FIRSTNAME, TAG_LASTNAME }, new int[] {
									R.id.department_id, R.id.contact_id,
									R.id.txt_title, R.id.txt_firstname,
									R.id.txt_lastname });
					// updating listview
					setListAdapter(adapter);

					// Change Activity Title
					setTitle(department_name);
				}
			});

		}

	}

}

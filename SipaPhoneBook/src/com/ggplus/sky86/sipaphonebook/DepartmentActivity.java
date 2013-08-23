package com.ggplus.sky86.sipaphonebook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
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

public class DepartmentActivity extends ListActivity {

	// Connection detector
	ConnectionDetector cd;

	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();

	// Progress Dialog
	private ProgressDialog Dialog;

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();

	ArrayList<HashMap<String, String>> departmentsList;

	JSONArray departments = null;

	private static final String URL_DEPARTMENT = "http://sipacontact-sky86.rhcloud.com/department.php/";

	// ALL JSON node names
	private static final String TAG_ID = "id";
	private static final String TAG_DEPARTMENTNAME = "department_name";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.department_list);

		cd = new ConnectionDetector(getApplicationContext());

		// Check for internet connection
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(DepartmentActivity.this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}

		// Hashmap for ListView
		departmentsList = new ArrayList<HashMap<String, String>>();

		// Loading Albums JSON in Background Thread
		new LoadDepartment().execute();

		// get listview
		ListView lv = getListView();

		// Listview item click
		lv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				// on selecting a single department
				// department
				Intent i = new Intent(getApplicationContext(),
						ContactActivity.class);

				// send department
				String department_id = ((TextView) view
						.findViewById(R.id.department_id)).getText().toString();
				i.putExtra("department_id", department_id);
				startActivity(i);
				Log.d("Department ID", department_id);
				
			}
		});
	}

	
	//Background Async Task 
	class LoadDepartment extends AsyncTask<String, String, String> {

		
		//Before starting background thread Show Progress Dialog
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Dialog = new ProgressDialog(DepartmentActivity.this);
			Dialog.setMessage("Loading Department ...");
			Dialog.setIndeterminate(false);
			Dialog.setCancelable(false);
			Dialog.show();
		}
		
		//getting JSON
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			// getting JSON string from URL
			String json = jsonParser.makeHttpRequest(URL_DEPARTMENT, "GET",
					params);

			// Check log cat for JSON response
			Log.d("Department JSON: ", "> " + json);

			try {
				departments = new JSONArray(json);

				if (departments != null) {
					// looping through All department
					for (int i = 0; i < departments.length(); i++) {
						JSONObject c = departments.getJSONObject(i);

						// Storing each json item values in variable
						String department_id = c.getString(TAG_ID);
						String departmentName = c.getString(TAG_DEPARTMENTNAME);

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(TAG_ID, department_id);
						map.put(TAG_DEPARTMENTNAME, departmentName);

						// adding HashList to ArrayList
						departmentsList.add(map);
					}
				} else {
					Log.d("Department: ", "null");
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		//Dismiss dialog after success doInBackground
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all albums
			Dialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					
					//Updating parsed JSON data into ListView
					ListAdapter adapter = new SimpleAdapter(
							DepartmentActivity.this, departmentsList,
							R.layout.department_list_item,
							new String[] { TAG_ID, TAG_DEPARTMENTNAME },
							new int[] {R.id.department_id, R.id.txt_departmentname });

					// updating listview
					setListAdapter(adapter);
				}
			});

		}

	}

}

package com.ggplus.sky86.sipaphonebook;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ggplus.sky86.helper.ConnectionDetector;
import com.ggplus.sky86.helper.JSONParser;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SingleContactActivity extends Activity {

	// Connection detector
	ConnectionDetector cd;

	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();

	// Progress Dialog
	private ProgressDialog Dialog;

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();

	JSONArray departments = null;

	// Id
	String department_id = null;
	String contact_id = null;

	String person_id, title, firstname, lastname, nickname, department,
			position, email, mobile, telephone;

	// single song JSON url
	private static final String URL_CONTACT = "http://sipacontact-sky86.rhcloud.com/contact.php/";

	// ALL JSON node names
	private static final String TAG_PERSONID = "pid";
	private static final String TAG_TITLE = "title";
	private static final String TAG_FIRSTNAME = "firstname";
	private static final String TAG_LASTNAME = "lastname";
	private static final String TAG_NICKNAME = "nickname";
	private static final String TAG_DEPARTMENT = "department";
	private static final String TAG_POSITION = "position";
	private static final String TAG_EMAIL = "email";
	private static final String TAG_MOBILE = "mobile";
	private static final String TAG_TELEPHONE = "telephone";

	SQLiteDatabase db;

	// Create Database
	MyDBClass myDB = new MyDBClass(this);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_contact_activity);

		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(SingleContactActivity.this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}

		// Get id
		Intent i = getIntent();
		department_id = i.getStringExtra("department_id");
		contact_id = i.getStringExtra("contact_id");

		myDB.getWritableDatabase();

		// calling background thread
		new LoadSingleContact().execute();

	}

	// Background Async Task
	class LoadSingleContact extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Dialog = new ProgressDialog(SingleContactActivity.this);
			Dialog.setMessage("Loading data ...");
			Dialog.setIndeterminate(false);
			Dialog.setCancelable(false);
			Dialog.show();
		}

		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			// post id as GET parameters
			params.add(new BasicNameValuePair("departmentname", department_id));
			params.add(new BasicNameValuePair("contact", contact_id));

			// getting JSON string from URL
			String json = jsonParser
					.makeHttpRequest(URL_CONTACT, "GET", params);

			// Check log cat for JSON reponse
			Log.d("Single Contact JSON: ", json);

			try {
				JSONObject jObj = new JSONObject(json);
				if (jObj != null) {
					person_id = jObj.getString(TAG_PERSONID);
					title = jObj.getString(TAG_TITLE);
					firstname = jObj.getString(TAG_FIRSTNAME);
					lastname = jObj.getString(TAG_LASTNAME);
					nickname = jObj.getString(TAG_NICKNAME);
					department = jObj.getString(TAG_DEPARTMENT);
					position = jObj.getString(TAG_POSITION);
					email = jObj.getString(TAG_EMAIL);
					mobile = jObj.getString(TAG_MOBILE);
					telephone = jObj.getString(TAG_TELEPHONE);
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String file_url) {
			// dismiss dialog
			Dialog.dismiss();

			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {

					TextView txt_person_id = (TextView) findViewById(R.id.txt_person_id);
					TextView txt_title = (TextView) findViewById(R.id.text_title);
					TextView txt_firstname = (TextView) findViewById(R.id.text_firstname);
					TextView txt_lastname = (TextView) findViewById(R.id.text_lastname);
					TextView txt_nickname = (TextView) findViewById(R.id.text_nickname);
					TextView txt_department = (TextView) findViewById(R.id.text_department);
					TextView txt_position = (TextView) findViewById(R.id.text_position);
					TextView txt_email = (TextView) findViewById(R.id.text_email);
					TextView txt_mobile = (TextView) findViewById(R.id.text_mobile);
					TextView txt_telephone = (TextView) findViewById(R.id.text_telephone);

					// displaying data in view
					txt_person_id.setText(person_id);
					txt_title.setText(title);
					txt_firstname.setText(firstname);
					txt_lastname.setText(lastname);
					txt_nickname.setText(nickname);
					txt_department.setText(department);
					txt_position.setText(position);
					txt_email.setText(email);
					txt_mobile.setText(mobile);
					txt_telephone.setText(telephone);

					final String toEmail = txt_email.getText().toString();
					final String mobile = txt_mobile.getText().toString();
					final String telephone = txt_telephone.getText().toString();

					Button btnMobileCall = (Button) findViewById(R.id.btn_mobilecall);
					Button btnTelephoneCall = (Button) findViewById(R.id.btn_telephonecall);
					Button btnSendEmail = (Button) findViewById(R.id.btn_sendmail);

					// Button click for call to mobile number
					btnMobileCall
							.setOnClickListener(new View.OnClickListener() {

								@Override
								public void onClick(View v) {

									Intent callIntent = new Intent(
											Intent.ACTION_CALL);
									callIntent.setData(Uri.parse("tel:"
											+ mobile));
									startActivity(callIntent);

								}
							});

					// Button click for call to telephone number
					btnTelephoneCall
							.setOnClickListener(new View.OnClickListener() {

								@Override
								public void onClick(View v) {

									Intent callIntent = new Intent(
											Intent.ACTION_CALL);
									callIntent.setData(Uri.parse("tel:"
											+ telephone));
									startActivity(callIntent);

								}
							});

					btnSendEmail.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {

							Intent email = new Intent(Intent.ACTION_SEND);
							email.putExtra(Intent.EXTRA_EMAIL,
									new String[] { toEmail });

							// need this to prompts email client only
							email.setType("message/rfc822");

							startActivity(Intent.createChooser(email,
									"Choose an Email client :"));

						}
					});

					// Change Activity Title
					setTitle(title + firstname + "  " + lastname);
				}
			});

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.add_favorite:
			// Statement 1
			long flg1 = myDB.InsertData(person_id, title, firstname, lastname,
					nickname, department, position, email, mobile, telephone);
			if (flg1 > 0) {
				Toast.makeText(SingleContactActivity.this,
						"Add to Favorite Successfully", Toast.LENGTH_LONG)
						.show();
			} else {
				Toast.makeText(SingleContactActivity.this,
						"Add to Favorite Failed", Toast.LENGTH_LONG).show();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);

		}
	}
}

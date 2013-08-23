package com.gplus.sky86.sipawifi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class SipaWifiLogin extends SherlockActivity implements TextWatcher {

	private EditText editTextUsername;
	private EditText editTextPassword;
	private String username;
	private String password;
	private WifiManager mgr;
	private String wifiSSID1 = "SIPAWIFI-TDD";
	private String wifiSSID2 = "SIPAWIFI-ADMIN";
	private String wifiSSID3 = "SIPAWIFI-LAW";
	private String wifiSSID4 = "SIPA-FINANCE";
	private String wifiSSID5 = "SIPAWIFI-DED";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*
		 * getWindow().setFormat(PixelFormat.RGBA_8888);
		 * requestWindowFeature(Window.FEATURE_NO_TITLE);
		 */
		setContentView(R.layout.main);

		mgr = (WifiManager) (getSystemService(Context.WIFI_SERVICE));

		editTextUsername = (EditText) findViewById(R.id.editText_username);
		editTextPassword = (EditText) findViewById(R.id.editText_password);

		editTextUsername.addTextChangedListener(this);
		editTextPassword.addTextChangedListener(this);

		Button buttonConnect = (Button) findViewById(R.id.btn_login);
		buttonConnect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				WifiInfo info;

				// stop the checking task if it is running
				Intent i = new Intent(SipaWifiLogin.this, SipaWifiService.class);
				i.putExtra("mode", 1);
				startService(i);

				if (!mgr.isWifiEnabled()) {
					new AlertDialog.Builder(SipaWifiLogin.this)
							.setTitle("WiFi is off")
							.setMessage("Please turn on your WiFi")
							.setPositiveButton("OK",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											startActivity(new Intent(
													Settings.ACTION_WIFI_SETTINGS));
										}
									}).show();
				} else {
					info = mgr.getConnectionInfo();
					if (info == null) {
						Toast.makeText(SipaWifiLogin.this,
								"No WiFi connection, please try again later",
								Toast.LENGTH_SHORT).show();
					} else {
						if (info.getSSID() == null) {
							Toast.makeText(
									SipaWifiLogin.this,
									"No WiFi connection, please try again later",
									Toast.LENGTH_SHORT).show();
						} else {
							Log.d("WiFi Info", info.getSSID());
							if (Build.VERSION.SDK_INT >= 17) {
								if (info.getSSID().equals(
										"\"" + wifiSSID1 + "\"")
										|| info.getSSID().equals(
												"\"" + wifiSSID2 + "\"")
										|| info.getSSID().equals(
												"\"" + wifiSSID3 + "\"")
										|| info.getSSID().equals(
												"\"" + wifiSSID4 + "\"")
										|| info.getSSID().equals(
												"\"" + wifiSSID5 + "\"")) {
									i = new Intent(SipaWifiLogin.this,
											SipaWifiService.class);
									i.putExtra("mode", 0);
									startService(i);
									Toast.makeText(
											SipaWifiLogin.this,
											"Connected to SIPA WIFI successful",
											Toast.LENGTH_SHORT).show();
									startActivity(new Intent(
											Intent.ACTION_VIEW,
											Uri.parse("http://intranet.sipa.or.th/")));
								} else {
									Toast.makeText(SipaWifiLogin.this,
											"Connected to other WiFi",
											Toast.LENGTH_SHORT).show();
								}
							} else {
								if (info.getSSID().equals(wifiSSID1)
										|| info.getSSID().equals(wifiSSID2)
										|| info.getSSID().equals(wifiSSID3)
										|| info.getSSID().equals(wifiSSID4)
										|| info.getSSID().equals(wifiSSID5)) {
									i = new Intent(SipaWifiLogin.this,
											SipaWifiService.class);
									i.putExtra("mode", 0);
									startService(i);
									Toast.makeText(
											SipaWifiLogin.this,
											"Connected to SIPA WIFI successful",
											Toast.LENGTH_SHORT).show();
									startActivity(new Intent(
											Intent.ACTION_VIEW,
											Uri.parse("http://intranet.sipa.or.th/")));
								} else {
									Toast.makeText(SipaWifiLogin.this,
											"Connected to other WiFi",
											Toast.LENGTH_SHORT).show();
								}
							}
						}
					}
				}
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		username = pref.getString("auth_user", "");
		password = pref.getString("auth_pass", "");
		editTextUsername.setText(username);
		editTextPassword.setText(password);
	}

	private void changePreference() {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = pref.edit();
		editor.putString("auth_user", editTextUsername.getText().toString());
		editor.putString("auth_pass", editTextPassword.getText().toString());
		editor.commit();
	}

	@Override
	public void afterTextChanged(Editable s) {
		changePreference();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.activity_itemlist, menu);

		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		
		switch (item.getItemId()) {
		case R.id.help:
			new AlertDialog.Builder(SipaWifiLogin.this).setIcon(R.drawable.ic_action_help)
					.setTitle("Help")
					.setMessage(
							"SIPA WiFi คือแอพพลิเคชั่นที่ทำการล็อกอิน\nผ่านหน้า Authentication ให้อัติโนมัติเมื่อทำการเชื่อมต่อกับ Wifi ภายในสำนักงาน\nวิธีใช้งาน :\nกรอก Username และ Password จากนั้นทำการกดปุ่ม Login เพื่อเชื่อมต่อ Wifi ของ SIPA เมื่อเชื่อมต่อสำเร็จก็จะมี Notification ขึ้นมาเตือน")
					.show();
			return true;
		case R.id.about:
			new AlertDialog.Builder(SipaWifiLogin.this).setIcon(R.drawable.ic_action_about)
					.setTitle("About")
					.setMessage(
							"App SIPA WiFi Auto Login\nVersion: 1.0\nContact: am4s3kt@gmail.com\nPowered by Opensource SIPA")
					.show();
			return true;
		}
		return super.onOptionsItemSelected(item);

	}
}

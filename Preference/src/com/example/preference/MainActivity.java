package com.example.preference;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// load data from preference
		
		final SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(this);
		
		Button bntShowData = (Button) findViewById(R.id.button1);
		bntShowData.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String username = preference.getString("username", "n/a");
				String password = preference.getString("password", "n/a");
				
				showPref(username,password);
				
			}
		});
		
	}

	protected void showPref(String username, String password) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "preference data = " + username +" and " + password, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case R.id.action_settings:
				Intent intent = new Intent(this,PreferenceActivity.class);
				startActivity(intent);
			break;
		}

		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

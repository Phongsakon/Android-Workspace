package com.example.preference;

import android.os.Bundle;

public class PreferenceActivity extends android.preference.PreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.preference);
	}
	

}

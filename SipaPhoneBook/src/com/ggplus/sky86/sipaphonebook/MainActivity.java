package com.ggplus.sky86.sipaphonebook;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

	// TabSpec Names
	private static final String CONTACT_SPEC = "Contact";
	private static final String FAVORITE_SPEC = "Favorite";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		TabHost tabHost = getTabHost();

		// Contact Tab
		TabSpec contactSpec = tabHost.newTabSpec(CONTACT_SPEC);
		// Tab Icon
		contactSpec.setIndicator(CONTACT_SPEC,
				getResources().getDrawable(R.drawable.icon_contact));
		Intent contactIntent = new Intent(this, DepartmentActivity.class);
		// Tab Content
		contactSpec.setContent(contactIntent);

		// Favorite Tab
		TabSpec favoriteSpec = tabHost.newTabSpec(FAVORITE_SPEC);
		// Tab Icon
		favoriteSpec.setIndicator(FAVORITE_SPEC,
				getResources().getDrawable(R.drawable.icon_favorite));
		Intent favoriteIntent = new Intent(this, FavoriteActivity.class);
		// Tab Content
		favoriteSpec.setContent(favoriteIntent);

		// Adding all TabSpec to TabHost
		tabHost.addTab(contactSpec); // Adding Contact tab
		tabHost.addTab(favoriteSpec); // Adding Favorite tab

	}
}

package com.ggplus.sky86.sipaphonebook;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class FavoriteActivity extends Activity {

	ArrayList<HashMap<String, String>> favoriteList;
	String[] popTxt = { "Delete" };

	MyDBClass myDb = new MyDBClass(this);
	
	SimpleAdapter adapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favorite_list);

		// get Data from SQLite
		favoriteList = myDb.SelectAllData();
		ListView listview = (ListView) findViewById(R.id.listview_favo);

		adapter = new SimpleAdapter(
				FavoriteActivity.this,
				favoriteList,
				R.layout.favorite_list_item,

				new String[] { "favoriteID", "Title", "Firstname", "Lastname" },
				new int[] { R.id.favorite_id, R.id.favorite_txt_title,
						R.id.favorite_txt_firstname, R.id.favorite_txt_lastname });
		listview.setAdapter(adapter);
		
		
		registerForContextMenu(listview);

		listview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				// TODO Auto-generated method stub
				Intent newActivity = new Intent(FavoriteActivity.this,
						SingleContactFavoriteActivity.class);
				newActivity.putExtra("FavoriteID", favoriteList.get(position)
						.get("favoriteID").toString());
				startActivity(newActivity);

			}

		});
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		menu.setHeaderTitle("Delete : "
				+ favoriteList.get(info.position).get("Firstname").toString());
		String[] menuItems = popTxt;

		for (int i = 0; i < menuItems.length; i++) {
			menu.add(Menu.NONE, i, i, menuItems[i]);

		}

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		int menuItemIndex = item.getItemId();
		String[] menuItems = popTxt;
		String popTxtname = menuItems[menuItemIndex];
		String FavoID = favoriteList.get(info.position).get("favoriteID")
				.toString();
		;

		// Check Event Command
		long flg = myDb.DeleteData(FavoID);
		if (flg > 0 && "Delete".equals(popTxtname)) {
			Toast.makeText(FavoriteActivity.this, "Delete Successfully",
					Toast.LENGTH_LONG).show();
			adapter.notifyDataSetChanged();
		} else {
			Toast.makeText(FavoriteActivity.this, "Delete Failed.",
					Toast.LENGTH_LONG).show();
		}
		return true;
	}
}

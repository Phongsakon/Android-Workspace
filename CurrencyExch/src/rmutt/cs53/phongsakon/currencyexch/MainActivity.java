package rmutt.cs53.phongsakon.currencyexch;

import java.text.NumberFormat;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ListView;

public class MainActivity extends Activity {

	public ListView listview;
	private ListViewAdapter listViewAdapter;

	public ArrayList<ListEntry> entry;

	private Animation animation;
	private LayoutAnimationController layoutAnimetionControl;

	private int[] drawable = { R.drawable.australia_flag,
			R.drawable.cannada_flag, R.drawable.swiss_flag,
			R.drawable.denmark_flag, R.drawable.euro_flag, R.drawable.uk_flag,
			R.drawable.hongkong_flag, R.drawable.japan_flag,
			R.drawable.mexico_flag, R.drawable.newzealand_flag,
			R.drawable.philippin_flag, R.drawable.sweden_flag,
			R.drawable.singapore_flag, R.drawable.thailand_flag,
			R.drawable.usa_flag, R.drawable.south_african_flag };

	private String[] units = { "AUD", "CAD", "CHF", "DKK", "EUR", "GBP", "HKD",
			"JPY", "MXN", "NZD", "PHP", "SEK", "SGD", "THB", "USD", "ZAR" };

	private String[] titles = { "Australian Dollar", "Cannadian Dollar",
			"Swiss Franc", "Danish Krone", "Euro", "Pound Sterling",
			"Hong Kong Dollar", "Japanese Yen", "Maxican Peso",
			"New Zealand Dollar", "Philippine Peso", "Swedish Krona",
			"Singapore Dollar", "Thailand Baht", "United States Dollar",
			"South African Rand" };

	private String[] runRate = { "AUD", "CAD", "CHF", "DKK", "EUR", "GBP",
			"HKD", "JPY", "MXN", "NZD", "PHP", "SEK", "SGD", "THB", "USD",
			"ZAR" };

	private String jString = "rate";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// View Match
		listview = (ListView) findViewById(R.id.main_listview);

		// Load Animation
		animation = AnimationUtils.loadAnimation(MainActivity.this,
				R.anim.listview_animation);
		layoutAnimetionControl = new LayoutAnimationController(animation);

		// Data
		entry = new ArrayList<ListEntry>();

		String sourceUnits = getIntent().getStringExtra("myUnitRate");

		// Add Data
		for (int i = 0; i < units.length; i++) {

			if (sourceUnits == null) {

				String strRate = "Select";

				ListEntry listEntry = new ListEntry();
				listEntry.setDrawables(getResources().getDrawable(drawable[i]));
				listEntry.setUnit(units[i]);
				listEntry.setTitle(titles[i]);
				listEntry.setRate(strRate);

				entry.add(listEntry);

			} else {

				String url = "http://currency-api.appspot.com/api/"
						+ runRate[i] + "/" + sourceUnits
						+ ".json?key=9fcdd1e2105e446319825d0c173d8010fa468a9c";

				JSONPaser jPaser = new JSONPaser();
				JSONObject json = jPaser.getJSONFromUrl(url);

				String strRate = "N/A";

				NumberFormat nf = NumberFormat.getInstance();
				nf.setMaximumFractionDigits(2);
				try {

					strRate = nf.format(json.getDouble(jString));
					Log.d("Log Tag", strRate);

				} catch (JSONException e) { // TODO Auto-generated catch block
					e.printStackTrace();

				}

				ListEntry listEntry = new ListEntry();
				listEntry.setDrawables(getResources().getDrawable(drawable[i]));
				listEntry.setUnit(units[i]);
				listEntry.setTitle(titles[i]);
				listEntry.setRate(strRate);

				entry.add(listEntry);

			}

		}

		// Setup Adapter
		listViewAdapter = new ListViewAdapter();

		listview.setAdapter(listViewAdapter);

		listview.setLayoutAnimation(layoutAnimetionControl);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position + 1) {
				case 1:

					Intent audActivity = new Intent(MainActivity.this,
							MainActivity.class);
					String source1 = "AUD";
					audActivity.putExtra("myUnitRate", source1);
					startActivity(audActivity);
					this.finish();

					break;
				case 2:
					Intent cadActivity = new Intent(MainActivity.this,
							MainActivity.class);
					String source2 = "CAD";
					cadActivity.putExtra("myUnitRate", source2);
					startActivity(cadActivity);
					this.finish();
					break;
				case 3:
					Intent chfActivity = new Intent(MainActivity.this,
							MainActivity.class);
					String source3 = "CHF";
					chfActivity.putExtra("myUnitRate", source3);
					startActivity(chfActivity);
					this.finish();
					break;
				case 4:
					Intent dkkActivity = new Intent(MainActivity.this,
							MainActivity.class);
					String source4 = "DKK";
					dkkActivity.putExtra("myUnitRate", source4);
					startActivity(dkkActivity);
					this.finish();
					break;
				case 5:
					Intent eurActivity = new Intent(MainActivity.this,
							MainActivity.class);
					String source5 = "EUR";
					eurActivity.putExtra("myUnitRate", source5);
					startActivity(eurActivity);
					this.finish();
					break;
				case 6:
					Intent gbpActivity = new Intent(MainActivity.this,
							MainActivity.class);
					String source6 = "GBP";
					gbpActivity.putExtra("myUnitRate", source6);
					startActivity(gbpActivity);
					this.finish();
					break;
				case 7:
					Intent hkdActivity = new Intent(MainActivity.this,
							MainActivity.class);
					String source7 = "HKD";
					hkdActivity.putExtra("myUnitRate", source7);
					startActivity(hkdActivity);
					this.finish();
					break;
				case 8:
					Intent jpyActivity = new Intent(MainActivity.this,
							MainActivity.class);
					String source8 = "JPY";
					jpyActivity.putExtra("myUnitRate", source8);
					startActivity(jpyActivity);
					this.finish();
					break;
				case 9:
					Intent mxnActivity = new Intent(MainActivity.this,
							MainActivity.class);
					String source9 = "MXN";
					mxnActivity.putExtra("myUnitRate", source9);
					startActivity(mxnActivity);
					this.finish();
					break;
				case 10:
					Intent nzdActivity = new Intent(MainActivity.this,
							MainActivity.class);
					String source10 = "NZD";
					nzdActivity.putExtra("myUnitRate", source10);
					startActivity(nzdActivity);
					this.finish();
					break;
				case 11:
					Intent phpActivity = new Intent(MainActivity.this,
							MainActivity.class);
					String source11 = "PHP";
					phpActivity.putExtra("myUnitRate", source11);
					startActivity(phpActivity);
					this.finish();
					break;
				case 12:
					Intent sekActivity = new Intent(MainActivity.this,
							MainActivity.class);
					String source12 = "SEK";
					sekActivity.putExtra("myUnitRate", source12);
					startActivity(sekActivity);
					this.finish();
					break;
				case 13:
					Intent sgdActivity = new Intent(MainActivity.this,
							MainActivity.class);
					String source13 = "SGD";
					sgdActivity.putExtra("myUnitRate", source13);
					startActivity(sgdActivity);
					this.finish();
					break;
				case 14:
					Intent thbActivity = new Intent(MainActivity.this,
							MainActivity.class);
					String source14 = "THB";
					thbActivity.putExtra("myUnitRate", source14);
					startActivity(thbActivity);
					this.finish();
					break;
				case 15:
					Intent usdActivity = new Intent(MainActivity.this,
							MainActivity.class);
					String source15 = "USD";
					usdActivity.putExtra("myUnitRate", source15);
					startActivity(usdActivity);
					this.finish();
					break;
				case 16:
					Intent zarActivity = new Intent(MainActivity.this,
							MainActivity.class);
					String source16 = "ZAR";
					zarActivity.putExtra("myUnitRate", source16);
					startActivity(zarActivity);
					this.finish();
					break;
				}

			}

			private void finish() {
				// TODO Auto-generated method stub

			}
		});

	}

	private class ListViewAdapter extends BaseAdapter {

		private ListViewHolder holder;

		@Override
		public int getCount() {
			return entry.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			// create View
			convertView = LayoutInflater.from(MainActivity.this).inflate(
					R.layout.listview_layout, null);

			// View Match
			holder = new ListViewHolder();
			holder.image = (ImageView) convertView
					.findViewById(R.id.listview_image);
			holder.unit = (TextView) convertView
					.findViewById(R.id.listview_unit);
			holder.title = (TextView) convertView
					.findViewById(R.id.listview_title);
			holder.rates = (TextView) convertView
					.findViewById(R.id.listview_rate);

			// View Setting
			// Set Title
			if (entry.get(position).getTitle() != null) {
				holder.title.setText(entry.get(position).getTitle());
			}

			// Set Image
			if (entry.get(position).getDrawables() != null) {
				holder.image.setImageDrawable(entry.get(position)
						.getDrawables());
			}

			// Set Unit
			if (entry.get(position).getUnit() != null) {
				holder.unit.setText(entry.get(position).getUnit());
			}

			// Set Rate
			if (entry.get(position).getUnit() != null) {
				holder.rates.setText(entry.get(position).getRate());
			}

			return convertView;
		}

		public class ListViewHolder {

			public ImageView image;
			public TextView unit;
			public TextView title;
			public TextView rates;
		}
	}

}

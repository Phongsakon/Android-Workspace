package com.gplus.phongsakon.mangarefresh;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.gplus.phongsakon.helper.RSSItem;
import com.gplus.phongsakon.helper.RSSPaser;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FeedMangaChapter extends ListActivity {

	private ArrayList<RSSItem> itemlist = null;
	private RSSListAdaptor rssadaptor = null;

	String link;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.RGBA_8888);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chapter_listview);

		itemlist = new ArrayList<RSSItem>();

		Intent intent = getIntent();
		link = intent.getStringExtra("link");

		new RetrieveRSSFeeds().execute();

		ListView listview = getListView();

		listview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				// TODO Auto-generated method stub

				RSSItem data = itemlist.get(position);
				String chapterLink = data.getLink();
				Toast.makeText(FeedMangaChapter.this, "Link => " + chapterLink,
						Toast.LENGTH_LONG).show();
				Intent i = new Intent(FeedMangaChapter.this, ChapterImageList.class);
				i.putExtra("chapterLink", chapterLink);
				startActivity(i);
			}
		});
	}

	private void retrieveRSSFeed(String urlToRssFeed, ArrayList<RSSItem> list) {
		try {
			URL url = new URL(urlToRssFeed);
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			XMLReader xmlreader = parser.getXMLReader();
			RSSPaser theRssHandler = new RSSPaser(list);

			xmlreader.setContentHandler(theRssHandler);

			InputSource is = new InputSource(url.openStream());

			xmlreader.parse(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class RetrieveRSSFeeds extends AsyncTask<Void, Void, Void> {
		private ProgressDialog progress = null;

		@Override
		protected Void doInBackground(Void... params) {
			retrieveRSSFeed("http://www.niceoppai.net/manga-rss/" + link + "/",
					itemlist);

			rssadaptor = new RSSListAdaptor(FeedMangaChapter.this,
					R.layout.chapter_list_item, itemlist);

			return null;
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected void onPreExecute() {
			progress = ProgressDialog.show(FeedMangaChapter.this, null,
					"Loading...");
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Void result) {
			setListAdapter(rssadaptor);

			progress.dismiss();

			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}
	}

	private class RSSListAdaptor extends ArrayAdapter<RSSItem> {
		private List<RSSItem> objects = null;

		public RSSListAdaptor(Context context, int textviewid,
				List<RSSItem> objects) {
			super(context, textviewid, objects);

			this.objects = objects;
		}

		@Override
		public int getCount() {
			return ((null != objects) ? objects.size() : 0);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public RSSItem getItem(int position) {
			return ((null != objects) ? objects.get(position) : null);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;

			if (null == view) {
				LayoutInflater vi = (LayoutInflater) FeedMangaChapter.this
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = vi.inflate(R.layout.chapter_list_item, null);
			}

			RSSItem data = objects.get(position);

			if (null != data) {
				TextView title = (TextView) view.findViewById(R.id.txtTitle);
				TextView date = (TextView) view.findViewById(R.id.txtDate);
				// TextView description =
				// (TextView)view.findViewById(R.id.txtDescription);

				title.setText(data.getTitle());
				date.setText("on " + data.getDate());
				// description.setText(data.description);
			}

			return view;
		}
	}

}

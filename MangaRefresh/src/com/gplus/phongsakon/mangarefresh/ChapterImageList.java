package com.gplus.phongsakon.mangarefresh;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.ListView;

public class ChapterImageList extends Activity {

	ListView listview;
	ImageAdapter imageAdapter;
	ArrayList<HashMap<String, String>> mangaList;
	Document doc;

	static final String[] getImageLink = new String[60];
	static final String getImage = null;
	String chapterLink;
	
	int count;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.chapteimage__listview);
		
		setProgressBarIndeterminateVisibility(true);
		new LoadChapterImage().execute();
	}

	class LoadChapterImage extends AsyncTask<Object, Integer, Object> {

		@Override
		protected Object doInBackground(Object... params) {

			ArrayList<HashMap<String, String>> mangaList = new ArrayList<HashMap<String, String>>();
			
			Intent intent = getIntent();
			String chapterLink = intent.getStringExtra("chapterLink");

			try {
				doc = Jsoup.connect(chapterLink + "?all").get();
				Elements images = doc.select("img[src~=(?i)\\.(jpe?g)]");
				int i = 0;
				for (org.jsoup.nodes.Element image : images) {
					i = (i + 1);
					count++;
					String postLink = image.toString();
					getImageLink[i] = postLink.substring(postLink.indexOf("<")+10,postLink.indexOf(">")-3);
					Log.d("Image Link", getImageLink[i]);
				}
				count = (count - 2);
				Log.d("Counter", String.valueOf(count));
				Log.d("Log Get", getImageLink[1]);

				for (i = 1; i < count; i++) {
					// creating new HashMap
		            HashMap<String, String> map = new HashMap<String, String>();
					map.put(getImage, getImageLink[i]);
					
					mangaList.add(map);
					
					publishProgress(i);
				}
				
				imageAdapter = new ImageAdapter(this, mangaList);
		        listview.setAdapter(imageAdapter);
		        
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		public void onProgressUpdate(Integer... progress){
			imageAdapter.notifyDataSetChanged();
		}
		
		@Override
		protected void onPostExecute(Object result){
			setProgressBarIndeterminateVisibility(false);
		}
	}
}

package com.gplus.phongsakon.mangarefresh;

import java.util.ArrayList;
import java.util.HashMap;

import com.gplus.phongsakon.mangarefresh.ChapterImageList.LoadChapterImage;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;

	public ImageAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View vi =convertView; 
		if(convertView == null)
			vi = inflater.inflate(R.layout.chapterimage_list_item, null);
		
		ImageView manga_image = (ImageView) vi.findViewById(R.id.manga_img);
		
		HashMap<String, String> item = new HashMap<String, String>();
        item = data.get(position);
        
        imageLoader.DisplayImage(item.get(ChapterImageList.getImage), manga_image);
		
		return null;
	}
}

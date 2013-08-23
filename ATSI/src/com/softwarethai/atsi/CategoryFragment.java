package com.softwarethai.atsi;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CategoryFragment extends ListFragment {

	//public static final String ARG_CATEGORY_NUMBER = "category_number";
	
	ListView listView;
	
	String[] value = {"App_01", "App_02", "App_03", "App_04", "App_05", "App_06", "App_07", "App_08", "App_09", "App_10"};

	public CategoryFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_category, container,false);
		listView = (ListView) rootView.findViewById(R.id.list);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.fragment_list_item, value);
		listView.setAdapter(adapter);
		
		/*
		 * int i = getArguments().getInt(ARG_CATEGORY_NUMBER); String category =
		 * getResources().getStringArray(R.array.category_array)[i];
		 * 
		 * int imageId = getResources().getIdentifier(
		 * category.toLowerCase(Locale.getDefault()), "drawable",
		 * getActivity().getPackageName()); ((ImageView)
		 * rootView.findViewById(R.id.image)) .setImageResource(imageId);
		 * getActivity().setTitle(category);
		 */
		return rootView;
	}

}

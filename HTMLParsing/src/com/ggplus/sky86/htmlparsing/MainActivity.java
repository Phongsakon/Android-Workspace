package com.ggplus.sky86.htmlparsing;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		
		try {
			//Connect HTTP Protocol
			Document doc = Jsoup.connect("http://www.google.co.th/").get();
			
			//Get tag page <title> from htttp://www.google.co.th/
			String title = doc.title();
			
			TextView txtTitle =(TextView) findViewById(R.id.txt_title);
			txtTitle.setText(title);
			
			
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

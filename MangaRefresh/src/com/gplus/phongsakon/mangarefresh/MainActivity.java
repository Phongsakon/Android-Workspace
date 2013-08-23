package com.gplus.phongsakon.mangarefresh;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.RGBA_8888);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);

		Button btnNaruto = (Button) findViewById(R.id.btn_naruto);
		Button btnToriko = (Button) findViewById(R.id.btn_toriko);
		Button btnBeelzebub = (Button) findViewById(R.id.btn_beelzebub);
		Button btnAttackOnTitan = (Button) findViewById(R.id.btn_attackOnTitan);
		Button btnNanatsuNoTaizai = (Button) findViewById(R.id.btn_nanatsuNoTaizai);

		btnNaruto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String link = "naruto";
				Intent i = new Intent(MainActivity.this, FeedMangaChapter.class);
				i.putExtra("link", link);
				startActivity(i);
			}
		});

		btnToriko.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String link = "toriko";
				Intent i = new Intent(MainActivity.this, FeedMangaChapter.class);
				i.putExtra("link", link);
				startActivity(i);
			}
		});

		btnBeelzebub.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String link = "beelzebub";
				Intent i = new Intent(MainActivity.this, FeedMangaChapter.class);
				i.putExtra("link", link);
				startActivity(i);

			}
		});

		btnAttackOnTitan.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String link = "attack-on-titans";
				Intent i = new Intent(MainActivity.this, FeedMangaChapter.class);
				i.putExtra("link", link);
				startActivity(i);

			}
		});

		btnNanatsuNoTaizai.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String link = "nanatsu-no-taizai";
				Intent i = new Intent(MainActivity.this, FeedMangaChapter.class);
				i.putExtra("link", link);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
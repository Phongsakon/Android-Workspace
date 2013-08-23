package com.ggplus.sky86.sipaphonebook;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SingleContactFavoriteActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showfavorite_contact_actiovity);

		// Read variable from Intent
		Intent intent = getIntent();
		String FavoriteID = intent.getStringExtra("FavoriteID");

		// Show Data
		ShowData(FavoriteID);

	}

	private void ShowData(String favoriteID) {
		// TODO Auto-generated method stub
		final TextView txt_id = (TextView) findViewById(R.id.txtfavo_id);
		final TextView txt_title = (TextView) findViewById(R.id.txtfavo_title);
		final TextView txt_firstname = (TextView) findViewById(R.id.txtfavo_firstname);
		final TextView txt_lastname = (TextView) findViewById(R.id.txtfavo_lastname);
		final TextView txt_nickname = (TextView) findViewById(R.id.txtfavo_nickname);
		final TextView txt_department = (TextView) findViewById(R.id.txtfavo_department);
		final TextView txt_position = (TextView) findViewById(R.id.txtfavo_position);
		final TextView txt_email = (TextView) findViewById(R.id.txtfavo_email);
		final TextView txt_mobile = (TextView) findViewById(R.id.txtfavo_mobile);
		final TextView txt_telephone = (TextView) findViewById(R.id.txtfavo_telephone);
		
		final MyDBClass myDb = new MyDBClass(this);
		
		String arrData[] = myDb.SelectData(favoriteID);
		if(arrData != null){
			
			txt_id.setText(arrData[0]);
			txt_title.setText(arrData[1]);
			txt_firstname.setText(arrData[2]);
			txt_lastname.setText(arrData[3]);
			txt_nickname.setText(arrData[4]);
			txt_department.setText(arrData[5]);
			txt_position.setText(arrData[6]);
			txt_email.setText(arrData[7]);
			txt_mobile.setText(arrData[8]);
			txt_telephone.setText(arrData[9]);
			
		}
		
		final String toEmail = txt_email.getText().toString();
		final String mobile = txt_mobile.getText().toString();
		final String telephone = txt_telephone.getText().toString();

		Button btnMobileCall = (Button) findViewById(R.id.btnfavo_mobilecall);
		Button btnTelephoneCall = (Button) findViewById(R.id.btnfavo_telephonecall);
		Button btnSendEmail = (Button) findViewById(R.id.btnfavo_sendmail);

		// Button click for call to mobile number
		btnMobileCall
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						Intent callIntent = new Intent(
								Intent.ACTION_CALL);
						callIntent.setData(Uri.parse("tel:"
								+ mobile));
						startActivity(callIntent);

					}
				});

		// Button click for call to telephone number
		btnTelephoneCall
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						Intent callIntent = new Intent(
								Intent.ACTION_CALL);
						callIntent.setData(Uri.parse("tel:"
								+ telephone));
						startActivity(callIntent);

					}
				});

		btnSendEmail.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent email = new Intent(Intent.ACTION_SEND);
				email.putExtra(Intent.EXTRA_EMAIL,
						new String[] { toEmail });

				// need this to prompts email client only
				email.setType("message/rfc822");

				startActivity(Intent.createChooser(email,
						"Choose an Email client :"));

			}
		});

	}
}

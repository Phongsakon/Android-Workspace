package th.or.sipa.btucalculator;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class BtuCalActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final EditText value1 = (EditText) findViewById(R.id.editText1);
		final EditText value2 = (EditText) findViewById(R.id.editText2);
		final EditText value3 = (EditText) findViewById(R.id.editText3);
		final EditText result = (EditText) findViewById(R.id.editText4);

		final Button cal = (Button) findViewById(R.id.button1);

		cal.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				/*if(){
					
				}else{*/
				Float sum = (float) (((Float.parseFloat(value1.getText()
						.toString()))
						* (Float.parseFloat(value2.getText().toString())) * (Float
						.parseFloat(value3.getText().toString()))) / 2) * 1000;

				result.setText(sum.toString());
				//}
			}
		});
	}
}

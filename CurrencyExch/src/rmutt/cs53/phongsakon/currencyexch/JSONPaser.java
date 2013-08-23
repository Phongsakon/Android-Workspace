package rmutt.cs53.phongsakon.currencyexch;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONPaser {
	
	 InputStream is = null;
	 JSONObject jsonObject = null;
	 String result = "";

	public  JSONObject getJSONFromUrl(String url) {
		
		//HTTP
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			return null;
		}
		
		//Read response to String
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
			Log.d("Json String", result);

		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting resualt ");
		}
		
		//Convert String to object
		try {
			jsonObject = new JSONObject(result);
		} catch (JSONException e) {
			Log.e("Json Paser", "Error parsing data");
		}

		return jsonObject;
	}
}

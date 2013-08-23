package com.gplus.sky86.sipawifi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

public class SipaWifiService extends Service {

	private TrustManager[] trustAllCerts;
	private SharedPreferences pref;
	private String username;
	private String password;
	private boolean isChecking = false;
	private WifiInfo info;
	private WifiManager mgr;
	private boolean shouldStop = true;
	private String wifiSSID1 = "SIPAWIFI-TDD";
	private String wifiSSID2 = "SIPAWIFI-ADMIN";
	private String wifiSSID3 = "SIPAWIFI-LAW";
	private String wifiSSID4 = "SIPA-FINANCE";
	private String wifiSSID5 = "SIPAWIFI-DED";

	@Override
	public void onCreate() {
		super.onCreate();

		Log.d("Check Service", "SIPA WiFi Service created");

		pref = PreferenceManager.getDefaultSharedPreferences(this);

		mgr = (WifiManager) (getSystemService(Context.WIFI_SERVICE));

		trustAllCerts = new TrustManager[] {

		new X509TrustManager() {

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs,
					String authType) {

			}

			public void checkServerTrusted(X509Certificate[] certs,
					String authType) {

			}

		} };
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		int mode = 0;

		if (intent != null) {
			mode = intent.getIntExtra("mode", 0);
		}

		Log.d("Recive Mode", "Receive Start Command mode " + mode);

		switch (mode) {
		case 0:
			username = pref.getString("auth_user", "");
			password = pref.getString("auth_pass", "");

			info = mgr.getConnectionInfo();
			if (info != null) {
				if (info.getSSID() != null) {
					if (Build.VERSION.SDK_INT >= 17) {
						if (info.getSSID().equals("\"" + wifiSSID1 + "\"")
								|| info.getSSID().equals(
										"\"" + wifiSSID2 + "\"")
								|| info.getSSID().equals(
										"\"" + wifiSSID3 + "\"")
								|| info.getSSID().equals(
										"\"" + wifiSSID4 + "\"")
								|| info.getSSID().equals(
										"\"" + wifiSSID5 + "\"")) {
							if (!isChecking) {
								new CheckTask().execute();
								isChecking = true;
							}
						}
					} else {
						if (info.getSSID().equals(wifiSSID1)
								|| info.getSSID().equals(wifiSSID2)
								|| info.getSSID().equals(wifiSSID3)
								|| info.getSSID().equals(wifiSSID4)
								|| info.getSSID().equals(wifiSSID5)) {
							if (!isChecking) {
								new CheckTask().execute();
								isChecking = true;
							}
						}
					}
				}
			}
			break;
		case 1:
			shouldStop = true;
			stopSelf();
			break;
		}

		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	class CheckTask extends AsyncTask<Void, Void, Boolean> {

		boolean canPing = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			shouldStop = false;
		}

		@Override
		protected Boolean doInBackground(Void... params) {

			Log.d("Start Ping", "Pinging 172.16.16.123:8000");

			int count = 0;

			while (!canPing) {
				try {
					if (shouldStop) {
						return canPing;
					}
					Thread.sleep(250);
					count++;
					if (count > 50) {
						return canPing;
					}
					SSLContext sc = SSLContext.getInstance("TLS");
					sc.init(null, trustAllCerts, new SecureRandom());

					HttpsURLConnection.setDefaultSSLSocketFactory(sc
							.getSocketFactory());
					HttpsURLConnection
							.setDefaultHostnameVerifier(new HostnameVerifier() {

								@Override
								public boolean verify(String hostname,
										SSLSession session) {
									return true;
								}
							});
					URL url = new URL("http://172.16.16.123:8000");
					info = mgr.getConnectionInfo();
					if (info != null) {
						if (info.getSSID() != null) {
							if (Build.VERSION.SDK_INT >= 17) {
								if (info.getSSID().equals(
										"\"" + wifiSSID1 + "\"")
										|| info.getSSID().equals(
												"\"" + wifiSSID2 + "\"")
										|| info.getSSID().equals(
												"\"" + wifiSSID3 + "\"")
										|| info.getSSID().equals(
												"\"" + wifiSSID4 + "\"")
										|| info.getSSID().equals(
												"\"" + wifiSSID5 + "\"")) {
									URLConnection conn = url.openConnection();
									conn.getInputStream();
									canPing = true;
									shouldStop = true;
								} else {
									return canPing;
								}
							} else {
								if (info.getSSID().equals(wifiSSID1)
										|| info.getSSID().equals(wifiSSID2)
										|| info.getSSID().equals(wifiSSID3)
										|| info.getSSID().equals(wifiSSID4)
										|| info.getSSID().equals(wifiSSID5)) {
									URLConnection conn = url.openConnection();
									conn.getInputStream();
									canPing = true;
									shouldStop = true;
								} else {
									return canPing;
								}
							}
						} else {
							return canPing;
						}
					} else {
						return canPing;
					}
					Log.d("Ping Check", "count: " + count);
				} catch (Exception e) {
					Log.e("SIPAWiFi", e.toString());
				}
			}
			return canPing;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			isChecking = false;
			Log.d("Result Start Service", result.toString());
			if (result) {
				new SendTask().execute();
			} else {
				Log.d("Not Connect Server", "SIPA WiFi Server not found");
				stopSelf();
			}
		}
	}

	class SendTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... arg0) {
			// Check HTTP POST
			Log.d("Start HTTP Post", "HTTP Post to SIPA WiFi");

			try {
				// Construct data
				String data = URLEncoder.encode("auth_user", "UTF-8") + "="
						+ URLEncoder.encode(username, "UTF-8");
				data += "&" + URLEncoder.encode("auth_pass", "UTF-8") + "="
						+ URLEncoder.encode(password, "UTF-8");
				data += "&" + URLEncoder.encode("accept", "UTF-8") + "="
						+ URLEncoder.encode("4", "UTF-8");

				SSLContext sc = SSLContext.getInstance("TLS");
				sc.init(null, trustAllCerts, new SecureRandom());

				HttpsURLConnection.setDefaultSSLSocketFactory(sc
						.getSocketFactory());
				HttpsURLConnection
						.setDefaultHostnameVerifier(new HostnameVerifier() {

							@Override
							public boolean verify(String hostname,
									SSLSession session) {
								return true;
							}
						});

				URL url = new URL("http://172.16.16.123:8000/index.php");
				info = mgr.getConnectionInfo();
				if (info != null) {
					if (info.getSSID() != null) {
						if (Build.VERSION.SDK_INT >= 17) {
							if (info.getSSID().equals(
									"\"" + wifiSSID1 + "\"")
									|| info.getSSID().equals(
											"\"" + wifiSSID2 + "\"")
									|| info.getSSID().equals(
											"\"" + wifiSSID3 + "\"")
									|| info.getSSID().equals(
											"\"" + wifiSSID4 + "\"")
									|| info.getSSID().equals(
											"\"" + wifiSSID5 + "\"")) {
								URLConnection conn = url.openConnection();
								conn.setDoOutput(true);
								OutputStreamWriter writer = new OutputStreamWriter(
										conn.getOutputStream());

								// Send data
								writer.write(data);
								writer.flush();

								// Get response
								BufferedReader reader = new BufferedReader(
										new InputStreamReader(
												conn.getInputStream()));
								String str;
								StringBuilder results = new StringBuilder();
								while ((str = reader.readLine()) != null) {
									results.append(str + "\n");
								}
								writer.close();
								reader.close();
								Log.d("DATA", writer.getEncoding().toString());
								return results.toString();

							}
						} else {
							if (info.getSSID().equals(wifiSSID1)
									|| info.getSSID().equals(wifiSSID2)
									|| info.getSSID().equals(wifiSSID3)
									|| info.getSSID().equals(wifiSSID4)
									|| info.getSSID().equals(wifiSSID5)) {
								URLConnection conn = url.openConnection();
								conn.setDoOutput(true);
								OutputStreamWriter writer = new OutputStreamWriter(
										conn.getOutputStream());

								// Send data
								writer.write(data);
								writer.flush();

								// String redirectUrl =
								// conn.getURL().toString();

								// Get response
								BufferedReader reader = new BufferedReader(
										new InputStreamReader(
												conn.getInputStream()));
								String str;
								StringBuilder results = new StringBuilder();
								while ((str = reader.readLine()) != null) {
									results.append(str + "\n");
								}
								writer.close();
								reader.close();
								Log.d("DATA", writer.getEncoding().toString());
								return results.toString();
							}

						}
					}
				}
			} catch (Exception e) {
				Log.e("SIPA WIFI", e.toString());
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			// Check result
			//Log.d("Result", result);
			//Log.d("Send Check Result", result.toString());
			createNotification();
			stopSelf();
		}
	}

	@SuppressWarnings("deprecation")
	public void createNotification() {
		NotificationManager nftManger = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		Notification notification = new Notification(
				R.drawable.ic_notification, "New Notification",
				System.currentTimeMillis());

		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://intranet.sipa.or.th/"));
		PendingIntent activity = PendingIntent.getActivity(this, 0, intent, 0);
		String Title = "SIPA WIFI Auto Login";
		String Message = "Connect to SIPA WIFI Successful";

		notification.setLatestEventInfo(this, Title, Message, activity);
		notification.number += 1;

		notification.defaults = Notification.DEFAULT_SOUND;

		nftManger.notify(1, notification);

	}

}

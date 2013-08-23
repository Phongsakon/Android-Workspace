package com.gplus.sky86.sipawifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;

public class WifiConnect extends BroadcastReceiver {

	private String wifiSSID1 = "SIPAWIFI-TDD";
	private String wifiSSID2 = "SIPAWIFI-ADMIN";
	private String wifiSSID3 = "SIPAWIFI-LAW";
	private String wifiSSID4 = "SIPA-FINANCE";
	private String wifiSSID5 = "SIPAWIFI-DED";

	@Override
	public void onReceive(Context context, Intent intent) {

		Log.d("Reciveing Intent", "Receiving Intent for SUPPLICANT_STATE_CHANGED_ACTION");	
		Log.d("WIFI State Receive", "supplicant state: " + intent.getParcelableExtra("newState").toString());	
		if (intent.getParcelableExtra("newState").toString()
				.equals("COMPLETED")) {
			WifiManager mgr = (WifiManager) (context
					.getSystemService(Context.WIFI_SERVICE));
			WifiInfo info = mgr.getConnectionInfo();

			if (info != null) {
				Log.d("SIPA WIFI Info", info.toString());
				if (info.getSSID() != null) {
					Log.d("SSID Receive", "SSID:" + info.getSSID());
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
							Intent i = new Intent(context,
									SipaWifiService.class);
							i.putExtra("mode", 0);
							context.startService(i);
						} else {
							Log.d("SIPA WIFI", "Not connected to SIPA WiFi");		
						}
					} else {
						if (info.getSSID().equals(wifiSSID1)
								|| info.getSSID().equals(wifiSSID2)
								|| info.getSSID().equals(wifiSSID3)
								|| info.getSSID().equals(wifiSSID4)
								|| info.getSSID().equals(wifiSSID5)) {
							Intent i = new Intent(context,
									SipaWifiService.class);
							i.putExtra("mode", 0);
							context.startService(i);
						} else {
							Log.d("SIPA WIFI", "Not connected to SIPA WiFi");		
						}

					}
				} else {
					Log.d("Check SSID", "Null SSID");
				}
			} else {
				Log.d("Check Wifi Info", "Null WiFi Info");
			}
		} else if (intent.getParcelableExtra("newState").toString()
				.equals("DISCONNECTED")) {
			Intent i = new Intent(context, SipaWifiService.class);
			i.putExtra("mode", 1);
			context.startService(i);
		}
	}
}

package com.ggplus.sky86.sipaphonebook;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBClass extends SQLiteOpenHelper {

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "MyDatabase";

	// Table Name
	private static final String TABLE_FAVORITE = "favorite";

	public MyDBClass(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE " + TABLE_FAVORITE
				+ "(favoriteID INTEGER PRIMARY KEY," + "Title TEXT(100),"
				+ "Firstname TEXT(100)," + "Lastname TEXT(100),"
				+ "Nickname TEXT(100)," + "Department TEXT(100),"
				+ "Position TEXT(100)," + "Email TEXT(100),"
				+ "Mobile TEXT(100)," + "Telephone TEXT(100));");

		Log.d("CREATE TABLE", "Create Table Successfully.");

	}

	// Insert Data
	public long InsertData(String strFavoriteID, String strTitle,
			String strFirstname, String strLastname, String strNickname,
			String strDepartment, String strPosition, String strEmail,
			String strMobile, String strTelephone) {

		try {

			SQLiteDatabase db;
			db = this.getWritableDatabase(); // Write Data
			ContentValues Val = new ContentValues();
			Val.put("favoriteID", strFavoriteID);
			Val.put("Title", strTitle);
			Val.put("Firstname", strFirstname);
			Val.put("Lastname", strLastname);
			Val.put("Nickname", strNickname);
			Val.put("Department", strDepartment);
			Val.put("Position", strPosition);
			Val.put("Email", strEmail);
			Val.put("Mobile", strMobile);
			Val.put("Telephone", strTelephone);
			long rows = db.insert(TABLE_FAVORITE, null, Val);

			db.close();

			return rows; // return rows inserted.

		} catch (Exception e) {
			return -1;
		}
	}

	// Show All Data
	public ArrayList<HashMap<String, String>> SelectAllData() {
		// TODO Auto-generated method stub

		try {

			ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> map;

			SQLiteDatabase db;
			// Read Data
			db = this.getReadableDatabase();

			String strSQL = "SELECT  * FROM " + TABLE_FAVORITE;
			Cursor cursor = db.rawQuery(strSQL, null);

			if (cursor != null) {
				if (cursor.moveToFirst()) {
					do {
						map = new HashMap<String, String>();
						map.put("favoriteID", cursor.getString(0));
						map.put("Title", cursor.getString(1));
						map.put("Firstname", cursor.getString(2));
						map.put("Lastname", cursor.getString(3));

						MyArrList.add(map);

					} while (cursor.moveToNext());
				}
			}
			cursor.close();
			db.close();

			return MyArrList;

		} catch (Exception e) {
			return null;
		}
	}

	// Select Data
	public String[] SelectData(String strFavoriteID) {
		// TODO Auto-generated method stub
		try {
			String arrData[] = null;
			SQLiteDatabase db;
			// Read Data
			db = this.getReadableDatabase();
			Cursor cursor = db.query(TABLE_FAVORITE, new String[] { "*" },
					"favoriteID=?",
					new String[] { String.valueOf(strFavoriteID) }, null, null,
					null, null);
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					arrData = new String[cursor.getColumnCount()];
					arrData[0] = cursor.getString(0);
					arrData[1] = cursor.getString(1);
					arrData[2] = cursor.getString(2);
					arrData[3] = cursor.getString(3);
					arrData[4] = cursor.getString(4);
					arrData[5] = cursor.getString(5);
					arrData[6] = cursor.getString(6);
					arrData[7] = cursor.getString(7);
					arrData[8] = cursor.getString(8);
					arrData[9] = cursor.getString(9);
				}
			}
			cursor.close();
			db.close();
			return arrData;
		} catch (Exception e) {
			return null;
		}
	}

	// Delete Data
	public long DeleteData(String strFavoriteID) {
		// TODO Auto-generated method stub
		try {

			SQLiteDatabase db;
			db = this.getWritableDatabase(); // Write Data
			long rows = db.delete(TABLE_FAVORITE, "favoriteID = ?",
					new String[] { String.valueOf(strFavoriteID) });
			db.close();

			// return rows delete.
			return rows;

		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);

		// Re Create on method onCreate
		onCreate(db);

	}

}

package com.example.androidlearning;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class SQLiteActivity extends Activity {
	private Button insertButton = null;
	private Button deleteButton = null;
	private Button modifyButton = null;
	private Button queryButton = null;
	private TextView showDetail = null;
	private SQLiteDatabase db = null;
	private String db_name = "gallery.sqlite";
	private String table_name = "pic";
	final DbHelper helper = new DbHelper(this, db_name, null, 1);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sqlite);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		setTitle("SQLite simple use case");
		
		db = helper.getWritableDatabase();
		initDatabase(db);
		updateSpinner();
		
		OnClickListener ocl = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ContentValues cv = new ContentValues();
				switch(v.getId()) {
				case R.id.insertButton:
					cv.put("fileName", "pic5.jpg");
					cv.put("description", "Fifth picture");
					long long1 = db.insert("pic", "", cv);
					if(long1 == -1) {
						Toast.makeText(SQLiteActivity.this, "Insert pic fail", Toast.LENGTH_LONG).show();
					}
					else {
						Toast.makeText(SQLiteActivity.this, "Insert pic successfully", Toast.LENGTH_LONG).show();
					}
					updateSpinner();
					break;
				case R.id.deleteButton:
					long long2 = db.delete("pic", "description='Fifth picture'", null);
					Toast.makeText(SQLiteActivity.this, "Delete " + long2 + " pictures", Toast.LENGTH_LONG).show();					
					updateSpinner();
					break;
				case R.id.modifyButton:
					cv.put("fileName", "pic0.jpg");
					cv.put("description", "Zero picture");
					int long3 = db.update("pic", cv, "fileName='pic5.jpg'", null);
					Toast.makeText(SQLiteActivity.this, "Update " + long3 + " pictures", Toast.LENGTH_LONG).show();
					updateSpinner();
					break;
				case R.id.queryButton:
					Cursor c = db.query("pic", null, null, null, null, null, null);
					Toast.makeText(SQLiteActivity.this, "Total " + c.getCount() +"items : ", Toast.LENGTH_LONG).show();
					for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
						Toast.makeText(SQLiteActivity.this, "The " + c.getInt(0) + " data, with fileName = " + c.getString(1) + ", description = " + c.getString(2), Toast.LENGTH_LONG).show();
					}
					updateSpinner();
					break;
				}
			}
			
		};
		
		// Insert
		insertButton = (Button)findViewById(R.id.insertButton);
		insertButton.setText("Insert Button");
		insertButton.setOnClickListener(ocl);
		
		// Delete
		deleteButton = (Button)findViewById(R.id.deleteButton);
		deleteButton.setText("Delete Button");
		deleteButton.setOnClickListener(ocl);
				
		// Modify
		modifyButton = (Button)findViewById(R.id.modifyButton);
		modifyButton.setText("Modify Button");
		modifyButton.setOnClickListener(ocl);		
		
		// Query
		queryButton = (Button)findViewById(R.id.queryButton);
		queryButton.setText("Query Button");
		queryButton.setOnClickListener(ocl);

	}

	
	// Initialize database
	public void initDatabase(SQLiteDatabase db) {
		ContentValues cv = new ContentValues();
		
		cv.put("fileName", "pic1.jpg");
		cv.put("description", "First picture");
		db.insert(table_name, "", cv);
		
		cv.put("fileName", "pic2.jpg");
		cv.put("description", "Second picture");
		db.insert(table_name, "", cv);
		
		cv.put("fileName", "pic3.jpg");
		cv.put("description", "Third picture");
		db.insert(table_name, "", cv);
		
		cv.put("fileName", "pic4.jpg");
		cv.put("description", "Forth picture");
		db.insert(table_name, "", cv);
	}
	
	public void updateSpinner() {
		final TextView tv = (TextView)findViewById(R.id.textView01);
		Spinner s = (Spinner)findViewById(R.id.spinner01);
		
		final Cursor cursor = db.query("pic", null, null, null, null, null, null);
		
		@SuppressWarnings("deprecation")
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursor, new String[] {
				"fileName", "description"}, new int[] {android.R.id.text1, android.R.id.text2});
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		s.setAdapter(adapter);
		
		OnItemSelectedListener oisl = new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				cursor.moveToPosition(position);
				tv.setText("Current pic description : " + cursor.getString(2));
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		};
		
		s.setOnItemSelectedListener(oisl);
	}
	
	public void onDestory() {
		super.onDestroy();
		db.delete(table_name, null, null);
		updateSpinner();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sqlite, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_sqlite,
					container, false);
			return rootView;
		}
	}
	
	
	/*
	 * Help class 
	 */
	private class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			String sql = "CREATE TABLE pic (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, fileName VARCHAR, description VARCHAR)";
			db.execSQL(sql);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
	}

}

package com.example.androidlearning;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends Activity {
	private TextView text = null;
	private Button secondButton = null;		// go to second activity
	private Button sqliteButton = null;
	private EditText InputText = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		// identify using activity_main.xml to be the layout

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		text = (TextView)findViewById(R.id.DefaultTextView);
		text.setText("Default text here!");
		Intent textFromSecond = getIntent();
		String info = textFromSecond.getStringExtra(Intent.EXTRA_TEXT);
		if(info != null)
			text.setText(info);
		
		InputText = (EditText)findViewById(R.id.InputEditText);
		
		secondButton = (Button)findViewById(R.id.SecondButton);
		secondButton.setText("send to Second");
		secondButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String text2 = InputText.getText().toString();
				Intent gotoSecond = new Intent(MainActivity.this, SecondActivity.class);
				gotoSecond.setAction(Intent.ACTION_SEND);
				gotoSecond.putExtra(Intent.EXTRA_TEXT, text2);
				gotoSecond.setType("text/plain");
				startActivity(gotoSecond);
			}
			
		});
		
		
		sqliteButton = (Button)findViewById(R.id.SqliteButton);
		sqliteButton.setText("Use SQLite");
		sqliteButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}

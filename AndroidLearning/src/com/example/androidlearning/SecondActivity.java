package com.example.androidlearning;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Build;

public class SecondActivity extends Activity {
	private TextView text = null;
	private Button backButton =null;
	private EditText InputText = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		text = (TextView)findViewById(R.id.textView);
		text.setText("Second Default Text");
		Intent gIntent = getIntent();
		String info = gIntent.getStringExtra(Intent.EXTRA_TEXT);
		if(info != null) {
			text.setText(info);
		}
		
		InputText = (EditText)findViewById(R.id.editText);	
		
		backButton = (Button)findViewById(R.id.backButton);
		backButton.setText("Back to Main");
		backButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String backText = InputText.getText().toString();	 // only here works as expected
				Intent backIntent = new Intent(SecondActivity.this, MainActivity.class);
				backIntent.setAction(Intent.ACTION_SEND);
				backIntent.setType("text/plain");
				backIntent.putExtra(Intent.EXTRA_TEXT, backText);
				startActivity(backIntent);
			}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_second,
					container, false);
			return rootView;
		}
	}

}

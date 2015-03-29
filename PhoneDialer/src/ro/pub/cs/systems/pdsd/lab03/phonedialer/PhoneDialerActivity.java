package ro.pub.cs.systems.pdsd.lab03.phonedialer;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class PhoneDialerActivity extends Activity {
	
	private class MyButtonListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			StringBuilder phoneNumber = new StringBuilder("");
			
			EditText phoneNumberText = (EditText)findViewById(R.id.edit_text);
			if (phoneNumberText == null) {
				return;
			}
			
			String phoneText = phoneNumberText.getText().toString();

			phoneNumber.append(phoneText);
			
			if (v instanceof Button) {
				Button buttonAcction = (Button)v;
				phoneNumber.append(buttonAcction.getText().toString());
			} else if (v instanceof ImageButton) {
				ImageButton imageButtonAction = (ImageButton)v;
				if (phoneNumber.length() > 0) {
					switch(imageButtonAction.getId()) {
					case R.id.back_space:
						phoneNumber.deleteCharAt(phoneNumber.length()-1);
						break;
					
					case R.id.call:
						try {
							Intent intent = new Intent(Intent.ACTION_CALL);
							intent.setData(Uri.parse("tel:"+phoneNumber.toString()));
							startActivity(intent);
						} catch (Exception e) {
							System.out.println("Are you serious?");
							e.printStackTrace();
							finish();
						} finally {
							System.out.println("Bye bye");
						}
						break;
					
					case R.id.hung:
						finish();
						break;
					default:
						System.out.println("Error");
					}
				}
			}
			
			phoneNumberText.setText(phoneNumber.toString());
			
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_phone_dialer);

		MyButtonListener listener = new MyButtonListener();
		
		for (int i = 0; i < Constants.buttons.length; i++) {
			Button button = (Button)findViewById(Constants.buttons[i]);
			button.setOnClickListener(listener);
		}
		
		for (int i = 0; i < Constants.images.length; i++) {
			ImageButton imgButton = (ImageButton)findViewById(Constants.images[i]);
			imgButton.setOnClickListener(listener);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.phone_dialer, menu);
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
}

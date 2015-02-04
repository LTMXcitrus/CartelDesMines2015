package cartel.mines.nantes2015;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class NotificationDisplay extends Activity{
	
	String SENDER_ID = "176135343380";

	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;


	private String mail ="";
	private EditText mailInput;
	private Button send;
	private TextView title;
	private TextView content;

	static final String TAG = "GCMDemo";
	TextView mDisplay;
	GoogleCloudMessaging gcm;
	AtomicInteger msgId = new AtomicInteger();
	SharedPreferences prefs;
	Context context;

	String regid;

	

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification_display);

		

		send =(Button) findViewById(R.id.pushsend);
		send.setEnabled(false);
		mailInput=(EditText) findViewById(R.id.mailinput);
		title = (TextView) findViewById(R.id.pushtitle);
		content = (TextView) findViewById(R.id.pushcontent);
		mDisplay = (TextView) findViewById(R.id.displaykey);
		
		String titleText = "";
		String contentText = "";
		
		Intent intent =this.getIntent();
		Bundle extras= intent.getExtras();
		if(extras.containsKey("title")){
			titleText = extras.getString("title");
			contentText = extras.getString("msg");
		}
		
		title.setText(titleText);
		content.setText(contentText);

		context = getApplicationContext();

		// Check device for Play Services APK. If check succeeds, proceed with
		//  GCM registration.
		if (checkPlayServices()) {
			gcm = GoogleCloudMessaging.getInstance(this);
			regid = getRegistrationId(context);

			if (regid.isEmpty()) {
				RegisterInBackground rIB = new RegisterInBackground();
				rIB.execute();

			}
			else{
				mDisplay.setText(regid);
				send.setEnabled(true);
				send.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						mail = mailInput.getText().toString();
						Intent intent = new Intent(Intent.ACTION_SENDTO);
						intent.setData(Uri.parse("mailto:"+mail)); // only email apps should handle this
						intent.putExtra(Intent.EXTRA_EMAIL, mail);
						intent.putExtra(Intent.EXTRA_TEXT, regid);
						if (intent.resolveActivity(getPackageManager()) != null) {
							startActivity(intent);
						}
					}
				});


			}
		} else {
			Log.i(TAG, "No valid Google Play Services APK found.");
		}

	}

	@SuppressLint("NewApi")
	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGCMPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId.isEmpty()) {
			Log.i(TAG, "Registration not found in preferences.");
			return "";
		}
		// Check if app was updated; if so, it must clear the registration ID
		// since the existing regID is not guaranteed to work with the new
		// app version.
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			Log.i(TAG, "App version changed.");
			return "";
		}
		return registrationId;
	}

	private SharedPreferences getGCMPreferences(Context context) {
		// This sample app persists the registration ID in shared preferences, but
		// how you store the regID in your app is up to you.
		return getSharedPreferences(NotificationDisplay.class.getSimpleName(),
				Context.MODE_PRIVATE);
	}



	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Log.i(TAG, "This device is not supported.");
				finish();
			}
			return false;
		}
		return true;
	}

	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	private void storeRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = getGCMPreferences(context);
		int appVersion = getAppVersion(context);
		Log.i(TAG, "Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}

	private void sendRegistrationIdToBackend() {
		// Send RegistrationId to your server
	}

	private class RegisterInBackground extends AsyncTask<Void, Void, String>{

		protected String doInBackground(Void... params) {
			String msg = "";
			try {
				System.out.println("Je suis passé par l'asynctask");
				if (gcm == null) {
					gcm = GoogleCloudMessaging.getInstance(context);
				}
				regid = gcm.register(SENDER_ID);
				msg = "Device registered, registration ID=" + regid;

				// You should send the registration ID to your server over HTTP,
				// so it can use GCM/HTTP or CCS to send messages to your app.
				// The request to your server should be authenticated if your app
				// is using accounts.
				sendRegistrationIdToBackend();

				// For this demo: we don't need to send it because the device
				// will send upstream messages to a server that echo back the
				// message using the 'from' address in the message.

				// Persist the regID - no need to register again.
				storeRegistrationId(context, regid);
			} catch (IOException ex) {
				msg = "Error :" + ex.getMessage();
				// If there is an error, don't just keep trying to register.
				// Require the user to click a button again, or perform
				// exponential back-off.
			}
			return msg;
		}
		protected void onPostExecute(String msg) {
			mDisplay.append(msg + "\n");
			send.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Intent.ACTION_SENDTO);
					intent.setData(Uri.parse("mailto:"+"mattcitron45140@gmail.com")); // only email apps should handle this
					intent.putExtra(Intent.EXTRA_EMAIL, "mattcitron45140@gmail.com");
					intent.putExtra(Intent.EXTRA_TEXT, regid);
					if (intent.resolveActivity(getPackageManager()) != null) {
						startActivity(intent);
					}
				}
			});

			send.setEnabled(true);
		}

	}
	


}

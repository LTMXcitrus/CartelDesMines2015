package cartel.mines.nantes2015;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import loaders.DelegationsListLoader;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import tools.DelegationsListLoaderCallback;
import adapters.SpinnerDelegationChoiceAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class RegistrationActivity extends Activity implements DelegationsListLoaderCallback{

	String SENDER_ID = "176135343380";

	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	
	Handler handler = new Handler();

	static final String TAG = "Cartel2015";
	TextView mDisplay;
	GoogleCloudMessaging gcm;
	AtomicInteger msgId = new AtomicInteger();
	SharedPreferences prefs;
	Context context;
	String[] delegationsString;
	
	EditText usernameInput;

	String regid;
	String delegation;
	
	TextView erreur;
	Spinner delegationsSpinner;
	Button validate;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration_display);
		
		DelegationsListLoader loader = new DelegationsListLoader(this);
		loader.start();
		
		getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bleu_cartel)));
		
		erreur  = (TextView) findViewById(R.id.registration_explication);
		
		delegationsSpinner = (Spinner) findViewById(R.id.delegation_choice_registration);
		
		validate = (Button) findViewById(R.id.onRegistrationDone);
		validate.setEnabled(false);
		
		usernameInput = (EditText) findViewById(R.id.username_input);
		usernameInput.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String input = usernameInput.getText().toString();
				if(input.isEmpty()){
					validate.setEnabled(false);
				}else{
					validate.setEnabled(true);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,	int after) {}
			@Override
			public void afterTextChanged(Editable s) {}
		});

		context = getApplicationContext();

		// Check device for Play Services APK. If check succeeds, proceed with
		//  GCM registration.
		
		if (checkPlayServices()) {
			gcm = GoogleCloudMessaging.getInstance(this);
			regid = getRegistrationId(context);
			validate.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (regid.isEmpty()) {
						RegisterInBackground rIB = new RegisterInBackground();
						rIB.execute();
					}
					SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
					prefs.edit().putBoolean("registered", true).commit();
					prefs.edit().putString("username", usernameInput.getText().toString()).commit();
					prefs.edit().putString("delegation", (String) delegationsSpinner.getSelectedItem()).commit();
					startActivity(new Intent(RegistrationActivity.this,Accueil.class));
				}
			});
			
		} else {
			Log.i(TAG, "No valid Google Play Services APK found.");
			erreur.setText("No valid Google Play Services APK found.");
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
		return getSharedPreferences(RegistrationActivity.class.getSimpleName(),
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
		System.out.println(regId);
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}

	private void sendRegistrationIdToBackend() throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		HttpPost post  = new HttpPost("http://cartel2015.com/fr/perso/notifs/sendtoken.php");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("token", regid));
		params.add(new BasicNameValuePair("delegation", (String) delegationsSpinner.getSelectedItem()));
		params.add(new BasicNameValuePair("system", "Android"));
		post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		client.execute(post);
	}

	private class RegisterInBackground extends AsyncTask<Void, Void, String>{

		protected String doInBackground(Void... params) {
			String msg = "";
			try {
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

	}

	@Override
	public void onLoadFinished(final ArrayList<String> delegations) {
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				delegationsSpinner.setAdapter(new SpinnerDelegationChoiceAdapter(RegistrationActivity.this, delegations));
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				prefs.edit().putStringSet("delegations", new HashSet<String>(delegations)).commit();
			}
		});
		
	}



}

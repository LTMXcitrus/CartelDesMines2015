package notifications;

import loaders.ImageLoader;
import tools.ImageLoaderListener;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import beans.Notification;
import cartel.mines.nantes2015.Accueil;
import cartel.mines.nantes2015.NotificationActivity;
import cartel.mines.nantes2015.RegistrationActivity;
import cartel.mines.nantes2015.R;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import core.GcmBroadcastReceiver;

public class GcmIntentService extends IntentService implements ImageLoaderListener{
	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;
	static final String TAG = "Cartel2015";

	public GcmIntentService() {
		super("GcmIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

		// The getMessageType() intent parameter must be the intent you received
		// in your BroadcastReceiver.
		String messageType = gcm.getMessageType(intent);



		if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
			/*
			 * Filter messages based on message type. Since it is likely that GCM
			 * will be extended in the future with new message types, just ignore
			 * any message types you're not interested in, or that you don't
			 * recognize.
			 */
			if (GoogleCloudMessaging.
					MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
				System.out.println("Send error: " + extras.toString());
			} else if (GoogleCloudMessaging.
					MESSAGE_TYPE_DELETED.equals(messageType)) {
				System.out.println("Deleted messages on server: " +
						extras.toString());
				// If it's a regular GCM message, do some work.
			} else if (GoogleCloudMessaging.
					MESSAGE_TYPE_MESSAGE.equals(messageType)) {

				String msg = extras.getString("msg");
				String title = extras.getString("title");
				String imageUrl = "";
				String thumbnail = "";
				if(extras.containsKey("image") && extras.containsKey("thumbnail")){
					imageUrl = extras.getString("image");
					thumbnail = extras.getString("thumbnail");
				}
				Notification notif = new Notification(title, msg, imageUrl, thumbnail);

				Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
				// Post notification of received message.
				sendNotification(notif);
				Log.i(TAG, "Received: " + extras.toString());
			}
		}
		// Release the wake lock provided by the WakefulBroadcastReceiver.
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	// Put the message into a notification and post it.
	public void sendNotification(Notification notif) {
		mNotificationManager = (NotificationManager)
				this.getSystemService(Context.NOTIFICATION_SERVICE);

		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, Accueil.class), 0);
		Intent intent =new Intent(this,NotificationActivity.class);
		intent.putExtra("notif", notif);

		Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

		long[] pattern = {0,1000};

		NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(this);
		if(notif.getImageUrl().isEmpty()){
			mBuilder.setSmallIcon(R.drawable.ic_launcher)
			.setContentTitle(notif.getTitre())
			.setStyle(new NotificationCompat.BigTextStyle()
			.bigText(notif.getBody()))
			.setContentText(notif.getBody())
			.setVibrate(pattern)
			.setSound(sound).setLights(Color.BLUE, 500, 500)
			.setAutoCancel(true);
			contentIntent = PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_ONE_SHOT);

			mBuilder.setContentIntent(contentIntent);
			mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
		}
		else{
			ImageLoader loader = new ImageLoader(notif.getImageThumbnailUrl(), notif,this);
			loader.start();
		}
	}

	@Override
	public void onLoadFinished(Bitmap bitmap, Notification notif) {
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, NotificationActivity.class), 0);
		Intent intent =new Intent(this,NotificationActivity.class);
		
		intent.putExtra("notif", notif);
		long[] pattern = {0,1000};
		
		
		Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(this);
		mBuilder.setLargeIcon(bitmap)
		.setSmallIcon(R.drawable.ic_launcher)
		.setContentTitle(notif.getTitre())
		.setStyle(new NotificationCompat.BigTextStyle()
		.bigText(notif.getBody()))
		.setContentText(notif.getBody())
		.setVibrate(pattern)
		.setSound(sound).setLights(Color.BLUE, 500, 500)
		.setAutoCancel(true);
		contentIntent = PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_ONE_SHOT);

		mBuilder.setContentIntent(contentIntent);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
	}
}

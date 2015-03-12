package tools;

import beans.Notification;
import android.graphics.Bitmap;

public interface ImageLoaderListener {
	
	public void onLoadFinished(Bitmap bitmap, Notification notif);

}

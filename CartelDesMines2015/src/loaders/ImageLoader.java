package loaders;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import beans.Notification;
import android.graphics.BitmapFactory;
import tools.ImageLoaderListener;

public class ImageLoader extends Thread{

	String imageUrl;
	ImageLoaderListener handler;
	Notification notif;

	public ImageLoader(String imageUrl, Notification notif, ImageLoaderListener handler){
		this.imageUrl=imageUrl;
		this.notif=notif;
		this.handler=handler;
	}

	@Override
	public void run() {
		URL url;
		try {
			url = new URL(imageUrl);

			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream stream;

			stream = connection.getInputStream();
			handler.onLoadFinished(BitmapFactory.decodeStream(stream),notif);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}

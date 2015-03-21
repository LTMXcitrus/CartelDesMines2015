package loaders;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import beans.Image;
import tools.ImageThreadListener;

public class ImageThreadLoader extends Thread{

	//TODO set to webservices
	//TODO create Webservices

	ImageThreadListener handler;

	public ImageThreadLoader(ImageThreadListener handler){
		this.handler=handler;
	}

	@Override
	public void run(){
		try {
			ArrayList<Image> images = new ArrayList<Image>();
			ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();

			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet("http://cartel2015.com/fr/perso/webservices/getImageThreadList.php");

			HttpResponse r = client.execute(get);

			String json = EntityUtils.toString(r.getEntity());

			JSONObject imagesJSON = new JSONObject(json);
			JSONObject entries = imagesJSON.getJSONObject("entries");
			Iterator<String> keys = entries.keys();
			while(keys.hasNext()){
				String key = keys.next();
				Image image = Image.createImageFromJson(entries.getJSONObject(key),key);
				images.add(image);
			}
			
			Collections.sort(images);
			Collections.reverse(images);


			handler.onLoadFinished(images,bitmaps);

			/*for(Image image : images){
				
				URL url = new URL(image.getThumbnailUrl());

				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setDoInput(true);
				connection.connect();
				InputStream stream;

				stream = connection.getInputStream();
				bitmaps.add(BitmapFactory.decodeStream(stream));
				handler.onLoadFinished(images, bitmaps);
			}*/

		} catch (IOException | JSONException | ParseException e) {
			e.printStackTrace();
		}


	}

}

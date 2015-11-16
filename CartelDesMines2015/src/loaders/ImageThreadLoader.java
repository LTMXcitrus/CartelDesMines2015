package loaders;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import tools.ImageThreadListener;
import android.graphics.Bitmap;
import beans.Image;

public class ImageThreadLoader extends Thread{

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

			String json = EntityUtils.toString(r.getEntity(), "UTF-8");

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

		} catch (IOException | JSONException | ParseException e) {
			e.printStackTrace();
		}


	}

}

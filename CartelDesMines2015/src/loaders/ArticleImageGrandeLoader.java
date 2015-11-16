package loaders;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.BitmapFactory;
import tools.ArticleImageGrandeLoaderListener;

public class ArticleImageGrandeLoader extends Thread{

	String imageUrl;
	ArticleImageGrandeLoaderListener handler;

	public ArticleImageGrandeLoader(String imageUrl, ArticleImageGrandeLoaderListener handler){
		this.imageUrl=imageUrl;
		this.handler=handler;
	}

	@Override
	public void run(){
		try {
			URL url = new URL(imageUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream stream;

			stream = connection.getInputStream();
			handler.onLoadFinished(BitmapFactory.decodeStream(stream));
		} catch (IOException e) {
			System.out.println(e);
		}

	}

}

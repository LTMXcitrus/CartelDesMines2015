package loaders;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.BitmapFactory;
import beans.Article;
import tools.ArticlesImagesLoaderListener;
import tools.ArticlesLoaderListener;

public class ArticlesLoader extends Thread{

	ArticlesLoaderListener handlerArticles;
	ArticlesImagesLoaderListener handlerImages;
	
	
	public ArticlesLoader(ArticlesLoaderListener handlerArticles, ArticlesImagesLoaderListener handlerImages){
		this.handlerArticles = handlerArticles;
		this.handlerImages = handlerImages;
	}

	@Override
	public void run(){
		try {
			ArrayList<Article> articles = new ArrayList<Article>();
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet("http://1-dot-inlaid-span-809.appspot.com/articles");
			HttpResponse r = client.execute(get);
			
			String json = EntityUtils.toString(r.getEntity());
			JSONObject objectJson = new JSONObject(json);
			JSONArray arrayClassement = objectJson.getJSONArray("Articles");
			for(int i=0; i<arrayClassement.length();  i++){
				JSONObject object = arrayClassement.getJSONObject(i);
				Article article = Article.createArticleFromJson(object);
				articles.add(article);
			}
			
			handlerArticles.onLoadFinished(articles);
			for(int i=0; i< articles.size(); i++){
				Article article = articles.get(i);
				
				URL url = new URL(article.getThumbnailURL());
		        HttpURLConnection connection = (HttpURLConnection) url
		                .openConnection();
		        connection.setDoInput(true);
		        connection.connect();
				InputStream stream = connection.getInputStream();
				handlerImages.onImageLoadFinished(i,BitmapFactory.decodeStream(stream));
			}
			
			
		} catch (IOException | JSONException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

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

import cartel.mines.nantes2015.R;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import beans.Article;
import tools.ArticlesImagesLoaderListener;
import tools.ArticlesLoaderListener;

public class ArticlesLoader extends Thread{

	ArticlesLoaderListener handlerArticles;
	ArticlesImagesLoaderListener handlerImages;
	Context context;


	public ArticlesLoader(Context context, ArticlesLoaderListener handlerArticles, ArticlesImagesLoaderListener handlerImages){
		this.handlerArticles = handlerArticles;
		this.handlerImages = handlerImages;
		this.context=context;
	}

	@Override
	public void run(){
		try {
			ArrayList<Article> articles = new ArrayList<Article>();
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet("http://cartel2015.com/fr/perso/webservices/getArticlesArray.php");
			HttpResponse r = client.execute(get);

			String json = EntityUtils.toString(r.getEntity());

			JSONArray arrayClassement = new JSONArray(json);
			for(int i=0; i<arrayClassement.length();  i++){
				JSONObject object = arrayClassement.getJSONObject(i);
				Article article = Article.createArticleFromJson(object);
				articles.add(article);
			}

			handlerArticles.onLoadFinished(articles);
			for(int i=0; i< articles.size(); i++){
				Article article = articles.get(i);
				if(article.getThumbnailURL().isEmpty()){
					handlerImages.onImageLoadFinished(i, BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher));
				}else{
					URL url = new URL(article.getThumbnailURL());
					HttpURLConnection connection = (HttpURLConnection) url
							.openConnection();
					connection.setDoInput(true);
					connection.connect();
					InputStream stream = connection.getInputStream();
					handlerImages.onImageLoadFinished(i,BitmapFactory.decodeStream(stream));
				}
			}


		} catch (IOException | JSONException | ParseException e) {
			System.out.println(e);
		}
	}

}

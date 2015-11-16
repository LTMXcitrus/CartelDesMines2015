package loaders;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tools.SportsLoaderListener;

public class SportsLoader extends Thread{
	
	SportsLoaderListener handler;
	
	public SportsLoader(SportsLoaderListener handler){
		this.handler=handler;
	}

	@Override
	public void run(){
		try {
			ArrayList<String> sports = new ArrayList<String>();
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet("http://cartel2015.com/fr/perso/webservices/getSports.php");
			HttpResponse r = client.execute(get);
			
			String json = EntityUtils.toString(r.getEntity(), "UTF-8");
			
			
			JSONArray arraySports = new JSONArray(json);
			for(int i=0; i<arraySports.length(); i++){
				JSONObject sportObject =arraySports.getJSONObject(i);
				String sport = sportObject.getString("sport");
				sports.add(sport);
			}
			
			handler.onLoadFinished(sports);
			
		} catch (IOException | JSONException e) {
			System.out.println(e);
		}
	}

}

package loaders;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tools.ResultatsListener;
import android.util.Log;
import beans.Classement;
import beans.Course;
import beans.Match;
import beans.Resultat;

public class ResultatsLoader extends Thread{
	

	private ResultatsListener handler;

	public ResultatsLoader(ResultatsListener handler){
		this.handler=handler;
	}

	public void run(){
		try {
			ArrayList<Resultat> resultats = new ArrayList<Resultat>();
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet("http://cartel2015.com/fr/perso/webservices/getMatchesByDate.php");
			System.out.println(get.getURI());
			HttpResponse r = client.execute(get);

			String json = EntityUtils.toString(r.getEntity());
			JSONObject objectJson = new JSONObject(json);
			JSONObject entries  = objectJson.getJSONObject("entries");
			Iterator<String> keys = entries.keys();
			while(keys.hasNext()){
				String key = keys.next();
				JSONObject matchJson = entries.getJSONObject(key);
				if(!isCourse(matchJson)){
					Match match = new Match();
					match = match.createFromJson(matchJson,key);
					resultats.add(match);
				}
				else{
					Course course = new Course();
					course = course.createFromJson(matchJson, key);
					resultats.add(course);
				}
			}

			handler.onLoadFinished(resultats);

		} catch (IOException | JSONException | ParseException e) {
			Log.e("Cartel2015",e.getMessage());
		}

	}

	private boolean isCourse(JSONObject json){
		return json.has("participants");
	}

}

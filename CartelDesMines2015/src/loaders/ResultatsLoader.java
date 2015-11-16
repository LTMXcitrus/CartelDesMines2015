package loaders;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

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
			ArrayList<Resultat> resultatsJour1 = new ArrayList<Resultat>();
			ArrayList<Resultat> resultatsJour2 = new ArrayList<Resultat>();
			ArrayList<Resultat> resultatsJour3 = new ArrayList<Resultat>();
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet("http://cartel2015.com/fr/perso/webservices/getMatchesByDate.php");
			HttpResponse r = client.execute(get);

			String json = EntityUtils.toString(r.getEntity(), "UTF-8");
			JSONArray entries  = new JSONArray(json);
			for(int i=0; i<entries.length(); i++){
				JSONObject matchJson = entries.getJSONObject(i);
				if(!isCourse(matchJson)){
					Match match = new Match();
					match = match.createFromJson(matchJson);
					switch(match.getDayOfMonth()){
					case 11:
						resultatsJour1.add(match);
						break;
					case 12:
						resultatsJour2.add(match);
						break;
					case 13:
						resultatsJour3.add(match);
						break;
					}
				}
				else{
					Course course = new Course();
					course = course.createFromJson(matchJson);
					switch(course.getDayOfMonth()){
					case 11:
						resultatsJour1.add(course);
						break;
					case 12:
						resultatsJour2.add(course);
						break;
					case 13:
						resultatsJour3.add(course);
						break;
					}
				}
			}

			handler.onLoadFinished(resultatsJour1, resultatsJour2, resultatsJour3);

		} catch (IOException | JSONException | ParseException e) {
			Log.e("Cartel2015",e.getMessage());
		}

	}

	private boolean isCourse(JSONObject json){
		return json.has("participants");
	}

}

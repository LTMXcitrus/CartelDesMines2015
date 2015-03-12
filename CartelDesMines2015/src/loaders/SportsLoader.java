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

import tools.SportsLoaderListener;
import beans.Course;
import beans.Match;
import beans.Resultat;

public class SportsLoader extends Thread{
	
	SportsLoaderListener handler;
	
	public SportsLoader(SportsLoaderListener handler){
		this.handler=handler;
	}

	@Override
	public void run(){
		try {
			ArrayList<String> sports = new ArrayList<String>();
			ArrayList<Resultat> resultats  = new ArrayList<Resultat>();
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet("http://1-dot-inlaid-span-809.appspot.com/matchesliveclassement");
			HttpResponse r = client.execute(get);
			
			String json = EntityUtils.toString(r.getEntity());
			JSONObject objectJson = new JSONObject(json);
			JSONArray arrayMatches  = objectJson.getJSONArray("Matches");
			for(int i=0;i<arrayMatches.length(); i++){
				JSONObject matchJson = arrayMatches.getJSONObject(i);
				if(!isCourse(matchJson)){
					Match match = new Match();
					match = match.createFromJson(matchJson);
					resultats.add(match);
				}
				else{
					Course course = new Course();
					course = course.createFromJson(matchJson);
					resultats.add(course);
				}
			}
			JSONArray arraySports = objectJson.getJSONArray("Sports");
			for(int i=0; i<arraySports.length(); i++){
				JSONObject sportObject =arraySports.getJSONObject(i);
				String sport = sportObject.getString("sport");
				sports.add(sport);
			}
			
			handler.onLoadFinished(resultats, sports);
			
		} catch (IOException | JSONException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean isCourse(JSONObject json){
		return json.has("participants");
	}

}

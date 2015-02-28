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
import beans.Match;

public class SportsLoader extends Thread{
	
	SportsLoaderListener handler;
	
	public SportsLoader(SportsLoaderListener handler){
		this.handler=handler;
	}

	@Override
	public void run(){
		try {
			ArrayList<String> sports = new ArrayList<String>();
			ArrayList<Match> matches  = new ArrayList<Match>();
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet("http://1-dot-inlaid-span-809.appspot.com/matchesliveclassement");
			HttpResponse r = client.execute(get);
			
			String json = EntityUtils.toString(r.getEntity());
			JSONObject objectJson = new JSONObject(json);
			JSONArray arrayMatches  = objectJson.getJSONArray("Matches");
			for(int i=0;i<arrayMatches.length(); i++){
				JSONObject matchJson = arrayMatches.getJSONObject(i);
				Match match = Match.createMatchFromJson(matchJson);
				matches.add(match);
			}
			JSONArray arraySports = objectJson.getJSONArray("Sports");
			for(int i=0; i<arraySports.length(); i++){
				JSONObject sportObject =arraySports.getJSONObject(i);
				String sport = sportObject.getString("sport");
				sports.add(sport);
			}
			
			handler.onLoadFinished(matches, sports);
			
		} catch (IOException | JSONException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

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

import tools.MatchesListener;
import android.util.Log;
import beans.Classement;
import beans.Match;

public class MatchesLoader extends Thread{
	
	private MatchesListener handler;
	
	public MatchesLoader(MatchesListener handler){
		this.handler=handler;
	}

	public void run(){
		try {
			ArrayList<Match> matches = new ArrayList<Match>();
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
			
			handler.onLoadFinished(matches);
			
		} catch (IOException | JSONException | ParseException e) {
			Log.e("Cartel2015",e.getMessage());
		}
		
	}

}

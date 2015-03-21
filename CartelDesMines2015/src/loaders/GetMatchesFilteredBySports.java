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
import org.json.JSONException;
import org.json.JSONObject;

import tools.MatchesFilteredListener;
import beans.Match;
import beans.Resultat;

public class GetMatchesFilteredBySports extends Thread{
	
	String sport;
	MatchesFilteredListener handler;
	
	public GetMatchesFilteredBySports(String sport, MatchesFilteredListener handler){
		this.sport=sport;
		this.handler=handler;
	}
	
	@Override
	public void run() {
		try {
			ArrayList<Resultat> matchesOfSports = new ArrayList<Resultat>();
			sport = sport.replace(" ", "%20");

			HttpClient client = new DefaultHttpClient();				
			HttpGet get = new HttpGet("http://cartel2015.com/fr/perso/webservices/getMatchesBySport.php?sport=" + sport );
			HttpResponse r = client.execute(get);
			
			String json = EntityUtils.toString(r.getEntity());
			JSONObject objectJson = new JSONObject(json);
			JSONObject entries = objectJson.getJSONObject("entries");
			Iterator<String> keys = entries.keys();
			while(keys.hasNext()){
				String key = keys.next();
				JSONObject entry = entries.getJSONObject(key);
				Match match = new Match();
				match = match.createFromJson(entry, key, sport);
				matchesOfSports.add(match);
			}
			
			handler.onMatchesFilteredAvailable(matchesOfSports);
			
		} catch (IOException | JSONException | ParseException e) {
			System.out.println(e);
		}
	}

}

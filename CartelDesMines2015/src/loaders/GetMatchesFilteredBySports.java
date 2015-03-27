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
			ArrayList<Resultat> resultatsJour1 = new ArrayList<Resultat>();
			ArrayList<Resultat> resultatsJour2 = new ArrayList<Resultat>();
			ArrayList<Resultat> resultatsJour3 = new ArrayList<Resultat>();
			sport = sport.replace(" ", "%20");

			HttpClient client = new DefaultHttpClient();				
			HttpGet get = new HttpGet("http://cartel2015.com/fr/perso/webservices/getMatchesBySport.php?sport=" + sport );
			HttpResponse r = client.execute(get);
			
			String json = EntityUtils.toString(r.getEntity(), "UTF-8");
			
			JSONArray entries = new JSONArray(json);
			for(int i=0; i<entries.length(); i++){
				JSONObject entry = entries.getJSONObject(i);
				Match match = new Match();
				match = match.createFromJson(entry, sport);
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
			
			handler.onMatchesFilteredAvailable(resultatsJour1, resultatsJour2, resultatsJour3);
			
		} catch (IOException | JSONException | ParseException e) {
			System.out.println(e);
		}
	}

}

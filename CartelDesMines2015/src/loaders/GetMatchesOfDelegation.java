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

public class GetMatchesOfDelegation extends Thread{
	
	String delegation;
	MatchesFilteredListener handler;
	
	public GetMatchesOfDelegation(String delegation, MatchesFilteredListener handler){
		this.delegation=delegation;
		this.handler=handler;
	}
	
	@Override
	public void run() {
		try {
			ArrayList<Resultat> resultatsJour1 = new ArrayList<Resultat>();
			ArrayList<Resultat> resultatsJour2 = new ArrayList<Resultat>();
			ArrayList<Resultat> resultatsJour3 = new ArrayList<Resultat>();
			delegation = delegation.replace(" ", "%20");

			HttpClient client = new DefaultHttpClient();				
			HttpGet get = new HttpGet("http://cartel2015.com/fr/perso/webservices/getMatchesByDelegation.php?delegation=" + delegation );
			HttpResponse r = client.execute(get);
			
			String json = EntityUtils.toString(r.getEntity(), "UTF-8");
			
			JSONArray entries = new JSONArray(json);
			for(int i=0; i<entries.length(); i++){
				JSONObject entry = entries.getJSONObject(i);
				Match match = new Match();
				match = match.createFromJson(entry);
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

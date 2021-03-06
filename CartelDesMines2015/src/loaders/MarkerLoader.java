package loaders;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tools.AsyncListener;
import android.location.Location;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MarkerLoader extends Thread{
	
	//TODO correction of the webservices (at least one bad pair of coordonates)

	private URL urlCible = null;
	private AsyncListener delegate = null;
	private static final String SPORT = "sport";
	private static final String LOGEMENT = "logement";
	private static final String SOIREE = "soiree";

	public MarkerLoader(URL u, AsyncListener l){
		this.urlCible=u;
		this.delegate=l;
	}
	public void run(){
		try{
			ArrayList<MarkerOptions> markersResult = new ArrayList<MarkerOptions>();
			HashMap<Integer,String> markersQueryResources =new HashMap<Integer,String>();
			ArrayList<String> types = new ArrayList<String>();

			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet("http://cartel2015.com/fr/perso/webservices/static/poi.json");
			HttpResponse r = client.execute(get);
			String json = EntityUtils.toString(r.getEntity(), "UTF-8");
			JSONObject service = new JSONObject(json);
			JSONArray sport = service.getJSONArray("Sports");


			for(int i=0;i< sport.length();i++){
				JSONObject marker = sport.getJSONObject(i);
				String nom = marker.getString("nom");
				double latitude = Double.parseDouble(marker.getString("latitude"));
				double longitude = Double.parseDouble(marker.getString("longitude"));
				Location markerLocation = new Location("");
				markerLocation.setLatitude(latitude);
				markerLocation.setLongitude(longitude);

				String snippet = marker.getString("snippet");
				String type = SPORT;
				float hue_code=BitmapDescriptorFactory.HUE_YELLOW;
				markersResult.add(createMarker(nom, latitude, longitude, snippet,hue_code));
				markersQueryResources.put(i, (nom+snippet).toLowerCase());
				types.add(type);
			}

			JSONArray logement = service.getJSONArray("H�tels");
			int length = markersResult.size();
			for(int i=0; i<logement.length();i++){

				JSONObject marker = logement.getJSONObject(i);
				String nom = marker.getString("nom");
				double latitude = Double.parseDouble(marker.getString("latitude"));
				double longitude = Double.parseDouble(marker.getString("longitude"));
				Location markerLocation = new Location("");
				markerLocation.setLatitude(latitude);
				markerLocation.setLongitude(longitude);

				String snippet = marker.getString("snippet");
				String type = LOGEMENT;
				float hue_code=BitmapDescriptorFactory.HUE_RED;
				markersResult.add(createMarker(nom, latitude, longitude, snippet,hue_code));
				markersQueryResources.put(i+length-1, (nom+snippet).toLowerCase());
				types.add(type);	
			}
			
			JSONArray soiree = service.getJSONArray("Soir�es");
			length = markersResult.size();
			for(int i = 0; i<soiree.length();i++){
				JSONObject marker = soiree.getJSONObject(i);
				String nom = marker.getString("nom");
				double latitude = Double.parseDouble(marker.getString("latitude"));
				double longitude = Double.parseDouble(marker.getString("longitude"));
				Location markerLocation = new Location("");
				markerLocation.setLatitude(latitude);
				markerLocation.setLongitude(longitude);

				String snippet =marker.getString("snippet");
				String type = SOIREE;
				float hue_code=BitmapDescriptorFactory.HUE_BLUE;
				markersResult.add(createMarker(nom, latitude, longitude, snippet,hue_code));
				markersQueryResources.put(i+length-1, (nom+snippet).toLowerCase());
				types.add(type);
			}


			delegate.chargementTermine(urlCible, markersResult,markersQueryResources, types);
		}
		catch(IOException | JSONException m){
			System.err.println(m);
		}
	}

	public MarkerOptions createMarker(String nom, double latitude, double longitude, String snippet, float hue_code){
		LatLng position = new LatLng(latitude, longitude);
		MarkerOptions marker = new MarkerOptions().position(position).title(nom).snippet(snippet).visible(true).icon(BitmapDescriptorFactory.defaultMarker(hue_code));
		return marker;
	}

}



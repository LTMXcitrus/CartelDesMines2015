package tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MarkerLoader extends Thread{

	private URL urlCible = null;
	private AsyncListener delegate = null;
	private static final String SPORT = "sport";
	private static final String LOGEMENT = "logement";
	private static final String REPAS = "repas";
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

			HttpURLConnection urlConnection = (HttpURLConnection) urlCible.openConnection();
			InputStream in = urlConnection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String result, line = reader.readLine();
			result  = line;
			while((line=reader.readLine())!=null){
				result+=line;
			}
			JSONObject service = new JSONObject(result);
			JSONArray sport = service.getJSONArray("sport");


			for(int i=0;i< sport.length();i++){
				JSONObject marker = sport.getJSONObject(i);
				String nom = new String(marker.getString("nom").getBytes("ISO-8859-1"), "UTF-8");
				double latitude = Double.parseDouble(marker.getString("latitude"));
				double longitude = Double.parseDouble(marker.getString("longitude"));
				Location markerLocation = new Location("");
				markerLocation.setLatitude(latitude);
				markerLocation.setLongitude(longitude);

				String snippet = new String(marker.getString("snippet").getBytes("ISO-8859-1"),"UTF-8");
				String type = SPORT;
				float hue_code=BitmapDescriptorFactory.HUE_YELLOW;
				markersResult.add(createMarker(nom, latitude, longitude, snippet,hue_code));
				markersQueryResources.put(i, (nom+snippet).toLowerCase());
				types.add(type);
			}

			JSONArray logement = service.getJSONArray("hotels");
			int length = markersResult.size();
			for(int i=0; i<logement.length();i++){

				JSONObject marker = logement.getJSONObject(i);
				String nom = new String(marker.getString("nom").getBytes("ISO-8859-1"), "UTF-8");
				double latitude = Double.parseDouble(marker.getString("latitude"));
				double longitude = Double.parseDouble(marker.getString("longitude"));
				Location markerLocation = new Location("");
				markerLocation.setLatitude(latitude);
				markerLocation.setLongitude(longitude);

				String snippet = new String(marker.getString("snippet").getBytes("ISO-8859-1"),"UTF-8");
				String type = LOGEMENT;
				float hue_code=BitmapDescriptorFactory.HUE_RED;
				markersResult.add(createMarker(nom, latitude, longitude, snippet,hue_code));
				markersQueryResources.put(i+length-1, (nom+snippet).toLowerCase());
				types.add(type);	
			}
			
			JSONArray soiree = service.getJSONArray("soiree");
			length = markersResult.size();
			for(int i = 0; i<soiree.length();i++){
				JSONObject marker = soiree.getJSONObject(i);
				String nom = new String(marker.getString("nom").getBytes("ISO-8859-1"), "UTF-8");
				double latitude = Double.parseDouble(marker.getString("latitude"));
				double longitude = Double.parseDouble(marker.getString("longitude"));
				Location markerLocation = new Location("");
				markerLocation.setLatitude(latitude);
				markerLocation.setLongitude(longitude);

				String snippet = new String(marker.getString("snippet").getBytes("ISO-8859-1"),"UTF-8");
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



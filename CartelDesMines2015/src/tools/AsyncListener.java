package tools;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.android.gms.maps.model.MarkerOptions;

public interface AsyncListener {

	public void chargementTermine(URL u, ArrayList<MarkerOptions> markers, HashMap<Integer,String> markersQueryResources, ArrayList<String> types);
}
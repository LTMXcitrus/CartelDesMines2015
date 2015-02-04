package cartel.mines.nantes2015;

import java.lang.Thread.State;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import tools.AsyncListener;
import tools.MarkerLoader;
import tools.MarkerSearchListAdapter;
import tools.MyInfoWindowAdapterListener;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class CarteFragment extends SupportMapFragment implements AsyncListener{
	
	private ArrayList<MarkerOptions> markersOptions;
	private ArrayList<Marker> markers;
	/**
	 * List of strings, that allow to find a marker wanted by the user.
	 * The strings are given by appending the name and the information from the given marker.
	 */
	private ArrayList<String> types;
	private HashMap<Integer,String> markersQueryResources;
	private GoogleMap map;
	private ListView markerSearchResultsList;
	private String currentSearch = "";
	private ArrayList<MarkerOptions> currentResults;
	ArrayList<Integer> suggestions;
	private Button sportsPoiVisibility;
	private Button repasPoiVisibity;
	private Button logementsPoiVisibility;

	@Override
	public void chargementTermine(URL u, ArrayList<MarkerOptions> markers, HashMap<Integer, String> markersQueryResources, ArrayList<String> types) {
		this.markersOptions=markers;
		this.markersQueryResources=markersQueryResources;
		this.types=types;
	}
	
	public static SupportMapFragment newInstance() {
		CarteFragment fragment = new CarteFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}
		
	public CarteFragment(){ }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView=inflater.inflate(R.layout.carte_layout, container,
				false);
		rootView.findViewById(R.id.linearlayout);
		
		MarkerLoader getMarkers=null;
		try {
			getMarkers = new MarkerLoader(new URL("http://1-dot-inlaid-span-809.appspot.com/"), this);
		} catch (MalformedURLException e) {
			System.out.println(e);
		}
		getMarkers.start();

		map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		map.setMyLocationEnabled(true);
		map.animateCamera(CameraUpdateFactory.newLatLngBounds(getAboveNantes(),400,400,1));

		sportsPoiVisibility = (Button) rootView.findViewById(R.id.sportsvisibility);
		sportsPoiVisibility.setOnClickListener(new PoiVisibilityListener(PoiVisibilityListener.SPORT));
		repasPoiVisibity = (Button) rootView.findViewById(R.id.repasvisibility);
		repasPoiVisibity.setOnClickListener(new PoiVisibilityListener(PoiVisibilityListener.REPAS));
		logementsPoiVisibility = (Button) rootView.findViewById(R.id.logementsvisibility);
		logementsPoiVisibility.setOnClickListener(new PoiVisibilityListener(PoiVisibilityListener.LOGEMENT));

		markerSearchResultsList =(ListView) rootView.findViewById(R.id.markerSearchResultsList);
		markerSearchResultsList.setVisibility(View.GONE);

		while(getMarkers.getState()!=State.TERMINATED){	}
		addMarkersToMap();
		markerSearchResultsList.setAdapter(new MarkerSearchListAdapter(getActivity(), R.layout.marker_search_item, markersOptions));
		markerSearchResultsList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				markerSearchResultsList.setVisibility(View.GONE);
				Marker marker = markers.get(suggestions.get(position));
				map.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 14));
				marker.showInfoWindow();
			}
		});

		MyInfoWindowAdapterListener adapterListener = new MyInfoWindowAdapterListener(getActivity());
		map.setInfoWindowAdapter(adapterListener);
		map.setOnInfoWindowClickListener(adapterListener);	
		return rootView;
	}
	
	/**
	 * Add the Markers from markers ArrayList to the map
	 */
	public void addMarkersToMap(){
		markers = new ArrayList<Marker>();
		for(int i=0;i<markersOptions.size();i++){			
			markers.add(map.addMarker(markersOptions.get(i)));
		}
	}

	/**
	 * Set the visibility of all the Markers
	 * @param visible
	 */
	public void setAllMarkersVisibility(boolean visible){
		for(Marker marker : markers){
			marker.setVisible(visible);
		}
	}
	/**
	 * 
	 * @param type
	 * @param visible
	 */
	public void setMarkersVisibility(String type, boolean visible){
		for(int i=0;i<markers.size();i++){
			if(types.get(i).equals(type)){
				markers.get(i).setVisible(visible);
			}
		}
	}

	/**
	 * set The camera position above Nantes
	 * @return
	 */
	public LatLngBounds getAboveNantes(){
		double maxSud = 47.14;
		double maxOuest = -1.69;
		double maxNord =  47.29;
		double maxEst = -1.44;

		LatLng southwest = new LatLng(maxSud, maxOuest);
		LatLng northeast = new LatLng(maxNord, maxEst);
		LatLngBounds rectangle = new LatLngBounds(southwest, northeast);
		return rectangle;
	}
	
	private class PoiVisibilityListener implements View.OnClickListener{

		public final static String SPORT = "sport";
		public final static String LOGEMENT = "logement";
		public final static	String	REPAS = "repas";

		private HashMap<String, Boolean> visibilities;

		private String visibilityType;

		public PoiVisibilityListener(String visibilityType) {
			this.visibilityType=visibilityType;
			this.visibilities=new HashMap<String, Boolean>();
			this.visibilities.put(SPORT, true);
			this.visibilities.put(LOGEMENT, true);
			this.visibilities.put(REPAS,true);
		}

		@Override
		public void onClick(View v) {
			visibilities.put(visibilityType, !this.visibilities.get(visibilityType));
			setMarkersVisibility(visibilityType, this.visibilities.get(visibilityType));
			if(this.visibilities.get(visibilityType)){
				((Button) v).setTextColor(Color.BLACK);
			}
			else{
				((Button) v).setTextColor(Color.GRAY);
			}
		}

	}


}

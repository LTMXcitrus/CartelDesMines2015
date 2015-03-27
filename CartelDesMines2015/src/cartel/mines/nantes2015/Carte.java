package cartel.mines.nantes2015;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import loaders.MarkerLoader;
import tools.AsyncListener;
import tools.MyInfoWindowAdapterListener;
import tools.SearchInMarkers;
import adapters.MarkerSearchListAdapter;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnCloseListener;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Carte extends ActionBarActivity implements AsyncListener{

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

	private TextView emptyList;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.carte_layout);

		ActionBar actionBar = getSupportActionBar();

		actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bleu_cartel)));
		actionBar.setDisplayHomeAsUpEnabled(true);
		if(!Accueil.isDeviceConnected(this)){
			
			notConnectedDialog(this);
			
		}else{

			MarkerLoader getMarkers = null;
			try {
				getMarkers = new MarkerLoader(new URL("http://cartel2015.com/fr/perso/webservices/static/poi.json"), this);
			} catch (MalformedURLException e) {
				System.out.println(e);
			}
			getMarkers.start();

			map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			map.setMyLocationEnabled(true);
			map.animateCamera(CameraUpdateFactory.newLatLngBounds(getAboveNantes(),400,400,1));

			sportsPoiVisibility = (Button) findViewById(R.id.sportsvisibility);
			sportsPoiVisibility.setOnClickListener(new PoiVisibilityListener(PoiVisibilityListener.SPORT));
			repasPoiVisibity = (Button) findViewById(R.id.repasvisibility);
			repasPoiVisibity.setOnClickListener(new PoiVisibilityListener(PoiVisibilityListener.SOIREE));
			logementsPoiVisibility = (Button) findViewById(R.id.logementsvisibility);
			logementsPoiVisibility.setOnClickListener(new PoiVisibilityListener(PoiVisibilityListener.LOGEMENT));

			emptyList = (TextView) findViewById(R.id.empty_list_item_search_list);
			emptyList.setVisibility(View.GONE);

			markerSearchResultsList =(ListView) findViewById(R.id.markerSearchResultsList);
			markerSearchResultsList.setVisibility(View.GONE);
		}
	}


	@Override
	public void chargementTermine(URL u, ArrayList<MarkerOptions> markers, HashMap<Integer,String> markersQueryResources, ArrayList<String> types) {
		this.markersOptions=markers;
		this.markersQueryResources=markersQueryResources;
		this.types=types;
		sportsPoiVisibility.post(new Runnable() {

			@Override
			public void run() {
				addMarkersToMap();
				markerSearchResultsList.setAdapter(new MarkerSearchListAdapter(Carte.this, R.layout.marker_search_item, markersOptions));

				markerSearchResultsList.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						markerSearchResultsList.setVisibility(View.GONE);
						Marker marker = Carte.this.markers.get(suggestions.get(position));
						map.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 14));
						marker.showInfoWindow();
					}
				});

				MyInfoWindowAdapterListener adapterListener = new MyInfoWindowAdapterListener(Carte.this);
				map.setInfoWindowAdapter(adapterListener);
				map.setOnInfoWindowClickListener(adapterListener);
				invalidateOptionsMenu();
			}
		});

	}
	/**
	 * Add the Markers from markers ArrayList to the map
	 */
	public void addMarkersToMap(){
		markers = new ArrayList<Marker>();
		for(int i=0; i<markersOptions.size(); i++){			
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
	 * @param type  the type of markers among SPORT, REPAS, LOGEMENT
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.carte, menu);
		final SearchView searchView= (SearchView) menu.findItem(R.id.action_search).getActionView();
		searchView.setQuery(currentSearch, false);
		final SearchInMarkers search  = new SearchInMarkers(this.markersQueryResources);
		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String arg0) {
				return false;
			}

			@Override
			public boolean onQueryTextChange(String arg0) {
				suggestions = search.contains(arg0);
				markerSearchResultsList.setVisibility(View.VISIBLE);
				currentResults = new ArrayList<MarkerOptions>();
				for(int i=0;i<markersOptions.size();i++){
					if(suggestions.contains(i)){
						MarkerOptions m = markersOptions.get(i);
						currentResults.add(m);
					}
				}
				if(suggestions.isEmpty()){
					emptyList.setVisibility(View.VISIBLE);
				}
				markerSearchResultsList.setAdapter(new MarkerSearchListAdapter(Carte.this, R.layout.marker_search_item, currentResults));
				return true;
			}
		});
		searchView.setOnCloseListener(new OnCloseListener() {
			@Override
			public boolean onClose() {
				markerSearchResultsList.setVisibility(View.GONE);
				emptyList.setVisibility(View.GONE);
				return false;
			}
		});
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_search) {
			markerSearchResultsList.setVisibility(View.VISIBLE);
			if(currentResults!=null){
				markerSearchResultsList.setAdapter(new MarkerSearchListAdapter(Carte.this, R.layout.marker_search_item, currentResults));
			}
			return false;
		}
		if(id == android.R.id.home){
			Intent intent = new Intent(this,Accueil.class);
			intent.putExtra("carte", "carte");
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void hideKeyboard() {   
		// Check if no view has focus:
		View view = this.getCurrentFocus();
		if (view != null) {
			InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
		}
	}

	private class PoiVisibilityListener implements View.OnClickListener{

		public final static String SPORT = "sport";
		public final static String LOGEMENT = "logement";
		public final static	String	SOIREE = "soiree";

		private HashMap<String, Boolean> visibilities;

		private String visibilityType;

		/**
		 * 
		 * @param visibilityType
		 */
		public PoiVisibilityListener(String visibilityType) {
			this.visibilityType = visibilityType;
			this.visibilities = new HashMap<String, Boolean>();
			this.visibilities.put(SPORT, true);
			this.visibilities.put(LOGEMENT, true);
			this.visibilities.put(SOIREE,true);
		}

		@Override
		public void onClick(View v) {
			visibilities.put(visibilityType, !this.visibilities.get(visibilityType));
			setMarkersVisibility(visibilityType, this.visibilities.get(visibilityType));
			if(this.visibilities.get(visibilityType)){
				if(visibilityType.equals(SPORT)){
					((Button) v).setTextColor(getResources().getColor(R.color.jaune_cartel));
				}
				if(visibilityType.equals(LOGEMENT)){
					((Button) v).setTextColor(getResources().getColor(R.color.rouge_cartel));
				}
				if(visibilityType.equals(SOIREE)){
					((Button) v).setTextColor(getResources().getColor(R.color.bleu_cartel));
				}

			}
			else{
				((Button) v).setTextColor(Color.GRAY);
			}
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if ((keyCode == KeyEvent.KEYCODE_HOME || keyCode == KeyEvent.KEYCODE_BACK )) {
			Intent intent = new Intent(this,Accueil.class);
			intent.putExtra("carte", "carte");
			startActivity(intent);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void notConnectedDialog(Context context){
		Builder notConnected= new AlertDialog.Builder(context);
		notConnected.setCancelable(true);
		notConnected.setPositiveButton("Réessayer", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog,
					int which) {
				recreate();				
			}
		});
		LayoutInflater factory = LayoutInflater.from(context);
		final View notconnectedDialog = factory.inflate(R.layout.device_not_connected_dialog, null);
		notConnected.setView(notconnectedDialog);
		notConnected.setTitle("Non connecté");
		notConnected.show();
	}
}
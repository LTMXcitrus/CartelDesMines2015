package cartel.mines.nantes2015;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import beans.Match;
import beans.Resultat;
import tools.MatchesFilteredListener;
import tools.SportsLoaderListener;
import loaders.ClassementLoader;
import loaders.GetMatchesFilteredBySports;
import loaders.SportsLoader;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FragmentsSports extends ListFragment implements SportsLoaderListener, MatchesFilteredListener{

	ProgressDialog dialog;
	ListView list;

	public static FragmentsSports newInstance(){
		return new FragmentsSports();
	}

	public FragmentsSports(){	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,	Bundle savedInstanceState){

		return inflater.inflate(R.layout.fragment_resultats, container, false);

	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		dialog = ProgressDialog.show(getActivity(), "Veuillez patienter...", "Chargement...");
		
		
		final SportsLoader loader = new SportsLoader(this);
		loader.start();

		
		dialog.setCancelable(true);
		dialog.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				loader.interrupt();
			}
		});

		list = getListView();

	}

	@Override
	public void onLoadFinished( final ArrayList<String> sports) {
		dialog.dismiss();
		list.post(new Runnable() {

			@Override
			public void run() {
				list.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, sports));

				list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						getMatchesOfSport(sports.get(position));
						
					}
				});
			}
		});
	}

	public void getMatchesOfSport(final String sport){
		GetMatchesFilteredBySports loader = new GetMatchesFilteredBySports(sport, this);
		loader.start();
	}

	@Override
	public void onMatchesFilteredAvailable(ArrayList<Resultat> resultats) {
		Intent intent =new Intent(getActivity(),MatchesParSportsActivity.class);
		intent.putExtra("matches", resultats);
		startActivity(intent);
	}

}

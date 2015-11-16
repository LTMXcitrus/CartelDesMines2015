package cartel.mines.nantes2015;

import java.util.ArrayList;

import loaders.GetMatchesFilteredBySports;
import loaders.SportsLoader;
import tools.MatchesFilteredListener;
import tools.SportsLoaderListener;
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
import beans.Resultat;

public class FragmentsSports extends ListFragment implements SportsLoaderListener, MatchesFilteredListener{

	ProgressDialog dialog;
	ListView list;

	public static FragmentsSports newInstance(){
		return new FragmentsSports();
	}

	public FragmentsSports(){	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,	Bundle savedInstanceState){

		return inflater.inflate(R.layout.listfragment, container, false);

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
	public void onMatchesFilteredAvailable(ArrayList<Resultat> resultatsJour1,
			ArrayList<Resultat> resultatsJour2,
			ArrayList<Resultat> resultatsJour3) {
		Intent intent =new Intent(getActivity(),MatchesParSportsActivity.class);
		intent.putExtra("matchesJour1", resultatsJour1);
		intent.putExtra("matchesJour2", resultatsJour2);
		intent.putExtra("matchesJour3", resultatsJour3);
		startActivity(intent);
		
	}

}

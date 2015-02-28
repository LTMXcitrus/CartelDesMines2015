package cartel.mines.nantes2015;

import java.util.ArrayList;

import beans.Match;
import tools.SportsLoaderListener;
import loaders.ClassementLoader;
import loaders.SportsLoader;
import android.app.ProgressDialog;
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

public class FragmentsSports extends ListFragment implements SportsLoaderListener{

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
		SportsLoader loader = new SportsLoader(this);
		loader.start();

		dialog = ProgressDialog.show(getActivity(), "Veuillez patienter...", "Chargement...");

		list = getListView();

	}

	@Override
	public void onLoadFinished(final ArrayList<Match> matches, final ArrayList<String> sports) {
		dialog.dismiss();
		list.post(new Runnable() {

			@Override
			public void run() {
				list.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, sports));

				list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						Intent intent =new Intent(getActivity(),MatchesParSportsActivity.class);
						intent.putExtra("matches", getMatchesOfSport(matches, sports.get(position)));
						startActivity(intent);
					}
				});
			}
		});
	}


	public static ArrayList<Match> getMatchesOfSport(ArrayList<Match> matches, String sport){
		ArrayList<Match> matchesOfSports = new ArrayList<Match>();
		for(Match match : matches){
			if(match.getSport().equals(sport)){
				matchesOfSports.add(match);
			}
		}
		return matchesOfSports;
	}

}

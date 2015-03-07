package cartel.mines.nantes2015;

import java.util.ArrayList;

import tools.SportsLoaderListener;
import loaders.MatchesLoader;
import loaders.ResultatsDelegationLoader;
import beans.Match;
import adapters.MatchesListAdapter;
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

public class FragmentResultatsDelegation extends ListFragment implements SportsLoaderListener{
	
	ListView list;
	ProgressDialog dialog;

	public static FragmentResultatsDelegation newInstance(){
		FragmentResultatsDelegation fragment = new FragmentResultatsDelegation();
		return fragment;
	}

	public FragmentResultatsDelegation() {	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,	Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.fragment_resultats, container, false);
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		final ResultatsDelegationLoader loader = new ResultatsDelegationLoader(this);
		loader.start();
		
		dialog = ProgressDialog.show(getActivity(), "Veuillez patienter...", "Chargement...");
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
	public void onLoadFinished(final ArrayList<Match> matches, final ArrayList<String> delegations) {
		dialog.dismiss();
		list.post(new Runnable() {
			
			@Override
			public void run() {
				list.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,delegations));
				list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						ArrayList<Match> matchesOfDelegation = getMatchesOfDelegation(matches, delegations.get(position));
						Intent intent = new Intent(getActivity(),MatchesParSportsActivity.class);
						intent.putExtra("matches", matchesOfDelegation);
						startActivity(intent);
					}
				});
			}
		});	
	}
	
	public static ArrayList<Match> getMatchesOfDelegation(ArrayList<Match> matches, String delegation){
		ArrayList<Match> result = new ArrayList<Match>();
		for(Match match : matches){
			if(match.getPlayer1().equals(delegation) || match.getPlayer2().equals(delegation)){
				result.add(match);
			}
		}
		return result;
	}
}

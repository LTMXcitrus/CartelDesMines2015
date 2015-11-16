package cartel.mines.nantes2015;

import java.util.ArrayList;

import loaders.GetMatchesOfDelegation;
import loaders.ResultatsDelegationLoader;
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

public class FragmentResultatsDelegation extends ListFragment implements SportsLoaderListener, MatchesFilteredListener{

	ListView list;
	ProgressDialog dialog;

	public static FragmentResultatsDelegation newInstance(){
		FragmentResultatsDelegation fragment = new FragmentResultatsDelegation();
		return fragment;
	}

	public FragmentResultatsDelegation() {	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,	Bundle savedInstanceState) {

		return inflater.inflate(R.layout.listfragment, container, false);

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
	public void onLoadFinished(final ArrayList<String> delegations) {
		dialog.dismiss();
		list.post(new Runnable() {

			@Override
			public void run() {
				list.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,delegations));
				list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						getMatchesOfDelegation(delegations.get(position));

					}
				});
			}
		});	
	}

	public void getMatchesOfDelegation(String delegation){
		GetMatchesOfDelegation loader = new GetMatchesOfDelegation(delegation, this);
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

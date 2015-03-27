package cartel.mines.nantes2015;

import java.util.ArrayList;

import loaders.ResultatsLoader;
import tools.ResultatsListener;
import beans.Match;
import beans.Resultat;
import adapters.ExpandableMatchesListAdapter;
import adapters.ResultatsListAdapter;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

public class FragmentMatchesResultats extends Fragment implements ResultatsListener{

	ExpandableListView list;
	ProgressDialog dialog;

	public static FragmentMatchesResultats newInstance(){
		FragmentMatchesResultats fragment = new FragmentMatchesResultats();
		return fragment;
	}

	public FragmentMatchesResultats() {	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,	Bundle savedInstanceState) {
		View convertView = inflater.inflate(R.layout.fragment_resultats, container, false);
		
		list = (ExpandableListView) convertView.findViewById(R.id.matches_list);
		
		return convertView;
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		final ResultatsLoader loader = new ResultatsLoader(this);
		loader.start();
		
		dialog = ProgressDialog.show(getActivity(), "Veuillez patienter...", "Chargement...");
		dialog.setCancelable(true);
		dialog.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				loader.interrupt();
			}
		});

		
	}

	@Override
	public void onLoadFinished(final ArrayList<Resultat> resultatsJour1, final ArrayList<Resultat> resultatsJour2,
			final ArrayList<Resultat> resultatsJour3) {
		dialog.dismiss();
		list.post(new Runnable() {

			@Override
			public void run() {
				ExpandableMatchesListAdapter adapter = new ExpandableMatchesListAdapter(getActivity(), resultatsJour1, resultatsJour2, resultatsJour3);
				list.setAdapter(adapter);
				list.expandGroup(adapter.getGroupCount()-1);
			}
		});
	}
}

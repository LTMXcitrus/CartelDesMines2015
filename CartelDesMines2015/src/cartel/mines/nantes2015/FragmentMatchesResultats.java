package cartel.mines.nantes2015;

import java.util.ArrayList;

import loaders.ResultatsLoader;
import tools.ResultatsListener;
import beans.Match;
import beans.Resultat;
import adapters.ResultatsListAdapter;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FragmentMatchesResultats extends ListFragment implements ResultatsListener{

	ListView list;
	ProgressDialog dialog;

	public static FragmentMatchesResultats newInstance(){
		FragmentMatchesResultats fragment = new FragmentMatchesResultats();
		return fragment;
	}

	public FragmentMatchesResultats() {	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,	Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.fragment_resultats, container, false);
		
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

		list = getListView();
	}

	@Override
	public void onLoadFinished(final ArrayList<Resultat> resultats) {
		dialog.dismiss();
		list.post(new Runnable() {

			@Override
			public void run() {
				list.setAdapter(new ResultatsListAdapter(getActivity(), R.layout.matches_list_item, resultats));
			}
		});
	}
}

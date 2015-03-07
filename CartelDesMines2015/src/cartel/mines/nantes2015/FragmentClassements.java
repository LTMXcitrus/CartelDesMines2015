package cartel.mines.nantes2015;

import java.util.ArrayList;
import java.util.Collections;

import loaders.ClassementLoader;
import loaders.MatchesLoader;
import beans.Classement;
import tools.ClassementListener;
import adapters.ClassementAdapter;
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

public class FragmentClassements extends ListFragment implements ClassementListener{
	
	ProgressDialog dialog;
	ListView list;
	
	public static FragmentClassements newInstance(){
		return new FragmentClassements();
	}
	
	private FragmentClassements() {	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,	Bundle savedInstanceState){
		
		return inflater.inflate(R.layout.fragment_resultats, container, false);
		
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		final ClassementLoader loader = new ClassementLoader(this);
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
	public void onLoadFinished(final ArrayList<Classement> classements) {
		Collections.sort(classements);
		dialog.dismiss();
		list.post(new Runnable() {
			
			@Override
			public void run() {
				ArrayAdapter<Classement> adapter = new ClassementAdapter(getActivity(), R.layout.leaderboards_list_item_layout, classements);
				list.setAdapter(adapter);
				list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = new Intent(getActivity(),ClassementDetailActivity.class);
						intent.putExtra("classement", classements.get(position));
						startActivity(intent);
					}
				});
				
			}
		});
	}

}

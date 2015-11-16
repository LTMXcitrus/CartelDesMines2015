package cartel.mines.nantes2015;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import loaders.ClassementLoader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import tools.ClassementListener;
import adapters.ClassementAdapter;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import beans.Classement;

public class FragmentClassements extends ListFragment implements ClassementListener{

	private static final String PREFS_LEADERBOARD_AVAILABLE = "leaderboardAvailable";

	ProgressDialog dialog;
	ListView list;

	public static FragmentClassements newInstance(){
		return new FragmentClassements();
	}

	private FragmentClassements() {	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,	Bundle savedInstanceState){

		return inflater.inflate(R.layout.listfragment, container, false);

	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		final ClassementLoader loader = new ClassementLoader(this);
		if(leaderboardIsAvailable()){
			loader.start();
		}

		dialog = ProgressDialog.show(getActivity(), "Veuillez patienter...", "Chargement...");
		dialog.setCancelable(true);
		dialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				loader.interrupt();
			}
		});
		list = getListView();
		ArrayList<Classement> objects = new ArrayList<Classement>();
		list.setAdapter(new ClassementAdapter(getActivity(), R.layout.leaderboards_list_item_layout, objects));
	}

	@Override
	public void onLoadFinished(final ArrayList<Classement> classements) {
		Collections.sort(classements);
		Collections.reverse(classements);
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
						intent.putExtra("rank", position+1);
						startActivity(intent);
					}
				});
			}
		});
	}

	private class IsLeaderBoardAvailable extends AsyncTask<Void,Void,Boolean>{

		@Override
		protected Boolean doInBackground(Void... params) {
			boolean result = false;
			try {
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet("http://cartel2015.com/fr/perso/webservices/static/isLeaderboardEnabled.php");
				HttpResponse r;

				r = client.execute(get);

				String entity = EntityUtils.toString(r.getEntity(), "UTF-8");
				if(entity.equals("YES")){
					result =true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(Boolean result){
			if(result){
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
				prefs.edit().putBoolean(PREFS_LEADERBOARD_AVAILABLE, result);
				ClassementLoader loader = new ClassementLoader(FragmentClassements.this);
				loader.start();
			}
			dialog.dismiss();
		}
	}

	private boolean leaderboardIsAvailable(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		boolean available = prefs.getBoolean(PREFS_LEADERBOARD_AVAILABLE, false);
		if(!available){
			IsLeaderBoardAvailable task = new IsLeaderBoardAvailable();
			task.execute();
		}
		return available;
	}
}

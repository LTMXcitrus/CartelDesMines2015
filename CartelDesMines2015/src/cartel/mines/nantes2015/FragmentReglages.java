package cartel.mines.nantes2015;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import loaders.DelegationsListLoader;
import tools.DelegationsListLoaderCallback;
import adapters.SpinnerDelegationChoiceAdapter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class FragmentReglages extends Fragment implements DelegationsListLoaderCallback{
	
	Handler handler = new Handler();
	
	Spinner spinner;
	Button valider;
	
	EditText usernameInput;
	
	String delegation;
	String previousDelegation;
	ArrayList<String> delegations;
	
	public static FragmentReglages newInstance(){
		return new FragmentReglages();
	}
	
	public FragmentReglages(){	}
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView=inflater.inflate(R.layout.registration_display, container, false);
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		String previousUsername = prefs.getString("username", "noname");
		previousDelegation = prefs.getString("delegation", "Nantes");
		
		delegations = new ArrayList<String>();
		
		spinner = (Spinner) rootView.findViewById(R.id.delegation_choice_registration);
		
		usernameInput = (EditText) rootView.findViewById(R.id.username_input);
		if(!previousUsername.equals("noname")){
			usernameInput.setText(previousUsername);
		}
			
		valider = (Button) rootView.findViewById(R.id.onRegistrationDone);
		valider.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
				prefs.edit().putString("username", usernameInput.getText().toString()).commit();
				prefs.edit().putString("delegation", (String) spinner.getSelectedItem()).commit();
				
				Toast.makeText(getActivity(), "Préférences sauvegardées", Toast.LENGTH_LONG).show();;
				
				//TODO
			}
		});
		
		return rootView;	
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		Set<String> delegationsSet = prefs.getStringSet("delegations", new HashSet<String>());
		
		delegations = new ArrayList<String>();
		delegations.addAll(delegationsSet);
		Collections.sort(delegations);
		
		if(delegationsSet.isEmpty()){
			DelegationsListLoader loader = new DelegationsListLoader(this);
			loader.start();
		}
		int chosenPosition = delegations.indexOf(previousDelegation);
		
		spinner.setAdapter(new SpinnerDelegationChoiceAdapter(getActivity(),delegations));
		spinner.setSelection(chosenPosition);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				delegation = delegations.get(position);
				valider.setEnabled(true);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				valider.setEnabled(false);
			}
		});
	}

	@Override
	public void onLoadFinished(final ArrayList<String> delegationsList) {
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				Collections.sort(delegationsList);
				delegations = delegationsList;
				
				int chosenPosition = delegationsList.indexOf(previousDelegation);
				
				spinner.setAdapter(new SpinnerDelegationChoiceAdapter(getActivity(), delegationsList));
				
				spinner.setSelection(chosenPosition);
				
				SharedPreferences  prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
				Set<String> delegationsSet = new HashSet<String>(delegationsList);
				prefs.edit().putStringSet("delegations", delegationsSet).commit();
			}
		});
		
	};

}

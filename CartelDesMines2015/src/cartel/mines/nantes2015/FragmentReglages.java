package cartel.mines.nantes2015;

import adapters.SpinnerDelegationChoiceAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class FragmentReglages extends Fragment{
	
	Spinner spinner;
	Button valider;
	
	String delegation;
	String[] delegations;
	
	public static FragmentReglages newInstance(){
		return new FragmentReglages();
	}
	
	public FragmentReglages(){	}
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView=inflater.inflate(R.layout.registration_display, container, false);
		
		delegations = getActivity().getResources().getStringArray(R.array.delegations);
		
		spinner = (Spinner) rootView.findViewById(R.id.delegation_choice_registration);
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				delegation = delegations[position];
				valider.setEnabled(true);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				valider.setEnabled(false);
			}
		});
		
		spinner.setAdapter(new SpinnerDelegationChoiceAdapter(getActivity()));
		
		valider = (Button) rootView.findViewById(R.id.onRegistrationDone);
		valider.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO
			}
		});
		
		return rootView;	
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	};

}

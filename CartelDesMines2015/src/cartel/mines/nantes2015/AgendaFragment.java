package cartel.mines.nantes2015;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import beans.CalendarEvent;
import loaders.PlanningLoader;
import tools.PlanningOnLoadListener;
import adapters.ExpandableAgendaListAdapter;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

public class AgendaFragment extends Fragment implements PlanningOnLoadListener{
	private ProgressDialog dialog;
	
	ArrayList<CalendarEvent> firstDay;
	ArrayList<CalendarEvent> secondDay;
	ArrayList<CalendarEvent> thirdDay;
	ArrayList<CalendarEvent> fourthDay;
	
	HashMap<String, ArrayList<CalendarEvent>> objects;
	String[] jours;
	
	ExpandableListView list;


	public static AgendaFragment newInstance(){
		return new AgendaFragment();

	}

	public AgendaFragment(){ }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		dialog = ProgressDialog.show(getActivity(), "Veuillez Patienter...", "Chargement...");
		dialog.setCancelable(true);		
		
		final PlanningLoader loader = new PlanningLoader(this);
		loader.start();
		dialog.setOnCancelListener(new OnCancelListener() {	
			@Override
			public void onCancel(DialogInterface dialog) {
				loader.interrupt();
			}
		});
				
		View rootView=inflater.inflate(R.layout.agenda_fragment, container, false);
		
		list = (ExpandableListView) rootView.findViewById(R.id.agendalist);
		
		return rootView;
	}
	
	

	@Override
	public void onLoadFinished(ArrayList<CalendarEvent> firstDayEvent,
			ArrayList<CalendarEvent> secondDayEvent,
			ArrayList<CalendarEvent> thirdDayEvent,
			ArrayList<CalendarEvent> fourthDayEvent) {
		
		jours = getResources().getStringArray(R.array.dates);
		
		Collections.sort(firstDayEvent);
		Collections.sort(secondDayEvent);
		Collections.sort(thirdDayEvent);
		Collections.sort(fourthDayEvent);
		
		
		this.firstDay=firstDayEvent;
		this.secondDay=secondDayEvent;
		this.thirdDay=thirdDayEvent;
		this.fourthDay=fourthDayEvent;
		
		objects = new HashMap<String, ArrayList<CalendarEvent>>();
		objects.put(jours[3], firstDayEvent);
		objects.put(jours[2], secondDayEvent);
		objects.put(jours[1], thirdDayEvent);
		objects.put(jours[0], fourthDayEvent);	
	
		final ExpandableAgendaListAdapter expandableAdapter = new ExpandableAgendaListAdapter(getActivity(), jours, objects);
		list.post(new Runnable() {
			
			@Override
			public void run() {
				
				list.setAdapter(expandableAdapter);
				list.expandGroup(3);
				list.setOnChildClickListener(new OnChildClickListener() {
					
					@Override
					public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
						Intent intent = new Intent(getActivity(),EventDetailActivity.class);
						intent.putExtra("event", objects.get(jours[groupPosition]).get(childPosition));
						startActivity(intent);
						return true;
					}
				});
				dialog.dismiss();
			}
		});
		
		
	}

	

}

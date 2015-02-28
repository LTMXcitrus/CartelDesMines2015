package cartel.mines.nantes2015;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.json.JSONArray;
import org.json.JSONException;

import beans.CalendarEvent;
import loaders.PlanningLoader;
import tools.PlanningOnLoadListener;
import adapters.GridViewAgendaAdapter;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class AgendaFragment extends Fragment implements PlanningOnLoadListener{
	private GridView grid;
	private ArrayList<ArrayList<CalendarEvent>> events;
	private ProgressDialog dialog;


	public static AgendaFragment newInstance(){
		return new AgendaFragment();

	}

	public AgendaFragment(){ }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		dialog = ProgressDialog.show(getActivity(), "Veuillez Patienter...", "Chargement...");
		PlanningLoader loader = new PlanningLoader(this);
		loader.start();
		View rootView=inflater.inflate(R.layout.agenda_fragment, container, false);
		grid = (GridView) rootView.findViewById(R.id.agenda_gridview);
		grid.setAdapter(new GridViewAgendaAdapter(getActivity(), R.layout.cell_gridview_agenda, getActivity().getResources().getStringArray(R.array.dates)));
		grid.setNumColumns(2);
		grid.setHorizontalSpacing(5);
		grid.setVerticalSpacing(5);
		
		return rootView;
	}

	@Override
	public void onLoadFinished(ArrayList<CalendarEvent> firstDayEvent,
			ArrayList<CalendarEvent> secondDayEvent,
			ArrayList<CalendarEvent> thirdDayEvent,
			ArrayList<CalendarEvent> fourthDayEvent) {
		
		events = new ArrayList<ArrayList<CalendarEvent>>();
		events.add(firstDayEvent);
		events.add(secondDayEvent);
		events.add(thirdDayEvent);
		events.add(fourthDayEvent);
		GridViewAgendaAdapter adapter = 
				new GridViewAgendaAdapter(getActivity(), R.layout.cell_gridview_agenda, getActivity().getResources().getStringArray(R.array.dates), events);
		grid.post(new Runnable() {
			
			@Override
			public void run() {
				GridViewAgendaAdapter adapter = 
						new GridViewAgendaAdapter(getActivity(), R.layout.cell_gridview_agenda, getActivity().getResources().getStringArray(R.array.dates), events);
				grid.setAdapter(adapter);
				grid.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						JSONArray array = new JSONArray();
						ArrayList<CalendarEvent> eventsOfDay = events.get(position);
						for(CalendarEvent event : eventsOfDay){
							try {
								array.put(event.toJson());
							} catch (JSONException e) {
								Log.e("Cartel2015", e.getMessage());
							}							
						}
						Intent intent = new Intent(getActivity(),EventsOfDayList.class);
						intent.putExtra("eventList", eventsOfDay);
						startActivity(intent);
					}
				});
				dialog.dismiss();
			}
		});
		
		
	}

	

}

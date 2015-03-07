package cartel.mines.nantes2015;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;

import com.google.android.gms.internal.fi;

import beans.CalendarEvent;
import loaders.PlanningLoader;
import tools.PlanningOnLoadListener;
import adapters.CalendarDayListAdapter;
import adapters.GridViewAgendaAdapter;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

public class AgendaFragment extends ListFragment implements PlanningOnLoadListener{
	private ListView list;
	private ArrayList<ArrayList<CalendarEvent>> events;
	private ProgressDialog dialog;
	
	ArrayList<CalendarEvent> firstDay;
	ArrayList<CalendarEvent> secondDay;
	ArrayList<CalendarEvent> thirdDay;
	ArrayList<CalendarEvent> fourthDay;


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

		return rootView;
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		
		list = getListView();
		
		
	};

	@Override
	public void onLoadFinished(ArrayList<CalendarEvent> firstDayEvent,
			ArrayList<CalendarEvent> secondDayEvent,
			ArrayList<CalendarEvent> thirdDayEvent,
			ArrayList<CalendarEvent> fourthDayEvent) {
		
		Collections.sort(firstDayEvent);
		Collections.sort(secondDayEvent);
		Collections.sort(thirdDayEvent);
		Collections.sort(fourthDayEvent);
		
		this.firstDay=firstDayEvent;
		this.secondDay=secondDayEvent;
		this.thirdDay=thirdDayEvent;
		this.fourthDay=fourthDayEvent;
		
		
		
		final int jour1 = firstDayEvent.size() + 1;
		final int jour2 = secondDayEvent.size() + jour1 + 1;
		final int jour3 = thirdDayEvent.size() + jour2 + 1;		
		
		final ArrayAdapter<CalendarEvent> adapter = new CalendarDayListAdapter(getActivity(), R.layout.calendar_day_list_item, firstDayEvent,
				secondDayEvent, thirdDayEvent, fourthDayEvent);
		
		list.post(new Runnable() {
			
			@Override
			public void run() {
				
				list.setAdapter(adapter);
				list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						Intent intent = new Intent(getActivity(), EventDetailActivity.class);
						if(position < jour1){
							intent.putExtra("event", firstDay.get(position - 1));
						}
						else if(position < jour2){
							intent.putExtra("event", secondDay.get(position - 1 - jour1));
						}
						else if(position < jour3){
							intent.putExtra("event", thirdDay.get(position - 1 - jour2));
						}
						else{
							intent.putExtra("event", fourthDay.get(position - 1 - jour3));
						}
						
						startActivity(intent);
					}
				});
				dialog.dismiss();
			}
		});
		
		
	}

	

}

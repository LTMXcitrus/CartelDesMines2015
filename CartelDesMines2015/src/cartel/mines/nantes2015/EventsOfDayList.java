package cartel.mines.nantes2015;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import beans.CalendarEvent;
import adapters.EventsListAdapter;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EventsOfDayList extends ListActivity{
	ArrayList<CalendarEvent> eventsOfDay; 
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.events_of_day_list);
		
		getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.orange_cartel)));
		
		ListView list = getListView();
		
		eventsOfDay = (ArrayList<CalendarEvent>) getIntent().getSerializableExtra("eventList");
		list.setAdapter(new EventsListAdapter(this, R.layout.event_list_item, eventsOfDay));
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(EventsOfDayList.this, EventDetailActivity.class);
				intent.putExtra("event", eventsOfDay.get(position));
				startActivity(intent);
			}
		});
	}

}

package cartel.mines.nantes2015;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import beans.CalendarEvent;

public class EventDetailActivity extends ActionBarActivity{
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_detail_activity);
		
		CalendarEvent event = (CalendarEvent) getIntent().getSerializableExtra("event");
		
		ActionBar actionBar = getSupportActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.orange_cartel)));
		actionBar.setTitle(event.getEvent());
		
		TextView date = (TextView) findViewById(R.id.dateEvent);
		TextView details = (TextView) findViewById(R.id.details);
		TextView place = (TextView) findViewById(R.id.placeEvent);
		
		date.setText("Le " + event.getDayOfMonth() +"/04, à " + event.getHourAsString() + "h" + event.getMinuteAsString());
		place.setText(event.getPlace());
		
		details.setText(event.getDescription());
	}
}

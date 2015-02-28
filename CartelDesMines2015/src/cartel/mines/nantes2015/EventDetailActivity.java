package cartel.mines.nantes2015;

import org.json.JSONException;

import beans.CalendarEvent;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class EventDetailActivity extends Activity{
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_detail_activity);
		
		getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.orange_cartel)));
		
		CalendarEvent event = (CalendarEvent) getIntent().getSerializableExtra("event");
		
		TextView details = (TextView) findViewById(R.id.details);
		
		try {
			details.setText(event.toJson());
		} catch (JSONException e) {
			Log.e("Cartel2015", e.getMessage());
		}
	}
}

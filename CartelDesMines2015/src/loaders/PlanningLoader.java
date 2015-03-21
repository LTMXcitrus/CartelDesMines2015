package loaders;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import beans.CalendarEvent;
import tools.PlanningOnLoadListener;

public class PlanningLoader extends Thread{
	
	//TODO set to webservices


	private static final int FIRST_DAY = 11;
	private static final int SECOND_DAY = 12;
	private static final int THIRD_DAY = 13;
	private static final int FOURTH_DAY = 14;

	private PlanningOnLoadListener handler;

	public PlanningLoader(PlanningOnLoadListener handler){
		this.handler=handler;
	}

	public void run(){
		try {
			ArrayList<CalendarEvent> firstDayEvent = new ArrayList<CalendarEvent>();
			ArrayList<CalendarEvent> secondDayEvent = new ArrayList<CalendarEvent>();
			ArrayList<CalendarEvent> thirdDayEvent = new ArrayList<CalendarEvent>();
			ArrayList<CalendarEvent> fourthDayEvent = new ArrayList<CalendarEvent>();

			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet("http://1-dot-inlaid-span-809.appspot.com/calendar");
			HttpResponse r = client.execute(get);
			String json = EntityUtils.toString(r.getEntity());

			JSONObject calendar = new JSONObject(json);
			JSONArray events = calendar.getJSONArray("Calendar");
			for(int i=0; i< events.length(); i++){
				JSONObject eventObject = events.getJSONObject(i);
				String event = eventObject.getString("event");
				String date = eventObject.getString("date"); 
				int duration = eventObject.getInt("duration");
				String place = eventObject.getString("place");
				String description = eventObject.getString("description");

				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
				Date utilDate = df.parse(date);
				DateTime jodaDate = new DateTime(utilDate);
				int dayOfMonth = jodaDate.getDayOfMonth();
				int hourOfDay = jodaDate.getHourOfDay();
				int minuteOfHour = jodaDate.getMinuteOfHour();

				switch(dayOfMonth){
				case FIRST_DAY:
					firstDayEvent.add(new CalendarEvent(event, dayOfMonth, hourOfDay, minuteOfHour, duration, place, description));
					break;
				case SECOND_DAY:
					secondDayEvent.add(new CalendarEvent(event, dayOfMonth, hourOfDay, minuteOfHour, duration, place, description));
					break;
				case THIRD_DAY:
					thirdDayEvent.add(new CalendarEvent(event, dayOfMonth, hourOfDay, minuteOfHour, duration, place, description));
					break;
				case FOURTH_DAY:
					fourthDayEvent.add(new CalendarEvent(event, dayOfMonth, hourOfDay, minuteOfHour, duration, place, description));
					break;
				default:
					break;
				}
			}

			handler.onLoadFinished(firstDayEvent, secondDayEvent, thirdDayEvent, fourthDayEvent);

		} catch (IOException | JSONException | ParseException e) {
			Log.e("Cartel2015", e.getMessage());
		}


	}

}

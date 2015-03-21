package beans;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class CalendarEvent implements Serializable, Comparable<CalendarEvent>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String event;
	private int dayOfMonth;
	private int hourOfDay;
	private int minuteOfHour;
	private int duration;
	private String place;
	private String description;
	
	
	public CalendarEvent(String event, int dayOfMonth, int hourOfDay,
			int minuteOfHour, int duration, String place, String description) {
		super();
		this.event = event;
		this.dayOfMonth = dayOfMonth;
		this.hourOfDay = hourOfDay;
		this.minuteOfHour = minuteOfHour;
		this.duration = duration;
		this.place = place;
		this.description = description;
	}
	
	public String toString(){
		return this.getEvent();
	}


	public String getEvent() {
		return event;
	}


	public void setEvent(String event) {
		this.event = event;
	}


	public int getDayOfMonth() {
		return dayOfMonth;
	}


	public void setDayOfMonth(int dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}


	public int getHourOfDay() {
		return hourOfDay;
	}


	public void setHourOfDay(int hourOfDay) {
		this.hourOfDay = hourOfDay;
	}


	public int getMinuteOfHour() {
		return minuteOfHour;
	}


	public void setMinuteOfHour(int minuteOfHour) {
		this.minuteOfHour = minuteOfHour;
	}


	public int getDuration() {
		return duration;
	}


	public void setDuration(int duration) {
		this.duration = duration;
	}


	public String getPlace() {
		return place;
	}


	public void setPlace(String place) {
		this.place = place;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getHourAsString(){
		String hour = Integer.toString(this.getHourOfDay());
		if(hour.length()==1){
			hour = "0"+hour; 
		}
		return hour;
	}
	
	public String getMinuteAsString(){
		String minute = Integer.toString(this.getMinuteOfHour());
		if(minute.length()==1){
			minute = "0"+minute; 
		}
		return minute;
	}
	
	
	public String toJson() throws JSONException{
		JSONObject o = new JSONObject();
		o.put("description", this.getDescription());
		o.put("place", this.getPlace());
		o.put("duration", this.getDuration());
		o.put("event",this.getEvent());
		o.put("dayofmonth", this.getDayOfMonth());
		o.put("hourofday",this.getHourOfDay());
		o.put("minuteofhour",this.getMinuteOfHour());
		
		return o.toString();
	}
	/**
	 * Creates a CalendarEvent Object from the given JSON String
	 * @param json
	 * @throws JSONException 
	 */
	public CalendarEvent(String json) throws JSONException{
		JSONObject o = new JSONObject(json);
		this.description = o.getString("description");
		this.place = o.getString("place");
		this.duration = o.getInt("duration");
		this.event = o.getString("event");
		this.dayOfMonth = o.getInt("dayofmonth");
		this.hourOfDay = o.getInt("hourofday");
		this.minuteOfHour = o.getInt("minuteofhour");
	}

	@Override
	public int compareTo(CalendarEvent another) {
		int result = 0;
		if(this.getDayOfMonth() < another.getDayOfMonth()){
			return -1;
		}
		else if(this.getDayOfMonth() > another .dayOfMonth){
			return 1;
		}
		else{
			if(this.getHourOfDay() < another.getHourOfDay()){
				result = -1;
			}
			else if(this.getHourOfDay() > another.getHourOfDay()){
				result = 1;
			}
			else{
				if(this.getMinuteOfHour() < another.getMinuteOfHour()){
					result = -1;
				}
				else if(this.getMinuteOfHour() > another.getMinuteOfHour()){
					result = 1;
				}
				else{
					result = 0;
				}
			}
			
		}
		
		return result;
	}
}

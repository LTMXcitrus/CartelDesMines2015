package beans;

import java.text.ParseException;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class Resultat implements Comparable<Resultat>{
	
	public abstract Resultat createFromJson(JSONObject json) throws JSONException, ParseException;
	
	public abstract String getSport();
	
	public abstract int getMinuteOfHour();
	
	public abstract int getHourOfDay();
	
	public abstract int getDayOfMonth();

}

package beans;

import java.text.ParseException;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class Resultat {
	
	public abstract Resultat createFromJson(JSONObject json) throws JSONException, ParseException;
	
	public abstract String getSport();

}

package beans;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.internal.jo;

public class Course extends Resultat implements Serializable{
	
	private String sport;
	private int hourOfDay;
	private int minuteOfHour;
	private int dayOfMonth;
	private String live;
	ArrayList<Participant> participants;
	private String matchType;
	
	public Course(){ }
	
	
	public Course(String sport, int hourOfDay, int minuteOfHour,
			int dayOfMonth, String live, ArrayList<Participant> participants,
			String matchType) {
		super();
		Collections.sort(participants);
		this.sport = sport;
		this.hourOfDay = hourOfDay;
		this.minuteOfHour = minuteOfHour;
		this.dayOfMonth = dayOfMonth;
		this.live = live;
		this.participants = participants;
		this.matchType = matchType;
	}


	public String getSport() {
		return sport;
	}


	public void setSport(String sport) {
		this.sport = sport;
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


	public int getDayOfMonth() {
		return dayOfMonth;
	}


	public void setDayOfMonth(int dayOhMonth) {
		this.dayOfMonth = dayOhMonth;
	}


	public String getLive() {
		return live;
	}


	public void setLive(String live) {
		this.live = live;
	}


	public ArrayList<Participant> getParticipants() {
		return participants;
	}


	public void setParticipants(ArrayList<Participant> participants) {
		this.participants = participants;
	}


	public String getMatchType() {
		return matchType;
	}


	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}
	
	@Override
	public Course createFromJson(JSONObject json) throws JSONException, ParseException{
		String sport = json.getString("sport");
		String date = json.getString("date");
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Date utilDate = df.parse(date);
		DateTime jodaDate = new DateTime(utilDate);
		
		int hourOfDay = jodaDate.getHourOfDay();
		int minuteOfHour = jodaDate.getMinuteOfHour();
		int dayOfMonth = jodaDate.getDayOfMonth();
		
		String live = json.getString("live");
		
		ArrayList<Participant> participants  = new ArrayList<Participant>();
		JSONArray participantsJSON = json.getJSONArray("participants");
		for(int i = 0; i<participantsJSON.length(); i++){
			JSONObject participantJSON = participantsJSON.getJSONObject(i);
			participants.add(Participant.createFromJSON(participantJSON));
		}
		String matchType = json.getString("matchType");
		
		return new Course(sport, hourOfDay, minuteOfHour, dayOfMonth, live, participants, matchType);
	}
	
	public Participant getVainqueur(){
		return this.getParticipants().get(0);
	}
	
	public Participant getDeuxieme(){
		return this.getParticipants().get(1);
	}
	
	public Participant getTroisieme(){
		return this.getParticipants().get(2);
	}

}

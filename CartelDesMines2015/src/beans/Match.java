package beans;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

public class Match extends Resultat implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sport;
	private int hourOfDay;
	private int minuteOfHour;
	private int dayOfMonth;
	private String live;
	private String player1;
	private String player2;
	private int scorePlayer1;
	private int scorePlayer2;
	private String matchType;
	
	public Match(){ }


	public Match(String sport, int hourOfDay, int minuteOfHour, int dayOfMonth,
			String live, String player1, String player2, int scorePlayer1,
			int scorePlayer2, String matchType) {
		super();
		this.sport = sport;
		this.hourOfDay = hourOfDay;
		this.minuteOfHour = minuteOfHour;
		this.dayOfMonth = dayOfMonth;
		this.live = live;
		this.player1 = player1;
		this.player2 = player2;
		this.scorePlayer1 = scorePlayer1;
		this.scorePlayer2 = scorePlayer2;
		this.matchType = matchType;
	}
	
	@Override
	public Match createFromJson(JSONObject json) throws JSONException, ParseException{
		
		String sport = json.getString("sport");
		
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		String date = json.getString("date");
		Date utilDate = df.parse(date);
		DateTime jodaDate = new DateTime(utilDate);
		int hourOfDay = jodaDate.getHourOfDay();
		int minuteOfHour = jodaDate.getMinuteOfHour();
		int dayOfMonth = jodaDate.getDayOfMonth();
		
		String live = json.getString("live");
		String player1 = json.getString("player1");
		String player2 = json.getString("player2");
		int scorePlayer1 = json.optInt("scorePlayer1");
		int scorePlayer2 = json.optInt("scorePlayer2");
		String matchType  = json.getString("matchType");
		
		return new Match(sport, hourOfDay, minuteOfHour, dayOfMonth, live, player1, player2, scorePlayer1, scorePlayer2, matchType);
		
	}
	
	public Match createFromJson(JSONObject json, String sport) throws JSONException, ParseException{
		json.put("sport", sport);
		return createFromJson(json);
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


	public void setDayOfMonth(int dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}


	public String getLive() {
		return live;
	}


	public void setLive(String live) {
		this.live = live;
	}


	public String getPlayer1() {
		return player1;
	}


	public void setPlayer1(String player1) {
		this.player1 = player1;
	}


	public String getPlayer2() {
		return player2;
	}


	public void setPlayer2(String player2) {
		this.player2 = player2;
	}


	public int getScorePlayer1() {
		return scorePlayer1;
	}


	public void setScorePlayer1(int scorePlayer1) {
		this.scorePlayer1 = scorePlayer1;
	}


	public int getScorePlayer2() {
		return scorePlayer2;
	}


	public void setScorePlayer2(int scorePlayer2) {
		this.scorePlayer2 = scorePlayer2;
	}


	public String getMatchType() {
		return matchType;
	}


	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}
	
	public String toString(){
		return this.getMatchType();
	}


	@Override
	public int compareTo(Resultat another) {
		int result = 0;
		if(this.getDayOfMonth() < another.getDayOfMonth()){
			result = -1;
		}
		else if(this.getDayOfMonth() > another.getDayOfMonth()){
			result = 1;
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

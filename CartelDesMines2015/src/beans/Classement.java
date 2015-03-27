package beans;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class Classement implements Serializable, Comparable<Classement>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String team;
	private int points;
	private int matchesplayed;
	private int wins;
	private int draws;
	private int losses;


	public Classement(String team, int points, int matchesplayed,
			int wins, int draws, int losses) {
		super();
		this.team = team;
		this.points = points;
		this.matchesplayed = matchesplayed;
		this.wins = wins;
		this.draws = draws;
		this.losses = losses;
	}

	public static Classement createClassementFromJson(JSONObject json, String team) throws JSONException{
		int points = json.getInt("points");
		int matchesplayed = json.getInt("matchesPlayed");
		int wins = json.getInt("wins");
		int draws = json.getInt("draws");
		int losses = json.getInt("losses");
		return new Classement(team, points, matchesplayed, wins, draws, losses);
	}

	public String getTeam() {
		return team;
	}


	public void setTeam(String team) {
		this.team = team;
	}


	public int getPoints() {
		return points;
	}


	public void setPoints(int points) {
		this.points = points;
	}


	public int getMatchesplayed() {
		return matchesplayed;
	}


	public void setMatchesplayed(int matchesplayed) {
		this.matchesplayed = matchesplayed;
	}


	public int getWins() {
		return wins;
	}


	public void setWins(int wins) {
		this.wins = wins;
	}


	public int getDraws() {
		return draws;
	}


	public void setDraws(int draws) {
		this.draws = draws;
	}


	public int getLosses() {
		return losses;
	}


	public void setLosses(int losses) {
		this.losses = losses;
	}

	public String toString(){
		return this.getTeam() +", "+ this.getPoints();
	}


	@Override
	public int compareTo(Classement another) {
		if(this.getPoints() < another.getPoints()){
			return -1;
		}
		else if(this.getPoints() == another.getPoints()){
			return 0;
		}
		else{
			return 1;
		}
	}



}

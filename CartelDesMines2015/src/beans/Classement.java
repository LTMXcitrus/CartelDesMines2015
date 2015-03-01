package beans;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class Classement implements Serializable, Comparable<Classement>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int rank;
	private String team;
	private int points;
	private int matchesplayed;
	private int wins;
	private int draws;
	private int losses;


	public Classement(int rank, String team, int points, int matchesplayed,
			int wins, int draws, int losses) {
		super();
		this.rank = rank;
		this.team = team;
		this.points = points;
		this.matchesplayed = matchesplayed;
		this.wins = wins;
		this.draws = draws;
		this.losses = losses;
	}

	public static Classement createClassementFromJson(JSONObject json) throws JSONException{
		int rank = json.getInt("rank");
		String team = json.getString("team");
		int points = json.getInt("points");
		int matchesplayed = json.getInt("matchesplayed");
		int wins = json.getInt("wins");
		int draws = json.getInt("draws");
		int losses = json.getInt("losses");
		return new Classement(rank, team, points, matchesplayed, wins, draws, losses);
	}


	public int getRank() {
		return rank;
	}


	public void setRank(int rank) {
		this.rank = rank;
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
		if(this.getRank() < another.getRank()){
			return -1;
		}
		else if(this.getRank() == another.getRank()){
			return 0;
		}
		else{
			return 1;
		}
	}



}

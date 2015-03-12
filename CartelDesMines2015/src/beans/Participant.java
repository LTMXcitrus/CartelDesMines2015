package beans;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

public class Participant implements Serializable, Comparable<Participant>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String nom;
	private String prenom;
	private String ecole;
	private String temps;	
	
	public Participant(String nom, String prenom, String ecole,	String temps) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.ecole = ecole;
		this.temps = temps;
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getPrenom() {
		return prenom;
	}
	
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	public String getEcole() {
		return ecole;
	}
	
	public void setEcole(String ecole) {
		this.ecole = ecole;
	}
	
	public String getTemps() {
		return temps;
	}
	
	public void setTemps(String temps) {
		this.temps = temps;
	}
	
	public static Participant createFromJSON(JSONObject json) throws JSONException, ParseException{
		String nom = json.getString("nom");
		String prenom = json.getString("prenom");
		String temps = json.getString("temps");
		
		String ecole = json.getString("ecole");
		
		return new Participant(nom, prenom, ecole, temps);
	}
	
	public int getMinutesOfTime() throws ParseException{
		String temps = this.getTemps();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Date utilDate = df.parse(temps);
		DateTime jodaDate = new DateTime(utilDate);
		return jodaDate.getMinuteOfHour();
	}
	
	public int getSecondesOfTime() throws ParseException{
		String temps = this.getTemps();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Date utilDate = df.parse(temps);
		DateTime jodaDate = new DateTime(utilDate);
		return jodaDate.getSecondOfMinute();
	}
	public int getMillisOfTime() throws ParseException{
		String temps = this.getTemps();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Date utilDate = df.parse(temps);
		DateTime jodaDate = new DateTime(utilDate);
		return jodaDate.getMillisOfSecond();
	}

	@Override
	public int compareTo(Participant another) {
		int result = 0;
		try {
			if(this.getMinutesOfTime() < another.getMinutesOfTime()){
				result = -1;
			}
			else if(this.getMinutesOfTime() > another.getMinutesOfTime()){
				result =  1;
			}
			else{
				if(this.getSecondesOfTime() < another.getSecondesOfTime()){
					result = -1;
				}
				else if(this.getSecondesOfTime() > another.getSecondesOfTime()){
					result = 1;
				}
				else{
					result = this.getMillisOfTime()-another.getMillisOfTime();
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	

}

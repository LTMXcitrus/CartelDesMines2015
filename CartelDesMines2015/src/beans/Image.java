package beans;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

public class Image implements Serializable, Comparable<Image>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String PREFIXE_HD = "http://cartel2015.com/fr/perso/imagethread/original/";
	private static final String PREFIXE_COMP = "http://cartel2015.com/fr/perso/imagethread/lowres/";

	String auteur;
	String comment;
	int dayOfMonth;
	int hourOfDay;
	int minutesOfHour;
	int secondesOfMinute;
	String imageUrl;
	String thumbnailUrl;


	public Image(String auteur, String comment, int dayOfMonth, int hourOfDay,
			int minutesOfHour, int secondesOfMinute, String imageUrl, String thumbnailUrl) {
		super();
		this.auteur = auteur;
		this.comment = comment;
		this.dayOfMonth = dayOfMonth;
		this.hourOfDay = hourOfDay;
		this.minutesOfHour = minutesOfHour;
		this.imageUrl = imageUrl;
		this.thumbnailUrl=thumbnailUrl;
		this.secondesOfMinute=secondesOfMinute;
	}


	public String getAuteur() {
		return auteur;
	}


	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
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


	public int getMinutesOfHour() {
		return minutesOfHour;
	}


	public void setMinutesOfHour(int minutesOfHour) {
		this.minutesOfHour = minutesOfHour;
	}


	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public String getThumbnailUrl() {
		return thumbnailUrl;
	}


	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	
	


	public int getSecondesOfMinute() {
		return secondesOfMinute;
	}


	public void setSecondesOfMinute(int secondesOfMinute) {
		this.secondesOfMinute = secondesOfMinute;
	}


	public static Image createImageFromJson(JSONObject json, String date) throws JSONException, ParseException{
		String auteur = json.getString("author");
		String comment = json.getString("comment");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Date utilDate = df.parse(date);
		DateTime jodaDate = new DateTime(utilDate);
		int dayOfMonth = jodaDate.getDayOfMonth();
		int hourOfDay = jodaDate.getHourOfDay();
		int minutesOfHour = jodaDate.getMinuteOfHour();
		int secondesOfMinute = jodaDate.getSecondOfMinute();
		String filename= json.getString("filename");
		String imageUrl = PREFIXE_HD+filename;
		String thumbnailUrl = PREFIXE_COMP+filename;

		return new Image(auteur, comment, dayOfMonth, hourOfDay, minutesOfHour, secondesOfMinute, imageUrl,thumbnailUrl);
	}


	@Override
	public int compareTo(Image another) {
		if(this.getDayOfMonth()<another.getDayOfMonth()){
			return -1;
		}
		else if(this.getDayOfMonth()>another.getDayOfMonth()){
			return 1;
		}
		else{
			if(this.getHourOfDay()<another.getHourOfDay()){
				return -1;
			}
			else if(this.getHourOfDay()>another.getHourOfDay()){
				return 1;
			}
			else{
				if(this.getMinutesOfHour()<another.getMinutesOfHour()){
					return -1;
				}
				else if(this.getMinutesOfHour()>another.getMinutesOfHour()){
					return 1;
					
				}
				else{
					if(this.getSecondesOfMinute()<another.getSecondesOfMinute()){
						return -1;
					}
					else if(this.getSecondesOfMinute()>another.getSecondesOfMinute()){
						return 1;
					}
					else{
						return 0;
					}
				}
			}
		}
		
	}
}

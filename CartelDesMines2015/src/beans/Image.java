package beans;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
	DateTime jodaDate;
	int minutesOfHour;
	int secondesOfMinute;
	String imageUrl;
	String thumbnailUrl;


	public Image(String auteur, String comment, int dayOfMonth, int hourOfDay,
			int minutesOfHour, int secondesOfMinute, String imageUrl, String thumbnailUrl, DateTime jodaDate) {
		super();
		this.auteur = auteur;
		this.comment = comment;
		this.dayOfMonth = dayOfMonth;
		this.hourOfDay = hourOfDay;
		this.minutesOfHour = minutesOfHour;
		this.imageUrl = imageUrl;
		this.thumbnailUrl=thumbnailUrl;
		this.secondesOfMinute=secondesOfMinute;
		this.jodaDate=jodaDate;
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



	public DateTime getJodaDate() {
		return jodaDate;
	}


	public void setJodaDate(DateTime jodaDate) {
		this.jodaDate = jodaDate;
	}


	public String getElapsedTimeSincePublication(){
		String prefixe = "il y a ";

		long currentMillis = System.currentTimeMillis();
		long imageMillis = this.getJodaDate().getMillis();
		long timeElapsed = currentMillis - imageMillis;

		long secondes = getSecondesFromMillis(timeElapsed);
		long minutes = getMinutesFromSecondes(secondes);
		long hours = getHourFromMinutes(minutes);
		long days = getDayFromHours(hours);
		long months = getMonthFromDays(days);
		long years = getYearFromMonth(months);

		if(years<1){
			if(months<1){
				if(days<1){
					if(hours<1){
						if(minutes<1){
							if(secondes>1){
								return prefixe + secondes + " secondes";
							}
							else{
								if(secondes<0){
									return prefixe + "0 seconde";
								}
								else{
									return prefixe + secondes + "seconde";
								}
							}
						}
						else{
							if(minutes>1){
								return prefixe + minutes + " minutes";
							}
							else{
								return prefixe + minutes + " minute";
							}
						}
					}
					else{
						if(hours>1){
							return prefixe + hours + " heures";
						}
						else{
							return prefixe + hours + " heure";
						}
					}
				}
				else{
					if(days>1){
						return prefixe + days + " jours";
					}
					else{
						return prefixe + days + " jour";
					}
				}
			}
			else{
				return prefixe+months+" mois";
			}
		}
		else{
			if(years>1){
				return prefixe+years+" ans";
			}
			else{
				return prefixe+years+" an";
			}
		}
	}

	/**
	 * 
	 * @param millis
	 * @return the number of secondes corresponding to the given number of millis
	 */
	public static long getSecondesFromMillis(long millis){
		return millis/1000;
	}


	/**
	 * 
	 * @param secondes
	 * @return the number of minutes corresponding to the given number of secondes
	 */
	public static long getMinutesFromSecondes(long secondes){
		return secondes/60;
	}

	/**
	 * 
	 * @param minutes
	 * @return the number of hours corresponding to the given number of minutes
	 */
	public static long getHourFromMinutes(long minutes){
		return minutes/60;
	}


	/**
	 * 
	 * @param hours
	 * @return the number of days corresponding to the given number of hours
	 */
	public static long getDayFromHours(long hours){
		return hours/24;
	}

	/**
	 * 
	 * @param days
	 * @return the number of month corresponding to the given number of days
	 */
	public static long getMonthFromDays(long days){
		return (long) (days/30.4375);
	}

	/**
	 * 
	 * @param months
	 * @return the number of years corresponding to the given number of months
	 */
	public static long getYearFromMonth(long months){
		return months/12;
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

		return new Image(auteur, comment, dayOfMonth, hourOfDay, minutesOfHour, secondesOfMinute, imageUrl,thumbnailUrl,jodaDate);
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

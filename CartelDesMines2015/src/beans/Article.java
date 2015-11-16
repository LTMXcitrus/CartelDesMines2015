package beans;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

public class Article implements Serializable, Comparable<Article>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String title;
	private int dayOfMonth;
	private int hourOfDay;
	private int minuteOfHour;
	private String author;
	private String imageURL;
	private String thumbnailURL;
	private boolean posted;
	private String body;
	private int monthOfYear;


	public Article(String title, int dayOfMonth, int hourOfDay,
			int minuteOfHour, String author, String imageURL,
			String thumbnailURL, boolean posted, String body, int monthOfYear) {
		super();
		this.title = title;
		this.dayOfMonth = dayOfMonth;
		this.hourOfDay = hourOfDay;
		this.minuteOfHour = minuteOfHour;
		this.author = author;
		this.imageURL = imageURL;
		this.thumbnailURL = thumbnailURL;
		this.posted = posted;
		this.body = body;
		this.monthOfYear=monthOfYear;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
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


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public String getImageURL() {
		return imageURL;
	}


	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}


	public String getThumbnailURL() {
		return thumbnailURL;
	}


	public void setThumbnailURL(String thumbnailURL) {
		this.thumbnailURL = thumbnailURL;
	}


	public boolean isPosted() {
		return posted;
	}


	public void setPosted(boolean posted) {
		this.posted = posted;
	}


	public String getBody() {
		return body;
	}


	public void setBody(String body) {
		this.body = body;
	}


	public int getMonthOfYear() {
		return monthOfYear;
	}


	public void setMonthOfYear(int monthOfYear) {
		this.monthOfYear = monthOfYear;
	}


	public String getMinuteOfHourAsString(){
		String result = Integer.toString(this.getMinuteOfHour());
		if(result.length()==1){
			result = "0"+result;
		}
		return result;

	}


	public static Article createArticleFromJson(JSONObject json) throws JSONException, ParseException{
		String title = json.getString("title");
		String date = json.getString("date");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Date utilDate = df.parse(date);
		DateTime jodaDate = new DateTime(utilDate);

		int hourOfDay = jodaDate.getHourOfDay();
		int dayOfMonth = jodaDate.getDayOfMonth();
		int minuteOfHour = jodaDate.getMinuteOfHour();
		int monthOfYear = jodaDate.getMonthOfYear();

		String author = json.getString("author");
		String imageURL = json.getString("imageurl");
		String thumbnailURL = json.getString("thumbnailurl");
		String postedString = json.getString("posted");
		boolean posted = postedString.equals("yes");

		String body = json.getString("body");

		return new Article(title, dayOfMonth, hourOfDay, minuteOfHour, author, imageURL, thumbnailURL, posted, body, monthOfYear);
	}


	@Override
	public int compareTo(Article another) {
		if(this.getMonthOfYear()> another.getMonthOfYear()){
			return -1;
		}
		else if(this.getMonthOfYear() < another.getMonthOfYear()){
			return 1;
		}
		else{
			if(this.getDayOfMonth() > another.getDayOfMonth()){
				return -1;
			}
			else if(this.getDayOfMonth() < another.getDayOfMonth()){
				return 1;
			}
			else{
				if(this.getHourOfDay() > another.getHourOfDay()){
					return -1;
				}
				else if(this.getHourOfDay() < another.getHourOfDay()){
					return 1;
				}
				else{
					if(this.getMinuteOfHour() > another.getMinuteOfHour()){
						return -1;
					}
					else if(this.getMinuteOfHour() < another.getMinuteOfHour()){
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

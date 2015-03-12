package beans;

import java.io.Serializable;

public class Notification implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String titre;
	private String body;
	private String imageUrl;
	private String imageThumbnailUrl;
	
	
	public Notification(String titre, String body, String imageUrl,String imageThumbnailUrl) {
		super();
		this.titre = titre;
		this.body = body;
		this.imageUrl = imageUrl;
		this.imageThumbnailUrl=imageThumbnailUrl;
	}


	public String getTitre() {
		return titre;
	}


	public void setTitre(String titre) {
		this.titre = titre;
	}


	public String getBody() {
		return body;
	}


	public void setBody(String body) {
		this.body = body;
	}


	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public String getImageThumbnailUrl() {
		return imageThumbnailUrl;
	}


	public void setImageThumbnailUrl(String imageThumbnailUrl) {
		this.imageThumbnailUrl = imageThumbnailUrl;
	}
}

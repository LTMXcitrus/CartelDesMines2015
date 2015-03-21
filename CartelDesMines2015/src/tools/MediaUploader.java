package tools;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class MediaUploader extends Thread{
	
	String pathToFile;
	String commentaire;
	String auteur;
	MediaUploaderListener handler;
	
	public MediaUploader(String pathToFile,String commentaire, String auteur, MediaUploaderListener handler) {
		super();
		this.pathToFile = pathToFile;
		this.commentaire = commentaire;
		this.handler = handler;
		this.auteur=auteur;
	}

	@Override
	public void run(){
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost postMedia = new HttpPost("http://cartel2015.com/fr/perso/imagethread/handleSentImage.php");
			
			FileBody fileBody  = new FileBody(new File(pathToFile));
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			
			builder.addPart("file", fileBody);
			builder.addPart("commentaire", new StringBody(commentaire));
			builder.addPart("auteur", new StringBody(auteur));
			HttpEntity entity = builder.build();
			postMedia.setEntity(entity);
			HttpResponse res = client.execute(postMedia);
			
			handler.onUploadFinished(res.getStatusLine().getStatusCode(),
					EntityUtils.toString(res.getEntity()));
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}

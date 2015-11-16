package tools;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
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
			
			ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8);
			
			builder.addPart("file", fileBody);
			builder.addTextBody("commentaire", commentaire.replace("\"","\\\""), contentType);
			builder.addTextBody("auteur", auteur, contentType);
			
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

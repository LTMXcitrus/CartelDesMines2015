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
	String fileName;
	MediaUploaderListener handler;
	
	public MediaUploader(String pathToFile,String fileName, MediaUploaderListener handler) {
		super();
		this.pathToFile = pathToFile;
		this.fileName = fileName;
		this.handler = handler;
	}

	@Override
	public void run(){
		try {
			// On récupère l'Url à laquelle uploader le media.
			HttpClient client = new DefaultHttpClient();
			HttpGet getUploadUrl = new HttpGet("http://1-dot-inlaid-span-809.appspot.com/getmediauploadurl");

			HttpResponse responseGetUploadUrl = client.execute(getUploadUrl);
			String uploadUrl = EntityUtils.toString(responseGetUploadUrl.getEntity());
			
			//On poste le media vers l'url obtenue
			HttpPost postMedia = new HttpPost(uploadUrl);
			
			FileBody fileBody  = new FileBody(new File(pathToFile));
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			
			builder.addPart("file", fileBody);
			builder.addPart("name", new StringBody(fileName));
			HttpEntity entity = builder.build();
			postMedia.setEntity(entity);
			HttpResponse res = client.execute(postMedia);
			
			handler.onUploadFinished(res);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}

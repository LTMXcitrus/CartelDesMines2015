package tools;

import org.apache.http.HttpResponse;

public interface MediaUploaderListener {
	
	public void onUploadFinished(int code, String entity);

}

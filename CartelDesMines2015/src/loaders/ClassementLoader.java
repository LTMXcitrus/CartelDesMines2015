package loaders;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tools.ClassementListener;
import android.util.Log;
import beans.Classement;

public class ClassementLoader extends Thread{
	
	ClassementListener handler;
	
	public ClassementLoader(ClassementListener handler){
		this.handler=handler;
	}

	@Override
	public void run(){
		try {
			//ArrayList to store the leaderboards
			ArrayList<Classement> classements = new ArrayList<Classement>();
			
			//Preparing the HttpRequest
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet("http://1-dot-inlaid-span-809.appspot.com/matchesliveclassement");
			HttpResponse r = client.execute(get);
			
			//Reading the httpResponse
			String json = EntityUtils.toString(r.getEntity());
			JSONObject objectJson = new JSONObject(json);
			JSONArray arrayClassement = objectJson.getJSONArray("Leaderboards");
			for(int i=0; i<arrayClassement.length(); i++){
				JSONObject object = arrayClassement.getJSONObject(i);
				classements.add(Classement.createClassementFromJson(object));
			}
			
			handler.onLoadFinished(classements);

		} catch (IOException | JSONException e) {
			Log.e("Cartel2015",e.getMessage());
		}
	}

}

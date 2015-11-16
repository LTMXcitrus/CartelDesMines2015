package loaders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
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
			HttpGet get = new HttpGet("http://cartel2015.com/fr/perso/webservices/getLeaderboard.php");

			HttpResponse r = client.execute(get);

			if(r.getStatusLine().getStatusCode()==200){

				//Reading the httpResponse
				String json = EntityUtils.toString(r.getEntity(), "UTF-8");
				JSONObject objectJson = new JSONObject(json);
				JSONObject entries = objectJson.getJSONObject("entries");
				Iterator<String> keys = entries.keys();
				while(keys.hasNext()){
					String key = keys.next();
					JSONObject entry = entries.getJSONObject(key);
					classements.add(Classement.createClassementFromJson(entry, key));
				}
			}

			handler.onLoadFinished(classements);

		} catch (IOException | JSONException e) {
			Log.e("Cartel2015",e.getMessage());
		}
	}

}

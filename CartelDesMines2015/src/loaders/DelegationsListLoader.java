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

import tools.DelegationsListLoaderCallback;

public class DelegationsListLoader extends Thread{
	
	DelegationsListLoaderCallback handler;
	
	public DelegationsListLoader(DelegationsListLoaderCallback handler) {
		this.handler=handler;
	}

	@Override
	public void run(){
		try {
			ArrayList<String> delegations =new ArrayList<String>();
			
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet("http://cartel2015.com/fr/perso/webservices/getDelegations.php");

			HttpResponse r = client.execute(get);
			
			String json = EntityUtils.toString(r.getEntity());
			JSONArray delegationsJSON = new JSONArray(json);
			
			for(int i=0; i<delegationsJSON.length(); i++){
				JSONObject delegationObject = delegationsJSON.getJSONObject(i); 
				delegations.add(delegationObject.getString("delegation"));
			}
			
			handler.onLoadFinished(delegations);
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}

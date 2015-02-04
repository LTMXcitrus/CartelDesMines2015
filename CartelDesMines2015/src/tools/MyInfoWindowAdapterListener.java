package tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import cartel.mines.nantes2015.R;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MyInfoWindowAdapterListener implements InfoWindowAdapter, OnInfoWindowClickListener{
	
	private Context context;
	
	public MyInfoWindowAdapterListener(Context context) {
		this.context=context;	
	}	
	
	
	//if both following methods return null, the default InfoWindow will be displayed
	@Override
	public View getInfoWindow(Marker arg0) {
		// TODO Auto-generated method stub
				return null;
	}
	
	@Override
	public View getInfoContents(Marker arg0) {
		LayoutInflater l = ((Activity) context).getLayoutInflater();
		View v = l.inflate(R.layout.info_window_layout, null);
		TextView title = (TextView) v.findViewById(R.id.infowindowname);
		TextView snippet = (TextView) v.findViewById(R.id.infowindowsnippet);
		title.setText(arg0.getTitle());
		snippet.setText(arg0.getSnippet());
		return v;
	}
	
	@Override
	public void onInfoWindowClick(Marker marker) {
		LatLng position = marker.getPosition();
		double latitude = position.latitude;
		double longitude = position.longitude;
		Uri gpsGo = Uri.parse("google.navigation:q="+latitude+","+longitude);
		Intent gpsIntent = new Intent(Intent.ACTION_VIEW,gpsGo);
		gpsIntent.setPackage("com.google.android.apps.maps");
		context.startActivity(gpsIntent);
	}

}

package adapters;

import java.util.ArrayList;
import java.util.List;

import cartel.mines.nantes2015.R;

import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MarkerSearchListAdapter extends ArrayAdapter<MarkerOptions> {
	
	private Context context;
	private int layoutResourceId;
	private ArrayList<MarkerOptions> data;
	

	
	public MarkerSearchListAdapter(Context context, int layoutResourceId, ArrayList<MarkerOptions> objects) {
		super(context, layoutResourceId, objects);
		this.context=context;
		this.layoutResourceId=layoutResourceId;
		this.data=objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		if(convertView==null){

			LayoutInflater inflater = ((Activity) context).getLayoutInflater();

			convertView = inflater.inflate(layoutResourceId, parent, false);
		}
		MarkerOptions marker = data.get(position);
		TextView title = (TextView) convertView.findViewById(R.id.marker_title);
		TextView snippet = (TextView) convertView.findViewById(R.id.marker_snippet);
		title.setText(marker.getTitle());
		snippet.setText(marker.getSnippet());
		return convertView;
	}
	
}

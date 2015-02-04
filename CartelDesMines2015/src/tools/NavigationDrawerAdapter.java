package tools;

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

public class NavigationDrawerAdapter extends ArrayAdapter<String>{
	
	private Context context;
	private int resource;
	private String[] data;
	

	public NavigationDrawerAdapter(Context context, int resource, String[] objects) {
		super(context, resource, objects);
		this.context=context;
		this.resource=resource;
		this.data=objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		if(convertView==null){

			LayoutInflater inflater = ((Activity) context).getLayoutInflater();

			convertView = inflater.inflate(resource, parent, false);
		}
		String title  = data[position];
		TextView textView = (TextView) convertView.findViewById(R.id.navigation_drawer_row_title);
		textView.setText(title);
		return convertView;
	}

	
}

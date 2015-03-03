package adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cartel.mines.nantes2015.R;
import beans.CalendarEvent;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CalendarDayListAdapter extends ArrayAdapter<CalendarEvent>{
	
	Context context;
	int resource;
	List<CalendarEvent> jour1;
	List<CalendarEvent> jour2;
	List<CalendarEvent> jour3;
	List<CalendarEvent> jour4;

	public CalendarDayListAdapter(Context context, int resource,
			List<CalendarEvent> objects) {
		super(context, resource, objects);
		this.context=context;
		this.resource=resource;
		this.jour1=objects;
	}
	
	public CalendarDayListAdapter(Context context, int resource,
			List<CalendarEvent> jour1, List<CalendarEvent> jour2, List<CalendarEvent> jour3, List<CalendarEvent> jour4) {
		this(context,resource,jour1);
		this.jour2=jour2;
		this.jour3=jour3;
		this.jour4=jour4;
	}
	
	@Override
	public int getCount(){
		return 4 + jour1.size() + jour2.size() + jour3.size() + jour4.size();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		if(convertView==null){

			LayoutInflater inflater = ((Activity) context).getLayoutInflater();

			convertView = inflater.inflate(resource, parent, false);
		}
		TextView section = (TextView) convertView.findViewById(R.id.CalendarDayListSection);
		TextView row = (TextView) convertView.findViewById(R.id.CalendarDayListSimpleRow);
		int header2 = jour1.size()+1;
		int header3 = jour2.size() + header2 + 1;
		int header4  = jour3.size() + header3 + 1;
		System.out.println(position);
		
		if(position == 0 || position == header2 || position == header3 || position == header4){
			row.setVisibility(View.GONE);
			
			String[] titles = context.getResources().getStringArray(R.array.dates);
			
			if(position == 0){
				section.setText(titles[0]);
			}
			if(position == header2){
				section.setText(titles[1]);
			}
			if(position == header3){
				section.setText(titles[2]);
			}
			if(position == header4){
				section.setText(titles[3]);
			}
			section.setTextColor(context.getResources().getColor(R.color.orange_cartel));
			convertView.setClickable(false);
			convertView.setOnClickListener(null);
			convertView.setOnLongClickListener(null);
		}
		else{
			section.setVisibility(View.GONE);
			if(position < header2){
				row.setText(jour1.get(position - 1).getEvent());
			}
			else if(position < header3){
				row.setText(jour2.get(position - header2 - 1).getEvent());
			}
			else if(position < header4){
				row.setText(jour3.get(position - header3 - 1).getEvent());
			}
			else if (position < header4 + jour4.size() + 1){
				row.setText(jour4.get(position - header4 - 1).getEvent());
			}
			
		}
		return convertView;
		
		
	}

}

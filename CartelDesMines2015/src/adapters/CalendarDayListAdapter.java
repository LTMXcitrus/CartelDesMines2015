package adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cartel.mines.nantes2015.R;
import beans.CalendarEvent;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
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
		
		int header2 = jour1.size()+1;
		int header3 = jour2.size() + header2 + 1;
		int header4  = jour3.size() + header3 + 1;

		if(position == 0 || position == header2 || position == header3 || position == header4){
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();

			convertView = inflater.inflate(resource, parent, false);
			TextView section = (TextView) convertView.findViewById(R.id.CalendarDayListSection);
			TextView row = (TextView) convertView.findViewById(R.id.CalendarDayListSimpleRow);
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
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();

			convertView = inflater.inflate(R.layout.event_list_item, parent, false);
			TextView horaire_hour = (TextView) convertView.findViewById(R.id.horaire_hour);
			TextView horaire_minute = (TextView) convertView.findViewById(R.id.horaire_minute);
			TextView event_title = (TextView) convertView.findViewById(R.id.event_title);
			TextView event_place = (TextView) convertView.findViewById(R.id.event_place);
			Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/StardusterCondensedModified.ttf");
			horaire_hour.setTypeface(type);
			horaire_minute.setTypeface(type);
			
			if(position < header2){
				CalendarEvent event= jour1.get(position - 1);
				
				horaire_hour.setText(event.getHourAsString());
				horaire_minute.setText(event.getMinuteAsString());

				event_title.setText(event.getEvent());
				event_place.setText(event.getPlace());
			}
			else if(position < header3){
				CalendarEvent event = jour2.get(position - header2 - 1);
				
				horaire_hour.setText(event.getHourAsString());
				horaire_minute.setText(event.getMinuteAsString());

				event_title.setText(event.getEvent());
				event_place.setText(event.getPlace());
			}
			else if(position < header4){
				CalendarEvent event = jour3.get(position - header3 - 1);
				
				horaire_hour.setText(event.getHourAsString());
				horaire_minute.setText(event.getMinuteAsString());

				event_title.setText(event.getEvent());
				event_place.setText(event.getPlace());
			}
			else if (position < header4 + jour4.size() + 1){
				CalendarEvent event = jour4.get(position - header4 - 1);
				
				horaire_hour.setText(event.getHourAsString());
				horaire_minute.setText(event.getMinuteAsString());

				event_title.setText(event.getEvent());
				event_place.setText(event.getPlace());
			}

		}
		return convertView;


	}

}

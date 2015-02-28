package adapters;

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

public class EventsListAdapter extends ArrayAdapter<CalendarEvent>{
	Context context;
	int layoutResourceId;
	List<CalendarEvent> objects;

	public EventsListAdapter(Context context, int layoutResourceId,	List<CalendarEvent> objects) {
		super(context, layoutResourceId, objects);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.objects = objects;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		if(convertView==null){

			LayoutInflater inflater = ((Activity) context).getLayoutInflater();

			convertView = inflater.inflate(layoutResourceId, parent, false);
		}
		CalendarEvent event = objects.get(position);
		TextView horaire_hour = (TextView) convertView.findViewById(R.id.horaire_hour);
		TextView horaire_minute = (TextView) convertView.findViewById(R.id.horaire_minute);
		TextView event_title = (TextView) convertView.findViewById(R.id.event_title);
		TextView event_place = (TextView) convertView.findViewById(R.id.event_place);

		Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/StardusterCondensedModified.ttf");
		horaire_hour.setTypeface(type);
		horaire_minute.setTypeface(type);

		horaire_hour.setText(event.getHourAsString());
		horaire_minute.setText(event.getMinuteAsString());

		event_title.setText(event.getEvent());
		event_place.setText(event.getPlace());


		return convertView;
	}

}

package adapters;

import java.util.ArrayList;
import java.util.HashMap;
import cartel.mines.nantes2015.R;
import beans.CalendarEvent;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandableAgendaListAdapter extends BaseExpandableListAdapter{

	Context context;
	String[] headers;
	HashMap<String, ArrayList<CalendarEvent>> objects;


	public ExpandableAgendaListAdapter(Context context, String[] headers, HashMap<String, ArrayList<CalendarEvent>> objects){
		this.context=context;
		this.headers=headers;
		this.objects=objects;
	}

	@Override
	public int getGroupCount() {
		return headers.length;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return objects.get(headers[groupPosition]).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return headers[groupPosition];
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return objects.get(headers[groupPosition]).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,	View convertView, ViewGroup parent) {

		LayoutInflater inflater = ((Activity) context).getLayoutInflater();

		convertView = inflater.inflate(R.layout.list_header, parent, false);
		TextView section = (TextView) convertView.findViewById(R.id.CalendarDayListSection);

		section.setText(headers[groupPosition]);
		section.setTextColor(context.getResources().getColor(R.color.orange_cartel));

		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,	boolean isLastChild, View convertView, ViewGroup parent) {
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();

		convertView = inflater.inflate(R.layout.event_list_item, parent, false);
		TextView horaire_hour = (TextView) convertView.findViewById(R.id.horaire_hour);
		TextView horaire_minute = (TextView) convertView.findViewById(R.id.horaire_minute);
		TextView event_title = (TextView) convertView.findViewById(R.id.event_title);
		TextView event_place = (TextView) convertView.findViewById(R.id.event_place);
		Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/StardusterCondensedModified.ttf");
		horaire_hour.setTypeface(type);
		horaire_minute.setTypeface(type);


		final CalendarEvent event= (CalendarEvent) getChild(groupPosition, childPosition);

		horaire_hour.setText(event.getHourAsString());
		horaire_minute.setText(event.getMinuteAsString());

		event_title.setText(event.getEvent());
		event_place.setText(event.getPlace());

		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}

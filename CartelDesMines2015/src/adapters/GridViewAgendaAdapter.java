package adapters;

import java.util.ArrayList;
import beans.CalendarEvent;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import cartel.mines.nantes2015.R;

public class GridViewAgendaAdapter extends ArrayAdapter<String>{


	private Context context;
	private int resource;
	private String[] objects;
	private ArrayList<ArrayList<CalendarEvent>> events;
	private boolean isPlanningLoaded = false;

	public GridViewAgendaAdapter(Context context, int resource, String[] objects) {
		super(context, resource);
		this.context=context;
		this.resource=resource;
		this.objects=objects;
	}

	public GridViewAgendaAdapter(Context context, int resource, String[] objects, ArrayList<ArrayList<CalendarEvent>> events) {

		this(context,resource, objects);

		this.context=context;
		this.resource=resource;
		this.objects=objects;
		this.events=events;
		this.isPlanningLoaded=true;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		System.out.println("getView");
		if(convertView == null){
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			convertView = inflater.inflate(resource, parent, false);
		}
		
		TextView dateTV  = (TextView) convertView.findViewById(R.id.gridview_agenda_date);
		dateTV.setText(getTitles()[position]);
		convertView.setBackgroundColor(getColors()[position]);
		
		if(isPlanningLoaded){
			ArrayList<CalendarEvent> eventsOfTheDay = events.get(position);
			TextView details = (TextView) convertView.findViewById(R.id.gridview_cell_details);
			details.setTextColor(getColors()[position]);
			String text = "";
			for(CalendarEvent event : eventsOfTheDay){
				text += event.getEvent()+"\n";
			}
			details.setText(text);
		}
		return convertView;
	}

	@Override
	public int getCount(){
		return 4;
	}

	public int[] getColors(){
		Resources r = context.getResources();
		int[] colors = new int[getCount()];

		colors[0] = r.getColor(R.color.bleu_cartel);
		colors[1] = r.getColor(R.color.rouge_cartel);
		colors[2] = r.getColor(R.color.vert_cartel);
		colors[3] = r.getColor(R.color.jaune_cartel);

		return colors;
	}

	public String[] getTitles(){
		String[] titles = new String[getCount()];
		titles[0] = "Samedi";
		titles[1] = "Dimanche";
		titles[2] = "Lundi";
		titles[3] = "Mardi";
		return titles;
	}

}

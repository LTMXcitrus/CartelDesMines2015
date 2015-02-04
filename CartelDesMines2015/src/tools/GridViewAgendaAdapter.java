package tools;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import cartel.mines.nantes2015.R;

public class GridViewAgendaAdapter extends ArrayAdapter<GregorianCalendar>{


	private Context context;
	private int resource;
	private ArrayList<GregorianCalendar> objects;

	public GridViewAgendaAdapter(Context context, int resource, ArrayList<GregorianCalendar> objects) {
		super(context, resource);
		this.context=context;
		this.resource=resource;
		this.objects=objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		System.out.println("getView");
		if(convertView == null){
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			convertView = inflater.inflate(resource, parent, false);
		}
		GregorianCalendar date = objects.get(position);
		TextView dateTV  = (TextView) convertView.findViewById(R.id.gridview_agenda_date);
		dateTV.setText("bonjour");
		convertView.setBackgroundColor(getColors()[position]);
		
		TextView details = (TextView) convertView.findViewById(R.id.gridview_cell_details);
		details.setText(getTitles()[position]);
		details.setTextColor(getColors()[position]);
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
		titles[0] = "premier jour";
		titles[1] = "second jour";
		titles[2] = "troisième jour";
		titles[3] = "quatrième jour";
		return titles;
	}

}

package adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import beans.Classement;
import cartel.mines.nantes2015.R;

public class ClassementAdapter extends ArrayAdapter<Classement>{
	
	private Context context;
	private int resource;
	private List<Classement> objects;

	public ClassementAdapter(Context context, int resource,	List<Classement> objects) {
		super(context, resource, objects);
		
		this.context = context;
		this.resource = resource;
		this.objects = objects;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		

			LayoutInflater inflater = ((Activity) context).getLayoutInflater();

			convertView = inflater.inflate(resource, parent, false);
		
		TextView classement = (TextView) convertView.findViewById(R.id.classement);
		TextView ville = (TextView) convertView.findViewById(R.id.ville);
		TextView points = (TextView) convertView.findViewById(R.id.points);
		
		Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/StardusterCondensedModified.ttf");
		classement.setTypeface(type);
		points.setTypeface(type);
		
		classement.setText(Integer.toString(position+1));
		
		String team = objects.get(position).getTeam();
		ville.setText(team);
		if(team.length()>10){
			ville.setTextSize(19);
		}
		
		points.setText(Integer.toString(objects.get(position).getPoints()) + " " + "PTS");
		
		if(position==0){
			classement.setTextColor(context.getResources().getColor(R.color.jaune_cartel));
		}
		if(position==1){
			classement.setTextColor(context.getResources().getColor(R.color.gris_deuxieme));
		}
		if(position==2){
			classement.setTextColor(context.getResources().getColor(R.color.bronze_troisieme));
		}
		

		return convertView;
	}

}

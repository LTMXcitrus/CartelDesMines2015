package adapters;

import java.text.ParseException;
import java.util.List;

import cartel.mines.nantes2015.R;
import beans.Participant;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ParticipantListeAdapter extends ArrayAdapter<Participant>{
	
	Context context;
	int resource;
	List<Participant> objects;

	public ParticipantListeAdapter(Context context, int resource, List<Participant> objects) {
		super(context, resource, objects);
		this.context = context;
		this.resource = resource;
		this.objects = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		if(convertView == null){
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();

			convertView = inflater.inflate(resource, parent, false);
		}
		
		Participant participant = objects.get(position);
		
		TextView participant_classement = (TextView) convertView.findViewById(R.id.participant_classement);
		Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/StardusterCondensedModified.ttf");
		participant_classement.setTypeface(type);
		
		TextView participant_ecole = (TextView) convertView.findViewById(R.id.participant_ecole);
		
		TextView participant_prenom_nom  = (TextView) convertView.findViewById(R.id.participant_prenom_nom);
		participant_prenom_nom.setTextColor(context.getResources().getColor(R.color.vert_cartel));
		TextView participant_temps = (TextView) convertView.findViewById(R.id.participant_temps);
		
		participant_ecole.setText(participant.getEcole());
		participant_prenom_nom.setText(participant.getPrenom() + " " + participant.getNom());
		try {
			participant_temps.setText(participant.getMinutesOfTime()+"min "+participant.getSecondesOfTime()+"s "+participant.getMillisOfTime());
		} catch (ParseException e) {
			System.out.println(e);
		}
		if(position == 0){
			participant_classement.setText("1");
			participant_classement.setTextColor(context.getResources().getColor(R.color.jaune_premier));
		}
		else if(position == 1){
			participant_classement.setText("2");
			participant_classement.setTextColor(context.getResources().getColor(R.color.gris_deuxieme));
		}
		else if(position == 2){
			participant_classement.setText("3");
			participant_classement.setTextColor(context.getResources().getColor(R.color.bronze_troisieme));
		}
		else{
			participant_classement.setText(Integer.toString(position+1));
			participant_classement.setTextColor(context.getResources().getColor(R.color.vert_cartel));
		}
		
		return convertView;		 
	}

}

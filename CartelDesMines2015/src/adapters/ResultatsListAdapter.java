package adapters;

import java.util.List;

import cartel.mines.nantes2015.CourseActivity;
import cartel.mines.nantes2015.R;
import beans.Course;
import beans.Match;
import beans.Resultat;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ResultatsListAdapter extends ArrayAdapter<Resultat>{

	private static final int resourceIdCourse = R.layout.course_item_row;

	Context context;
	int resource;
	List<Resultat> objects;

	public ResultatsListAdapter(Context context, int resource, List<Resultat> objects) {
		super(context, resource, objects);
		this.context=context;
		this.resource=resource;
		this.objects=objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		Resultat resultat = objects.get(position);
		Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/StardusterCondensedModified.ttf");
		if(resultat instanceof Match){

			LayoutInflater inflater = ((Activity) context).getLayoutInflater();

			convertView = inflater.inflate(resource, parent, false);



			Match match = (Match) resultat;

			TextView player1 = (TextView) convertView.findViewById(R.id.player1);
			TextView player2 = (TextView) convertView.findViewById(R.id.player2);
			TextView scores = (TextView) convertView.findViewById(R.id.scores);
			TextView sport = (TextView) convertView.findViewById(R.id.sport);
			TextView matchType = (TextView) convertView.findViewById(R.id.match_type);

			player1.setText(match.getPlayer1());
			player2.setText(match.getPlayer2());
			scores.setText(match.getScorePlayer1() + "-" + match.getScorePlayer2());
			
			String sportOfMatch = match.getSport().replace("%20", " ");
			sport.setText(sportOfMatch);
			matchType.setText(match.getMatchType());

			
			scores.setTypeface(type);

			convertView.setClickable(false);
			convertView.setOnClickListener(null);
			convertView.setOnLongClickListener(null);
		}
		else{
			final Course course = (Course) resultat;

			LayoutInflater inflater = ((Activity) context).getLayoutInflater();

			convertView = inflater.inflate(resourceIdCourse, parent, false);

			TextView sport = (TextView) convertView.findViewById(R.id.course_sport);
			sport.setText(course.getSport());
			//Number
			TextView course_premier = (TextView) convertView.findViewById(R.id.course_premier);
			course_premier.setTypeface(type);
			//Team
			TextView vainqueur = (TextView) convertView.findViewById(R.id.vainqueur);
			vainqueur.setText(course.getVainqueur().getEcole());
			
			//Number
			TextView course_deuxieme = (TextView) convertView.findViewById(R.id.course_deuxieme);
			course_deuxieme.setTypeface(type);
			//Team
			TextView deuxieme = (TextView) convertView.findViewById(R.id.deuxieme);
			deuxieme.setText(course.getDeuxieme().getEcole());
			
			//Number
			TextView course_troisieme = (TextView) convertView.findViewById(R.id.course_troisieme);
			course_troisieme.setTypeface(type);
			//Team
			TextView troisieme = (TextView) convertView.findViewById(R.id.troisieme);
			troisieme.setText(course.getTroisieme().getEcole());
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context,CourseActivity.class);
					intent.putExtra("course", course);
					context.startActivity(intent);
				}
			});
		}

		return convertView;
	}

}

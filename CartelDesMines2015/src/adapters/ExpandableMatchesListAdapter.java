package adapters;

import java.util.ArrayList;
import java.util.HashMap;

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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandableMatchesListAdapter extends BaseExpandableListAdapter{

	Context context;
	HashMap<String, ArrayList<Resultat>> objects;
	String[] headers = {"LUNDI 13","DIMANCHE 12","SAMEDI 11"  };

	public ExpandableMatchesListAdapter(Context context, ArrayList<Resultat> resultatsJour1, ArrayList<Resultat> resultatsJour2,
			 ArrayList<Resultat> resultatsJour3) {
		this.context=context;
		HashMap<String, ArrayList<Resultat>> objects = new HashMap<String, ArrayList<Resultat>>();
		objects.put(headers[0], resultatsJour3);
		objects.put(headers[1], resultatsJour2);
		objects.put(headers[2], resultatsJour1);
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
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();

		convertView = inflater.inflate(R.layout.list_header, parent, false);
		TextView section = (TextView) convertView.findViewById(R.id.CalendarDayListSection);

		if(objects.get(headers[groupPosition]).isEmpty()){
			section.setText(headers[groupPosition] + " - Vide");
		}
		else{
			section.setText(headers[groupPosition]);
		}
		section.setTextColor(context.getResources().getColor(R.color.orange_cartel));

		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		Resultat resultat = (Resultat) getChild(groupPosition, childPosition);
		Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/StardusterCondensedModified.ttf");
		if(resultat instanceof Match){

			LayoutInflater inflater = ((Activity) context).getLayoutInflater();

			convertView = inflater.inflate(R.layout.matches_list_item, parent, false);



			Match match = (Match) resultat;

			TextView player1 = (TextView) convertView.findViewById(R.id.player1);
			TextView player2 = (TextView) convertView.findViewById(R.id.player2);
			TextView scores = (TextView) convertView.findViewById(R.id.scores);
			TextView sport = (TextView) convertView.findViewById(R.id.sport);
			TextView matchType = (TextView) convertView.findViewById(R.id.match_type);

			String player1Text = match.getPlayer1();
			String player2Text = match.getPlayer2();
			player1.setText(player1Text);
			player2.setText(player2Text);
			if(player1Text.length()>8){
				player1.setTextSize(13);
			}
			if(player2Text.length()>8){
				player2.setTextSize(13);
			}
			if(match.getScorePlayer1()!=-1){
				scores.setText(match.getScorePlayer1() + "-" + match.getScorePlayer2());
			}
			else{
				scores.setText("EN ATTENTE");
				scores.setTextSize(25);
			}
			
			
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

			convertView = inflater.inflate(R.layout.course_item_row, parent, false);

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

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}

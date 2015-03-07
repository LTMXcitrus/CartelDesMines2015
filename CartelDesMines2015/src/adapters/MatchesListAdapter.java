package adapters;

import java.util.List;

import cartel.mines.nantes2015.R;
import beans.Match;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MatchesListAdapter extends ArrayAdapter<Match>{
	
	Context context;
	int resource;
	List<Match> objects;

	public MatchesListAdapter(Context context, int resource, List<Match> objects) {
		super(context, resource, objects);
		this.context=context;
		this.resource=resource;
		this.objects=objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		if(convertView==null){

			LayoutInflater inflater = ((Activity) context).getLayoutInflater();

			convertView = inflater.inflate(resource, parent, false);
		}
		Match match = objects.get(position);
		
		TextView player1 = (TextView) convertView.findViewById(R.id.player1);
		TextView player2 = (TextView) convertView.findViewById(R.id.player2);
		TextView scores = (TextView) convertView.findViewById(R.id.scores);
		TextView sport = (TextView) convertView.findViewById(R.id.sport);
		TextView matchType = (TextView) convertView.findViewById(R.id.match_type);
		
		player1.setText(match.getPlayer1());
		player2.setText(match.getPlayer2());
		scores.setText(match.getScorePlayer1() + "-" + match.getScorePlayer2());
		sport.setText(match.getSport());
		matchType.setText(match.getMatchType());
		
		Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/StardusterCondensedModified.ttf");
		scores.setTypeface(type);
		
		convertView.setClickable(false);
		convertView.setOnClickListener(null);
		convertView.setOnLongClickListener(null);
		

		return convertView;
	}

}

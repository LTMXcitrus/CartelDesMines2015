package cartel.mines.nantes2015;

import java.util.ArrayList;

import adapters.MatchesListAdapter;
import android.app.ListActivity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import beans.Match;

public class MatchesParSportsActivity extends ListActivity{
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.matches_par_sports_activity);
		
		getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.vert_cartel)));
		
		ArrayList<Match> matchesOfSport = (ArrayList<Match>) getIntent().getSerializableExtra("matches");
		getListView().setAdapter(new MatchesListAdapter(this, R.layout.matches_list_item, matchesOfSport));
	}

}

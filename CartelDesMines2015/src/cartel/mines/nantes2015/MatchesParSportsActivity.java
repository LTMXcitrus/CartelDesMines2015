package cartel.mines.nantes2015;

import java.util.ArrayList;

import adapters.ResultatsListAdapter;
import android.app.ListActivity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import beans.Match;
import beans.Resultat;

public class MatchesParSportsActivity extends ListActivity{
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.matches_par_sports_activity);
		
		getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.vert_cartel)));
		
		ArrayList<Resultat> matchesOfSport = (ArrayList<Resultat>) getIntent().getSerializableExtra("matches");
		getListView().setAdapter(new ResultatsListAdapter(this, R.layout.matches_list_item, matchesOfSport));
	}

}

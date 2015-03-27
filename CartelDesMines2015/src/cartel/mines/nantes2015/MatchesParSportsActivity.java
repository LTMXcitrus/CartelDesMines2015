package cartel.mines.nantes2015;

import java.util.ArrayList;

import adapters.ExpandableMatchesListAdapter;
import adapters.ResultatsListAdapter;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ExpandableListView;
import beans.Match;
import beans.Resultat;

public class MatchesParSportsActivity extends Activity{
	
	ExpandableListView list;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_resultats);
		
		list = (ExpandableListView) findViewById(R.id.matches_list);
		
		getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.vert_cartel)));
		
		Intent intent = getIntent();
		
		ArrayList<Resultat> resultatsJour1 = (ArrayList<Resultat>) intent.getSerializableExtra("matchesJour1");
		ArrayList<Resultat> resultatsJour2 = (ArrayList<Resultat>) intent.getSerializableExtra("matchesJour2");
		ArrayList<Resultat> resultatsJour3 = (ArrayList<Resultat>) intent.getSerializableExtra("matchesJour3");
		ExpandableMatchesListAdapter adapter = new ExpandableMatchesListAdapter(this, resultatsJour1, resultatsJour2, resultatsJour3);
		list.setAdapter(adapter);
		list.expandGroup(adapter.getGroupCount()-1);
	}

}

package cartel.mines.nantes2015;

import java.util.Locale;

import beans.Classement;
import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

public class ClassementDetailActivity extends Activity{
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_classement);
		
		getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.vert_cartel)));
		
		Classement classement = (Classement) getIntent().getSerializableExtra("classement");
		int rank = getIntent().getIntExtra("rank", 1);
		
		TextView ville = (TextView) findViewById(R.id.detailMatchClassement);
		TextView points = (TextView) findViewById(R.id.detailClassementPoints);
		TextView victoireNombre = (TextView) findViewById(R.id.detailMatchVictoireNombre);
		TextView nulsNombre = (TextView) findViewById(R.id.detailMatchNulsNombre);
		TextView defaitesNombre = (TextView) findViewById(R.id.detailMatchDefaitesNombre);
		
		Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/StardusterCondensedModified.ttf");
		ville.setText(rank + " - " + classement.getTeam().toUpperCase(Locale.FRANCE));
		ville.setTypeface(typeface);
		
		points.setText(classement.getPoints() + "  PTS");
		
		victoireNombre.setText(Integer.toString(classement.getWins()));
		victoireNombre.setTypeface(typeface);
		nulsNombre.setText(Integer.toString(classement.getDraws()));
		nulsNombre.setTypeface(typeface);
		defaitesNombre.setText(Integer.toString(classement.getLosses()));
		defaitesNombre.setTypeface(typeface);
	}

}

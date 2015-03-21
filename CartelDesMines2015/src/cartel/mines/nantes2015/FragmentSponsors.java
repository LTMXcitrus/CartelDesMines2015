package cartel.mines.nantes2015;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FragmentSponsors extends Fragment implements OnClickListener{
	
	private static final int SPIE = R.id.spie;
	private static final int CREDIT_MUTUEL = R.id.credit_mutuel;
	private static final int ALTEN = R.id.alten;
	private static final int MINES_ALUMNI = R.id.mines_nantes_alumni;
	private static final int SOCOTEC = R.id.socotec;
	private static final int SOPRA = R.id.sopra;
	private static final int MINES_CARTEL = R.id.cartel_et_emn;

	LinearLayout overlay;
	TextView overlayText;
	ImageView imageDescription;
	

	public static FragmentSponsors newInstance(){
		return new FragmentSponsors();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);

		View convertView = inflater.inflate(R.layout.sponsors, container, false);

		overlay = (LinearLayout) convertView.findViewById(R.id.overlay);
		RelativeLayout overlayChild = (RelativeLayout) convertView.findViewById(R.id.overlaychild);
		OnClickListener disappear = new OnClickListener() {

			@Override
			public void onClick(View v) {
				overlay.setVisibility(View.GONE);
			}
		};
		overlayChild.setOnClickListener(disappear);
		overlay.setOnClickListener(disappear);
		overlayText  = (TextView) convertView.findViewById(R.id.overlay_textView);
		
		imageDescription = (ImageView) convertView.findViewById(R.id.overlay_image);
		
		
		convertView.findViewById(SPIE).setOnClickListener(this);
		convertView.findViewById(ALTEN).setOnClickListener(this);
		convertView.findViewById(MINES_ALUMNI).setOnClickListener(this);
		convertView.findViewById(SOCOTEC).setOnClickListener(this);
		convertView.findViewById(CREDIT_MUTUEL).setOnClickListener(this);
		convertView.findViewById(SOPRA).setOnClickListener(this);
		convertView.findViewById(MINES_CARTEL).setOnClickListener(this);

		return convertView;
	}

	@Override
	public void onClick(View v) {
		imageDescription.setImageDrawable(((ImageView)v).getDrawable());
		System.out.println(v.getId());
		System.out.println("click!");

		overlay.setVisibility(View.VISIBLE);
		overlayText.setText("bonjour !");
		switch(v.getId()){
		case SPIE:
			overlayText.setText(getResources().getString(R.string.spie));
			break;
		case ALTEN:
			overlayText.setText("Alten");
			break;
		case MINES_ALUMNI:
			overlayText.setText("Mines Nantes Alumni");
			break;
		case SOCOTEC:
			overlayText.setText("Socotec");
			break;
		case CREDIT_MUTUEL:
			overlayText.setText(getResources().getString(R.string.credit_mutuel));
			break;
		case SOPRA:
			overlayText.setText("Sopra");
			break;
		case MINES_CARTEL:
			overlayText.setText("Mines Nantes et Cartel");
		}

		

	}

}

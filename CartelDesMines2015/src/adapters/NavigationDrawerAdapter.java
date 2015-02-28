package adapters;

import com.google.android.gms.drive.internal.t;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import cartel.mines.nantes2015.R;

public class NavigationDrawerAdapter extends ArrayAdapter<String>{

	private static final int CARTEL2015=0;
	private static final int VITRINE=1;
	private static final int CARTE=2;
	private static final int PLANNING=3;
	private static final int RESULTATS=4;
	private static final int MATCHS=5;
	private static final int PAR_SPORT=6;
	private static final int CLASSEMENT=7;
	private static final int NEWS=8;
	private static final int ACTUALITES=9;
	private static final int PICTURES=10;
	private static final int MEDIASHARE=11;

	private Context context;
	private int resource;
	private String[] data;
	private int selectedRow=VITRINE;


	public NavigationDrawerAdapter(Context context, int resource, String[] objects) {
		super(context, resource, objects);
		this.context=context;
		this.resource=resource;
		this.data=objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();


		String title  = data[position];

		convertView = inflater.inflate(resource, parent, false);
		TextView textView = (TextView) convertView.findViewById(R.id.navigation_drawer_row_title);
		textView.setText(title);
		TextView section = (TextView) convertView.findViewById(R.id.navigation_drawer_row_section);
		section.setText(title);

		switch(position){
		case CARTEL2015:
			setNonClickable(convertView);
			setTextViewSet(section, textView);
			section.setTextColor(context.getResources().getColor(R.color.rouge_cartel));
			break;
		case RESULTATS:
			setNonClickable(convertView);
			setTextViewSet(section, textView);
			section.setTextColor(context.getResources().getColor(R.color.vert_cartel));
			break;
		case NEWS:
			setNonClickable(convertView);
			setTextViewSet(section, textView);
			section.setTextColor(context.getResources().getColor(R.color.bleu_cartel));
			break;
		default:
			textView.setGravity(Gravity.CENTER_HORIZONTAL);
			textView.setTextSize(18);
			textView.setTextColor(Color.GRAY);
		}
		if(position==selectedRow){
			switch(selectedRow){
			case VITRINE:
			case CARTE:
			case PLANNING:
				textView.setTextColor(context.getResources().getColor(R.color.rouge_cartel));
				break;
			case MATCHS:
			case PAR_SPORT:
			case CLASSEMENT:
				textView.setTextColor(context.getResources().getColor(R.color.vert_cartel));
				break;
			case ACTUALITES:
			case PICTURES:
			case MEDIASHARE:
				textView.setTextColor(context.getResources().getColor(R.color.bleu_cartel));
				break;
			}
		}

		return convertView;
	}

	public void setNonClickable(View view){
		view.setClickable(false);
		view.setOnClickListener(null);
		view.setOnLongClickListener(null);
		LayoutParams lp = view.getLayoutParams();
		lp.width=LayoutParams.MATCH_PARENT;
		lp.height=LayoutParams.WRAP_CONTENT;
		view.requestLayout();
	}

	public void setTextViewSet(TextView section, TextView textView){
		Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/StardusterCondensedModified.ttf");
		section.setTypeface(typeface);
		section.setTextSize(25);
		section.setVisibility(View.VISIBLE);
		textView.setVisibility(View.GONE);
	}

	public int getSelectedRow(){
		return selectedRow;
	}
	public void setSelectedRow(int selectedRow){
		this.selectedRow=selectedRow;
	}


}

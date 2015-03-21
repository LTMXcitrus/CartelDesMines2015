package adapters;

import java.util.ArrayList;

import cartel.mines.nantes2015.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SpinnerDelegationChoiceAdapter extends BaseAdapter{

	Context context;
	ArrayList<String> delegations;


	public SpinnerDelegationChoiceAdapter(Context context, ArrayList<String> delegations) {
		this.context=context;
		this.delegations=delegations;
	}

	@Override
	public int getCount() {
		return delegations.size();
	}

	@Override
	public Object getItem(int position) {
		return delegations.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
			convertView = inflater.inflate(R.layout.spinner_item_custom, parent, false);
		}
		TextView spinnerOptionText = (TextView) convertView.findViewById(R.id.spinner_option_text);
		spinnerOptionText.setText(delegations.get(position));
		return convertView;
	}

}

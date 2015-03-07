package adapters;

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
	String[] objects;


	public SpinnerDelegationChoiceAdapter(Context context) {
		this.context=context;
		this.objects = context.getResources().getStringArray(R.array.delegations);
	}

	@Override
	public int getCount() {
		return objects.length;
	}

	@Override
	public Object getItem(int position) {
		return objects[position];
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
		spinnerOptionText.setText(objects[position]);
		return convertView;
	}

}

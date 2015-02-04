package cartel.mines.nantes2015;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import tools.GridViewAgendaAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class AgendaFragment extends Fragment{
	
	

	public static AgendaFragment newInstance(){
		return new AgendaFragment();
		
	}

	public AgendaFragment(){ }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		View rootView=inflater.inflate(R.layout.agenda_fragment, container, false);
		GridView grid = (GridView) rootView.findViewById(R.id.agenda_gridview);
		grid.setAdapter(new GridViewAgendaAdapter(getActivity(), R.layout.cell_gridview_agenda, buildCalendar()));
		grid.setNumColumns(2);
		grid.setHorizontalSpacing(5);
		grid.setVerticalSpacing(5);
		return rootView;
	}
	
	public ArrayList<GregorianCalendar> buildCalendar(){
		ArrayList<GregorianCalendar> objects = new ArrayList<GregorianCalendar>();
		objects.add(new GregorianCalendar(2015, 4, 11));
		objects.add(new GregorianCalendar(2015,4,12));
		objects.add(new GregorianCalendar(2015, 4, 13));
		objects.add(new GregorianCalendar(2015,4,14));
		return objects;
	}

}

package cartel.mines.nantes2015;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentSponsors extends Fragment{
	
	public static FragmentSponsors newInstance(){
		return new FragmentSponsors();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		
		return inflater.inflate(R.layout.sponsors, container, false);
	}

}

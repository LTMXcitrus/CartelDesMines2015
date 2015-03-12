package cartel.mines.nantes2015;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentNantes extends Fragment {
	
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static FragmentNantes newInstance() {
		return new FragmentNantes();
	}

	public FragmentNantes() {	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView=inflater.inflate(R.layout.nantes_fragment_1, container,
				false);
		return rootView;
	}
}
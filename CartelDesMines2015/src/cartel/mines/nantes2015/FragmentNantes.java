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
	//private int sectionNumber;
	private int[] layouts={R.layout.nantes_fragment_1,R.layout.nantes_fragment_2,R.layout.nantes_fragment_3,R.layout.nantes_fragment_4};
	
	/**
	 * to access the sectionNumber
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static FragmentNantes newInstance(int sectionNumber) {
		FragmentNantes fragment = new FragmentNantes();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber-1);
		fragment.setArguments(args);
		return fragment;
	}

	public FragmentNantes() {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
		View rootView=inflater.inflate(layouts[sectionNumber], container,
				false);
		rootView.findViewById(R.id.linearlayout);
		return rootView;
	}
}
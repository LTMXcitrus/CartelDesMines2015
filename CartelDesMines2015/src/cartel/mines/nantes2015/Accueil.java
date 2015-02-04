package cartel.mines.nantes2015;

import com.google.android.gms.identity.intents.AddressConstants.Extras;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class Accueil extends ActionBarActivity implements
									NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	FrameLayout container;

	ViewPager mViewPager;
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accueil);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		container =(FrameLayout) findViewById(R.id.container);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));




	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		if(mViewPager!=null){
			mViewPager.setVisibility(View.GONE);
		}
		if(container!=null){
			container.setVisibility(View.VISIBLE);
		}

		// update the main content by replacing fragments
		if(position==0){
			if(container!=null){
				container.setVisibility(View.GONE);
			}
			if(mViewPager!=null){
				mViewPager.setVisibility(View.VISIBLE);	
			}
		}
		if(position==1){
			startActivity(new Intent(Accueil.this,Carte.class));
		}
		if(position==2){
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager
			.beginTransaction()
			.replace(R.id.container,
					AgendaFragment.newInstance()).commit();
		}
		if(position==3){
			Intent versNotifications = new Intent(this,NotificationDisplay.class);
			Bundle extras = new Bundle();
			extras.putString("title", "vous arrivez de l'accueil");
			extras.putString("msg", "Si vous n'êtes pas enregistré, faites-le");
			versNotifications.putExtras(extras);
			startActivity(versNotifications);	
		}
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.cartel2015);
			break;
		case 2:
			mTitle = getString(R.string.carte);
			break;
		case 3:
			mTitle = getString(R.string.planning);
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceHolderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceHolderFragment newInstance() {
			PlaceHolderFragment fragment = new PlaceHolderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, 1);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceHolderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((Accueil) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}
	}


	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {

			return FragmentNantes.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return getResources().getStringArray(R.array.titles)[position];
		}
	}


}

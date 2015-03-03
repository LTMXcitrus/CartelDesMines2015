package cartel.mines.nantes2015;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
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

public class Accueil extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks, OnBackStackChangedListener{


	public static final int CARTEL2015=0;
	public static final int VITRINE=1;
	public static final int CARTE=2;
	public static final int PLANNING=3;
	public static final int RESULTATS=4;
	public static final int MATCHS=5;
	public static final int PAR_SPORT=6;
	public static final int CLASSEMENT=7;
	public static final int NEWS=8;
	public static final int ACTUALITES=9;
	public static final int PICTURES=10;
	public static final int MEDIASHARE=11;
	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	FrameLayout container;

	ViewPager viewPagerVitrine;
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;



	ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accueil);
		if(!isRegistered()){
			startActivity(new Intent(this,RegistrationActivity.class));
		}
		FragmentManager fragmentManager = getSupportFragmentManager();
		//Listen for changes in the back stack
		fragmentManager.addOnBackStackChangedListener(this);
		//Handle when activity is recreated like on orientation Change
		shouldDisplayHomeUp();

		actionBar = getSupportActionBar();
		setActionBarColorFromId(R.color.bleu_cartel);



		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(fragmentManager);

		// Set up the ViewPager vitrine with the sections adapter.
		viewPagerVitrine = (ViewPager) findViewById(R.id.pager_vitrine);
		viewPagerVitrine.setAdapter(mSectionsPagerAdapter);
		container =(FrameLayout) findViewById(R.id.container);

		mNavigationDrawerFragment = (NavigationDrawerFragment) fragmentManager.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));


		//Set up the ViewPager for Results
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		if(viewPagerVitrine!=null){
			viewPagerVitrine.setVisibility(View.GONE);
		}
		if(container!=null){
			container.setVisibility(View.VISIBLE);
		}
		if(actionBar!=null){
			setMyActionBarTitle(position);
		}

		// update the main content by replacing fragments
		

		if(position== VITRINE){
			if(container!=null){
				container.setVisibility(View.GONE);
			}
			if(viewPagerVitrine!=null){
				viewPagerVitrine.setVisibility(View.VISIBLE);	
			}
		}


		if(position== CARTE){
			setActionBarColorFromId(R.color.bleu_cartel);
			startActivity(new Intent(Accueil.this,Carte.class));
		}
		if(position == PLANNING){
			setActionBarColorFromId(R.color.orange_cartel);
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager
			.beginTransaction()
			.replace(R.id.container,
					AgendaFragment.newInstance()).commit();
		}

		if(position == MATCHS){
			setActionBarColorFromId(R.color.vert_cartel);
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
			.replace(R.id.container, FragmentMatchesResultats.newInstance()).commit();
		}

		if(position == PAR_SPORT){
			setActionBarColorFromId(R.color.vert_cartel);
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
			.replace(R.id.container, FragmentsSports.newInstance()).commit();
		}
		if(position == CLASSEMENT){
			setActionBarColorFromId(R.color.vert_cartel);
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
			.replace(R.id.container, FragmentClassements.newInstance()).commit();
		}
		if(position == ACTUALITES){
			setActionBarColorFromId(R.color.bleu_cartel);
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
			.replace(R.id.container, FragmentArticles.newInstance()).commit();
		}
		if(position == MEDIASHARE){
			startActivity(new Intent(Accueil.this,PicturesUploader.class));
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
		item.getItemId();
		
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
			return getResources().getStringArray(R.array.titles_vitrine)[position];
		}
	}

	/**
	 * 
	 * @return true if the device is registered to the backend, false otherwise.
	 */
	public boolean isRegistered(){
		final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		return prefs.getBoolean("registered", false);
	}


	@Override
	public void onBackStackChanged() {
		shouldDisplayHomeUp();
	}

	public void shouldDisplayHomeUp(){
		//Enable Up button only  if there are entries in the back stack
		boolean canback = getSupportFragmentManager().getBackStackEntryCount()>0;
		getSupportActionBar().setDisplayHomeAsUpEnabled(canback);
	}

	@Override
	public boolean onSupportNavigateUp() {
		//This method is called when the up button is pressed. Just the pop back stack.
		getSupportFragmentManager().popBackStack();
		return true;
	}

	public void setActionBarColorFromId(int id){
		actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(id)));
	}
	
	public void setMyActionBarTitle(int position){
		actionBar.setTitle(getResources().getStringArray(R.array.action_bar_titles)[position]);
	}




}

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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class Accueil extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks, OnBackStackChangedListener{


	private static final int CARTEL2015=0;
	private static final int VITRINE=1;
	private static final int CARTE=2;
	private static final int PLANNING=3;
	private static final int RESULTATS=4;
	private static final int MATCHS=5;
	private static final int PAR_SPORT=6;
	private static final int PAR_DELEGATION=7;
	private static final int CLASSEMENT=8;
	private static final int NEWS=9;
	private static final int ACTUALITES=10;
	private static final int PICTURES=11;
	private static final int MEDIASHARE=12;
	private static final int AUTRES=13;
	private static final int SPONSORS=14;
	private static final int REGLAGES=15;
	private static final int A_PROPOS=16;
	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	public DrawerLayout mDrawerLayout;

	FrameLayout container;

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

		container =(FrameLayout) findViewById(R.id.container);

		mNavigationDrawerFragment = (NavigationDrawerFragment) fragmentManager.findFragmentById(R.id.navigation_drawer);



		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
		mDrawerLayout = mNavigationDrawerFragment.getDrawerLayout();
		
		if(getIntent().hasExtra("carte")){
			onNavigationDrawerItemSelected(1);
		}

		//Set up the ViewPager for Results
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		if(actionBar!=null){
			setMyActionBarTitle(position);
		}

		// update the main content by replacing fragments

		if(position== VITRINE){
			if(actionBar!=null){
				setActionBarColorFromId(R.color.bleu_cartel);
			}
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager
			.beginTransaction()
			.replace(R.id.container,
					FragmentNantes.newInstance()).commit();
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
		if(position == PAR_DELEGATION){
			setActionBarColorFromId(R.color.vert_cartel);
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
			.replace(R.id.container, FragmentResultatsDelegation.newInstance()).commit();
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
		if(position == SPONSORS){
			setActionBarColorFromId(R.color.rouge_cartel);
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
			.replace(R.id.container, FragmentSponsors.newInstance()).commit();
		}
		if(position == REGLAGES){
			setActionBarColorFromId(R.color.rouge_cartel);
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
			.replace(R.id.container, FragmentReglages.newInstance()).commit();
		}
		if(position == A_PROPOS){
			setActionBarColorFromId(R.color.rouge_cartel);
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
			.replace(R.id.container, FragmentApropos.newInstance()).commit();
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

	@Override
	public void onBackPressed(){
		if(mNavigationDrawerFragment.isDrawerOpen()){
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}else{
			mDrawerLayout.openDrawer(Gravity.LEFT);
		}

	}




}

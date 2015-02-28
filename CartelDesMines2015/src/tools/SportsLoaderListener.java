package tools;

import java.util.ArrayList;

import beans.Match;

public interface SportsLoaderListener {
	
	public void onLoadFinished(ArrayList<Match>  matches, ArrayList<String> sports);

}

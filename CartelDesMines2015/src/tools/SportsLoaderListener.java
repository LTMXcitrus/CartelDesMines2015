package tools;

import java.util.ArrayList;

import beans.Match;
import beans.Resultat;

public interface SportsLoaderListener {
	
	public void onLoadFinished(ArrayList<Resultat>  matches, ArrayList<String> sports);

}

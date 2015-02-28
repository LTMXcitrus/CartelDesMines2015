package tools;

import java.util.ArrayList;

import beans.Classement;
import beans.Match;

public interface MatchesListener {
	
	public void onLoadFinished(ArrayList<Match> matches);

}

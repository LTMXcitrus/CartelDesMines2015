package tools;

import java.util.ArrayList;

import beans.Resultat;

public interface MatchesFilteredListener {
	
	public void onMatchesFilteredAvailable(ArrayList<Resultat> resultatsJour1, ArrayList<Resultat> resultatsJour2, ArrayList<Resultat> resultatsJour3);

}

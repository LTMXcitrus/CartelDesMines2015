package tools;

import java.util.ArrayList;

import beans.Resultat;

public interface ResultatsListener {
	
	public void onLoadFinished(ArrayList<Resultat> resultatsJour1, ArrayList<Resultat> resultatsJour2, ArrayList<Resultat> resultatsJour3);

}

package tools;

import java.util.ArrayList;

import beans.Resultat;

public interface ResultatsListener {
	
	public void onLoadFinished(ArrayList<Resultat> resultats);

}

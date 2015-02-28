package tools;

import java.util.ArrayList;

import beans.Classement;

public interface ClassementListener {
	
	public void onLoadFinished(ArrayList<Classement> classements);

}

package tools;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchInMarkers {
	/**
	 * The Strings are the resources for query, The Integers are the indexes of the corresponding markers.
	 */
	HashMap<Integer,String> markersQueryResources;

	public SearchInMarkers(HashMap<Integer,String> marekersQueryResources) {
		super();
		this.markersQueryResources = marekersQueryResources;
	}
	
	public ArrayList<Integer> contains(String query){
		ArrayList<Integer> result  = new ArrayList<Integer>();
		HashMap<Integer,String> resources = this.markersQueryResources;
		for(int i=0;i<resources.size();i++){
			if(((String) resources.get(i)).contains(query)){
				result.add(i);
			}
		}
		return result;
	}
	
	public int search(String query){
		return 0;
	}

}

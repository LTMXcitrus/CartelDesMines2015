package tools;

import java.util.ArrayList;

import beans.Article;

public interface ArticlesLoaderListener {
	
	public void onLoadFinished(ArrayList<Article> articles);

}

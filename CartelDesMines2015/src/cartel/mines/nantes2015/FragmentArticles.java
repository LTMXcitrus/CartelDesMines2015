package cartel.mines.nantes2015;

import java.util.ArrayList;

import loaders.ArticlesLoader;
import loaders.ResultatsLoader;
import beans.Article;
import tools.ArticlesImagesLoaderListener;
import tools.ArticlesLoaderListener;
import adapters.ArticlesListAdapter;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FragmentArticles extends ListFragment implements ArticlesImagesLoaderListener, ArticlesLoaderListener{
	
	ProgressDialog dialog;
	ListView list;
	ArrayList<Bitmap> imagesThumbnail;
	ArticlesListAdapter adapter;
	ArrayList<Article> articles;
	
	public static FragmentArticles newInstance(){
		return new FragmentArticles();
	}
	
	public FragmentArticles(){	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,	Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.fragment_resultats, container, false);
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		final ArticlesLoader loader = new ArticlesLoader(this,this);
		loader.start();
		imagesThumbnail = new ArrayList<Bitmap>();
		
		dialog = ProgressDialog.show(getActivity(), "Veuillez patienter...", "Chargement...");
		dialog.setCancelable(true);
		dialog.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				loader.interrupt();
			}
		});

		list = getListView();
	}
	
	
	

	@Override
	public void onLoadFinished(final ArrayList<Article> articles) {
		
		this.articles=articles;
		
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(),ArticleActivity.class);
				intent.putExtra("article", articles.get(position));
				startActivity(intent);
			}
		});
		
		dialog.dismiss();
		list.post(new Runnable() {
			
			@Override
			public void run() {
				adapter  = new ArticlesListAdapter(getActivity(), R.layout.article_list_item, articles, imagesThumbnail);
				list.setAdapter(adapter);
			}
		});
		
	}

	@Override
	public void onImageLoadFinished(int imageNumber, Bitmap image) {
		imagesThumbnail.add(image);
		adapter = new ArticlesListAdapter(getActivity(), R.layout.article_list_item, articles, imagesThumbnail);
		list.post(new Runnable() {
			
			@Override
			public void run() {
				list.setAdapter(adapter);
			}
		});
	}
	
	

}

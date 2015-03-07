package cartel.mines.nantes2015;

import tools.ArticleImageGrandeLoaderListener;
import loaders.ArticleImageGrandeLoader;
import beans.Article;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ArticleActivity extends Activity implements ArticleImageGrandeLoaderListener{
	ImageView imageArticle; 
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article_activity);
		
		Article article = (Article) getIntent().getSerializableExtra("article");
		
		getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bleu_cartel)));
		
		ArticleImageGrandeLoader loader = new ArticleImageGrandeLoader(article.getImageURL(), this);
		loader.start();
		
		TextView entete = (TextView) findViewById(R.id.entete);
		TextView contenu = (TextView) findViewById(R.id.articleContenu);
		imageArticle = (ImageView) findViewById(R.id.imageArticle);
		
		entete.setText("Ecrit par " + article.getAuthor() + ", le " + article.getDayOfMonth() + "/04 à " + article.getHourOfDay() + "h" + article.getMinuteOfHourAsString());
		entete.setTextColor(getResources().getColor(R.color.bleu_cartel));
		
		contenu.setText(article.getBody());
	}

	@Override
	public void onLoadFinished(final Bitmap image) {
		imageArticle.post(new Runnable() {
			
			@Override
			public void run() {
				imageArticle.setImageBitmap(image);
			}
		});
		
	}

}

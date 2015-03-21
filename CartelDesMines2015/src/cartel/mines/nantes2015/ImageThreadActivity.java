package cartel.mines.nantes2015;

import loaders.ImageLoader;
import tools.ImageLoaderListener;
import beans.Image;
import beans.Notification;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageThreadActivity extends Activity implements ImageLoaderListener{
	
	ImageView imageView;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article_activity);
		
		getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bleu_cartel)));
		
		Image image = (Image) getIntent().getSerializableExtra("image");
		
		ImageLoader loader = new ImageLoader(image.getImageUrl(), null, this);
		loader.start();
		
		TextView auteur = (TextView) findViewById(R.id.entete);		
		imageView = (ImageView) findViewById(R.id.imageArticle);		
		TextView comment = (TextView) findViewById(R.id.articleContenu);
		
		auteur.setText(image.getAuteur());
		auteur.setTextColor(getResources().getColor(R.color.bleu_cartel));
		
		comment.setText(image.getComment());
		
		//TODO
	}

	@Override
	public void onLoadFinished(final Bitmap bitmap, Notification notif) {
		imageView.post(new Runnable() {
			
			@Override
			public void run() {
				imageView.setImageBitmap(bitmap);
			}
		});
		
	}

}

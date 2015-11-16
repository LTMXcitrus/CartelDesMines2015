package cartel.mines.nantes2015;

import loaders.ImageLoader;
import tools.ImageLoaderListener;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import beans.Notification;

public class NotificationActivity extends Activity implements ImageLoaderListener{
	
	ImageView image;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article_activity);
		
		getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bleu_cartel)));
		
		Notification notif = (Notification) getIntent().getSerializableExtra("notif");
		
		System.out.println(notif.getImageUrl().isEmpty());
		System.out.println(notif.getImageUrl());
		if(!notif.getImageUrl().isEmpty()){
			ImageLoader loader = new ImageLoader(notif.getImageUrl(),null,this);
			loader.start();
			image = (ImageView) findViewById(R.id.imageArticle);
		}
		
		TextView title = (TextView) findViewById(R.id.entete);
		TextView body = (TextView) findViewById(R.id.articleContenu);
		title.setText(notif.getTitre());
		title.setTextColor(getResources().getColor(R.color.bleu_cartel));
		title.setTextSize(18);
		body.setText(notif.getBody());
	}

	@Override
	public void onLoadFinished(final Bitmap bitmap, Notification notif) {
		image.post(new Runnable() {
			
			@Override
			public void run() {
				image.setImageBitmap(bitmap);
			}
		});
		
	}

}

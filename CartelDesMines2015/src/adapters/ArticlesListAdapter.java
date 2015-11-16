package adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import beans.Article;
import cartel.mines.nantes2015.R;

public class ArticlesListAdapter extends ArrayAdapter<Article>{
	
	Context context;
	int resource;
	List<Article> objects;
	List<Bitmap> images;

	public ArticlesListAdapter(Context context, int resource, List<Article> objects) {
		super(context, resource, objects);
		this.context = context;
		this.resource = resource;
		this.objects = objects;
	}
	
	public ArticlesListAdapter(Context context, int resource, List<Article> objects, List<Bitmap> images) {
		this(context,resource,objects);
		this.images=images;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		if(convertView==null){

			LayoutInflater inflater = ((Activity) context).getLayoutInflater();

			convertView = inflater.inflate(resource, parent, false);
		}
		
		Article article = objects.get(position);
		
		ImageView imageView = (ImageView) convertView.findViewById(R.id.imageThumbnail);
		TextView title = (TextView) convertView.findViewById(R.id.articleTitle);
		TextView date = (TextView) convertView.findViewById(R.id.dateArticle);
		
		title.setText(article.getTitle());
		date.setText("le " + article.getDayOfMonth() + "/04, à " + article.getHourOfDay() + "h" + article.getMinuteOfHourAsString());
		date.setTextColor(context.getResources().getColor(R.color.bleu_cartel));
		
		if((images.size()>=position+1)){
			imageView.setImageBitmap(images.get(position));
		}
		

		return convertView;
	}

}

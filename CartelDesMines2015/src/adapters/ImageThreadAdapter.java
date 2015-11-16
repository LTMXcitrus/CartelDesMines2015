package adapters;

import java.util.List;

import loaders.MyImageLoaderCache;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import beans.Image;
import cartel.mines.nantes2015.R;

public class ImageThreadAdapter extends ArrayAdapter<Image>{

	Context context;
	int resource;
	List<Image> images;
	List<Bitmap> bitmaps;
	MyImageLoaderCache imageLoader;

	public ImageThreadAdapter(Context context, int resource, List<Image> images) {
		super(context, resource, images);
		this.context=context;
		this.resource=resource;
		this.images=images;
		imageLoader = new MyImageLoaderCache(context);
    
	}

	public ImageThreadAdapter(Context context, int resource, List<Image> images, List<Bitmap> bitmaps){
		this(context, resource, images);
		this.bitmaps=bitmaps;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){

		LayoutInflater inflater = ((Activity) context).getLayoutInflater();

		convertView = inflater.inflate(resource, parent, false);

		Image image = images.get(position);

		ImageView imageView = (ImageView) convertView.findViewById(R.id.imagethread_image);
		TextView auteur = (TextView) convertView.findViewById(R.id.imagethread_auteur);
		TextView comment = (TextView) convertView.findViewById(R.id.imagethread_comment);
		TextView elapsedTime = (TextView) convertView.findViewById(R.id.imagethread_date);

		elapsedTime.setText(image.getElapsedTimeSincePublication());
		auteur.setText(image.getAuteur());
		comment.setText(image.getComment());
		if(imageView!=null){
			imageLoader.DisplayImage(image.getThumbnailUrl(), imageView);
		}

		return convertView;
	}

	static class ImageThreadListViewHolder{
		ImageView imageView;
		TextView auteur;
		TextView comment;
	}

}

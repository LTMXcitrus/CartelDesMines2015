package adapters;

import java.util.List;

import loaders.ImageThreadAsyncTask;
import cartel.mines.nantes2015.R;
import beans.Image;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageThreadAdapter extends ArrayAdapter<Image>{

	Context context;
	int resource;
	List<Image> images;
	List<Bitmap> bitmaps;

	public ImageThreadAdapter(Context context, int resource, List<Image> images) {
		super(context, resource, images);
		this.context=context;
		this.resource=resource;
		this.images=images;
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


		auteur.setText(image.getAuteur());
		comment.setText(image.getComment());
		if(imageView!=null){
			new ImageThreadAsyncTask(imageView).execute(image.getImageUrl());
		}

		return convertView;
	}

	static class ImageThreadListViewHolder{
		ImageView imageView;
		TextView auteur;
		TextView comment;
	}

}

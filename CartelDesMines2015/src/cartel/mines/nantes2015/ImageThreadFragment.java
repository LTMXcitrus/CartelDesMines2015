package cartel.mines.nantes2015;

import java.util.ArrayList;

import loaders.ImageThreadLoader;
import tools.ImageThreadListener;
import adapters.ImageThreadAdapter;
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
import beans.Image;

public class ImageThreadFragment extends ListFragment implements ImageThreadListener{

	ListView list;
	ProgressDialog dialog;

	public static ImageThreadFragment newInstance(){
		return new ImageThreadFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,	Bundle savedInstanceState){
		View convertView =inflater.inflate(R.layout.listfragment, container, false);
		return convertView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		list = getListView();

				
		final ImageThreadLoader loader  =  new ImageThreadLoader(this);
		loader.start();
		dialog = ProgressDialog.show(getActivity(), "Chargement...", "Veuillez patienter...");
		dialog.setCancelable(true);
		dialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				loader.interrupt();
			}
		});
	}

	@Override
	public void onLoadFinished(final ArrayList<Image> images,final ArrayList<Bitmap> bitmaps) {
		dialog.dismiss();
		System.out.println(list==null);
		list.post(new Runnable() {
			
			@Override
			public void run() {
				list.setAdapter(new ImageThreadAdapter(getActivity(), R.layout.imagethread_item, images, bitmaps));
				list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = new Intent(getActivity(),ImageThreadActivity.class);
						intent.putExtra("image", images.get(position));
						startActivity(intent);
						
					}
				});
			}
		});

	}
}
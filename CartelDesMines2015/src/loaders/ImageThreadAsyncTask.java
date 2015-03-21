package loaders;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;



import cartel.mines.nantes2015.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageThreadAsyncTask extends AsyncTask<String, Void, Bitmap>{

	private ImageView imageView;


	public ImageThreadAsyncTask(ImageView imageView) {
		this.imageView=imageView;

	}

	@Override
	protected Bitmap doInBackground(String... params) {
		URL url;
		Bitmap bitmap = null;
		try {

			url = new URL(params[0]);

			HttpURLConnection connection;

			connection = (HttpURLConnection) url
					.openConnection();

			connection.setDoInput(true);
			connection.connect();

			InputStream stream = connection.getInputStream();

			bitmap = BitmapFactory.decodeStream(stream);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;
	}

	@Override
	protected void onPostExecute(Bitmap bitmap) {
		if (isCancelled()) {
			bitmap = null;
		}
		if (imageView != null) {

			if (bitmap != null) {
				imageView.setImageBitmap(bitmap);
			} else {
				imageView.setImageDrawable(imageView.getContext().getResources().getDrawable(R.drawable.ic_launcher));
			}
		}


	}

}

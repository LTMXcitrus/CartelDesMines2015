package tools;

import android.graphics.Bitmap;

public interface ArticlesImagesLoaderListener {
	
	public void onImageLoadFinished(int imageNumber, Bitmap image);

}

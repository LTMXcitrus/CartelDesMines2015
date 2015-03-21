package tools;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Bitmap;
import beans.Image;

public interface ImageThreadListener {
	
	public void onLoadFinished(ArrayList<Image> images, ArrayList<Bitmap> bitmaps);

}

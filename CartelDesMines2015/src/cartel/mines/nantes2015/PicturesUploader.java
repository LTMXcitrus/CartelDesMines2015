package cartel.mines.nantes2015;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

import tools.MediaUploader;
import tools.MediaUploaderListener;
import adapters.MarkerSearchListAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PicturesUploader extends Activity implements MediaUploaderListener{

	private final static int ACTIVITY_SELECT_IMAGE=0;
	private final static int REQUEST_IMAGE_CAPTURE=1;

	public static final int MEDIA_TYPE_IMAGE = 100;

	private static final String FILE_URI = "file_uri";

	private Uri fileUri;

	private File imageFile;

	TextView resultat;
	ImageView image;
	Button upload;

	Button chooseFileToUpload;
	Button takePicturesToUpload;
	TextView noFile;
	EditText nameInput;
	ProgressDialog progress;
	LinearLayout noFileLayout;
	File photoFile;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pictures_uploader);

		getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bleu_cartel)));

		loadWidgets();

		setListeners();

		upload.setEnabled(false);
		resultat.setVisibility(View.GONE);

		Intent incomingIntent = getIntent();
		String action = incomingIntent.getAction();
		String type = incomingIntent.getType();
		if(action!=null){
			if(action.equals(Intent.ACTION_SEND)){

				noFileLayout.setVisibility(View.GONE);

				if(type.startsWith("image/")){
					handleSendImage(incomingIntent);
				}
			}
		}
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		// Save the current Uri set for the file being chosen or picked

		if(fileUri!=null){
			savedInstanceState.putString(FILE_URI, fileUri.toString());
		}


		// Always call the superclass so it can save the view hierarchy state
		super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Always call the superclass so it can restore the view hierarchy
		super.onRestoreInstanceState(savedInstanceState);

		// Restore state members from saved instance

		fileUri  = Uri.parse(savedInstanceState.getString(FILE_URI));
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent){
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 
		switch(requestCode) { 
		case ACTIVITY_SELECT_IMAGE:
			if(resultCode == RESULT_OK){
				imageFile = new File(getRealPathFromURI(this, imageReturnedIntent.getData()));

				handleChosenTakenImage(imageReturnedIntent);				
			}
		case REQUEST_IMAGE_CAPTURE:
			if(resultCode == RESULT_OK){
				handleChosenTakenImage(imageReturnedIntent);
			}
		}
	}

	public void handleChosenTakenImage(Intent intent){
		noFile.setVisibility(View.GONE);
		chooseFileToUpload.setVisibility(View.GONE);
		upload.setEnabled(true);
		if(imageFile != null){

			Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
			

			fileUri = getImageContentUri(this, imageFile);

			image.setImageBitmap(bitmap);
		}

		//scaleImage();

	}

	public static Uri getImageContentUri(Context context, File imageFile) {
		String filePath = imageFile.getAbsolutePath();
		Cursor cursor = context.getContentResolver().query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				new String[] { MediaStore.Images.Media._ID },
				MediaStore.Images.Media.DATA + "=? ",
						new String[] { filePath }, null);

		if (cursor != null && cursor.moveToFirst()) {
			int id = cursor.getInt(cursor
					.getColumnIndex(MediaStore.MediaColumns._ID));
			Uri baseUri = Uri.parse("content://media/external/images/media");
			return Uri.withAppendedPath(baseUri, "" + id);
		} else {
			if (imageFile.exists()) {
				ContentValues values = new ContentValues();
				values.put(MediaStore.Images.Media.DATA, filePath);
				return context.getContentResolver().insert(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
			} else {
				return null;
			}
		}
	}

	public String getRealPathFromURI(Context context, Uri contentUri) {
		Cursor cursor = null;
		try { 
			String[] proj = { MediaStore.Images.Media.DATA };
			cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	private void handleSendImage(Intent intent) {
		fileUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
		if (fileUri != null) {

			image.setImageURI(fileUri);

			upload.setEnabled(true);
		}

	} 

	protected String convertMediaUriToPath(Uri uri) {
		String [] proj={MediaStore.Images.Media.DATA};
		Cursor cursor = getContentResolver().query(uri, proj,  null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		String path = cursor.getString(column_index); 
		cursor.close();
		return path;
	}

	@Override
	public void onUploadFinished(final int code, final String entity) {
		progress.dismiss();
		resultat.post(new Runnable() {
			@Override
			public void run() {
				resultat.setVisibility(View.VISIBLE);
				System.out.println(code);
				System.out.println(entity);


				if(code == 200){
					Intent intent = new Intent(PicturesUploader.this,Accueil.class);
					intent.putExtra("key","imagethread");
					startActivity(intent);
				}
				else{
					resultat.setText("Erreur: " + code);
				}		
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if(id == android.R.id.home){
			Intent intent = new Intent(this,Accueil.class);
			intent.putExtra("carte", "carte");
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void loadWidgets(){
		resultat = (TextView) findViewById(R.id.on_upload_result_text);
		noFile = (TextView) findViewById(R.id.no_file);
		chooseFileToUpload = (Button) findViewById(R.id.choose_file_to_upload);
		image = (ImageView) findViewById(R.id.image);
		upload = (Button) findViewById(R.id.upload);
		nameInput = (EditText) findViewById(R.id.comment_input);
		takePicturesToUpload = (Button) findViewById(R.id.take_picture_to_upload);
		noFileLayout = (LinearLayout) findViewById(R.id.no_file_layout);
	}

	public void setListeners(){
		upload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(nameInput.getText().toString().isEmpty()){
					Builder dialo = new AlertDialog.Builder(PicturesUploader.this);
					dialo.setCancelable(true);
					LayoutInflater factory = LayoutInflater.from(PicturesUploader.this);
					final View dialochargement = factory.inflate(R.layout.file_name_dialog, null);
					final EditText chooseName = (EditText) dialochargement.findViewById(R.id.choose_name);
					dialo.setMessage("Veuillez entrer un nom:");
					dialo.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							String chosenName =  chooseName.getText().toString();
							if(!chosenName.isEmpty()){
								nameInput.setText(chosenName);
								dialog.dismiss();
								upload.performClick();
							}
						}
					});
					dialo.setView(dialochargement);
					dialo.show();
				}
				else{
					progress = ProgressDialog.show(PicturesUploader.this, "Chargement...", "Veuillez patienter...", true);
					progress.setCancelable(true);

					SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
					String auteur = pref.getString("username", "noname");

					final MediaUploader m = new MediaUploader(convertMediaUriToPath(fileUri), nameInput.getText().toString(),
							auteur, PicturesUploader.this);
					m.start();
					progress.setOnCancelListener(new OnCancelListener() {
						@Override
						public void onCancel(DialogInterface dialog) {
							m.interrupt();
						}
					});

				}
			}
		});
		chooseFileToUpload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, ACTIVITY_SELECT_IMAGE);
			}
		});
		takePicturesToUpload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				imageFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
				fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
				System.out.println("before : fileUri: " + fileUri);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
				startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
			}
		});

		image.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				try {
					rotateImage();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return false;
			}
		});
	}
	private static Uri getOutputMediaFileUri(int type){
		return Uri.fromFile(getOutputMediaFile(type));
	}


	private static File getOutputMediaFile(int type){
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_PICTURES), "MyCameraApp");
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		if (! mediaStorageDir.exists()){
			if (! mediaStorageDir.mkdirs()){
				Log.d("MyCameraApp", "failed to create directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE){
			mediaFile = new File(mediaStorageDir.getPath() + File.separator +
					"IMG_"+ timeStamp + ".jpg");
		}
		else {
			return null;
		}

		return mediaFile;
	}

	private void scaleImage()
	{
		Drawable drawing = image.getDrawable();
		if (drawing == null) {
			return; // Checking for null & return, as suggested in comments
		}
		Bitmap bitmap = ((BitmapDrawable) drawing).getBitmap();

		// Get current dimensions AND the desired bounding box
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int bounding = dpToPx(250);
		Log.i("Test", "original width = " + Integer.toString(width));
		Log.i("Test", "original height = " + Integer.toString(height));
		Log.i("Test", "bounding = " + Integer.toString(bounding));

		// Determine how much to scale: the dimension requiring less scaling is
		// closer to the its side. This way the image always stays inside your
		// bounding box AND either x/y axis touches it.  
		float xScale = ((float) bounding) / width;
		float yScale = ((float) bounding) / height;
		float scale = (xScale <= yScale) ? xScale : yScale;
		Log.i("Test", "xScale = " + Float.toString(xScale));
		Log.i("Test", "yScale = " + Float.toString(yScale));
		Log.i("Test", "scale = " + Float.toString(scale));

		// Create a matrix for the scaling and add the scaling data
		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);

		// Create a new bitmap and convert it to a format understood by the ImageView 
		Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		width = scaledBitmap.getWidth(); // re-use
		height = scaledBitmap.getHeight(); // re-use
		BitmapDrawable result = new BitmapDrawable(scaledBitmap);
		Log.i("Test", "scaled width = " + Integer.toString(width));
		Log.i("Test", "scaled height = " + Integer.toString(height));

		// Apply the scaled bitmap
		image.setImageDrawable(result);

		// Now change ImageView's dimensions to match the scaled image
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) image.getLayoutParams(); 
		params.width = width;
		params.height = height;
		image.setLayoutParams(params);

		Log.i("Test", "done");
	}

	private void rotateImage() throws IOException{
		Matrix matrix = new Matrix();
		matrix.postRotate(90);
		Drawable drawing = image.getDrawable();
		Bitmap bitmap = ((BitmapDrawable) drawing).getBitmap();
		Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),bitmap.getHeight(), matrix, true);
		image.setImageBitmap(rotatedBitmap);

		if(imageFile!=null){
			FileOutputStream fOut = new FileOutputStream(imageFile);
			rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
			fOut.flush();
			fOut.close();
		}
	}

	private int dpToPx(int dp)
	{
		float density = getApplicationContext().getResources().getDisplayMetrics().density;
		return Math.round((float)dp * density);
	}

	@Override
	public void onBackPressed(){
		Intent intent = new Intent(this,Accueil.class);
		intent.putExtra("carte", "carte");
		startActivity(intent);
	}
}

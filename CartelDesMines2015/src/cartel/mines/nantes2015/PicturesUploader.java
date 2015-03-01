package cartel.mines.nantes2015;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpResponse;

import tools.MediaUploader;
import tools.MediaUploaderListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PicturesUploader extends Activity implements MediaUploaderListener{

	private final static int ACTIVITY_SELECT_IMAGE=0;
	private final static int REQUEST_IMAGE_CAPTURE=1;

	TextView resultat;
	ImageView image;
	Button upload;
	Uri imageUri;
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
		setContentView(R.layout.pictures_ploader);
		
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
	public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent){
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 
		switch(requestCode) { 
		case ACTIVITY_SELECT_IMAGE:
			if(resultCode == RESULT_OK){
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
		
		Uri selectedImage = intent.getData();
		String[] filePathColumn = {MediaStore.Images.Media.DATA};

		Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
		cursor.moveToFirst();

		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String filePath = cursor.getString(columnIndex);
		cursor.close();
		
		Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
		//Bitmap scaledBitmap = Bitmap.createScaledBitmap(yourSelectedImage, 300, 150, false);
		image.setImageBitmap(yourSelectedImage);
		imageUri= selectedImage;
		scaleImage();

	}

	private void handleSendImage(Intent intent) {
		imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
		if (imageUri != null) {

			image.setImageURI(imageUri);

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
	public void onUploadFinished(final HttpResponse res) {
		progress.dismiss();
		resultat.post(new Runnable() {
			@Override
			public void run() {
				resultat.setVisibility(View.VISIBLE);
				int code = res.getStatusLine().getStatusCode();
				if(code == 200){
					resultat.setText("Fichier envoyé !");
				}
				else{
					resultat.setText("Erreur: " + code);
				}		
			}
		});

	}

	public void loadWidgets(){
		resultat = (TextView) findViewById(R.id.on_upload_result_text);
		noFile = (TextView) findViewById(R.id.no_file);
		chooseFileToUpload = (Button) findViewById(R.id.choose_file_to_upload);
		image = (ImageView) findViewById(R.id.image);
		upload = (Button) findViewById(R.id.upload);
		nameInput = (EditText) findViewById(R.id.name_input);
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
							String choosenName =  chooseName.getText().toString();
							if(!choosenName.isEmpty()){
								nameInput.setText(choosenName);
								dialog.dismiss();
							}
						}
					});
					dialo.setView(dialochargement);
					dialo.show();
				}
				else{
					progress = ProgressDialog.show(PicturesUploader.this, "Chargement...", "Veuillez patienter...", true);
					MediaUploader m = new MediaUploader(convertMediaUriToPath(imageUri), nameInput.getText().toString(), PicturesUploader.this);
					m.start();

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
				startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
			}
		});
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

	private int dpToPx(int dp)
	{
	    float density = getApplicationContext().getResources().getDisplayMetrics().density;
	    return Math.round((float)dp * density);
	}
}

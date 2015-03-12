package cartel.mines.nantes2015;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class FragmentApropos extends Fragment{
	
	
	PackageInfo mPackageInfo;
	
	public static FragmentApropos newInstance(){
		return new FragmentApropos();
	}
	
	public FragmentApropos(){ }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,	Bundle savedInstanceState){
		
		View convertView  = inflater.inflate(R.layout.a_propos, container, false);
		
		TextView version = (TextView) convertView.findViewById(R.id.appversion);
		
		
		 mPackageInfo=null;
		try {
			mPackageInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
		} catch (NameNotFoundException e) {
			if(BuildConfig.DEBUG){
				System.out.println(e);
			}
		}
		if(mPackageInfo!=null){
			version.setText("Version: "+mPackageInfo.versionName);
		}
		
		TextView contact = (TextView) convertView.findViewById(R.id.contacteznous);
		
		contact.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_SENDTO);
				intent.setData(Uri.parse("mailto:matthieu.lemonnier45140@gmail.com")); // only email apps should handle this
				intent.putExtra(Intent.EXTRA_EMAIL, "matthieu.lemonnier45140@gmail.com");
				intent.putExtra(android.content.Intent.EXTRA_TEXT,"\n\n\n\n\nVersion de l'application: "+mPackageInfo.versionName+"\n"+"Modèle du téléphone: "+getDeviceName()+"\n"+"Version de l'OS: "+getOsVersion());
				if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
					startActivity(intent);
				}
			}
		});
		
		
		
		return convertView;
		
	}
	
	/**
	 * recherche et renvoit le modèle de l'appareil
	 * @return
	 */
	public String getDeviceName() {
		String manufacturer = Build.MANUFACTURER;
		String model = Build.MODEL;
		if (model.startsWith(manufacturer)) {
			return capitalize(model);
		} else {
			return capitalize(manufacturer) + " " + model;
		}
	}


	private String capitalize(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		char first = s.charAt(0);
		if (Character.isUpperCase(first)) {
			return s;
		} else {
			return Character.toUpperCase(first) + s.substring(1);
		}
	} 
	
	/**
	 * 
	 * @return la version de l'OS
	 */
	private String getOsVersion(){
		return Build.VERSION.RELEASE;
	}

}

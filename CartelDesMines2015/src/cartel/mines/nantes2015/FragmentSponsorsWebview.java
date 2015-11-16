package cartel.mines.nantes2015;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

@SuppressLint("SetJavaScriptEnabled")
public class FragmentSponsorsWebview extends Fragment{
	
	private static final String SPONSORS_URL = "http://cartel2015.com/fr/sponsors.html";
	
	WebView webView;
	
	
	public static FragmentSponsorsWebview newInstance(){
		return new FragmentSponsorsWebview();
	}
	
	public FragmentSponsorsWebview(){ };
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		
		View inflatedView = inflater.inflate(R.layout.fragment_sponsors_webview, container, false);
		
		webView = (WebView) inflatedView.findViewById(R.id.webview_sponsors);
		
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		
		
		webView.loadUrl(SPONSORS_URL);
		
		
		return inflatedView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		webView.loadUrl(SPONSORS_URL);
	}
	

}

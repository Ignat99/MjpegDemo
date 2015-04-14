package de.mjpegsample;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import de.mjpegsample.MjpegView.MjpegInputStream;
import de.mjpegsample.MjpegView.MjpegView;

public class MjpegSample extends Activity {
	private MjpegView mv;
	private String uriAPI = "http://192.168.1.33/led.cgi";
	
	public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
        //sample public cam
        String URL = "http://192.168.1.8:80?action=stream"; 
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        mv = new MjpegView(this);
        setContentView(mv);        
        
        mv.setSource(MjpegInputStream.read(URL));
        mv.setDisplayMode(MjpegView.SIZE_BEST_FIT);
        mv.showFps(true);
	}
	
	public void onPause() {
		super.onPause();
		mv.stopPlayback();
	}
	
//	@Override
//	public void loadPage(View v)
	public void loadPage() {
				final String msg = "0";
				Thread t = new Thread(new sendPostRunnable(msg));
				t.start();
	}
	public void toggleESP1() {
		final String msg = "1";
		Thread t = new Thread(new sendPostRunnable(msg));
		t.start();
}

// Populates the activity's options menu.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, menu);
		return true;
	}

// Handles the user's menu selection.
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
	// case R.id.settings:
	// Intent settingsActivity = new Intent(getBaseContext(),
	// SettingsActivity.class);
	// startActivity(settingsActivity);
	// return true;
		case R.id.refresh:
			loadPage();
			return true;
		case R.id.toggleButton1:
			toggleESP1();
//		loadPage();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	class sendPostRunnable implements Runnable
	{
		String strTxt = null;

		public sendPostRunnable(String strTxt)
		{
			this.strTxt = strTxt;
		}

		@Override
		public void run()
		{
			String result = sendPostDataToInternet(strTxt);
//			mHandler.obtainMessage(REFRESH_DATA, result).sendToTarget();
		}

	}

	private String sendPostDataToInternet(String strTxt)
	{

		/* HTTP Post */

		HttpPost httpRequest = new HttpPost(uriAPI);
		/*
		 * Post NameValuePair[]
		 */
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("led", strTxt));

		try

		{

			/* HTTP request */

			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			/* HTTP response */
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);
			/* 200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200)
			{
				/*  */
				String strResult = EntityUtils.toString(httpResponse
						.getEntity());
				// 
				return strResult;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

}

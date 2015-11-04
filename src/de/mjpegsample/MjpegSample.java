package de.mjpegsample;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
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
//import android.support.v7.app.ActionBarActivity;
//import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
//import de.mjpegsample.MjpegView.MjpegInputStream;
import de.mjpegsample.MjpegView.MjpegView;

//@SuppressWarnings("deprecation")
//ActionBar
public class MjpegSample extends Activity {
	//private MjpegView mv;
	//private String uriAPI = "http://192.168.1.33/led.cgi";
	private String uriAPI1 = "http://<your_ip>/control/relay.cgi?relay1=1";
	private String uriAPI0 = "http://<your_ip>/control/relay.cgi?relay1=0";
	//private static final String TAG = "RELAY";
	private String uriAPI21 = "http://<your_ip>:<your_port>/control/relay.cgi?relay1=1";
	private String uriAPI20 = "http://<your_ip>:<your_port>/control/relay.cgi?relay1=0";
	private int toggle_bnt1 = 0;
	private int toggle_bnt2 = 0;
	
	public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
        //sample public cam
        //String URL = "http://192.168.1.7:8080?action=stream"; 
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        //mv = new MjpegView(this);
        //setContentView(mv);        
        setContentView(R.layout.activity_main);
        
        //mv.setSource(MjpegInputStream.read(URL));
        //mv.setDisplayMode(MjpegView.SIZE_BEST_FIT);
        //mv.showFps(true);
	}
	
	public void onPause() {
		super.onPause();
		//mv.stopPlayback();
	}

	public void toggleESP10(View v) {
		if (toggle_bnt1 == 0) {
			//InputStream stream = downloadUrl(uriAPI1);
			//toggleESP1();
//			try {
				toggleESP1();
				toggle_bnt1 = 1;
//			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		} else {
			//InputStream stream = downloadUrl(uriAPI0);
			//loadPage();
//			try {
				loadPage();
				toggle_bnt1 = 0;
//			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
						
		}
		return;
	}	
	
	public void toggleESP20(View v) {
		if (toggle_bnt2 == 0) {
			//InputStream stream = downloadUrl(uriAPI21);
			//toggle_bnt2 = 1;
//			try {
				toggleESP2();
				toggle_bnt2 = 1;
//			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		} else {
			//InputStream stream = downloadUrl(uriAPI20);
			//toggle_bnt2 = 0;
//			try {
				loadPage2();
				toggle_bnt2 = 0;
//			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
		return;
	}	
	
//	@Override
	//	public void loadPage(View v)
	public void loadPage() {
				//final String msg = "0";
				Thread t = new Thread(new sendPostRunnable(uriAPI0));
				t.start();
				//InputStream stream = 
				//downloadUrl(uriAPI0);
	}
	public void toggleESP1() {
		//final String msg = "1";
		Thread t = new Thread(new sendPostRunnable(uriAPI1));
		t.start();
		//InputStream stream = 
		//downloadUrl(uriAPI1);
	}
	public void loadPage2() {
//		final String msg = "0";
		Thread t = new Thread(new sendPostRunnable(uriAPI20));
		t.start();
		//InputStream stream = 
		//downloadUrl(uriAPI20);
	}
	public void toggleESP2() {
		//final String msg = "1";
		Thread t = new Thread(new sendPostRunnable(uriAPI21));
		t.start();
		//InputStream stream = 
		//downloadUrl(uriAPI21);
	}

// Populates the activity's options menu.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, menu);
		//menu.findItem(R.id.refresh).setVisible(true);
		super.onCreateOptionsMenu(menu);
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
//			try {
				toggleESP1();
				return true;
		case R.id.toggleButton1:
//			try {
				loadPage();
				return true;
//			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		case R.id.refresh2:
//			try {
				toggleESP2();
				return true;
		case R.id.toggleButton2:
//			try {
				loadPage2();
				return true;
//			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		loadPage();
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
			//String result = sendPostDataToInternet(strTxt);
			InputStream stream = downloadUrl(strTxt);
//			mHandler.obtainMessage(REFRESH_DATA, result).sendToTarget();
		}

	}

	private String sendPostDataToInternet(String strTxt)
	{

		/* HTTP Post */

		HttpPost httpRequest = new HttpPost(uriAPI0);
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
	
	// Given a string representation of a URL, sets up a connection and gets
		// an input stream.
//		private InputStream downloadUrl(String urlString) throws IOException  {
			private InputStream downloadUrl(String urlString)  {
			//InputStream myInputStream = null;
			//StringBuilder sb = new StringBuilder();
			//sb.append("led=off");
			//URL url;
			//try {
				
			URL url = null;
			try {
				url = new URL(urlString);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			
			HttpURLConnection conn = null;
			try {
				conn = (HttpURLConnection) url.openConnection();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			}
			conn.setReadTimeout(1000 /* milliseconds 10000 */);
			conn.setConnectTimeout(1500 /* milliseconds 150000 */);
			//conn.setDoOutput(true);
			//conn.setRequestMethod("POST");
			try {
				conn.setRequestMethod("GET");
			} catch (ProtocolException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			conn.setDoInput(true);
			//OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			//wr.write(sb.toString());
			//wr.flush();
			
			// Starts the query
			try {
				conn.connect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			InputStream stream = null;
			try {
				stream = conn.getInputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			return stream;
			//myInputStream = conn.getInputStream();
			//wr.close();
			//} catch (Exception e) {
			//	Log.d(TAG,e.getMessage());
			//}
				//return myInputStream;
			
		}

}

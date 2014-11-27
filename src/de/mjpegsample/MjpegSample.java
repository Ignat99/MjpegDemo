package de.mjpegsample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import de.mjpegsample.MjpegView.MjpegInputStream;
import de.mjpegsample.MjpegView.MjpegView;

public class MjpegSample extends Activity {
	private MjpegView mv;
	
	public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
        //sample public cam
        String URL = "http://ignat99.crabdance.com:8080?action=stream"; 
        
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
}

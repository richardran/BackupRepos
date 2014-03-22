package com.richardran.MultRes;


import com.richardran.myandriodtest3.R;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceHolder;

public class MainActivity extends Activity {

	private MainSurface mySurfaceView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        
		setContentView(R.layout.activity_main);
	    mySurfaceView = new MainSurface(this);
	    
	    setContentView(mySurfaceView);		
	    
        new CountDownTimer(100000, 1000) {
            public void onTick(long millisUntilFinished) {
            	Log.w("app", "Successfully started timer");
            	
            	return ;
            }
            public void onFinish() {
                Log.w("app", "Successfully finished timer");

            }
        }.start();
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	   
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mySurfaceView.onResumeMySurfaceView();
	 }
	 
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mySurfaceView.onPauseMySurfaceView();
	}
}

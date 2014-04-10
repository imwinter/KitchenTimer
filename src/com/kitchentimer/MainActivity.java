package com.kitchentimer;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	private long counter;
	private CountDownTimer timer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Prevents the screen from dimming and going to sleep. 
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		counter = 0;
		timer = null;
	}
	
	@Override
	protected void onPause(){
		if(timer != null){
			timer.cancel();
			timer = null;
		}
		super.onPause();
	}
	
	public void clickStartStop(View v){
		if(timer != null){
			timer.cancel();
			timer = null;
		} else {
			restartCounter(true);
		}
		
		displayCounter(counter);
	}

	public void clickReset(View v){
		if(timer != null){
			timer.cancel();
			timer = null;
		}
		counter = 0;
		displayCounter(counter);
	}
		
	public void clickMinus(View v){
		counter -= 60 * 1000;
		counter = Math.max(0, counter);
		displayCounter(counter);
		restartCounter(false);
	}
	
	public void clickPlus(View v){
		counter += 60 * 1000;
		displayCounter(counter);
		restartCounter(false);
	}
	
	private void restartCounter(boolean always){
		boolean isRunning = (timer != null);
		if(isRunning){
			timer.cancel();
			timer = null;
		}
		if(always || isRunning){
			displayCounter(counter);
			if(counter > 0){
				timer = new CountDownTimer(counter, 1000){
					public void onTick(long remainingTimeMillis){
						counter = remainingTimeMillis;
						displayCounter(counter);
					}
					public void onFinish(){
						displayCounter(0);
						counter = 0;
					}
				};
				timer.start();
			}
		}
	}
	
	private void displayCounter(long c){
		TextView mCount = (TextView) findViewById(R.id.textView1);
		long seconds = c / 1000;
		long minutes = seconds / 60;
		seconds -= minutes *60;
		if(seconds<10 && minutes<10)
			mCount.setText( "0"+ minutes + ":0" + seconds);
		else if(minutes<10)
				mCount.setText("0" + minutes + ":" + seconds);
		else if(seconds<10)
			mCount.setText( minutes + ":0" + seconds);
		else 
			mCount.setText( minutes + ":" + seconds);
	}
	

	
}

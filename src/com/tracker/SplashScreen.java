package com.tracker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

public class SplashScreen extends Activity 
{
	protected boolean _active = true;
    protected int _splashTime = 6000;
    ImageView splash_imv;
    AnimationDrawable mAnimation;
    int DURATION = 100;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        
        splash_imv = (ImageView) findViewById(R.id.splash_imv);
        splash_imv.setBackgroundResource(R.animator.splash_anim);
    	
    	
    	
        // thread for displaying the SplashScreen
        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                	sleep(500);
                    int waited = 0;
                    //StartSplash();
                    mAnimation = (AnimationDrawable)splash_imv.getBackground();
                    mAnimation.setOneShot(true);
                    mAnimation.start();
                    
                    
                    while(_active && (waited < _splashTime)) {
                        sleep(100);
                        if(_active) {
                            waited += 100;
                        }
                    }                    
                	finish();
                    startActivity(new Intent(getApplicationContext(), Main.class));
                    stop();
                }
                catch (Exception e) {
					// TODO: handle exception
                	finish();
                    startActivity(new Intent(getApplicationContext(), Main.class));
				}
            }
        };
        splashTread.start();  
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            _active = false;
        }
        return true;
    }
}

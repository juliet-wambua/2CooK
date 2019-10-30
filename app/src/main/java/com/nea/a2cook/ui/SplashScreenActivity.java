package com.nea.a2cook.ui;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.nea.a2cook.R;

public class SplashScreenActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1200;
    private ImageView container;
    private AnimationDrawable animationDrawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_splashscreen);
        container = findViewById(R.id.iv_icons);
        container.setBackgroundResource(R.drawable.splash_animation);
        animationDrawable = (AnimationDrawable) container.getBackground();
        new Handler ().postDelayed( new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
    @Override
    protected void onResume() {
        super.onResume();
        animationDrawable.start();
        checkAnimationStatus(200, animationDrawable);
    }
    /**
     * check the animation status recursively, keep the animation until it reach the last frame.
     *
     * @param time              period of animation
     * @param animationDrawable animation list
     */
    private void checkAnimationStatus(final int time, final AnimationDrawable animationDrawable) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (animationDrawable.getCurrent() != animationDrawable.getFrame(animationDrawable.getNumberOfFrames() - 1))
                    checkAnimationStatus(time, animationDrawable);
                else finish();
            }
        }, time);
    }
}

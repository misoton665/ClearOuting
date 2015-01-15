package org.misoton.clear.clearouting;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class TitleSplashActivity extends ActionBarActivity{

   Timer mTimer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_splash);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mTimer = new Timer(true);
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mTimer.cancel();
                Intent intent = new Intent(TitleSplashActivity.this, MainActivity.class);
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent);
                finish();
            }
        }, 1500);
    }
}

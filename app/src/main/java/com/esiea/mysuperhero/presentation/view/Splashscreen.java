package com.esiea.mysuperhero.presentation.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;

import com.esiea.mysuperhero.R;

public class Splashscreen extends Activity {

    String myPackageName;

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
    /** Called when the activity is first created. */
    Thread splashTread;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myPackageName = getPackageName();
        setContentView(R.layout.splash_screen);
        StartAnimations();
    }

    private void StartAnimations() {

        splashTread = new Thread() {
            @Override
            public void run() {
                try {

                        sleep(2000);

                        Intent intent = new Intent(Splashscreen.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);


                        Splashscreen.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    Splashscreen.this.finish();
                }

            }
        };
        splashTread.start();

    }
}
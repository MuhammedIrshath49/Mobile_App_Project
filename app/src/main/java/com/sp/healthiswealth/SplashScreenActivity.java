package com.sp.healthiswealth;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.Locale;

public class SplashScreenActivity extends AppCompatActivity {

    private int SLEEP_TIMER = 3;
    TextToSpeech tts;
    String text = "Health Is Wealth";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        LogoLauncher logoLauncher = new LogoLauncher();
        logoLauncher.start();

        tts= new TextToSpeech(SplashScreenActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status!= TextToSpeech.ERROR)
                {
                    tts.setLanguage(Locale.ENGLISH);
                }
            }
        });

    }



    private class LogoLauncher extends Thread{
        public void run(){
            try{
                sleep(1000 * SLEEP_TIMER);
                tts.speak(text,TextToSpeech.QUEUE_FLUSH,null);
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            Intent intent = new Intent(SplashScreenActivity.this, HealthIsWealth.class);
            startActivity(intent);
            SplashScreenActivity.this.finish();
        }
    }
}




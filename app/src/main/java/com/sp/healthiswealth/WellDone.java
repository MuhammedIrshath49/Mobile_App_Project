package com.sp.healthiswealth;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Locale;

public class WellDone extends AppCompatActivity  {

    TextToSpeech tts;
    String text = "Well Done! You have completed the workout";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_well_done);

        tts= new TextToSpeech(WellDone.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status!= TextToSpeech.ERROR)
                {
                    tts.setLanguage(Locale.ENGLISH);
                }

                tts.speak(text,TextToSpeech.QUEUE_FLUSH,null);
            }
        });


    }



}


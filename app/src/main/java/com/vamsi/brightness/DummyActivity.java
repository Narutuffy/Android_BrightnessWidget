package com.vamsi.brightness;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DummyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);


        Handler handler = new Handler();

     handler.postDelayed(new Runnable() {
         @Override
         public void run() {
             finish();
         }
     },2000);

    }
}

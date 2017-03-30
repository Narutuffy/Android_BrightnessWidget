package com.vamsi.brightness;

import android.content.ContentResolver;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int brightness;
    SeekBar seekBar;
    Window window;
    ContentResolver cResolver;
    TextView txtPerc ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instantiate seekbar object
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        txtPerc = (TextView) findViewById(R.id.textPerc);
        //Get the content resolver
        cResolver =  getContentResolver();
        //Get the current window
        window = getWindow();

               BrightnessControl();


    }

    private void BrightnessControl() {
        //Set the seekbar range between 0 and 255
        //seek bar settings//
        //sets the range between 0 and 255
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        seekBar.setMax(255);
        //set the seek bar progress to 1
        seekBar.setKeyProgressIncrement(1);

        try
        {
            //Get the current system brightness
            Settings.System.putInt(cResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            //Get the current system brightness
            brightness = Settings.System.getInt(cResolver, Settings.System.SCREEN_BRIGHTNESS);

        }
        catch (Settings.SettingNotFoundException e)
        {
            //Throw an error case it couldn't be retrieved
            Log.e("Error", "Cannot access system brightness");
            e.printStackTrace();
        }

        //Set the progress of the seek bar based on the system's brightness
        seekBar.setProgress(brightness);

        //Register OnSeekBarChangeListener, so it can actually change values
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                //Set the system brightness using the brightness variable value
                Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightness);
                //Get the current window attributes
                WindowManager.LayoutParams layoutpars = window.getAttributes();
                //Set the brightness of this window
                layoutpars.screenBrightness = brightness / (float)255;
                //Apply attribute changes to this window
                window.setAttributes(layoutpars);

                startActivity(new Intent(MainActivity.this, DummyActivity.class));

            }
            public void onStartTrackingTouch(SeekBar seekBar)
            {
                //Nothing handled here
            }
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                //Set the minimal brightness level
                //if seek bar is 20 or any value below
                if(progress<=20)
                {
                    //Set the brightness to 20
                    brightness=20;
                }
                else //brightness is greater than 20
                {
                    //Set brightness variable based on the progress bar
                    brightness = progress;
                }
                //Calculate the brightness percentage
                float perc = (brightness /(float)255)*100;
                //Set the brightness percentage
                txtPerc.setText((int)perc +" %");
            }
        });
    }
}


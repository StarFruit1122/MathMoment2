package com.example.mathmoment2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       /* MediaPlayer music = MediaPlayer.create(MainActivity.this, R.raw.mmtrailtwo);
        music.start(); */
    }
    public void MazeInfo(View v) {
        Intent i = new Intent(this, RulePage.class); // "this" provides context and refers to the main activity content
        startActivity(i);

    }

    public void Settings(View v) {
        Intent i = new Intent(this, Settings.class); // "this" provides context and refers to the main activity content
        startActivity(i);
    }
}
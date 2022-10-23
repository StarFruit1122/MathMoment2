package com.example.mathmoment2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Settings extends AppCompatActivity {

    boolean TypeOfNumberInteger = true;
    int Range = 10;
    int DifficultyLevel = 1;
    int setGoal = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

       /* MediaPlayer music = MediaPlayer.create(Settings.this, R.raw.mmtrailtwo);
        music.start(); */

        SeekBar goalSetter = (SeekBar) findViewById(R.id.seekBar);
        TextView goal = (TextView) findViewById(R.id.SetWin);

        goalSetter.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                goal.setText("Goal:" + i);
                setGoal = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void Integers(View view) {
        TypeOfNumberInteger = true;
        view.setBackgroundColor(getResources().getColor(R.color.Grey));
        Button decimals = findViewById(R.id.DecimalButton);
      decimals.setBackgroundColor(getResources().getColor(R.color.settingscolor));
    }
    public void Decimals(View view) {
        TypeOfNumberInteger = false;
        view.setBackgroundColor(getResources().getColor(R.color.Grey));
        Button integers = findViewById(R.id.IntegerButton);
        integers.setBackgroundColor(getResources().getColor(R.color.settingscolor));
    }

    public void Ten(View view) {
        Range = 10;
        view.setBackgroundColor(getResources().getColor(R.color.Grey));
        Button twenty = findViewById(R.id.Button20);
        twenty.setBackgroundColor(getResources().getColor(R.color.settingscolor));
        Button fifty = findViewById(R.id.Button50);
        fifty.setBackgroundColor(getResources().getColor(R.color.settingscolor));
    }

    public void Twenty(View view) {
        Range = 20;
        view.setBackgroundColor(getResources().getColor(R.color.Grey));
        Button ten = findViewById(R.id.Button10);
        ten.setBackgroundColor(getResources().getColor(R.color.settingscolor));
        Button fifty = findViewById(R.id.Button50);
        fifty.setBackgroundColor(getResources().getColor(R.color.settingscolor));
    }

    public void Fifty(View view) {
        Range = 50;
        view.setBackgroundColor(getResources().getColor(R.color.Grey));
        Button twenty = findViewById(R.id.Button20);
        twenty.setBackgroundColor(getResources().getColor(R.color.settingscolor));
        Button ten = findViewById(R.id.Button10);
        ten.setBackgroundColor(getResources().getColor(R.color.settingscolor));
    }

    public void Easy(View view) {
        DifficultyLevel = 1;
        view.setBackgroundColor(getResources().getColor(R.color.Grey));
        Button hard = findViewById(R.id.HardButton);
        hard.setBackgroundColor(getResources().getColor(R.color.settingscolor));
        Button medium = findViewById(R.id.MediumButton);
        medium.setBackgroundColor(getResources().getColor(R.color.settingscolor));
    }

    public void Medium(View view) {
        DifficultyLevel = 2;
        view.setBackgroundColor(getResources().getColor(R.color.Grey));
        Button hard = findViewById(R.id.HardButton);
        hard.setBackgroundColor(getResources().getColor(R.color.settingscolor));
        Button easy = findViewById(R.id.EasyButton);
        easy.setBackgroundColor(getResources().getColor(R.color.settingscolor));
    }

    public void Hard(View view) {
        DifficultyLevel = 3;
        view.setBackgroundColor(getResources().getColor(R.color.Grey));
        Button easy = findViewById(R.id.EasyButton);
        easy.setBackgroundColor(getResources().getColor(R.color.settingscolor));
        Button medium = findViewById(R.id.MediumButton);
        medium.setBackgroundColor(getResources().getColor(R.color.settingscolor));
    }

    public void Play(View view) {
        Intent i = new Intent(this, Maze.class);
        if (TypeOfNumberInteger) {
            i.putExtra("TypeOfNumber", true);
        }
        else {
            i.putExtra("TypeOfNumber", false);
        }
        if (Range == 10) {
            i.putExtra("Range", 10);
        }
        else if (Range == 20) {
            i.putExtra("Range", 20);
        }
        else if (Range == 50) {
            i.putExtra("Range", 50);
        }
        if (DifficultyLevel == 1) {
            i.putExtra("DifficultyLevel", 1);
        }
        else if (DifficultyLevel == 2) {
            i.putExtra("DifficultyLevel", 2);
        }
        else if (DifficultyLevel == 3) {
            i.putExtra("DifficultyLevel", 3);
        }
        i.putExtra("setGoal", setGoal);
        startActivity(i);
    }
}
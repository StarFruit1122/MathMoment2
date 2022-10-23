package com.example.mathmoment2;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class WinningPage extends AppCompatActivity {

    boolean typeOfNum = true;
    int rangeOfNum = 10;
    int difficultyLevel = 1;
    int goalSet = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winning_page);
        Intent i = getIntent();
        typeOfNum = i.getBooleanExtra("TypeOfNumber", true);
        rangeOfNum = i.getIntExtra("Range", 10);
        difficultyLevel = i.getIntExtra("DifficultyLevel", 1);
        goalSet = i.getIntExtra("setGoal", 200);

        TextView title = findViewById(R.id.TitleText);
        ImageView smirking = findViewById(R.id.smirkingandroid);
        ImageView skates = findViewById(R.id.skates);

        if (i!= null) {
            if (i.getBooleanExtra("Result", true)) {
                smirking.setVisibility(View.VISIBLE);
                skates.setVisibility(View.INVISIBLE);
                title.setText("YOU WIN");
            }
            else {
                title.setTextSize(60);
                smirking.setVisibility(View.INVISIBLE);
                skates.setVisibility(View.VISIBLE);
                title.setText("You Lost. Try Again");
            }
        }
    }

    public void GoToSettings(View view) {
       // view.setBackgroundColor(getResources().getColor(R.color.Grey));
       Intent a = new Intent(this, Settings.class);
       startActivity(a);
    }

    public void replay(View view) {
        Intent n = new Intent(this, Maze.class);
        n.putExtra("DifficultyLevel", difficultyLevel);
        n.putExtra("Range", rangeOfNum);
        n.putExtra("setGoal", goalSet);
        n.putExtra("TypeOfNumber", typeOfNum);
        startActivity(n);
    }

    public void exit(View view) {
        Intent o = new Intent(this, MainActivity.class);
        startActivity(o);
    }
}
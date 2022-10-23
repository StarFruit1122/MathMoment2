package com.example.mathmoment2;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class RulePage2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule_page2);
    }
    public void ActivateMaze(View view) {
        Intent i = new Intent(this, Maze.class); // "this" provides context and refers to the main activity content
        startActivity(i);
    }
}
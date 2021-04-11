package com.finalproject_cst2335.soccergames;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.finalproject_cst2335.R;

public class SoccerMainActivity extends AppCompatActivity {

    private Button goalBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soccer_main);
        goalBtn = findViewById(R.id.sc_go_btn);
        goalBtn.setBackgroundColor(Color.parseColor("#2d7ceb"));

        goalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToScHome = new Intent(SoccerMainActivity.this,SoccerGameHomePage.class);
                startActivity(goToScHome);
            }
        });
    }
}
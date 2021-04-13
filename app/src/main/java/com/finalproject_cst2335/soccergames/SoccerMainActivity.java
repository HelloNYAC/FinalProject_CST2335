package com.finalproject_cst2335.soccergames;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.finalproject_cst2335.HomePage;
import com.finalproject_cst2335.R;
import com.finalproject_cst2335.Song.SongMainActivity;
import com.finalproject_cst2335.car.CarMainActivity;
import com.finalproject_cst2335.trivia.TriviaMainActivity;

public class SoccerMainActivity extends AppCompatActivity {

    private Button goalBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sc_activity_soccer_main);
        goalBtn = findViewById(R.id.sc_go_btn);
        goalBtn.setBackgroundColor(Color.parseColor("#2d7ceb"));

        Toolbar tg_myToolbar = (Toolbar)findViewById(R.id.scoccer_my_toolbar_m);
        setSupportActionBar(tg_myToolbar);

        goalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToScHome = new Intent(SoccerMainActivity.this,SoccerGameHomePage.class);
                startActivity(goToScHome);
            }
        });

    }

    /**
     * the menu inflater
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.scoccer_main_toolbar_item, menu);
        return true;
    }

    /**
     * opoption selected on toolbar
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String message = null;
        //Look at your menu XML file. Put a case for every id in that file:
        switch(item.getItemId())
        {
            case R.id.hp_homepage:
                message = getResources().getString(R.string.tg_backtomainpage);
                startActivity(new Intent(this, HomePage.class));
                break;
            case R.id.hp_trivia:
                message = getResources().getString(R.string.zz_gototrivia);
                startActivity(new Intent(this, TriviaMainActivity.class));
                break;
            case R.id.hp_song:
                message = getResources().getString(R.string.zz_gotosong);
                startActivity(new Intent(this, SongMainActivity.class));
                break;
            case R.id.hp_car:
                message = getResources().getString(R.string.zz_gotocar);
                startActivity(new Intent(this, CarMainActivity.class));
                break;
            //what to do when the menu item is selected:

        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        return true;
    }

}
package com.finalproject_cst2335;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import com.finalproject_cst2335.Song.SongMainActivity;
import com.finalproject_cst2335.car.CarMainActivity;
import com.finalproject_cst2335.soccergames.SoccerMainActivity;
import com.finalproject_cst2335.trivia.TriviaMainActivity;
import com.google.android.material.navigation.NavigationView;

public class HomePage extends AppCompatActivity {
    private Toolbar tb;
    private DrawerLayout drawL;
    private NavigationView navView;
    private ActionBarDrawerToggle toggler;
    private ImageView trivia,soccer,songsterr,car;

    /**
     * oncreate method of the whole project
     * to setview of the activity
     * to start jew activity on click of different images and toolbar icons
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hp_activity_home_page);
        tb = findViewById(R.id.home_tb);
        setSupportActionBar(tb);

        trivia =findViewById(R.id.hmimgtivia);
        trivia.setOnClickListener(v -> {
            Intent goToTriviaGame = new Intent(HomePage.this, TriviaMainActivity.class);
            startActivity(goToTriviaGame);
        });

        soccer =findViewById(R.id.hmimgsoccer);
        soccer.setOnClickListener(v -> {
            Intent goToSoccerGame = new Intent(HomePage.this, SoccerMainActivity.class);
            startActivity(goToSoccerGame);
        });

        car = findViewById(R.id.hmimgcar);
        car.setOnClickListener(v -> {
            Intent goToCarGame = new Intent(HomePage.this, CarMainActivity.class);
            startActivity(goToCarGame);
        });

        songsterr = findViewById(R.id.hmimgsong);
        songsterr.setOnClickListener(v -> {
                    Intent goToSongster = new Intent(HomePage.this, SongMainActivity.class);
                    startActivity(goToSongster);
        });

        drawL = findViewById(R.id.drawerL);
        navView = findViewById(R.id.navView);

        toggler = new ActionBarDrawerToggle(this, drawL, R.string.on, R.string.off);
        drawL.addDrawerListener(toggler);

        //Enable the drawer to open and close
//        getActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if( item.getItemId() == R.id.nav_toSoccerGame){
                    Intent goToSoccerGame = new Intent(HomePage.this, SoccerMainActivity.class);
                    startActivity(goToSoccerGame);
                }

                return false;
            }
        });
    }

    /**
     * opoption to load menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.tb_buttons,menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * opoption selected on toolbar
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggler.onOptionsItemSelected(item)) {
            return true;
        }

        if (item.getItemId() == R.id.toTrivia) {
            Intent goToTriviaGame = new Intent(HomePage.this, TriviaMainActivity.class);
            startActivity(goToTriviaGame);
        }

        if (item.getItemId() == R.id.toSongster) {
            Intent goToSongster = new Intent(HomePage.this, SongMainActivity.class);
            startActivity(goToSongster);
        }
        if( item.getItemId() == R.id.toSoccerGame){
            Intent goToSoccerGame = new Intent(HomePage.this, SoccerMainActivity.class);
            startActivity(goToSoccerGame);
        }

         if( item.getItemId() == R.id.toCar){
             Intent goToCar = new Intent(HomePage.this, CarMainActivity.class);
             startActivity(goToCar);
         }

            return super.onOptionsItemSelected(item);
        }

    /**
     * onpost crete to save instancestate
     * @param savedInstanceState
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggler.syncState();
    }

    /**
     * on configure method
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        toggler.onConfigurationChanged(newConfig);
    }
}

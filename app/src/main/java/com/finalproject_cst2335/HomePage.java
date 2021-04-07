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
import android.view.View;
import android.widget.ImageView;

import com.finalproject_cst2335.Song.SongMainActivity;
import com.google.android.material.navigation.NavigationView;

public class HomePage extends AppCompatActivity {
    private Toolbar tb;
    private DrawerLayout drawL;
    private NavigationView navView;
    private ActionBarDrawerToggle toggler;
    private ImageView trivia,soccer,songsterr,car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        tb = findViewById(R.id.home_tb);
        setSupportActionBar(tb);

//        trivia =findViewById(R.id.hmimgtivia);
//        trivia.setOnClickListener(v -> {
//            Intent goToSoccerGame = new Intent(HomePage.this, TriviaMainActivity.class);
//            startActivity(goToSoccerGame);
//        });
//
//        soccer =findViewById(R.id.hmimgsoccer);
//        trivia.setOnClickListener(v -> {
//            Intent goToSoccerGame = new Intent(HomePage.this, SoccerGameHomePage.class);
//            startActivity(goToSoccerGame);
//        });
//
//        car = findViewById(R.id.hmimgcar);
//        trivia.setOnClickListener(v -> {
//            Intent goToCarGame = new Intent(HomePage.this, CarGameHomePage.class);
//            startActivity(goToCarGame);
//        });
//
        songsterr = findViewById(R.id.hmimgsong);
        songsterr.setOnClickListener(v -> {
                    Intent goToSoccerGame = new Intent(HomePage.this, SongMainActivity.class);
                    startActivity(goToSoccerGame);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.tb_buttons,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggler.onOptionsItemSelected(item)){
            return true;
        }

//        if( item.getItemId() == R.id.toSoccerGame){
//            Intent goToSoccerGame = new Intent(HomePage.this, SoccerGameHomePage.class);
//            startActivity(goToSoccerGame);
//        }
//
//        if( item.getItemId() == R.id.toTrivia){
//            Intent goToSoccerGame = new Intent(HomePage.this, TriviaMainActivity.class);
//            startActivity(goToSoccerGame);
//        }

        if( item.getItemId() == R.id.toSongster){
            Intent goToSoccerGame = new Intent(HomePage.this, SongMainActivity.class);
            startActivity(goToSoccerGame);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggler.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        toggler.onConfigurationChanged(newConfig);
    }
}

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
import com.finalproject_cst2335.car.CarMainActivity;
import com.google.android.material.navigation.NavigationView;

public class HomePage extends AppCompatActivity {
    private Toolbar tb;
    private DrawerLayout drawL;
    private NavigationView navView;
    private ActionBarDrawerToggle toggler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        tb = findViewById(R.id.home_tb);
        setSupportActionBar(tb);

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

        if( item.getItemId() == R.id.toCar){
            Intent goToCar = new Intent(HomePage.this, CarMainActivity.class);
            startActivity(goToCar);
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

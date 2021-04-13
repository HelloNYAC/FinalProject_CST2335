package com.finalproject_cst2335.trivia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.finalproject_cst2335.HomePage;
import com.finalproject_cst2335.R;
import com.finalproject_cst2335.Song.SongMainActivity;
import com.finalproject_cst2335.car.CarMainActivity;
import com.finalproject_cst2335.soccergames.SoccerMainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class TriviaMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener{
    private SharedPreferences tg_pref_name;
    EditText tg_nameInput;

    /**
     * This is the oncreate method of the main activity
     * set up imageview with pic
     * allow use to select questions with 3 spinners
     * define the toolbar and navigation drawer
     * Toast and Snack message
     * AlertDialog
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tg_activity_main);

        tg_nameInput = findViewById(R.id.enter_nm_m);
        tg_pref_name = getSharedPreferences("p_name", Context.MODE_PRIVATE);
        String namedef = tg_pref_name.getString("p_name", "");
        tg_nameInput.setText(namedef);

        Button tg_playNow = findViewById(R.id.q_go_btn_m);
        Intent tg_GoToPlay = new Intent(this, TriviaGamePickActivity.class);
        tg_playNow.setOnClickListener(click->{
            tg_GoToPlay.putExtra("p_name", namedef);
            startActivity(tg_GoToPlay);
        });

        Toolbar tg_myToolbar = (Toolbar)findViewById(R.id.tg_my_toolbar_m);
        setSupportActionBar(tg_myToolbar);

        DrawerLayout tg_drawer = findViewById(R.id.tg_drawer_layout_m);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                tg_drawer, tg_myToolbar, R.string.tg_open, R.string.tg_close);
        tg_drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView tg_navigationView = findViewById(R.id.tg_nav_view_m);
        tg_navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView tg_bottomNav = findViewById(R.id.tg_bnv_m);
        tg_bottomNav.setOnNavigationItemSelectedListener(this);
    }

    /**
     * shared preference of the user input name
     */
    @Override
    protected void onPause() {
        super.onPause();
        tg_pref_name = getSharedPreferences("p_name", Context.MODE_PRIVATE);
        SharedPreferences.Editor editorName = tg_pref_name.edit();
        String nameInput = tg_nameInput.getText().toString();
        editorName.putString("p_name", nameInput);
        editorName.commit();
    }

    /**
     * the menu inflater
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tg_main_activity_actions, menu);
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
            case R.id.hp_song:
                message = getResources().getString(R.string.zz_gotosong);
                startActivity(new Intent(this, SongMainActivity.class));
                break;
            case R.id.hp_soccer:
                message = getResources().getString(R.string.zz_gotosoccer);
                startActivity(new Intent(this, SoccerMainActivity.class));
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

    /**
     * opoption selected on navigation drawer and bottom nav bar
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected( @NonNull MenuItem item) {
        String message = null;
        switch(item.getItemId())
        {
            case R.id.tg_homepage:
                message = getResources().getString(R.string.tg_backtomainpage);
                startActivity(new Intent(this, HomePage.class));
                break;
            case R.id.tg_ranking:
                message = getResources().getString(R.string.tg_ranking);
                Bundle btoRank = new Bundle();
                btoRank.putString("toolbarToRank", "toolbarToRank");
                Intent i_rk = new Intent();
                i_rk.putExtras(btoRank);
                i_rk.setClass(this, TriviaRankingPage.class);
                startActivity(i_rk);
                break;
            case R.id.tg_playagain:
                message = getResources().getString(R.string.tg_playagain);
                startActivity(new Intent(this, TriviaMainActivity.class));
                break;
            case R.id.tg_help:
                message = getResources().getString(R.string.tg_help);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle(getString(R.string.tg_alert_instr1))
                        .setMessage(getString(R.string.tg_alert_instr2)+ "\n"
                                + getString(R.string.tg_alert_instr3)+ "\n"
                                + getString(R.string.tg_alert_instr4)+ "\n"
                                + getString(R.string.tg_alert_instr5)+ "\n" + "\n"
                                + getString(R.string.tg_alert_instr_ques)+ "\n" )
                        .setPositiveButton(getString(R.string.tg_close), (click, arg) -> {
                            startActivity(new Intent(this, TriviaGamePickActivity.class));
                        })
                        .create().show();
                break;
        }

        DrawerLayout drawerLayout = findViewById(R.id.tg_drawer_layout_m);
        drawerLayout.closeDrawer(GravityCompat.START);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        return true;
    }
}

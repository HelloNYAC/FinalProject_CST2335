package com.finalproject_cst2335.Song;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.finalproject_cst2335.HomePage;
import com.finalproject_cst2335.R;
import com.finalproject_cst2335.car.CarMainActivity;
import com.finalproject_cst2335.soccergames.SoccerMainActivity;
import com.finalproject_cst2335.trivia.TriviaMainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

/**
 * This is the main Class which is let user input the artist name
 */
public class SongMainActivity extends AppCompatActivity {
    private static final String NAME_KEY="nameKey";
    private static final String HISTORY="history";
    private SharedPreferences ss_pref;
    private EditText ss_search;
    private Button ss_gobutton;
    private String song_snackMsg="search Artist name";
    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_activity_song_main);

        ss_search = findViewById(R.id.ss_inputname);
        ss_gobutton = findViewById(R.id.ss_gobt);
        ss_pref = getSharedPreferences("name_searched", Context.MODE_PRIVATE);
        String ss_nameVal = ss_pref.getString("name_searched","");
        ss_search.setText(ss_nameVal);

        Toolbar tg_myToolbar = (Toolbar)findViewById(R.id.song_my_toolbar_m);
        setSupportActionBar(tg_myToolbar);

        Toast.makeText(this,getResources().getString(R.string.song_welcome),Toast.LENGTH_LONG).show();
        ss_gobutton.setOnClickListener(v -> {
            String nameSearched = ss_search.getText().toString();
            TextView snack =findViewById(R.id.ss_snack);
            Snackbar.make(snack, getResources().getString(R.string.song_search_art), Snackbar.LENGTH_SHORT).show();
            Intent goToSearch = new Intent(SongMainActivity.this, SongSearchActivity.class);
            goToSearch.putExtra("NAME", nameSearched);
            startActivity(goToSearch);
        });

       }


    @Override
    protected void onPause() {
        super.onPause();
        ss_pref = getSharedPreferences("name_searched",Context.MODE_PRIVATE);
        SharedPreferences.Editor ss_editor = ss_pref.edit();
        String ss_nameSearched = ss_search.getText().toString();
        ss_editor.putString("name_searched",ss_nameSearched);
        ss_editor.commit();

    }

    /**
     * the menu inflater
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.song_main_toolbar_item, menu);
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


}
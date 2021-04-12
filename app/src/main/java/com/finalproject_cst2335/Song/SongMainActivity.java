package com.finalproject_cst2335.Song;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.finalproject_cst2335.R;
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

        Toast.makeText(this,"Welcome to Songster",Toast.LENGTH_LONG).show();
        ss_gobutton.setOnClickListener(v -> {
            String nameSearched = ss_search.getText().toString();
            TextView snack =findViewById(R.id.ss_snack);
            Snackbar.make(snack, "Search the artist name", Snackbar.LENGTH_SHORT).show();
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


}
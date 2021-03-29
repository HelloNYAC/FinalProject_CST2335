package com.finalproject_cst2335.soccergames;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ListView;

import com.finalproject_cst2335.R;

public class FavSoccerGames extends AppCompatActivity {

    private Toolbar tb;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_soccer_games);

        tb = findViewById(R.id.sc_fav_page_tb);
        setSupportActionBar(tb);

        lv = findViewById(R.id.sc_fav_page_list);
    }
}
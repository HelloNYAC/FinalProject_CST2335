package com.finalproject_cst2335.Song;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.finalproject_cst2335.R;

public class SongEmptyActivty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_empty_activty);
        Bundle song_dataToPass = getIntent().getExtras(); //get the data that was passed from FragmentExample

        //This is copied directly from FragmentExample.java lines 47-54
        SongDetailsFragment dFragment = new SongDetailsFragment();
        dFragment.setArguments( song_dataToPass ); //pass data to the the fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.Song_fragmentLocation, dFragment)
                .commit();
    }
}
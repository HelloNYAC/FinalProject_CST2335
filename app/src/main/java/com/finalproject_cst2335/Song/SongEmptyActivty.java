package com.finalproject_cst2335.Song;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.finalproject_cst2335.R;

import java.sql.SQLException;

public class SongEmptyActivty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_empty_activty);
        Bundle song_dataToPass = getIntent().getExtras(); //get the data that was passed from FragmentExample
        Bundle song_dataToRemove = getIntent().getExtras();
        Log.e("song_dataToPass", "onCreate: " + song_dataToPass.get("TO_SAVE") );
        Log.e("song_dataToRemove", "onCreate: " + song_dataToPass.get("TO_REMOVE") );


        //This is copied directly from FragmentExample.java lines 47-54

        SongDetailsFragment dFragment = new SongDetailsFragment();

        if(song_dataToPass.containsKey("TO_SAVE") && song_dataToPass.get("TO_REMOVE") == null ){
            dFragment.setArguments( song_dataToPass); //pass data to the the fragment
        }else if(song_dataToPass.containsKey("TO_REMOVE") && song_dataToPass.get("TO_SAVE") == null ){
            dFragment.setArguments( song_dataToRemove); //pass data to the the fragment
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.Song_fragmentLocation, dFragment)
                .commit();
    }

}
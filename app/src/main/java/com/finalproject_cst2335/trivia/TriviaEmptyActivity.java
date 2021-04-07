package com.finalproject_cst2335.trivia;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.finalproject_cst2335.R;


public class TriviaEmptyActivity extends AppCompatActivity {
    @Override

    /**
     * To pass bundle with data to fragement
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tg_acitivity_empty);

        Bundle tg_dataToPass = getIntent().getExtras(); //get the data that was passed from FragmentExample

        //This is copied directly from FragmentExample.java lines 47-54
        TriviaDetailsFragment tg_dFragment = new TriviaDetailsFragment();
        tg_dFragment.setArguments( tg_dataToPass ); //pass data to the the fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.tg_fragmentLocation, tg_dFragment)
                .commit();
    }
}

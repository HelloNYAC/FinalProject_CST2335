package com.finalproject_cst2335.Song;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.finalproject_cst2335.R;
import com.google.android.material.snackbar.Snackbar;

public class SongMainActivity extends AppCompatActivity {
    private static final String NAME_KEY="nameKey";
    private static final String HISTORY="history";
    private SharedPreferences pref;
    private EditText search;
    private Button gobutton;
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_main);

        pref = getSharedPreferences("name", Context.MODE_PRIVATE);
        search = findViewById(R.id.ss_inputname);
        gobutton = findViewById(R.id.ss_gobt);

//
//        String aname = sp.getString("name","");
//        search.setText(aname);

        Toast.makeText(this,"This is a Toast message",Toast.LENGTH_LONG).show();
        gobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameSearched = search.getText().toString();


                Intent goToSearch = new Intent(SongMainActivity.this, SongSearchActivity.class);
                goToSearch.putExtra("NAME", nameSearched);

                startActivity(goToSearch);

            }
        });



       }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = pref.edit();

        editor.commit();

    }


}
package com.finalproject_cst2335.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.finalproject_cst2335.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class TriviaMainActivity extends AppCompatActivity {
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    private EditText nameText;
    private Button qest_go;
    private Spinner spinner1, spinner2, spinner3;
    private String question_num, question_type, question_level;
    private ArrayList<TriviaQuestion> questList = new ArrayList<>();
    String TriviaURL;
    private SharedPreferences tg_pref_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(ACTIVITY_NAME, "In function" + "onCreate");

        setContentView(R.layout.tg_activity_main);

        spinner2 = (Spinner) findViewById(R.id.q_typ_btn);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                question_type = parent.getItemAtPosition(position).toString();
                Log.e("====TAG", "question_type: " + question_type );
                Snackbar.make(spinner2, "You selected " + question_type, Snackbar.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String[] spinList2 = getResources().getStringArray(R.array.spin_qust_typ);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinList2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        spinner1 = (Spinner) findViewById(R.id.q_num_btn);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                question_num = parent.getItemAtPosition(position).toString();
                Log.e("====TAG", "question_num: " + question_num );
                Snackbar.make(spinner1, "You selected " + question_num, Snackbar.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String[] spinList1 = getResources().getStringArray(R.array.spin_qust_num);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinList1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);

        spinner3 = findViewById(R.id.q_lvl_btn);
//        spinner3.setOnItemSelectedListener(this);

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                question_level = parent.getItemAtPosition(position).toString();

                Log.e("====TAG", "question_level: " + question_level );
                Snackbar.make(spinner3, "You selected " + question_level, Snackbar.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String[] spinList3 = getResources().getStringArray(R.array.spin_qust_lvl);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinList3);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);

        nameText = findViewById(R.id.enter_nm);
        tg_pref_name = getSharedPreferences("player_name", Context.MODE_PRIVATE);
        String namedef = tg_pref_name.getString("player_name", "");
        nameText.setText(namedef);

        qest_go = findViewById(R.id.q_go_btn);

        Intent GoToChalg = new Intent(TriviaMainActivity.this, TriviaPlayRoomActivity.class);

        qest_go.setOnClickListener(click -> {

            if (question_type.equals("True and False")) {
                TriviaURL = "https://opentdb.com/api.php?amount=" + question_num + "&difficulty=" + question_level.toLowerCase() + "&type=boolean";
                Log.e("======1===", "onCreate: " + TriviaURL );
            }
            if (question_type.equals("Multiple Choice")) {
                TriviaURL = "https://opentdb.com/api.php?amount=" + question_num + "&difficulty=" + question_level.toLowerCase() + "&type=multiple";
                Log.e("======2===", "onCreate: " + TriviaURL );
            }
            if (question_type.equals("Mixed")) {
                TriviaURL = "https://opentdb.com/api.php?amount=" + question_num + "&difficulty=" + question_level.toLowerCase();
                Log.e("======3===", "onCreate: " + TriviaURL );
            }

            Toast.makeText(this, "Loading Your Game....", Toast.LENGTH_LONG).show();
            GoToChalg.putExtra("TriviaURL", TriviaURL)
                .putExtra("question_level", question_level)
                .putExtra("player_name", nameText.getText().toString() );

            startActivity(GoToChalg);
        });
    }

    }
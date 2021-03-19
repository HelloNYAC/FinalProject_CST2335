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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TriviaGamePickActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    private EditText nameText;
    private Button qest_go;
    private Spinner spinner1, spinner2, spinner3;
    private String question_num, question_type, question_level;
    SharedPreferences pref_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(ACTIVITY_NAME, "In function" + "onCreate");

        setContentView(R.layout.tg_activity_game_pick);

        spinner1 = (Spinner)findViewById(R.id.q_num_btn);
        spinner1.setOnItemSelectedListener(this);
        String[] spinList1 = getResources().getStringArray(R.array.spin_qust_num);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinList1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);


        spinner2 = (Spinner)findViewById(R.id.q_typ_btn);
        spinner2.setOnItemSelectedListener(this);
        String[] spinList2 = getResources().getStringArray(R.array.spin_qust_typ);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinList2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        spinner3 = (Spinner)findViewById(R.id.q_lvl_btn);
        spinner3.setOnItemSelectedListener(this);
//        spinner3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String difficulty = parent.getItemAtPosition(position).toString();
//                //lowercase
//            }
//
//            //onNothing
//
//        });

        String[] spinList3 = getResources().getStringArray(R.array.spin_qust_lvl);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinList3);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);


        qest_go = findViewById(R.id.q_go_btn);
        Intent GoToChalg = new Intent(TriviaGamePickActivity.this, TriviaChatRoomActivity.class);
        qest_go.setOnClickListener(click -> {
            GoToChalg.putExtra("question_num", question_num);
            GoToChalg.putExtra("question_type", question_type);
            GoToChalg.putExtra("question_level", question_level);
            startActivity(GoToChalg);
        });

        nameText = findViewById(R.id.enter_nm);

        Intent fromMain = getIntent();
        nameText.setText(fromMain.getStringExtra("name"));
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (parent.getId()) {
            case R.id.qust_num:
                question_num = parent.getItemAtPosition(position).toString();
                Toast.makeText(TriviaGamePickActivity.this, "Question Number Picked", Toast.LENGTH_LONG).show();
                pref_num = getSharedPreferences("question_num", Context.MODE_PRIVATE);
                String numdef = pref_num.getString("question_num", "");

                break;
            case R.id.qust_typ:
                question_type = parent.getItemAtPosition(position).toString();
                Toast.makeText(TriviaGamePickActivity.this, "Question Type Picked", Toast.LENGTH_LONG).show();
                break;
            case R.id.qust_lvl:
                question_level = parent.getItemAtPosition(position).toString();
                Toast.makeText(TriviaGamePickActivity.this, "Question Level Picked", Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
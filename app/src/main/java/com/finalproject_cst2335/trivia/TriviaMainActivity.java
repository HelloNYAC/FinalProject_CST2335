package com.finalproject_cst2335.trivia;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.finalproject_cst2335.R;


public class TriviaMainActivity extends AppCompatActivity {
    private SharedPreferences pref_email, pref_name;
    private EditText enterEmail, enterName;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tg_activity_main);

        loginButton = findViewById(R.id.loginbutton);
        enterEmail = findViewById(R.id.Email_ph);
        pref_email = getSharedPreferences("email", Context.MODE_PRIVATE);
        String emaildef = pref_email.getString("email", "");

        enterName = findViewById(R.id.tg_name_ph);
        pref_name = getSharedPreferences("name", Context.MODE_PRIVATE);
        String namedef = pref_name.getString("name", "");
        enterEmail.setText(namedef);

        Intent goToProfile = new Intent(TriviaMainActivity.this, TriviaGamePickActivity.class);
        loginButton.setOnClickListener(click ->
        {
            goToProfile.putExtra("email", enterEmail.getText().toString())
                    .putExtra("name", enterName.getText().toString());
            startActivity(goToProfile);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override


    protected void onPause() {
        super.onPause();
        pref_email = getSharedPreferences("email", Context.MODE_PRIVATE);
        SharedPreferences.Editor editorEmail = pref_email.edit();
        String nameText = enterName.getText().toString();
        String emailText = enterEmail.getText().toString();

        editorEmail.putString("email", emailText);
        editorEmail.commit();
    }
}


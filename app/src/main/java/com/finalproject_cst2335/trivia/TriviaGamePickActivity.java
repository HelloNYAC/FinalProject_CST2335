package com.finalproject_cst2335.trivia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.finalproject_cst2335.HomePage;
import com.finalproject_cst2335.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class TriviaGamePickActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener{
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    private EditText nameText;
    private Button qest_go;
    private Spinner spinner1, spinner2, spinner3;
    private String question_num, question_type, question_level;
    private ArrayList<TriviaQuestion> questList = new ArrayList<>();
    String TriviaURL;
    private SharedPreferences tg_pref_name;
    public static final int RESULT_CODE = 500;
    Intent backToHomePage = new Intent();

    /**
     * This is the main page for user to set up the parameter of URL
     * allow use to select questions with 3 spinners
     * define the toolbar and navigation drawer
     * Toast and Snack message
     * AlertDialog
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(ACTIVITY_NAME, "In function" + "onCreate");

        setContentView(R.layout.tg_activity_game_pick);

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

        Toolbar tg_myToolbar = (Toolbar)findViewById(R.id.tg_my_toolbar);
        setSupportActionBar(tg_myToolbar);

        BottomNavigationView tg_bottomNav = findViewById(R.id.tg_bnv);
        tg_bottomNav.setOnNavigationItemSelectedListener(this);

        nameText = findViewById(R.id.enter_nm);
        Intent tg_namefhome = getIntent();
        nameText.setText(tg_namefhome.getStringExtra("p_name"));

        qest_go = findViewById(R.id.q_go_btn);

        Intent GoToChalg = new Intent(TriviaGamePickActivity.this, TriviaPlayRoomActivity.class);

        qest_go.setOnClickListener(click -> {

            if (question_type.equals("True and False")) {
                TriviaURL = "https://opentdb.com/api.php?amount=" + question_num + "&difficulty=" + question_level.toLowerCase() + "&type=boolean";
//                Log.e("======1===", "onCreate: " + TriviaURL );
            }
            if (question_type.equals("Multiple Choice")) {
                TriviaURL = "https://opentdb.com/api.php?amount=" + question_num + "&difficulty=" + question_level.toLowerCase() + "&type=multiple";
//                Log.e("======2===", "onCreate: " + TriviaURL );
            }
            if (question_type.equals("Mixed")) {
                TriviaURL = "https://opentdb.com/api.php?amount=" + question_num + "&difficulty=" + question_level.toLowerCase();
//                Log.e("======3===", "onCreate: " + TriviaURL );
            }

            Toast.makeText(this, "Loading Your Game....", Toast.LENGTH_LONG).show();
            GoToChalg.putExtra("TriviaURL", TriviaURL)
                .putExtra("question_level", question_level)
                .putExtra("player_name", nameText.getText().toString() );

            startActivity(GoToChalg);
        });
    }

    /**
     * the menu inflater
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tg_main_activity_actions, menu);
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
            //what to do when the menu item is selected:
            case R.id.tg_homepage:
                message = getResources().getString(R.string.tg_backtomainpage);
                startActivity(new Intent(this, HomePage.class));
                break;
            case R.id.tg_ranking:
                message = getResources().getString(R.string.tg_ranking);
                Bundle btoRank = new Bundle();
                btoRank.putString("toolbarToRank", "toolbarToRank");
                Intent i_rk = new Intent();
                i_rk.putExtras(btoRank);
                i_rk.setClass(TriviaGamePickActivity.this, TriviaRankingPage.class);
                startActivity(i_rk);
                break;
            case R.id.tg_playagain:
                message = getResources().getString(R.string.tg_playagain);
                startActivity(new Intent(this, TriviaMainActivity.class));
                break;
            case R.id.tg_help:
                message = getResources().getString(R.string.tg_help);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle(getString(R.string.tg_alert_instr1))
                        .setMessage(getString(R.string.tg_alert_instr2)+ "\n"
                                + getString(R.string.tg_alert_instr3)+ "\n"
                                + getString(R.string.tg_alert_instr4)+ "\n"
                                + getString(R.string.tg_alert_instr5)+ "\n" + "\n"
                                + getString(R.string.tg_alert_instr_ques)+ "\n" )
                        .setPositiveButton(getString(R.string.tg_close), (click, arg) -> {
                            startActivity(new Intent(this, TriviaGamePickActivity.class));
                        })
                        .create().show();
                break;
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        return true;
    }

    /**
     * opoption selected on navigation drawer and bottom nav
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected( @NonNull MenuItem item) {
        String message = null;
        switch(item.getItemId())
        {
            //what to do when the menu item is selected:
            case R.id.tg_homepage:
                message = getResources().getString(R.string.tg_backtomainpage);
                startActivity(new Intent(this, HomePage.class));
                break;
            case R.id.tg_ranking:
                message = getResources().getString(R.string.tg_ranking);
                Bundle btoRank = new Bundle();
                btoRank.putString("toolbarToRank", "toolbarToRank");
                Intent i_rk = new Intent();
                i_rk.putExtras(btoRank);
                i_rk.setClass(TriviaGamePickActivity.this, TriviaRankingPage.class);
                startActivity(i_rk);
                break;
            case R.id.tg_playagain:
                message = getResources().getString(R.string.tg_playagain);
                startActivity(new Intent(this, TriviaMainActivity.class));
                break;
            case R.id.tg_help:
                message = getResources().getString(R.string.tg_help);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle(getString(R.string.tg_alert_instr1))
                        .setMessage(getString(R.string.tg_alert_instr2)+ "\n"
                                + getString(R.string.tg_alert_instr3)+ "\n"
                                + getString(R.string.tg_alert_instr4)+ "\n"
                                + getString(R.string.tg_alert_instr5)+ "\n"
                                + getString(R.string.tg_alert_instr_ques)+ "\n" )
                        .setPositiveButton(getString(R.string.tg_close), (click, arg) -> {
                            startActivity(new Intent(this, TriviaGamePickActivity.class));
                        })
                        .create().show();
                break;
        }

        DrawerLayout drawerLayout = findViewById(R.id.tg_drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        return true;
    }
}
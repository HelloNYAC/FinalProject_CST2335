package com.finalproject_cst2335.car;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.finalproject_cst2335.R;
import com.google.android.material.navigation.NavigationView;

public class CarMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    LinearLayout reletivelayoutone;
    RelativeLayout toreplace;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    EditText car_make_name;
    Button search;
    TextView last_search_results;
    private Toolbar tb;
    private NavigationView sidenavigationview;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_activity_car_main);
        viewinnitilisers();

        last_search_results.setOnClickListener(v -> car_make_name.setText(last_search_results.getText().toString()));
        search.setOnClickListener(v -> {
            if (car_make_name.getText().toString().isEmpty()) {
                Toast.makeText(CarMainActivity.this, "fill_fields", Toast.LENGTH_LONG).show();

            } else {
                Intent intent = new Intent(CarMainActivity.this, CarListsView.class);
                intent.putExtra("car", car_make_name.getText().toString());
                startActivity(intent);
            }
        });

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);// it will set layout direction
        sidenavigationview = findViewById(R.id.side_navigation);// to get side navigation view by id
        sidenavigationview.setNavigationItemSelectedListener(this);//it will get the item selected on side navigation optinos
        drawerLayout = findViewById(R.id.drawerlayout);//it will get side navigation by id
        drawerLayout.setBackgroundColor(Color.parseColor("#ffffff"));// it will set navigation color
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);// it will show side navigation tool bar on action bar
        drawerLayout.addDrawerListener(actionBarDrawerToggle);// it it set on click listeners
        actionBarDrawerToggle.syncState();// it is for sync
        tb = findViewById(R.id.carMain_tb);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //tb.setNavigationIcon();
    }

    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        int id = item.getItemId();
        switch (id) {

            case R.id.iconSearchArtist:
                startActivity(new Intent(getApplicationContext(), CarMainActivity.class));
                break;
            case R.id.ShowFavrouties:
                startActivity(new Intent(getApplicationContext(), CarSavedListsView.class));
                break;
            case R.id.help:
                String url = "https://www.autotrader.ca/cars/?mdl=accord&make=honda&loc=K2G1V8, ";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;

            case R.id.help_fragment:
                fragment = new CarHelpFragment();
                reletivelayoutone = findViewById(R.id.reletivelayoutone);
                reletivelayoutone.setVisibility(View.GONE);
                toreplace.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void viewinnitilisers() {
        search = findViewById(R.id.search);
        car_make_name = findViewById(R.id.car_make_name);
        toreplace = findViewById(R.id.toreplace);
        last_search_results = findViewById(R.id.last_search_results);
    }

    public void savedata() {
        SharedPreferences.Editor editor = getSharedPreferences("Shared preferences", MODE_PRIVATE).edit();
        editor.putString("name", car_make_name.getText().toString());
        editor.apply();
    }

    public void loaddata() {
        SharedPreferences prefs = getSharedPreferences("Shared preferences", MODE_PRIVATE);
        String name = prefs.getString("name", "No name defined");//"No name defined" is the default value.
        last_search_results.setText(name);
    }

    @Override
    protected void onPause() {
        super.onPause();
        savedata();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loaddata();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loaddata();
    }
}



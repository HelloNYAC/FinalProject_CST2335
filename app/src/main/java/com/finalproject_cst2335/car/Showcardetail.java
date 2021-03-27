package com.finalproject_cst2335.car;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.finalproject_cst2335.R;
import com.google.android.material.snackbar.Snackbar;

public class Showcardetail extends AppCompatActivity {
    View linear_View;
    //views and variables declarations
    String id;
    String Model_name, Make_name, Model_ID, Make_ID;
    EditText postal;
    TextView tv_country_name, tv_province, tv_cases, tv_date, Add_to_Favorites;
    CarsDB carsDB;
    Button findcar;
    int ide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showcountrydetail);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        findcar = findViewById(R.id.findcar);
        postal = findViewById(R.id.postal);
        linear_View = findViewById(R.id.linear_View);
        findcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (postal.getText().toString().isEmpty() || postal.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(Showcardetail.this, "Please Enter postal code!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String url = "https://www.autotrader.ca/cars/?mdl=" + Make_name + "&make=" + Model_name + "&loc=" + postal.getText().toString().trim() + ",";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        //Views Initializer
        Add_to_Favorites = findViewById(R.id.Add_to_Favorites);
        tv_country_name = findViewById(R.id.tv_country_name);
        tv_province = findViewById(R.id.tv_province);
        tv_date = findViewById(R.id.tv_date);
        tv_cases = findViewById(R.id.tv_cases);
        carsDB = new CarsDB(Showcardetail.this);

        //This Action will send data to the database
        Add_to_Favorites.setOnClickListener(v -> {
            Snackbar.make(linear_View, R.string.adder, Snackbar.LENGTH_SHORT).show();
            carsDB.insertCountry(new cars(Integer.parseInt(Make_ID), Make_name, Integer.parseInt(Model_ID), Model_name));
        });
        try {
            Intent intent = getIntent();

            Make_ID = intent.getStringExtra("id");
            Make_name = intent.getStringExtra("name");
            Model_ID = intent.getStringExtra("makeID");
            Model_name = intent.getStringExtra("makeName");

            tv_country_name.setText(Make_ID);
            tv_province.setText(Make_name);
            tv_cases.setText(Model_ID);
            tv_date.setText(Model_name);
        } catch (Exception e) {
            Toast.makeText(Showcardetail.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
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
import androidx.appcompat.widget.Toolbar;

import com.finalproject_cst2335.R;
import com.google.android.material.snackbar.Snackbar;

public class CarListsDetail extends AppCompatActivity {
    View linear_View;
    //views and variables declarations
    String id;
    String Model_name, Make_name, Model_ID, Make_ID;
    EditText postal;
    TextView tv_make_id, tv_make_name, tv_model_id, tv_model_name;
    CarsDB carsDB;
    Button btn_shopcar, btn_view_detail, btn_add_to_favorites;
    int ide;
    private Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_lists_detail_fragment);
        //tb = findViewById(R.id.car_searchResults_tb);
        //setSupportActionBar(tb);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Views Initializer
        btn_add_to_favorites = findViewById(R.id.Add_to_Favorites);
        tv_make_id = findViewById(R.id.tv_make_id);
        tv_make_name = findViewById(R.id.tv_make_name);
        tv_model_name = findViewById(R.id.tv_model_name);
        tv_model_id = findViewById(R.id.tv_model_id);
        carsDB = new CarsDB(CarListsDetail.this);
        btn_shopcar = findViewById(R.id.shopcar);
        //postal = findViewById(R.id.postal);
        linear_View = findViewById(R.id.linear_View);
        btn_view_detail = findViewById(R.id.view_car_detail);

        try {
            Intent intent = getIntent();

            Make_ID = intent.getStringExtra("id");
            Make_name = intent.getStringExtra("name");
            Model_ID = intent.getStringExtra("makeID");
            Model_name = intent.getStringExtra("makeName");

            tv_make_id.setText(Make_ID);
            tv_make_name.setText(Make_name);
            tv_model_id.setText(Model_ID);
            tv_model_name.setText(Model_name);
        } catch (Exception e) {
            Toast.makeText(CarListsDetail.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        btn_view_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://www.google.com/search?q=" + Make_name + "+" + Model_name;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        btn_shopcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                if (postal.getText().toString().isEmpty() || postal.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(CarListsDetail.this, "Please Enter postal code!", Toast.LENGTH_SHORT).show();
                    return;
                }
                 */
                String url = "https://www.autotrader.ca/cars/?mdl=" + Model_name + "&make=" + Make_name + "&loc=K2G1V8";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        //This Action will send data to the database
        btn_add_to_favorites.setOnClickListener(v -> {
            Snackbar.make(linear_View, R.string.adder, Snackbar.LENGTH_SHORT).show();
            carsDB.insertCar(new Cars(Integer.parseInt(Make_ID), Make_name, Integer.parseInt(Model_ID), Model_name));
        });

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
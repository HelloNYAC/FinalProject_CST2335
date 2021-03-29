package com.finalproject_cst2335.car;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
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
    TextView tv_make_id, tv_make_name, tv_model_id, tv_model_name, Add_to_Favorites;
    CarsDB carsDB;
    Button findcar;
    int ide;
    private Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_lists_detail_fragment);
        //tb = findViewById(R.id.car_searchResults_tb);
        //setSupportActionBar(tb);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
        }

        CarFragment carFragment = new CarFragment();
        Bundle bundle = new Bundle(); //save the data in the bundle for later retrieval.
        bundle.putString("id", getIntent().getStringExtra("id"));
        bundle.putString("name", getIntent().getStringExtra("name"));
        bundle.putString("makeID", getIntent().getStringExtra("makeID"));
        bundle.putString("makeName", getIntent().getStringExtra("makeName"));
        carFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.listview_framelayout, carFragment)
                .commit();
*/
        findcar = findViewById(R.id.findcar);
        postal = findViewById(R.id.postal);
        linear_View = findViewById(R.id.linear_View);

        findcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (postal.getText().toString().isEmpty() || postal.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(CarListsDetail.this, "Please Enter postal code!", Toast.LENGTH_SHORT).show();
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
        tv_make_id = findViewById(R.id.tv_make_id);
        tv_make_name = findViewById(R.id.tv_make_name);
        tv_model_name = findViewById(R.id.tv_model_name);
        tv_model_id = findViewById(R.id.tv_model_id);
        carsDB = new CarsDB(CarListsDetail.this);

        //This Action will send data to the database
        Add_to_Favorites.setOnClickListener(v -> {
            Snackbar.make(linear_View, R.string.adder, Snackbar.LENGTH_SHORT).show();
            carsDB.insertCar(new Cars(Integer.parseInt(Make_ID), Make_name, Integer.parseInt(Model_ID), Model_name));
        });
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
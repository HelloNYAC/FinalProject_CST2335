package com.finalproject_cst2335.car;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.finalproject_cst2335.R;

public class CarSavedListsDetail extends AppCompatActivity {
    Bundle id;
    String makeName, name, makeiD, carID;
    TextView tv_make_id, tv_make_name, tv_model_id, tv_model_name;
    Button btn_shopcar, btn_view_detail, btn_del_to_favorites;
    CarsDB carsDB;
    int ide;
    private Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_activity_car_saved_lists_fragment);
        tb = findViewById(R.id.carShowFavoritesDetail_tb);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn_del_to_favorites = findViewById(R.id.del_from_Favorites);
        tv_make_id = findViewById(R.id.tv_make_id);
        btn_shopcar = findViewById(R.id.shopcar);
        tv_make_name = findViewById(R.id.tv_make_name);
        tv_model_name = findViewById(R.id.tv_model_name);
        tv_model_id = findViewById(R.id.tv_model_id);
        carsDB = new CarsDB(CarSavedListsDetail.this);
        getdata();
        carsDB = new CarsDB(CarSavedListsDetail.this);
        btn_view_detail = findViewById(R.id.view_car_detail);

        btn_view_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://www.google.com/search?q=" + makeName + "+" + name;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        btn_shopcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.autotrader.ca/cars/?mdl=" + name + "&make=" + makeName + "&loc=K2G1V8";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        btn_del_to_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(CarSavedListsDetail.this);
                builder1.setMessage(R.string.deleteoption);
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                int get = carsDB.delete(ide);
                                Toast.makeText(CarSavedListsDetail.this, R.string.del, Toast.LENGTH_LONG).show();
                                startActivity(new Intent(CarSavedListsDetail.this, CarSavedListsView.class));
                                dialog.cancel();

                            }
                        });

                builder1.setNegativeButton(
                        R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void getdata() {
        try {
            Intent intent = getIntent();
            carID = (intent.getStringExtra("id"));
            name = intent.getStringExtra("name");
            makeiD = (intent.getStringExtra("makeID"));
            makeName = intent.getStringExtra("makeName");
            ide = Integer.parseInt(makeiD);

            tv_make_id.setText(carID);
            tv_make_name.setText(name);
            tv_model_id.setText(makeiD);
            tv_model_name.setText(makeName);

        } catch (Exception e) {
            Toast.makeText(CarSavedListsDetail.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
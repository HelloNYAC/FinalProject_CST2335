package com.finalproject_cst2335.car;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.finalproject_cst2335.R;

public class ShowFavourtiesDetail extends AppCompatActivity {
    Bundle id;
    String makeName, name, makeiD, carID;
    TextView tv_country_name, tv_province, tv_cases, tv_date, del_from_Favorites, findcar;
    CarsDB carsDB;
    int ide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_favourties_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        del_from_Favorites = findViewById(R.id.del_from_Favorites);
        tv_country_name = findViewById(R.id.tv_country_name);
        findcar = findViewById(R.id.findcar);
        tv_province = findViewById(R.id.tv_province);
        tv_date = findViewById(R.id.tv_date);
        tv_cases = findViewById(R.id.tv_cases);
        carsDB = new CarsDB(ShowFavourtiesDetail.this);
        getdata();
        carsDB = new CarsDB(ShowFavourtiesDetail.this);
        findcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://www.google.com/search?q=" + name + makeName + "";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        del_from_Favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowFavourtiesDetail.this);
                builder1.setMessage(R.string.deleteoption);
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                int get = carsDB.delete(ide);
                                Toast.makeText(ShowFavourtiesDetail.this, R.string.del, Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ShowFavourtiesDetail.this, Showfavourites.class));
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

            tv_country_name.setText(carID);
            tv_province.setText(name);
            tv_cases.setText(makeiD);
            tv_date.setText(makeName);

        } catch (Exception e) {
            Toast.makeText(ShowFavourtiesDetail.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
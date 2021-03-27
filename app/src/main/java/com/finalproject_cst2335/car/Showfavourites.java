package com.finalproject_cst2335.car;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.finalproject_cst2335.R;

import java.util.ArrayList;
import java.util.List;

public class Showfavourites extends AppCompatActivity {

    ListView favlistview;
    ProgressDialog progressDialog;
    CarsDB carsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showfavourites);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        favlistview = findViewById(R.id.favlistview);

        carsDB = new CarsDB(this);
        showProgreesbar();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// API 5+ solution
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showProgreesbar() {
        progressDialog = new ProgressDialog(Showfavourites.this, R.style.MyTheme);
        progressDialog.setCancelable(false);

        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();

        List<cars> favouritesModels;
        favouritesModels = carsDB.get_favourites();
        favListAdapter listAdapter;
        listAdapter = new favListAdapter(Showfavourites.this, favouritesModels);
        favlistview.setAdapter(listAdapter);
        progressDialog.dismiss();
    }

    //this adapter will set the view after getting data in the form of list
    private class favListAdapter extends BaseAdapter {
        Context context;
        List<cars> favouritesModels;

        public favListAdapter(Context context, List<cars> trackDetailModels) {
            this.context = context;
            this.favouritesModels = trackDetailModels;
        }

        @Override
        public int getCount() {
            return favouritesModels.size();
        }

        @Override
        public Object getItem(int position) {
            return favouritesModels.get(position);
        }

        @Override

        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final cars favouritesModel = favouritesModels.get(position);
            View view = convertView;
            view = getLayoutInflater().inflate(R.layout.countrydetail, parent, false);

            TextView tv_country_name, tv_province, tv_cases, tv_date;
            tv_country_name = view.findViewById(R.id.tv_country_name);
            tv_province = view.findViewById(R.id.tv_province);
            tv_date = view.findViewById(R.id.tv_date);
            tv_cases = view.findViewById(R.id.tv_cases);

            tv_country_name.setText(favouritesModel.getModel_ID() + "");
            tv_province.setText(favouritesModel.getModel_Name() + "");
            tv_cases.setText(favouritesModel.getMake_ID() + "");
            tv_date.setText(favouritesModel.getMake_Name());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Showfavourites.this, ShowFavourtiesDetail.class);

                    intent.putExtra("id", String.valueOf(favouritesModel.getModel_ID()));
                    intent.putExtra("name", favouritesModel.getModel_Name());
                    intent.putExtra("makeID", String.valueOf(favouritesModel.getMake_ID()));
                    intent.putExtra("makeName", favouritesModel.getMake_Name());
                    startActivity(intent);
                }
            });
            return view;
        }
    }
}
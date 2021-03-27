package com.finalproject_cst2335.car;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.finalproject_cst2335.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public class CarsList extends AppCompatActivity {
    ListView listview;// Initialize variable to set view for later use
    ProgressDialog progressDialog;// for progress bar
    static String url;// url to make API call
    List<cars> posts;
    private List<cars> list;
    private Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars_lists);
        tb = findViewById(R.id.carsList_tb);
        setSupportActionBar(tb);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        listview = findViewById(R.id.listview);
        try {
            String country = getIntent().getStringExtra("country");
            url = "https://vpic.nhtsa.dot.gov/api/vehicles/GetModelsForMake/" + country + "?format=json";
            new getCarsData().execute(url);
        } catch (Exception ex) {
            Toast.makeText(CarsList.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// API 5+ solution
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * this class will get data from API using async task
     * it has three function on preExecute to start progress bar
     * do it in background this will fetch data from API and set it in the listview
     * postExecute this will close the Progress bar
     */
    public class getCarsData extends AsyncTask<String, Void, String> {
        String server_response;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(CarsList.this, R.style.MyTheme);
            progressDialog.setCancelable(false);

            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    server_response = readStream(urlConnection.getInputStream());

                    Gson gson = new Gson();
                    String jsonOutput = server_response;

                    Type listType = new TypeToken<CarsModel>() {
                    }.getType();
                    CarsModel carsModel = gson.fromJson(jsonOutput, listType);
                    posts = carsModel.Results;

                    //test
                    runOnUiThread(() -> {
                        ListAdapter listAdapter;
                        listAdapter = new theListAdapter(CarsList.this, posts);
                        listview.setAdapter(listAdapter);
                    });
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
        }
    }

    /**
     * @param in it get data in from server as input
     * @return and return it in the string form
     */
    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }

    /**
     * this will set the list view by getting data in the form of list and set it int he view
     */
    private class theListAdapter extends BaseAdapter {
        Context context;
        List<cars> trackDetailModels;

        public theListAdapter(Context context, List<cars> trackDetailModels) {
            this.context = context;
            this.trackDetailModels = trackDetailModels;
        }

        /**
         * @return this will return the size of list
         */
        @Override
        public int getCount() {
            return trackDetailModels.size();
        }

        /**
         * @param position this will return the index of the list
         * @return
         */
        @Override
        public Object getItem(int position) {
            return trackDetailModels.get(position);
        }

        @Override

        public long getItemId(int position) {
            return 0;
        }

        /**
         * @param position    it will set the value of list item at postions of the listview
         * @param convertView it will get the view to set data and convert it into updated values
         * @param parent      it will return the context of the parent
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final cars countrydatalist = trackDetailModels.get(position);
            View view;
            view = getLayoutInflater().inflate(R.layout.countrydetail, parent, false);

            TextView tv_country_name, tv_province, tv_cases, tv_date;
            tv_country_name = view.findViewById(R.id.tv_country_name);
            tv_province = view.findViewById(R.id.tv_province);
            tv_date = view.findViewById(R.id.tv_date);
            tv_cases = view.findViewById(R.id.tv_cases);

            tv_country_name.setText(countrydatalist.getMake_ID() + "");
            tv_province.setText(countrydatalist.getMake_Name() + "");
            tv_cases.setText(countrydatalist.getModel_ID() + "");
            tv_date.setText(countrydatalist.getModel_Name());
            // It will send data to next Activity in the form of intents
            view.setOnClickListener(view1 -> {
                Intent intent = new Intent(CarsList.this, Showcardetail.class);

                intent.putExtra("id", String.valueOf(countrydatalist.getModel_ID()));
                intent.putExtra("name", String.valueOf(countrydatalist.getModel_Name()));
                intent.putExtra("makeID", String.valueOf(countrydatalist.getMake_ID()));
                intent.putExtra("makeName", String.valueOf((countrydatalist.getMake_Name())));
                startActivity(intent);
            });
            return view;
        }
    }
}
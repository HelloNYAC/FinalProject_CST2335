package com.finalproject_cst2335.soccergames;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.finalproject_cst2335.R;
import com.finalproject_cst2335.soccergames.Utils.SoccerGameDBHelper;
import com.finalproject_cst2335.soccergames.entities.SoccerNews;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SoccerNewsDetailPage extends AppCompatActivity {

    private TextView titleTv;
    private TextView linkTv;
    private TextView dateTv;
    private TextView descTv;
    private SoccerNews soccerNews;
    private Toolbar tb;
    private Button saveBtn;
    private SoccerGameDBHelper dbHelper;
    private ImageView thumbnail;
    private String thumbnailURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soccer_news_detail_page);
        titleTv = findViewById(R.id.sc_news_title);
        linkTv = findViewById(R.id.sc_detail_link);
        dateTv = findViewById(R.id.sc_detail_date);
        descTv = findViewById(R.id.sc_detail_desc);
        thumbnail = findViewById(R.id.sc_detail_thumbnail);
        tb = findViewById(R.id.sc_detail_tb);
        setSupportActionBar(tb);

        dbHelper = new SoccerGameDBHelper(getApplicationContext());

        soccerNews = (SoccerNews) getIntent().getSerializableExtra(SoccerGameHomePage.SOCCER_NEWS_DETAIL);

        Log.i("Thumbnail", soccerNews.getImage());

        if( soccerNews != null){
            titleTv.setText(soccerNews.getTitle());
            linkTv.setText(soccerNews.getArticleUrl());
            dateTv.setText(soccerNews.getDate());
            descTv.setText(soccerNews.getDescription());
            thumbnailURL = soccerNews.getImage();

            ImageDownloader downloader = new ImageDownloader();
            downloader.execute();
        }

        saveBtn = findViewById(R.id.sc_save_fav);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(SoccerNewsDetailPage.this,"Test ...", Toast.LENGTH_SHORT).show();
                long affectRows = dbHelper.addNewSoccerGame(soccerNews);
                if( affectRows >= 1){
                    Toast.makeText(SoccerNewsDetailPage.this,"Add successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SoccerNewsDetailPage.this,"Unable to save this news", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sc_detail_page_tb_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return true;
    }

    private class ImageDownloader extends AsyncTask<String,Integer,String>{
        Bitmap image = null;
        @Override
        protected String doInBackground(String... strings) {
            if( thumbnailURL != null && !thumbnailURL.equals("")){
                try {
                    URL url = new URL(thumbnailURL);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    image = BitmapFactory.decodeStream(inputStream);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return "done";
        }

        @Override
        protected void onPostExecute(String s) {
            if( image!=null){
                thumbnail.setImageBitmap(image);
            }
        }
    }
}
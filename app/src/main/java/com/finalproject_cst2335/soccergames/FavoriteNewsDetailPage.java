package com.finalproject_cst2335.soccergames;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class FavoriteNewsDetailPage extends AppCompatActivity {

    private Toolbar tb;
    private TextView titleTv;
    private ImageView thumbnailImage;
    private TextView linkTv;
    private TextView dateTv;
    private TextView descTv;
    private Button removeBtn;
    private Button openBrowserBtn;
    private String thumbnailURL;
    private SoccerNews news;
    private SoccerGameDBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_news_detail_page);

        tb = findViewById(R.id.sc_fav_detail_tb);
        titleTv = findViewById(R.id.sc_fav_news_title);
        thumbnailImage = findViewById(R.id.sc_fav_detail_thumbnail);
        linkTv = findViewById(R.id.sc_fav_detail_link);
        dateTv = findViewById(R.id.sc_fav_detail_date);
        descTv= findViewById(R.id.sc_fav_detail_desc);
        removeBtn = findViewById(R.id.sc_remove_fav);
        openBrowserBtn = findViewById(R.id.sc_fav_open_browser);
        dbHelper = new SoccerGameDBHelper(getApplicationContext());

        news = (SoccerNews) getIntent().getSerializableExtra(FavSoccerGames.NEWS_TO_PASS);

        if( news != null){
            thumbnailURL = news.getImage();
            titleTv.setText(news.getTitle());
            linkTv.setText(news.getArticleUrl());
            dateTv.setText(news.getDate());
            descTv.setText(news.getDescription());
            ImageDownloader downloader = new ImageDownloader();
            downloader.execute();

            removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long affectedRows = dbHelper.removeNews( news);
                    if( affectedRows >= 1 ){
                        Toast.makeText(FavoriteNewsDetailPage.this,"Remove successfully",Toast.LENGTH_SHORT).show();
                        Intent back = new Intent(FavoriteNewsDetailPage.this,FavSoccerGames.class);
                        startActivity(back);
                    }else{
                        Toast.makeText(FavoriteNewsDetailPage.this,"Failed to remove this news record",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            openBrowserBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.getArticleUrl()));
                    startActivity(browserIntent);
                }
            });

        }


    }

    private class ImageDownloader extends AsyncTask<String,Integer,String> {
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
                thumbnailImage.setImageBitmap(image);
            }
        }
    }
}
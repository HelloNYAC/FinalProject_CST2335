package com.finalproject_cst2335.soccergames;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.finalproject_cst2335.R;
import com.finalproject_cst2335.soccergames.entities.SoccerNews;

public class SoccerNewsDetailPage extends AppCompatActivity {

    private TextView titleTv;
    private TextView linkTv;
    private TextView dateTv;
    private TextView descTv;
    private SoccerNews soccerNews;
    private Toolbar tb;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soccer_news_detail_page);
        titleTv = findViewById(R.id.sc_news_title);
        linkTv = findViewById(R.id.sc_detail_link);
        dateTv = findViewById(R.id.sc_detail_date);
        descTv = findViewById(R.id.sc_detail_desc);
        tb = findViewById(R.id.sc_detail_tb);
        setSupportActionBar(tb);

        soccerNews = (SoccerNews) getIntent().getSerializableExtra(SoccerGameHomePage.SOCCER_NEWS_DETAIL);

        if( soccerNews != null){
            titleTv.setText(soccerNews.getTitle());
            linkTv.setText(soccerNews.getArticleUrl());
            dateTv.setText(soccerNews.getDate());
            descTv.setText(soccerNews.getDescription());
        }

        saveBtn = findViewById(R.id.sc_save_fav);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SoccerNewsDetailPage.this,"Test ...", Toast.LENGTH_SHORT).show();
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
}
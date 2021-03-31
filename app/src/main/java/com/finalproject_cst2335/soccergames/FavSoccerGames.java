package com.finalproject_cst2335.soccergames;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.finalproject_cst2335.R;
import com.finalproject_cst2335.soccergames.Utils.SoccerGameDBHelper;
import com.finalproject_cst2335.soccergames.entities.SoccerNews;

import java.util.ArrayList;
import java.util.List;

public class FavSoccerGames extends AppCompatActivity {

    private Toolbar tb;
    private ListView lv;
    private List<SoccerNews> favNewsList = new ArrayList<>();
    private SoccerGameDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_soccer_games);

        tb = findViewById(R.id.sc_fav_page_tb);
        setSupportActionBar(tb);
        dbHelper = new SoccerGameDBHelper(FavSoccerGames.this);
        favNewsList = dbHelper.getAllGames();
        lv = findViewById(R.id.sc_fav_page_list);
        FavListAdapter adapter = new FavListAdapter();
        lv.setAdapter(adapter);
    }

    private class FavListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return favNewsList.size();
        }

        @Override
        public Object getItem(int position) {
            return favNewsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return favNewsList.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            SoccerNews scnews = (SoccerNews) getItem(position);
            convertView = inflater.inflate(R.layout.news_item_layout,parent,false);
            ImageView thumbnailIv = convertView.findViewById(R.id.sc_thumbnail);
            TextView linkTv = convertView.findViewById(R.id.sc_link);
            linkTv.setText(scnews.getArticleUrl());
            TextView dateTv = convertView.findViewById(R.id.sc_date);
            dateTv.setText(scnews.getDate());
            TextView descTv = convertView.findViewById(R.id.sc_desc);
            descTv.setText(scnews.getDescription());

            return convertView;
        }
    }
}
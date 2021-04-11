package com.finalproject_cst2335.soccergames;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
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
    private FrameLayout favDetailFrame;
    public static final String NEWS_TO_PASS = "NEWS_TO_PASS";
    private boolean isTablet;
    private static final String CURRENT_FRAME = "CURRENT_FRAME";
    public static final int BACK_FROM_FAV = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_soccer_games);
        setTitle("My Favorite Soccer Games");
        tb = findViewById(R.id.sc_fav_page_tb);
        favDetailFrame = findViewById(R.id.sc_fav_detail_fragment);
        isTablet = favDetailFrame != null;
        setSupportActionBar(tb);
        dbHelper = new SoccerGameDBHelper(getApplicationContext());
        favNewsList = dbHelper.getAllGames();
        lv = findViewById(R.id.sc_fav_page_list);
        FavListAdapter adapter = new FavListAdapter();
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(isTablet){
                    SoccerNews newsToBePass = (SoccerNews) adapter.getItem(position);
                    FavNewsDetailFragment newsDetailFragment = new FavNewsDetailFragment(newsToBePass, FavSoccerGames.this);
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().
                            replace(R.id.sc_fav_detail_fragment,newsDetailFragment, CURRENT_FRAME)
                            .commit();
                }else{
                    Intent goToDetailPage = new Intent(FavSoccerGames.this, FavoriteNewsDetailPage.class);
                    SoccerNews newsToBePassed = (SoccerNews) adapter.getItem(position);
                    goToDetailPage.putExtra(NEWS_TO_PASS, newsToBePassed);
                    startActivity(goToDetailPage);
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fav_list_tb_items,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if( item.getItemId() == R.id.sc_fav_list_to_home){
            Intent backToHome = new Intent(FavSoccerGames.this,SoccerGameHomePage.class);
            startActivity(backToHome);
        }
        return true;
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
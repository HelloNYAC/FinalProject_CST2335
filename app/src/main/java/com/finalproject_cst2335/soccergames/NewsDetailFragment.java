package com.finalproject_cst2335.soccergames;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.finalproject_cst2335.R;
import com.finalproject_cst2335.soccergames.Utils.SoccerGameDBHelper;
import com.finalproject_cst2335.soccergames.entities.SoccerNews;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NewsDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SoccerNews news;
    private AppCompatActivity parent;
    private ImageView thumbnail;
    private SoccerGameDBHelper dbHelper;

    public NewsDetailFragment( SoccerNews news, AppCompatActivity parent) {
        // Required empty public constructor
        this.news = news;
        this.parent = parent;
        this.dbHelper = new SoccerGameDBHelper(this.parent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_detail, container, false);
        TextView titleTv = view.findViewById(R.id.sc_detailfragment_news_title);
        TextView linkTv = view.findViewById(R.id.sc_detailfragment_link);
        TextView dateTv = view.findViewById(R.id.sc_detailfragment_date);
        TextView descTv = view.findViewById(R.id.sc_detailfragment_desc);
        Button addToFavBtn = view.findViewById(R.id.sc_detailfragment_save_fav);
        Button hideBtn = view.findViewById(R.id.sc_detailfragment_hide);
        Button openBrowserBtn = view.findViewById(R.id.sc_detailfragment_open_browser);
        thumbnail = view.findViewById(R.id.sc_detailfragment_thumbnail);

        hideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.getSupportFragmentManager()
                        .beginTransaction()
                        .remove(NewsDetailFragment.this)
                        .commit();
            }
        });

        titleTv.setText(this.news.getTitle());
        linkTv.setText(this.news.getArticleUrl());
        dateTv.setText(this.news.getDate());
        descTv.setText(this.news.getDescription());

        addToFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.addNewSoccerGame(news);
            }
        });

        openBrowserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.getArticleUrl()));
                startActivity(browserIntent);
            }
        });

        ImageDownloader downloader = new ImageDownloader(this.news.getImage());
        downloader.execute();

        return view;
    }

    private class ImageDownloader extends AsyncTask<String,Integer,String> {
        Bitmap image = null;
        String thumbnailURL;

        private ImageDownloader(String thumbnailURL){
            this.thumbnailURL = thumbnailURL;
        }
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
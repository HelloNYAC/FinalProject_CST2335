package com.finalproject_cst2335.soccergames;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.finalproject_cst2335.soccergames.entities.SoccerNews;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
public class FavNewsDetailFragment extends Fragment {

    private TextView titleTv;
    private TextView dateTv;
    private TextView descTv;
    private TextView linkTv;
    private ImageView thumbNailIv;
    private Button removeBtn;
    private Button openInBrowserBtn;
    private Button hideBtn;

    private SoccerNews news;
    private AppCompatActivity parent;

    public FavNewsDetailFragment( SoccerNews news, AppCompatActivity parent) {
        // Required empty public constructor
        this.news = news;
        this.parent = parent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fav_news_detail, container, false);
        titleTv = view.findViewById(R.id.sc_favDetailFragment_news_title);
        dateTv = view.findViewById(R.id.sc_favDetailFragment_date);
        descTv = view.findViewById(R.id.sc_favDetailFragment_desc);
        linkTv = view.findViewById(R.id.sc_favDetailFragment_link);
        thumbNailIv = view.findViewById(R.id.sc_favDetailFragment_thumbnail);
        removeBtn = view.findViewById(R.id.sc_favDetailFragment_remove_fav);
        openInBrowserBtn = view.findViewById(R.id.sc_favDetailFragment_open_browser);
        hideBtn = view.findViewById(R.id.sc_favDetailFragment_hide);

        titleTv.setText(this.news.getTitle());
        dateTv.setText(this.news.getDate());
        descTv.setText(this.news.getDescription());
        linkTv.setText(this.news.getArticleUrl());

        ImageDownloader downloader = new ImageDownloader(this.news.getImage());
        downloader.execute();
        return view;
    }

    private class ImageDownloader extends AsyncTask<String,Integer,String> {
        Bitmap image = null;
        String url;

        private ImageDownloader(String url){
            this.url = url;
        }
        @Override
        protected String doInBackground(String... strings) {
            if( this.url != null && !this.url.equals("")){
                try {
                    URL url = new URL(this.url);
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
                thumbNailIv.setImageBitmap(image);
            }
        }
    }
}
package com.finalproject_cst2335.soccergames;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.finalproject_cst2335.R;
import com.finalproject_cst2335.soccergames.entities.SoccerNews;

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

    public NewsDetailFragment( SoccerNews news, AppCompatActivity parent) {
        // Required empty public constructor
        this.news = news;
        this.parent = parent;
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

        openBrowserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.getArticleUrl()));
                startActivity(browserIntent);
            }
        });

        return view;
    }
}
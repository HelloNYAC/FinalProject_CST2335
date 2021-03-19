package com.finalproject_cst2335.Song;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.finalproject_cst2335.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SongSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_search);
        
        ForecastQuery req = new ForecastQuery();
        req.execute();
    }
    
    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        private ForecastQuery(){
        }

        ProgressBar progressBar = findViewById(R.id.progressBar);
        TextView artist = findViewById(R.id.ss_searchtextview);
        ListView songlist = findViewById(R.id.ss_searchlistview);
        String artistname = null;
        String songname = null;

        protected String doInBackground(String... strings) {
            publishProgress(20,50,75);
            String ex = null;
            return null;

            try {
                //create a URL object of what server to contact:
                String artistURL = "http://www.songsterr.com/a/wa/artist?id=XXX";

                URL url = new URL(artistURL);

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data:
                InputStream response = urlConnection.getInputStream();

                //From part 3: slide 19
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(response, "UTF-8");

            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
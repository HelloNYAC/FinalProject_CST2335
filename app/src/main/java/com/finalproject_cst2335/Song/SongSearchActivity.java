package com.finalproject_cst2335.Song;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.finalproject_cst2335.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SongSearchActivity  extends AppCompatActivity {
    TextView artistname;
    ArrayList<String> songlist = new ArrayList<String>();
    String searchedArtist;
//    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_search);

        TextView artistname = findViewById(R.id.song_artistNameText);
        ListView lv = findViewById(R.id.song_searchlistview);

        EditText searchRst = findViewById(R.id.search_rst);

        Intent fromMain = getIntent();
        searchedArtist = fromMain.getStringExtra("NAME");
        searchRst.setText(searchedArtist);

        String artistURL = "https://www.songsterr.com/a/ra/songs.json?pattern="+searchedArtist;

        Songsearch findReq = new Songsearch();
        findReq.execute(artistURL);
    }

    private class Songsearch extends AsyncTask< String, Integer, String>
    {
        //Type3                Type1
        public String doInBackground(String ... args)
        {
            try {

                //create a URL object of what server to contact:
                URL url = new URL(args[0]);

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data:
                InputStream response = urlConnection.getInputStream();

                //JSON reading:   Look at slide 26
                //Build the entire string response:
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                String result = sb.toString(); //result is the whole string


                // convert string to JSON: Look at slide 27:
                JSONObject SongObject = new JSONObject(result);
                JSONArray jsonArray = SongObject.getJSONArray(result);

                for(int i=0; i<jsonArray.length(); i++){
                    JSONObject eachObject = (JSONObject)jsonArray.getJSONObject(i);
                    String songID = eachObject.getString("id");
                    String songTitle = eachObject.getString("title");

                    JSONObject innerObject = (JSONObject)eachObject.getJSONObject("artist");
                    String artistID = innerObject.getString("id");
                    String artistName = innerObject.getString("name");


                }


                //get the double associated with "value"
                double uvRating = uvReport.getDouble("value");

                Log.i("MainActivity", "The uv is now: " + uvRating) ;

            }
            catch (Exception e)
            {

            }

            return "Done";
        }

        //Type 2
        public void onProgressUpdate(Integer ... args)
        {

        }
        //Type3
        public void onPostExecute(String fromDoInBackground)
        {
            Log.i("HTTP", fromDoInBackground);
        }
    }
    }






















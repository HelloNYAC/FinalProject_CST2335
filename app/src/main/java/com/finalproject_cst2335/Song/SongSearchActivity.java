package com.finalproject_cst2335.Song;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.finalproject_cst2335.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class SongSearchActivity  extends AppCompatActivity {
    TextView artistname;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_search);
        Songsearch find = new Songsearch();
        find.execute();
    }

    private class Songsearch extends AsyncTask<String, Integer, String> {

        private Songsearch() {
        }

        TextView artistname = findViewById(R.id.ss_artistNameText);
        ListView listview = findViewById(R.id.ss_searchlistview);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        Intent fromMain = getIntent();
  //      ArrayList<HashMap<String, String>> searchList = new ArrayList<>();


        String artist = null;
        String songname = null;
        String title = null;
        String name = null;
        String song = null;

        public boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        //      listview.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,new ArrayList<String>()));

        protected String doInBackground(String... strings) {
            publishProgress(20, 50, 75);
            String ex = null;
            return null;

            try {
                //create a URL object of what server to contact:

                String songsterurl = "https://vpic.nhtsa.dot.gov/api/vehicles/GetModelsForMake/?";
                URL url = new URL(songsterurl);

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data:
                InputStream response = urlConnection.getInputStream();

                //From part 3: slide 19
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(response, "UTF-8");


                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        if (xpp.getName().equals("Artist")) {
                            String title = xpp.getAttributeValue(null, "title");
                            String name = xpp.getAttributeValue(null, "name");
                            String song = xpp.getAttributeValue(null, "Song");
                        } else if (xpp.getName().equals("Artist")) ;
                    }
                }

            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ex;
        }

            protected void onProgressUpdate (Integer...values){
                super.onProgressUpdate(values);
                //Update GUI stuff only:
                try {

                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(values[0]);
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void onPostExecute (String sentFromDoInBackground){
                super.onPostExecute(sentFromDoInBackground);
                //update GUI Stuff:
                artistname.setText(fromMain.getStringExtra("NAME"));
                song.setText("Songname " + songname);
                title.setText("Title :" + title);
                name.setText("name is  :" + name);
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }





















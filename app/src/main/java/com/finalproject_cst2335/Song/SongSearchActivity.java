package com.finalproject_cst2335.Song;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.finalproject_cst2335.R;

public class SongSearchActivity  extends AppCompatActivity {
    TextView artistname;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_search);
//        Songsearch find = new Songsearch();
//        find.execute();

        artistname = findViewById(R.id.ss_artistNameText);
        ListView listview = findViewById(R.id.ss_searchlistview);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        Intent fromMain = getIntent();
        artistname.setText(getIntent().getStringExtra("NAME"));
    }


    private class Songsearch extends AsyncTask<String, Integer, String> {
        private Songsearch() {


            ArrayList<HashMap<String, String>> searchList = new ArrayList<>();


            String artist = null;
            String songname = null;

            String title = null;
            String name = null;
            String song = null;
        }

        public boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }


        protected String doInBackground(String... strings) {
            publishProgress(20, 50, 75);
            String ex = null;
            return null;

            try {
                //create a URL object of what server to contact:
                String webURL = "http://www.songsterr.com/a/wa/artist?";

                URL url = new URL(webURL);

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
                            title = xpp.getAttributeValue(null, "title");
                            name = xpp.getAttributeValue(null, "name");
                            song = xpp.getAttributeValue(null, "Song");
                        } else if (xpp.getName().equals("")) ;
                    }
                }

            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

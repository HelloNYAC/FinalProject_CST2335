package com.finalproject_cst2335.Song;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.finalproject_cst2335.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SongSearchActivity  extends AppCompatActivity {
    public static final String SONG_ITEM_ID= "ITEM_ID";
    public static final String SONG_ARTISTID="ARTIST_ID";
    public static final String SONG_ID = "SONG_ID";
    public static final String SONG_TITLE= "SONG_TITLE";


    ArrayList<SongMessage> songArrayList = new ArrayList<>();
    private SonglistAdapter songAdapter;
    String searchedArtist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_search);
        ListView song_lv = findViewById(R.id.song_searchlistview);


        songAdapter = new SonglistAdapter();
        song_lv.setAdapter(songAdapter);

        EditText searchRst = findViewById(R.id.search_rst);

        Intent fromMain = getIntent();
        searchedArtist = fromMain.getStringExtra("NAME");
        searchRst.setText(searchedArtist);
        FrameLayout frameLayout = findViewById(R.id.Song_fragmentLocation);
        boolean isTablet = frameLayout != null;

        String artistURL = "https://www.songsterr.com/a/ra/songs.json?pattern=" + searchedArtist;

        Songsearch findReq = new Songsearch();
        findReq.execute(artistURL);

        song_lv.setOnItemClickListener((list, item, position, id) -> {
            Bundle song_dataToPass = new Bundle();
            song_dataToPass.putLong(SONG_ITEM_ID, id);
            song_dataToPass.putString(SONG_ARTISTID, songArrayList.get(position).getArtistID());
            song_dataToPass.putString(SONG_ID, songArrayList.get(position).getSongID());
            song_dataToPass.putString(SONG_TITLE, songArrayList.get(position).getSongTitle());

            if (isTablet) {
                SongDetailsFragment song_dFragment = new SongDetailsFragment(); //add a DetailFragment
                song_dFragment.setArguments(song_dataToPass); //pass it a bundle for information
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.Song_fragmentLocation, song_dFragment) //Add the fragment in FrameLayout
                        .commit(); //actually load the fragment. Calls onCreate() in DetailFragment
            } else //isPhone
            {
                Intent nextActivity = new Intent(SongSearchActivity.this, SongEmptyActivty.class);
                nextActivity.putExtras(song_dataToPass); //send data to next activity
                startActivity(nextActivity); //make the transition
            }
        });


//        final ImageButton song_likebtn = findViewById(R.id.song_likebtn);
//        song_likebtn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                boolean isFavourite = readState();
//
//                if (isFavourite) {
//                    song_likebtn.setBackgroundResource(R.drawable.song_staroff);
//                    isFavourite = false;
//                    saveState(isFavourite);
//
//                } else {
//                    song_likebtn.setBackgroundResource(R.drawable.song_staron);
//                    isFavourite = true;
//                    saveState(isFavourite);
//
//                }
//
//            }
//        });
//
//    }
//
//    private void saveState(boolean isFavourite) {
//        SharedPreferences aSharedPreferences = this.getSharedPreferences(
//                "Favourite", Context.MODE_PRIVATE);
//        SharedPreferences.Editor aSharedPreferencesEdit = aSharedPreferences
//                .edit();
//        aSharedPreferencesEdit.putBoolean("State", isFavourite);
//        aSharedPreferencesEdit.commit();
//    }
//
//    private boolean readState() {
//        SharedPreferences aSharedPreferences = this.getSharedPreferences(
//                "Favourite", Context.MODE_PRIVATE);
//        return aSharedPreferences.getBoolean("State", true);
//    }
//

//        song_lv.setOnItemClickListener((parent, view, row, id)->{
//            Snackbar
//                    .make(song_likebtn, getResources().getString(R.string.song_snackbar1), Snackbar.LENGTH_SHORT)
//                    .setAction("Undo",click->sw.setChecked((!b)))
//                    .show();
//        });

//
        song_lv.setOnItemLongClickListener((parent, view, row, id) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(getString(R.string.SONG_rm_title))
                    .setMessage(getString(R.string.SONG_rm_msg1) + row +
                            "      " + getString(R.string.SONG_rm_msg2) + id)
                    .setPositiveButton(getString(R.string.SONG_p_msg1), (click, arg) -> {
                        songArrayList.remove(row);
                        songAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton(getString(R.string.SONG_n_msg2), (click, arg) -> {
                    })
                    .create().show();
            return true;
        });

    }
    private class Songsearch extends AsyncTask<String, Integer, String> {
        ProgressBar song_pgbar = findViewById(R.id.song_progressBar);



        //Type3                Type1
        public String doInBackground(String... args) {
            publishProgress(25);
            try {

                //create a URL object of what server to contact:
                URL song_url = new URL(args[0]);

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) song_url.openConnection();

                //wait for data:
                InputStream response = urlConnection.getInputStream();

                //JSON reading:   Look at slide 26
                //Build the entire string response:
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString(); //result is the whole string
                publishProgress(50);

                JSONArray array = new JSONArray(result);

                for (int i = 0; i < array.length(); i++) {
                    try{
                    JSONObject jsonObject = array.getJSONObject(i);
                    String songid = jsonObject.getString("id");
                    String songtitle = jsonObject.getString("title");
                    String artid = jsonObject.getJSONObject("artist").getString("id");
                    String artsname = jsonObject.getJSONObject("artist").getString("nameWithoutThePrefix");
                    SongMessage song_message = new SongMessage(i + 1, songid, songtitle, artid,artsname);
                    songArrayList.add(song_message);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            publishProgress(100);
            return "Done";



        }



        //Type 2
        public void onProgressUpdate(Integer... args) {

        }

        //Type3
        public void onPostExecute(String fromDoInBackground) {
            super.onPostExecute(fromDoInBackground);
            Log.i("HTTP", fromDoInBackground);
            System.out.println(songArrayList.size());
            songAdapter.notifyDataSetChanged();
            Log.e("======", "onPostExecute: "+songArrayList.get(1).toString() );
        }
    }

    public class SonglistAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return songArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return songArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return songArrayList.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View itemlistView;
            itemlistView = inflater.inflate(R.layout.song_item_list, parent, false);
            TextView song_item_id = itemlistView.findViewById(R.id.song_item_id);
            song_item_id.setText(""+ songArrayList.get(position).getId());
            TextView song_title = itemlistView.findViewById(R.id.Song_detail_Songtitle);
            song_title.setText(songArrayList.get(position).getSongTitle());
            TextView song_artsname = itemlistView.findViewById(R.id.song_artistname);
            song_artsname.setText(songArrayList.get(position).artistName);
//            //ImageButton likebtn = itemlistView.findViewById(R.id.song_likebtn);
            songAdapter.notifyDataSetChanged();

            return itemlistView;
        }
    }
}





















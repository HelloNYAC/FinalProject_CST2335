package com.finalproject_cst2335.Song;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.finalproject_cst2335.R;

import java.util.ArrayList;


public class SongfavList extends AppCompatActivity {


    /**
     * This class is used for save favorite songs in my list
     */
    public ArrayList<SongMessage> songElements = new ArrayList<>();
    SQLiteDatabase dbfav;
    favListAdapter favAdapter;
    public static final String SONG_ITEM_ID= "ITEM_ID";
    public static final String SONG_ARTISTID="ARTIST_ID";
    public static final String SONG_ID = "SONG_ID";
    public static final String SONG_TITLE= "SONG_TITLE";
    public static final String ARTIST_NAME = "ARTIST_NAME";
    public static final String TO_REMOVE = "TO_REMOVE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_activity_songfav_list);

        favAdapter = new favListAdapter();
        ListView songfvlistview = findViewById(R.id.song_fvlist_listview);
        songfvlistview.setAdapter(favAdapter);

        loadDataFromDatabase();
        favAdapter.notifyDataSetChanged();



        songfvlistview.setOnItemClickListener((list, item, position, id) -> {
            Bundle song_dataToRemove = new Bundle();
            song_dataToRemove.putLong(SONG_ITEM_ID, id);
            song_dataToRemove.putString(SONG_ID, songElements.get(position).getSongID());
            song_dataToRemove.putString(SONG_TITLE, songElements.get(position).getSongTitle());
            song_dataToRemove.putString(SONG_ARTISTID, songElements.get(position).getArtistID());
            song_dataToRemove.putString(ARTIST_NAME, songElements.get(position).getArtistName());
            song_dataToRemove.putString(TO_REMOVE, "TO_REMOVE");

            Log.e("-----", "onCreate: "+ ARTIST_NAME );

            FrameLayout frameLayout = findViewById(R.id.Song_fragmentLocation);
            boolean isTablet = frameLayout != null;

            if (isTablet) {
                SongDetailsFragment song_dFragment = new SongDetailsFragment(); //add a DetailFragment
                song_dFragment.setArguments(song_dataToRemove); //pass it a bundle for information
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.Song_fragmentLocation, song_dFragment) //Add the fragment in FrameLayout
                        .commit(); //actually load the fragment. Calls onCreate() in DetailFragment
            } else //isPhone
            {
                Intent nextActivity = new Intent(SongfavList.this, SongEmptyActivty.class);
                nextActivity.putExtras(song_dataToRemove); //send data to next activity
                startActivity(nextActivity); //make the transition
            }
        });

    }

    /**
     * get a database
     */
    private void loadDataFromDatabase() {
        SongOpener dbOpener = new SongOpener(this);
        dbfav = dbOpener.getWritableDatabase();

        String[] columns = {SongOpener.COL_ID, SongOpener.SONG_ID, SongOpener.SONG_TITLE, SongOpener.ARTIST_ID, SongOpener.ARTIST_NAME};
        Cursor results = dbfav.query(false, SongOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        int IDColumnIndex = results.getColumnIndex(SongOpener.COL_ID);
        int songIDColumnIndex = results.getColumnIndex(SongOpener.SONG_ID);
        int songTitleColumnIndex = results.getColumnIndex(SongOpener.SONG_TITLE);
        int artIDColumnIndex = results.getColumnIndex(SongOpener.ARTIST_ID);
        int artNameColumnIndex = results.getColumnIndex(SongOpener.ARTIST_NAME);

        while (results.moveToNext()) {
            int table_ID = results.getInt(IDColumnIndex);
            String songID = results.getString(songIDColumnIndex);
            String songTitle = results.getString(songTitleColumnIndex);
            String artID = results.getString(artIDColumnIndex);
            String artName = results.getString(artNameColumnIndex);
            songElements.add(new SongMessage(table_ID, songID, songTitle, artID, artName));
        }
    }

    /**
     *
     */

    public class favListAdapter extends BaseAdapter {
        /**
         *
         * @return
         */
        @Override
        public int getCount() {
            return songElements.size();
        }

        /**
         *
         * @param position
         * @return
         */
        @Override
        public SongMessage getItem(int position) {
            return songElements.get(position);
        }

        /**
         *
         * @param position
         * @return
         */
        @Override
        public long getItemId(int position) {
            return songElements.get(position).getId();
        }

        /**
         *
         * @param position
         * @param convertView
         * @param parent
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View favListView;
            favListView = inflater.inflate(R.layout.song_fav_item_list, parent, false);

            TextView song_favsongid = favListView.findViewById(R.id.song_fvlist_songid);
            song_favsongid.setText(""+ songElements.get(position).getSongID());

            TextView Song_fvartid = favListView.findViewById(R.id.song_fvlist_artid);
            Song_fvartid.setText(songElements.get(position).getArtistID());

            favAdapter.notifyDataSetChanged();

            return favListView;


        }

    }
}

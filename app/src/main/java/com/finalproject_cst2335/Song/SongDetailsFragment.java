package com.finalproject_cst2335.Song;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.finalproject_cst2335.R;

import java.util.ArrayList;

public class SongDetailsFragment extends Fragment {

    private Bundle songDataFromActivity;
    private ArrayList<SongMessage> favSongList = new ArrayList();
    private long id;
    SQLiteDatabase db;
    private AppCompatActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragmentArtistID
        songDataFromActivity = getArguments();
        id = songDataFromActivity.getLong(SongSearchActivity.SONG_ITEM_ID);

        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.activity_song__details_fragment, container, false);

        TextView ItemID = (TextView) result.findViewById(R.id.Song_detail_item_ID);
        ItemID.setText("ItemID = " + songDataFromActivity.getString(SongSearchActivity.SONG_ARTISTID));

        //show the message
        TextView ArtID = (TextView) result.findViewById(R.id.Song_detail_ArtID);
        ArtID.setText("ArtID = " + songDataFromActivity.getString(SongSearchActivity.SONG_ARTISTID));

        //show the id:
        TextView songID = (TextView) result.findViewById(R.id.Song_detail_Songid);
        songID.setText("SongID = " + songDataFromActivity.getString(SongSearchActivity.SONG_ID));

        TextView songTitle = (TextView) result.findViewById(R.id.Song_detail_Songtitle);
        songTitle.setText("SongTitle = " + songDataFromActivity.getString(SongSearchActivity.SONG_TITLE));

        // get the delete button, and add a click listener:
        Button songDtlsavetofvbtn = result.findViewById(R.id.Song_detail_SavetoFv_Button);
        songDtlsavetofvbtn.setOnClickListener(clk -> {

            ContentValues songNewRowValues = new ContentValues();
            songNewRowValues.put(SongOpener.SONG_ID, songID.getText().toString());
            songNewRowValues.put(SongOpener.SONG_TITLE, songTitle.getText().toString());
            songNewRowValues.put(SongOpener.ARTIST_ID, ArtID.getText().toString());

//            long songDfNewId = db.insert(SongOpener.TABLE_NAME, null, songNewRowValues);
//            SongMessage song_msg = new SongMessage()
//            Toast.makeText(this, "You add fav id:" + SONG_TITLE , Toast.LENGTH_LONG).show();
            parentActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();


        });
        return result;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //context will either be FragmentExample for a tablet, or EmptyActivity for phone
        parentActivity = (AppCompatActivity) context;
    }
}
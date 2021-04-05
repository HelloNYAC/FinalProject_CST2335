package com.finalproject_cst2335.Song;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.finalproject_cst2335.R;

import java.sql.SQLException;
import java.util.ArrayList;

public class SongDetailsFragment extends Fragment {

  //  private Bundle songDataFromActivity;
    private Bundle songDataFromAnyWhere;
    private ArrayList<SongMessage> favSongList = new ArrayList();
    private long id;
    SQLiteDatabase db;
    private AppCompatActivity parentActivity;
    TextView ArtID, songID, songTitle, artName;
    Button favBtn;
    View result;
    public boolean stop = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragmentArtistID
        songDataFromAnyWhere = getArguments();

        //  songDataFav= getArguments();
        // long idfav = songDataFav.getLong(SongfavList.SONG_ITEM_ID);SONG_ITEM_ID
        if (songDataFromAnyWhere.containsKey("TO_SAVE")) {
            id = songDataFromAnyWhere.getLong(SongSearchActivity.SONG_ITEM_ID);
            // Inflate the layout for this fragment
            result = inflater.inflate(R.layout.activity_song__details_fragment, container, false);
            favBtn = result.findViewById(R.id.Song_detail_ToggleButton);
            favBtn.setText("ADD TO FAVORITE");
            //show the message
            songID = result.findViewById(R.id.Song_detail_Songid);
            ArtID = result.findViewById(R.id.Song_detail_ArtID);
     //       ArtID.setText("ArtID = " + songDataFromAnyWhere.getString(SongSearchActivity.SONG_ARTISTID));

            String fSongID =songDataFromAnyWhere.getString(SongSearchActivity.SONG_ID);
            String idStr="<a href=\"http://www.songsterr.com/a/wa/song?id="+fSongID+"\">songID="+fSongID+"</a>";
            songID.setMovementMethod(LinkMovementMethod.getInstance());
            songID.setText(Html.fromHtml(idStr));

            String fAratistID = songDataFromAnyWhere.getString(SongSearchActivity.SONG_ARTISTID);
            String artistIDStr = "<a href=\"http://www.songsterr.com/a/wa/artist?id="+fAratistID +"\">ArtID="+fAratistID +"</a>";
            ArtID.setMovementMethod(LinkMovementMethod.getInstance());
            ArtID.setText(Html.fromHtml(artistIDStr));

            //show the id:

//            songID.setText("SongID = " + songDataFromAnyWhere.getString(SongSearchActivity.SONG_ID));

            songTitle = result.findViewById(R.id.Song_detail_Songtitle);
            songTitle.setText("SongTitle = " + songDataFromAnyWhere.getString(SongSearchActivity.SONG_TITLE));

            artName = result.findViewById(R.id.Song_detail_Songname);
            artName.setText("artName = " + songDataFromAnyWhere.getString(SongSearchActivity.ARTIST_NAME));
        } else if (songDataFromAnyWhere.containsKey("TO_REMOVE")) {
            id = songDataFromAnyWhere.getLong(SongfavList.SONG_ITEM_ID);

            // Inflate the layout for this fragment
            result = inflater.inflate(R.layout.activity_song__details_fragment, container, false);
            favBtn = result.findViewById(R.id.Song_detail_ToggleButton);
            favBtn.setText("UNFAVORITE");
            //show the message
            ArtID = (TextView) result.findViewById(R.id.Song_detail_ArtID);
            ArtID.setText(songDataFromAnyWhere.getString(SongSearchActivity.SONG_ARTISTID));

            //show the id:
            songID = (TextView) result.findViewById(R.id.Song_detail_Songid);
            songID.setText(songDataFromAnyWhere.getString(SongSearchActivity.SONG_ID));

            String fSongID =songDataFromAnyWhere.getString(SongSearchActivity.SONG_ID);
            String idStr="<a href=\"http://www.songsterr.com/a/wa/song?id="+fSongID+"\">"+fSongID+"</a>";
            songID.setMovementMethod(LinkMovementMethod.getInstance());
            songID.setText(Html.fromHtml(idStr));

            String fArtistID = songDataFromAnyWhere.getString(SongSearchActivity.SONG_ARTISTID);
            String artistIDStr = "<a href=\"http://www.songsterr.com/a/wa/artist?id="+fArtistID +"\">"+fArtistID +"</a>";
            ArtID.setMovementMethod(LinkMovementMethod.getInstance());
            ArtID.setText(Html.fromHtml(artistIDStr));

            songTitle = (TextView) result.findViewById(R.id.Song_detail_Songtitle);
            songTitle.setText(songDataFromAnyWhere.getString(SongSearchActivity.SONG_TITLE));

            artName = (TextView) result.findViewById(R.id.Song_detail_Songname);
            artName.setText(songDataFromAnyWhere.getString(SongSearchActivity.ARTIST_NAME));
        }


        // get the delete button, and add a click listener:
//        ToggleButton songDtlsavetofvbtn = result.findViewById(R.id.Song_detail_ToggleButton);
        favBtn.setOnClickListener(clk -> {
            SongOpener dbEntry = new SongOpener(getActivity());
            db = dbEntry.getWritableDatabase();
            if (songDataFromAnyWhere.containsKey("TO_SAVE")) {
                addFavToDB();
            } else if (songDataFromAnyWhere.containsKey("TO_REMOVE")) {
                removeFavFmDB();
            }
        });
        return result;
    }

        private void addFavToDB(){
                ContentValues songNewRowValues = new ContentValues();
                songNewRowValues.put(SongOpener.SONG_ID, songID.getText().toString());

                songNewRowValues.put(SongOpener.SONG_TITLE, songTitle.getText().toString());
                songNewRowValues.put(SongOpener.ARTIST_ID, ArtID.getText().toString());
                songNewRowValues.put(SongOpener.ARTIST_NAME, artName.getText().toString());

//                db.execSQL("select"+ query +"from FAVSONG_TABLE");
                String [] columns = {SongOpener.SONG_ID};
                Cursor c = db.query(SongOpener.TABLE_NAME, columns, null, null, null, null, null);
                int songidIndex = c.getColumnIndex(SongOpener.SONG_ID);
                while(c.moveToNext()){
                    if(c.getString(songidIndex).equals(songID.getText().toString())){
                        Toast.makeText(getActivity(), "The song is already in your favorite list", Toast.LENGTH_LONG).show();
                        c.moveToLast();

                    }
                }
                if (stop) {
                    long songDfNewId = db.insert(SongOpener.TABLE_NAME, null, songNewRowValues);

                    Toast.makeText(getActivity(), "The following song is added to your favorite list" + songTitle.getText().toString(), Toast.LENGTH_LONG).show();
                }
//                Intent nextActivity = new Intent(getActivity(), SongfavList.class);
//                startActivity(nextActivity);
                parentActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();

            }

        private void removeFavFmDB(){
            db.delete(SongOpener.TABLE_NAME, SongOpener.COL_ID + "= ?", new String[] {Long.toString(songDataFromAnyWhere.getLong(SongfavList.SONG_ITEM_ID))});
            Toast.makeText(getActivity(), "Removed to your favorite list" + songTitle.getText().toString(), Toast.LENGTH_LONG).show();
//            Intent nextActivity = new Intent(getActivity(), SongfavList.class);
//            startActivity(nextActivity);
            parentActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //context will either be FragmentExample for a tablet, or EmptyActivity for phone
        parentActivity = (AppCompatActivity) context;
    }
}
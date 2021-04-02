package com.finalproject_cst2335.Song;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.finalproject_cst2335.R;

import java.util.ArrayList;

public class SongfavList extends AppCompatActivity {
    private ArrayList<SongMessage> songElements = new ArrayList<>();
    SQLiteDatabase db;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songfav_list);

  //      MyListAdapter myAdapter = new MyListAdapter();
        ListView songfvlistview = findViewById(R.id.song_fvlist_listview);
    //    songfvlistview.setAdapter(myAdapter);
        
        
       // loadSongFvListDataFromDatabase();
    }

//    private void loadSongFvListDataFromDatabase() {
//        SongOpener songdbopener = new SongOpener(this);
//        db = songdbopener.getWritableDatabase();
//        String[] columns = {SongOpener.SONG_ID, SongOpener.ARTIST_ID};
//        Cursor results = db.query(false, SongOpener.TABLE_NAME,columns,null,null,null,null,null,null);
//
//        printCursor(results,db.getVersion());
//        int songidIndex = results.getColumnIndex(SongOpener.SONG_ID);
//        int artistidIndex = results.getColumnIndex(SongOpener.ARTIST_ID);
//        int idColIndex = results.getColumnIndex(SongOpener.COL_ID);
//
//    }
//
//    private void printCursor(Cursor results, int version) {
//        int songcolumN = results.getColumnCount();
//        Log.e("Version",Integer.toString(db.getVersion()));
//        Log.e("Number Columns",Integer.toString(songcolumN));
//        for (int i = 0; i<songcolumN; i++){
//            Log.e("Column Name", results.getColumnName(i));
//        }
//        Log.e("Number Rows", Integer.toString(results.getCount()));
//        results.moveToFirst();
//        for(int i=0;i<results.getCount();i++) {
//            Log.e("row", Integer.toString(i+1)
//            + ";  Song_ID   " + results.getString(results.getColumnIndex(SongOpener.SONG_ID))
//            + ";  Artist_ID " + Integer.toString(results.getInt(results.getColumnIndex(SongOpener.ARTIST_ID))));
//            results.moveToNext();
//        }
//        results.moveToPosition(-1);
//    }
//
//
//    class MyListAdapter extends BaseAdapter {
//
//        @Override
//        public int getCount() {
//            return songElements.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return songElements.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return songElements.get(position).getId();
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            SongMessage songmsg = (SongMessage) getItem(position);
//            LayoutInflater inflater = getLayoutInflater();
//
//        return true;
//        }
//
//    }

}

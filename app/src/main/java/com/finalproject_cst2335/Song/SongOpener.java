package com.finalproject_cst2335.Song;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SongOpener extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME = "FAVSONG";
    protected final static int VERSION_NUM = 5;
    public final static String TABLE_NAME = "FAVSONG_TABLE";
    public final static String COL_ID = "_id";
    public final static String SONG_ID = "SONG_ID";
    public final static String SONG_TITLE = "SONG_TITLE";
    public final static String ARTIST_ID = "ARTIST_ID";
    public final static String ARTIST_NAME = "ARTIST_NAME";


    public SongOpener(Context ctx){
        super(ctx, DATABASE_NAME, null,VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "  (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SONG_ID + "  INTEGER,"
                + SONG_TITLE  + "  TEXT,"
                + ARTIST_ID + "  INTEGER,"
                + ARTIST_NAME  + "  TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create the new table:
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {   //Drop the old table:
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create the new table:
        onCreate(db);
    }
}

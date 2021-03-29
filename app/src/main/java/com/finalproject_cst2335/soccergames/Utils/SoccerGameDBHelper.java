package com.finalproject_cst2335.soccergames.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.finalproject_cst2335.soccergames.entities.SoccerNews;

public class SoccerGameDBHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "SoccerGames";
    private static final int DEFAULT_VERSION = 1;
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DATE = "publish_date";
    public static final String COLUMN_LINK = "link";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_THUMBNAIL = "thumbnail";

    private static final String DROP_SQL = "DROP TABLE IF EXISTS "+TABLE_NAME;
    private static final String CREATE_TABLE_SQL = " CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ( "
                                                    + COLUMN_ID + " INTEGER PRIMARY KEY, "
                                                    + COLUMN_TITLE + " TEXT NOT NULL, "
                                                    + COLUMN_DATE + " TEXT NOT NULL, "
                                                    + COLUMN_LINK + " TEXT NOT NULL, "
                                                    + COLUMN_DESCRIPTION + " TEXT NOT NULL, "
                                                    + COLUMN_THUMBNAIL + " TEXT NOT NULL )";

    public SoccerGameDBHelper( Context ctx){
        super(ctx,null,null,DEFAULT_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DROP_SQL);
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_SQL);
        db.execSQL(CREATE_TABLE_SQL);
    }

    public long addNewSoccerGame(SoccerNews news){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, news.getTitle());
        cv.put(COLUMN_DATE,news.getDate());
        cv.put(COLUMN_LINK,news.getArticleUrl());
        cv.put(COLUMN_DESCRIPTION,news.getDescription());
        cv.put(COLUMN_THUMBNAIL,news.getImage());
        return db.insert(TABLE_NAME,null,cv);
    }
}

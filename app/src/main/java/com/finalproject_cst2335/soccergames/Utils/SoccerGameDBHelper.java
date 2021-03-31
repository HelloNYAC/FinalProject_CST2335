package com.finalproject_cst2335.soccergames.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.finalproject_cst2335.soccergames.entities.SoccerNews;

import java.util.ArrayList;
import java.util.List;

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

    public List<SoccerNews> getAllGames(){
        ArrayList<SoccerNews> games = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null,null,null,null,null);
        if( cursor!=null){

            while ( cursor.moveToNext()){

                long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                String ariticleUrl = cursor.getString(cursor.getColumnIndex(COLUMN_LINK));
                String desc = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                String thumbnailLink = cursor.getString(cursor.getColumnIndex(COLUMN_THUMBNAIL));
                SoccerNews news = new SoccerNews();
                news.setTitle(title);
                news.setArticleUrl(ariticleUrl);
                news.setDate(date);
                news.setDescription(desc);
                news.setImage(thumbnailLink);
                games.add(news);

            }

        }

        return games;
    }
}

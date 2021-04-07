package com.finalproject_cst2335.trivia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TriviaDatabase extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME = "TRIVIADB";
    protected final static int VERSION_NUM = 5;
    public final static String TABLE_NAME = "RANK_TABLE";
    public final static String PLAYER_NAME = "PLAYER_NAME";
    public final static String GAME_LEVEL = "GAME_LEVEL";
    public final static String GAME_SCORE = "GAME_SCORE";
    public final static String COL_ID = "_id";

    public TriviaDatabase(Context ctx)
    {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    /**
     * To create datebase with the table and columns
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PLAYER_NAME  + "  text,"
                + GAME_LEVEL  + "  text,"
                + GAME_SCORE + " INTEGER);");
    }

    /**
     * To drop table on upgrade
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * To drop on downgrade
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

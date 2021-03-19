package com.finalproject_cst2335.trivia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TriviaMyOpener extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME = "MESSAGEDB";
    protected final static int VERSION_NUM = 3;
    public final static String TABLE_NAME = "MESSAGE_TABLE";
    public final static String SEND_TYPE = "SEND_TYPE";
    public final static String TEXT_MESSAGE = "TEXT_MESSAGE";
    public final static String COL_ID = "_id";

    public TriviaMyOpener(Context ctx)
    {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TEXT_MESSAGE + " text,"
                + SEND_TYPE  + " INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

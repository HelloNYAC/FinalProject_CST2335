package com.finalproject_cst2335.car;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class CarsDB extends SQLiteOpenHelper {
    public static final String DATABASE = "carsDB.db";

    public static final int VERSION = 1;

    public CarsDB(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String favourites = "CREATE TABLE cars (Make_ID INTEGER PRIMARY KEY," +
                "Make_Name TEXT NOT NULL," +
                "Model_ID INTEGER NOT NULL," +
                "Model_Name TEXT NOT NULL)";
        db.execSQL(favourites);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS favourites ");
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS favourites ");
        onCreate(db);
    }

    public long insertCar(Cars carModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Make_ID", carModel.getMake_ID());
        values.put("Make_Name", carModel.getMake_Name());
        values.put("Model_ID", carModel.getModel_ID());
        values.put("Model_Name", carModel.getModel_Name());

        long n = db.insert("cars", null, values);
        return n;
    }

    public List<Cars> get_favourites() {
        List<Cars> Favourites = new ArrayList<>();
        String sql = "SELECT * FROM cars";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                String make_id = (cursor.getString(cursor.getColumnIndex("Make_ID")));
                String make_name = cursor.getString(cursor.getColumnIndex("Make_Name"));
                String model_id = cursor.getString(cursor.getColumnIndex("Model_ID"));
                String model_name = cursor.getString(cursor.getColumnIndex("Model_Name"));

                Cars favouritesModel = new Cars(parseInt(make_id), make_name, Integer.parseInt(model_id), model_name);
                Favourites.add(favouritesModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return Favourites;
    }

    public int delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("cars", "Make_ID" + "='" + id + "'", null);
    }
}
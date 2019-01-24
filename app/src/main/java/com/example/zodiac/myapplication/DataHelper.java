package com.example.zodiac.myapplication;

import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Zongkir.db";
    public static final String TABLE_NAME = "ongkirTable";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "ETNAME";
    public static final String COL_3 = "COUNTRY_SPINNER";
    public static final String COL_4 = "STATE_SPINNER";
    public static final String COL_5 = "CITY_SPINNER";
    public static final String COL_6 = "ETNAMETO";
    public static final String COL_7 = "ETALAMAT";
    public static final String COL_8 = "TOCOUNTRY_SPINNER";
    public static final String COL_9 = "TOSTATE_SPINNER";
    public static final String COL_10 = "TOCITY_SPINNER";
    public static final String COL_11 = "BERAT";
    public static final String COL_12 = "ETPANJANG";
    public static final String COL_13 = "ETLEBAR";
    public static final String COL_14 = "ETTINGGI";
    public static final String COL_15 = "ETPRICE";

    public DataHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, ETNAME TEXT, COUNTRY_SPINNER TEXT, STATE_SPINNER TEXT, CITY_SPINNER TEXT, ETNAMETO TEXT, ETALAMAT TEXT, TOCOUNTRY_SPINNER TEXT, TOSTATE_SPINNER TEXT, TOCITY_SPINNER TEXT, BERAT INTEGER, ETPANJANG INTEGER, ETLEBAR INTEGER, ETTINGGI INTEGER, ETPRICE INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String etname, String countrysspinner, String statespinner, String cityspinner, String etnameto, String etalamat, String tocountryspinner, String tostatespinner, String tocityspinner, String berat, String etpanjang, String etlebar, String ettinggi, String etprice){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(COL_1,id);
        contentValues.put(COL_2, etname);
        contentValues.put(COL_3, countrysspinner);
        contentValues.put(COL_4, statespinner);
        contentValues.put(COL_5, cityspinner);
        contentValues.put(COL_6, etnameto);
        contentValues.put(COL_7, etalamat);
        contentValues.put(COL_8, tocountryspinner);
        contentValues.put(COL_9, tostatespinner);
        contentValues.put(COL_10, tocityspinner);
        contentValues.put(COL_11, berat);
        contentValues.put(COL_12, etpanjang);
        contentValues.put(COL_13, etlebar);
        contentValues.put(COL_14, ettinggi);
        contentValues.put(COL_15, etprice);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME, null);
        return res;
    }
}


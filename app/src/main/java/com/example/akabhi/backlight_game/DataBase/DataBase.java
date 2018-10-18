package com.example.akabhi.backlight_game.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

    //This is the database of this app
    private static final String DATABASE_NAME = "BackLightDataBase.db";
    private static final int DATABASE_VERSION = 1;

    //1. Score History Data======================================================
    private static final String SCORE_TABLE = "Score_Data";
    //a. its column name
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String SCORE = "score";

    private String QUERY;
    //QUERY OF THE TABLE INSIDE DATABASE
    //==============================================================================================
    //==============================================================================================
    private String CREATE_SCORE_TABLE = " CREATE table IF NOT EXISTS " + SCORE_TABLE + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT, " + SCORE + " INTEGER)";

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_SCORE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CREATE_SCORE_TABLE);
        onCreate(sqLiteDatabase);
    }

    //==============================================================================================
    //==============================================================================================
    public void Insert_Function_Score(String player_name, int score) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, player_name);
        contentValues.put(SCORE, score);
        sqLiteDatabase.insert(SCORE_TABLE, null, contentValues);
    }

    public Cursor Select_Function() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        QUERY = "SELECT * FROM " + SCORE_TABLE + " ORDER BY " + SCORE + " DESC";
        Cursor cursor = sqLiteDatabase.rawQuery(QUERY, null);
        return cursor;
    }
}

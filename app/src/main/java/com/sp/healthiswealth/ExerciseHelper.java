package com.sp.healthiswealth;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExerciseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "healthiswealth.db";
    private static final int SCHEMA_VERSION = 1;

    public ExerciseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Will be called once when the database is not created
        db.execSQL("CREATE TABLE exercises_table ( _id INTEGER PRIMARY KEY AUTOINCREMENT, healthiswealthName TEXT, healthiswealthExercise TEXT, healthiswealthReps TEXT);");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Will not be called until SCHEMA_VERSION increases
        // Here we can upgrade the database e.g. add more tables
    }

    /* Read all records from restaurants_table */
    public Cursor getAll(String orderBy) {
        return (getReadableDatabase().rawQuery(
                "SELECT _id, healthiswealthName, healthiswealthExercise, healthiswealthReps FROM exercises_table ORDER BY " + orderBy, null));
    }

    public Cursor getById(String id) {
         String[] args = {id};
         return (getReadableDatabase().rawQuery(
                 "SELECT _id, healthiswealthName, healthiswealthExercise, healthiswealthReps FROM exercises_table WHERE _ID = ?", args));
         }

    /* Write a record into alarm_table */
    public void insert(String healthiswealthName, String healthiswealthExercise, String healthiswealthReps) {
        ContentValues cv = new ContentValues();

        cv.put("healthiswealthName", healthiswealthName);
        cv.put("healthiswealthExercise", healthiswealthExercise);
        cv.put("healthiswealthReps", healthiswealthReps);

        getWritableDatabase().insert("exercises_table", "healthiswealthName", cv);
    }

    public void update(String id, String healthiswealthName, String healthiswealthExercise, String healthiswealthReps) {
         ContentValues cv = new ContentValues();
         String[] args = {id};
         cv.put("healthiswealthName", healthiswealthName);
         cv.put("healthiswealthExercise", healthiswealthExercise);
         cv.put("healthiswealthReps", healthiswealthReps);
         getWritableDatabase().update("exercises_table", cv, " _ID = ?", args);
         }



    /* Read a record id value from alarm_table */
         public String getID(Cursor c) { return (c.getString(0)); }



    public String getHealthIsWealthName(Cursor c) {
        return (c.getString(1));
    }

    public String getHealthIsWealthExercise(Cursor c) {
        return (c.getString(2));
    }


    public String getHealthIsWealthReps(Cursor c) {
        return (c.getString(3));
    }








}



package com.example.android.notepad.data;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.notepad.data.NotepadContract.NotepadEntry;

/**
 * Created by User on 5/6/2018.
 */

public class Notepad_dbHelper extends SQLiteOpenHelper {
    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION=1;
    /** Name of the database file */
    private static final String DATABASE_NAME="notepad.db";

    public Notepad_dbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table

        String CREATE_TABLE="CREATE TABLE "+ NotepadEntry.NOTEPAD_TABLE_NAME+"("+ NotepadEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
               + NotepadEntry.COLUMN_NOTES+" TEXT NOT NULL, "+ NotepadEntry.COLUMN_TITLE +" TEXT NOT NULL);";
        // Execute the SQL statement

        db.execSQL(CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

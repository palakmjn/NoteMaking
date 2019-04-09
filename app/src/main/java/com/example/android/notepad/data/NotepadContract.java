package com.example.android.notepad.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by User on 5/6/2018.
 */

public class NotepadContract {
    public  static final String CONTENT_AUTHORITY="com.example.android.notepad";
    public  static final String PATH_TABLE="notepad";


    private NotepadContract(){

}
    /**
     * Inner class that defines constant values for the pets database table.
     * Each entry in the table represents a single pet.
     */
    public static final class NotepadEntry implements BaseColumns {

        public static final String NOTEPAD_TABLE_NAME="notepad";
        public static final String _ID=BaseColumns._ID;
        public static final String COLUMN_NOTES="note";
        public static final String COLUMN_TITLE="title";
        public static final Uri BASE_URI=Uri.parse("content://"+ CONTENT_AUTHORITY);
        public  static final Uri CONTENT_URI= Uri.withAppendedPath(BASE_URI,PATH_TABLE);
        public  static final String CONTENT_LIST_TYPE= ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_TABLE;
        public  static final String CONTENT_ITEM_TYPE= ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_TABLE;
    }
    }



package com.example.android.notepad.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.notepad.data.NotepadContract.NotepadEntry;

import static com.example.android.notepad.R.id.notes;

/**
 * Created by User on 5/6/2018.
 */

public class NotepadProvider extends ContentProvider {
    private Notepad_dbHelper helper;
    private static final int NOTEPAD = 100;
    private static final int NOTEPAD_ID = 101;
    //Uri matcher object to match a content URI to a corresponding code.
    //input is always NO_MATCH to return it as a root URI
    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    //static initializer
    static {
        //call to addUri goes here,for all the content URI patterns provider should recognize.All paths added to Urimatcher
        //have a corresponding code to return when a match is found
        //the uri content://..../notepad will map to integer code 100.
        uriMatcher.addURI(NotepadContract.CONTENT_AUTHORITY, NotepadContract.PATH_TABLE, NOTEPAD);
        //the content uri content://.../notepad/# will map to integer code 101.# is replaced by number
        uriMatcher.addURI(NotepadContract.CONTENT_AUTHORITY, NotepadContract.PATH_TABLE +"/#", NOTEPAD_ID);

    }

    @Override
    public boolean onCreate() {
        helper = new Notepad_dbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortorder) {
        SQLiteDatabase db = helper.getReadableDatabase();
        int match = uriMatcher.match(uri);
        Cursor cursor;
        switch (match) {
            case NOTEPAD:
                cursor = db.query(NotepadEntry.NOTEPAD_TABLE_NAME, projection, selection, selectionArgs, null, null, sortorder);
                break;
            case NOTEPAD_ID:
                //for notepad id,extract out id from URI.
                selection = NotepadEntry._ID + "=?";
                //Thisconverts last segment of uri path into number,
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(NotepadEntry.NOTEPAD_TABLE_NAME, projection, selection, selectionArgs, null, null, sortorder);
                break;
            default:
                throw new IllegalArgumentException("unknown query");
        }
        //set notification uri on cursor so we know what content uri cursor was created for.
        //if data at this URI changes,then we know we need to update the cursor.
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        int match = uriMatcher.match(uri);
        switch (match){
            case NOTEPAD:
                return NotepadEntry.CONTENT_LIST_TYPE;
            case NOTEPAD_ID:
                return NotepadEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("invalid");
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        switch (match) {
            //only case1 because it does'nt make sense to insert into a single row
            case NOTEPAD:
                return insertnote(uri, contentValues);
            default:
                throw new IllegalArgumentException("no insertion");
        }
    }

    public Uri insertnote(Uri uri, ContentValues values) {
        String notes = values.getAsString(NotepadEntry.COLUMN_NOTES);
        if (notes == null)
            throw new IllegalArgumentException("not possible");
        String title = values.getAsString(NotepadEntry.COLUMN_TITLE);
        if (notes == null)
            throw new IllegalArgumentException("not possible");
        SQLiteDatabase db = helper.getWritableDatabase();
        //insert new pet with given values
        long id = db.insert(NotepadEntry.NOTEPAD_TABLE_NAME, null, values);
        if (id == -1) {
            Log.e("Palak", "Insertion failed");
            return null;
        }
        //Notify all listeners that the data has changed for the note content uri
        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri, id);
    }


    @Override
    public int delete(Uri uri, String s, String[] strings) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int rowdel;
        switch (match) {
            case NOTEPAD:
                rowdel= db.delete(NotepadEntry.NOTEPAD_TABLE_NAME,s,strings);
                break;
            case NOTEPAD_ID:
                s=NotepadEntry._ID + "=?";
               strings = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowdel= db.delete(NotepadEntry.NOTEPAD_TABLE_NAME,s,strings);
                break;
        default:
        throw new IllegalArgumentException("invalid");}
        //if 1 or more rows is deleted,then notify all listeners that the data
        //  at this uri has changed
        if(rowdel!=0)
            getContext().getContentResolver().notifyChange(uri,null);
return rowdel;

    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        int match = uriMatcher.match(uri);
        switch (match) {
            case NOTEPAD:
                return updatenote(uri,contentValues,selection,selectionArgs);
            case NOTEPAD_ID:
                selection = NotepadEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updatenote(uri,contentValues,selection,selectionArgs);
            default:
                throw new IllegalArgumentException("wrong update");
        }
    }
    //update notepad with the given values;apply changes to rows spcified in selection.
    public int updatenote(Uri uri,ContentValues values,String selection,String[] selectionArgs){
        SQLiteDatabase db = helper.getWritableDatabase();
        int rowdel;
        //if key is present,check notes are not null.
        if(values.containsKey(NotepadEntry.COLUMN_NOTES)){
        String notes=values.getAsString(NotepadEntry.COLUMN_NOTES);
            if(notes==null)
                throw new IllegalArgumentException("wrong update");}
        if(values.containsKey(NotepadEntry.COLUMN_TITLE)){
            String title=values.getAsString(NotepadEntry.COLUMN_TITLE);
            if(title==null)
                throw new IllegalArgumentException("wrong update");}
       rowdel= db.update(NotepadEntry.NOTEPAD_TABLE_NAME,values,selection,selectionArgs);
        //if 1 or more rows is changed,then notify all listeners that the data
        //  at this uri has changed
        if(rowdel!=0)
            getContext().getContentResolver().notifyChange(uri,null);
        return rowdel;

    }
}


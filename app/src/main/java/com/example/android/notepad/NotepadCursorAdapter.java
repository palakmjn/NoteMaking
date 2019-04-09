package com.example.android.notepad;

import android.content.Context;
import android.database.Cursor;
import java.util.Calendar;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.notepad.data.NotepadContract;

import static android.icu.util.Calendar.getInstance;

/**
 * Created by User on 5/6/2018.
 */

public class NotepadCursorAdapter extends CursorAdapter {
    public NotepadCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
     //creates and returns blank new list item view from XML file
        return LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
//read data from current row in cursor and set it to views within
// list item view generated from new view.
        TextView t1=(TextView)view.findViewById(R.id.title1);
        String title=cursor.getString(cursor.getColumnIndex(NotepadContract.NotepadEntry.COLUMN_TITLE));
        t1.setText(title);
        TextView t2=(TextView)view.findViewById(R.id.date);
        Calendar calendar= Calendar.getInstance();
        int yy=calendar.get(Calendar.SECOND);
        int mm=calendar.get(Calendar.MONTH);
        int dd=calendar.get(Calendar.HOUR);
        t2.setText(new StringBuilder().append(yy).append(" ").append("-").append(mm+1).append("-").append(dd));
    }
}

package com.example.android.notepad;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.notepad.data.NotepadContract.NotepadEntry;
import com.example.android.notepad.data.Notepad_dbHelper;

import static android.R.attr.id;
import static android.R.id.text1;
import static com.example.android.notepad.R.id.delete;


/**
 * Created by User on 5/6/2018.
 */

public class EditorActivity extends AppCompatActivity {
    private Uri muri;
    private EditText text1;
    private EditText text2;
    Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Intent intent=getIntent();
       muri=intent.getData();
         text1=(EditText)findViewById(R.id.notes);
        text2=(EditText)findViewById(R.id.title);
        calendar=Calendar.getInstance();
        int yy=calendar.get(Calendar.SECOND);
        int mm=calendar.get(Calendar.MONTH);
        int dd=calendar.get(Calendar.HOUR);

        if(muri!=null)
        {
            String[] proj={NotepadEntry._ID,NotepadEntry.COLUMN_TITLE,NotepadEntry.COLUMN_NOTES};
            Cursor c=getContentResolver().query(muri,proj,null,null,null);
            if(c.moveToFirst()){
                String note=c.getString(c.getColumnIndex(NotepadEntry.COLUMN_NOTES));
                String title=c.getString(c.getColumnIndex(NotepadEntry.COLUMN_TITLE));
                //String date=c.getString(c.get);
                text1.setText(note);
                text2.setText(title);
            }
        }


 }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.editor_menu, menu);
        return true;
    }
public  void showDeleteDialogBox(){
    //Create an AlertDialog.Builder and set message,and click listeners

    //for positive and negative buttons on dialog.
    AlertDialog.Builder builder=new AlertDialog.Builder(this);
    builder.setTitle("Delete Entry");
    builder.setMessage(R.string.delete_msg);
    builder.setPositiveButton(R.string.delete,new DialogInterface.OnClickListener(){
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
 deleteNote();
        }

    });
    builder.setNegativeButton(R.string.cancel,new DialogInterface.OnClickListener(){
        @Override
        public void onClick(DialogInterface dialog, int i) {
            dialog.dismiss();
        }
    });
    //Create and show AlertDialog
    AlertDialog alertDialog=builder.create();
    alertDialog.show();
}
    public void deleteNote(){
       int rows=getContentResolver().delete(muri,null,null);
        if(rows==0)
            Toast.makeText(this,"error in deleting",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"note deleted",Toast.LENGTH_SHORT).show();
        //Close the activity
        finish();
    }
public void insert(){


    String notes=text1.getText().toString();
    String title=text2.getText().toString();
    ContentValues values=new ContentValues();
    values.put(NotepadEntry.COLUMN_NOTES,notes);
    //Insert a new note into Content provider,returning content uri for this new note.
    values.put(NotepadEntry.COLUMN_TITLE,title);
    if(muri==null){
    Uri nuri=getContentResolver().insert(NotepadEntry.CONTENT_URI,values);
    if(nuri==null)
     Toast.makeText(this,"not possible",Toast.LENGTH_SHORT).show();
    else
        Toast.makeText(this,"note added",Toast.LENGTH_SHORT).show();}
    else{
        //This is an existing note,so update the note;pass in new ContentValues
        int rowsAffected=getContentResolver().update(muri,values,null,null);
        if(rowsAffected==0)
            Toast.makeText(this,"not possible",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"note updated",Toast.LENGTH_SHORT).show();
    }

}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case delete:
                showDeleteDialogBox();
                break;
            case R.id.save:
                insert();
                break;

        }
        return super.onOptionsItemSelected(item);

    }}

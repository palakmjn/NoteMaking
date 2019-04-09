package com.example.android.notepad;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.notepad.data.NotepadContract;
import com.example.android.notepad.data.NotepadContract.NotepadEntry;
import com.example.android.notepad.data.Notepad_dbHelper;

public class MainActivity extends AppCompatActivity {
Cursor cursor;
    private  ListView lv;
    private NotepadCursorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        lv=(ListView)findViewById(R.id.listview);
        //Create custom adapter to create list item for every row in cursor, by passing current activity context(this)
        // and cursor containing items(initially null)
        adapter=new NotepadCursorAdapter(this,null);//cursor or null
        lv.setAdapter(adapter);
        //set up item click listener
       lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
           @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
               Intent intent=new Intent(MainActivity.this,EditorActivity.class);
//                //form the content uri that represents the specific note was clicked on,by
//                //appending id(passed on as input to this method)onto the CONTENT_URI.
                Uri curi= ContentUris.withAppendedId(NotepadEntry.CONTENT_URI,id);
//                //set URI on the data field of intent
               intent.setData(curi);
//                //launch the activity
                startActivity(intent);
            }
        });
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        Notepad_dbHelper helper=new Notepad_dbHelper(this);

    }
@Override
protected void onResume(){
    super.onResume();
    String[] projection={NotepadEntry._ID,NotepadEntry.COLUMN_TITLE,NotepadEntry.COLUMN_NOTES};
    Cursor ncursor=getContentResolver().query(NotepadEntry.CONTENT_URI,projection,null,null,null);
    adapter.swapCursor(ncursor);

}
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // User clicked on a menu option in the app bar overflow menu

        switch(item.getItemId()) {
            case R.id.delete:
                deleteAllNotes();
                return true;

        }
            return super.onOptionsItemSelected(item);
        }
    public void deleteAllNotes(){
        int rows=getContentResolver().delete(NotepadEntry.CONTENT_URI,null,null);
        if(rows==0)
            Toast.makeText(this,"error in deleting",Toast.LENGTH_SHORT).show();
            else
            Toast.makeText(this,"Delete successful",Toast.LENGTH_SHORT).show();
    }

}

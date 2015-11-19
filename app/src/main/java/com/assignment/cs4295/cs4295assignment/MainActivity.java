package com.assignment.cs4295.cs4295assignment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private ListView lv;
    private ArrayList<Event> data;
    private EventListAdapter adapter;

    private void loadEventList() {
        //clear the existing data from the original list
        data.clear();

        //get the current date
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MMM dd, HH:MM");
        String currentDate = df.format(c.getTime());

        //execute query from database
        EventDBHelper dbHelper=new EventDBHelper(this);
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String[] projection={
                EventEntry.COL_NAME_TASK_TITLE,
                EventEntry.COL_NAME_TASK_DESC,
                EventEntry.COL_NAME_TASK_DATE};
        String orderBy = EventEntry.COL_NAME_PUSH_IND + " DESC ,  " + EventEntry.COL_NAME_TASK_DATE + " ASC ";
        String whereCause = EventEntry.COL_NAME_TASK_DATE + " >= '" + currentDate + "'";
        Cursor cursor=db.query(EventEntry.TBL_NAME, projection, whereCause, null, null, null, orderBy, null);

        //read data from the result
        if (cursor.moveToFirst()) {
            do{
                Event t = new Event();
                t.setTitle(cursor.getString(0));
                t.setDesc(cursor.getString(1));
                t.setDate(cursor.getString(2));
                data.add(t);
            }while (cursor.moveToNext());
        }

        cursor.close();
        adapter.notifyDataSetChanged(); //update the list view
    }

    //push the item on the top that user feel interested of it
    private void pushItemUp(String eventTitle) {
        EventDBHelper dbHelper=new EventDBHelper(this);
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EventEntry.COL_NAME_PUSH_IND, "1");
        String whereCause = EventEntry.COL_NAME_TASK_TITLE + "='" + eventTitle + "' ";
        db.update(EventEntry.TBL_NAME, contentValues, whereCause, null);
        loadEventList();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int listPosition = info.position;
        String eventTitle = data.get(listPosition).getTitle();

        switch(item.getItemId()) {
            case R.id.menu_Push:
                pushItemUp(eventTitle);
                break;
            case R.id.menu_video:
                Intent videoInt =new Intent(this,VideoActivity.class);
                videoInt.putExtra("title_name", eventTitle);
                startActivity(videoInt);
                break;
            case R.id.menu_location:
                Intent mapInt = new Intent(this, MapsActivity.class);
                mapInt.putExtra("latitude", 22.3371078);
                mapInt.putExtra("longitude", 114.170141);
                startActivity(mapInt);
                break;
            default:
                return super.onContextItemSelected(item);
        }

        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context, menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initial setup
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list_main);
        data =  new ArrayList<Event>();
        adapter = new EventListAdapter(this, data);

        //load data to the list
        loadEventList();

        //set data onto the screen
        lv = (ListView) findViewById(R.id.TaskList);
        lv.setAdapter(adapter);
        this.registerForContextMenu(lv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

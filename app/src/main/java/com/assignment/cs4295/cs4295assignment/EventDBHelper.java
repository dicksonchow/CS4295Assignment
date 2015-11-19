package com.assignment.cs4295.cs4295assignment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EventDBHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "task.db";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
            + EventEntry.TBL_NAME + " (" +
            EventEntry._ID + " INTEGER PRIMARY KEY, " +
            EventEntry.COL_NAME_TASK_TITLE + " TEXT, " +
            EventEntry.COL_NAME_TASK_DESC + " TEXT, " +
            EventEntry.COL_NAME_TASK_DATE + " TEXT," +
            EventEntry.COL_NAME_PUSH_IND + " TEXT );";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "+ EventEntry.TBL_NAME;

    private void createDataEntries(SQLiteDatabase db, String id, String name, String venue, String time, String pushInd) {
        String insertStatement = "INSERT INTO " + EventEntry.TBL_NAME + " VALUES ( '"+
                id + "', '" + name + "', '" + venue + "', '" + time + "', '" + "0' );";
        db.execSQL(insertStatement);
    }

    public EventDBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        createDataEntries(db, "1", "Importance of Glassware", "Room 321", "Nov 1, 10:00", "0");
        createDataEntries(db, "2", "Wine as Agriculture", "Room 401", "Nov 29, 12:00", "0");
        createDataEntries(db, "3", "How to Store Wine", "Room 555", "Nov 29, 13:00", "0");
        createDataEntries(db, "4", "Natural Wines", "Room 602", "Nov 29, 14:00", "0");
        createDataEntries(db, "5", "Astringency in Red Wine", "Room 704", "Nov 30, 17:00", "0");
        createDataEntries(db, "6", "Red Wine Benefits", "Room 102", "Nov 30, 10:00", "0");
        createDataEntries(db, "7", "Drinking Wine for Anti Aging", "Room 507", "Nov 30, 12:00", "0");
        createDataEntries(db, "8", "Red wine and health", "Room 800", "Nov 30, 15:00", "0");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}

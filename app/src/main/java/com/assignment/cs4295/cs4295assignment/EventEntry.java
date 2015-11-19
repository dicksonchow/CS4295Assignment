package com.assignment.cs4295.cs4295assignment;
import android.provider.BaseColumns;

public abstract class EventEntry implements BaseColumns {
    public static final String TBL_NAME = "event";
    public static final String COL_NAME_TASK_TITLE = "name";
    public static final String COL_NAME_TASK_DESC = "venue";
    public static final String COL_NAME_TASK_DATE = "date";
    public static final String COL_NAME_PUSH_IND = "push_ind";
}

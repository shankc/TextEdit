package com.kaidoh.mayuukhvarshney.textedit.Contracts;

import android.provider.BaseColumns;

/**
 * Created by mayuukhvarshney on 10/09/17.
 */

public class TextEditContract {

    private TextEditContract(){}

    public static class TextEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String DATE_CREATED="date_created";
        public static final String COLUMN_NAME_TITLE = "title";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TextEntry.TABLE_NAME + " (" +
                    TextEntry._ID + " INTEGER PRIMARY KEY," +
                    TextEntry.DATE_CREATED+" datetime,"+
                    TextEntry.COLUMN_NAME_TITLE + " TEXT)"
                   ;

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TextEntry.TABLE_NAME;
}



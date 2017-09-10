package com.kaidoh.mayuukhvarshney.textedit;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Html;

import com.kaidoh.mayuukhvarshney.textedit.Contracts.TextEditContract;
import com.kaidoh.mayuukhvarshney.textedit.DBHelper.TextDBhelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayuukhvarshney on 11/09/17.
 */

public class Utils {

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd");


    public static String getLatestPost(Context context)
    {
        TextDBhelper mDbHelper = new TextDBhelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String latestPost = "";
        String queryText = "select * from "
                + TextEditContract.TextEntry.TABLE_NAME
                + " order by date( "
                +TextEditContract.TextEntry.DATE_CREATED
                + ") desc, _id desc limit 1";
        Cursor cursor = db.rawQuery(queryText,null);

        List itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(TextEditContract.TextEntry._ID));

           latestPost = Html.fromHtml(cursor.getString(cursor.getColumnIndexOrThrow(TextEditContract.TextEntry.COLUMN_NAME_TITLE))).toString();

            itemIds.add(itemId);

        }
        cursor.close();

        return latestPost;
    }

}

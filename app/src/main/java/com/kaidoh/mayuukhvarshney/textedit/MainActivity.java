package com.kaidoh.mayuukhvarshney.textedit;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kaidoh.mayuukhvarshney.textedit.Contracts.TextEditContract;
import com.kaidoh.mayuukhvarshney.textedit.DBHelper.TextDBhelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import jp.wasabeef.richeditor.RichEditor;

import static com.kaidoh.mayuukhvarshney.textedit.Contracts.TextEditContract.TextEntry.TABLE_NAME;

public class MainActivity extends AppCompatActivity {

    TextView tvWordCount;
    RichEditor tvNovelText;
    Button btnSave, btnSubmit;
    public static final String TAG = MainActivity.class.getSimpleName();
    public static String LAST_SAVED_TEXT = "last_saved_text";
    public static String LAST_SAVED_WORD_COUNT ="last_saved_word_count";
    TextDBhelper mDbHelper = new TextDBhelper(this);

    boolean italicSwitch = false;
    boolean bulletSwitch = false;
    private Calendar mCurrentDate;
    private static String currentText="";
    private static String mWordCount="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setUpViewObjects();

        loadLastState();

        setDate(Calendar.getInstance());

        setUpWordCountObserver();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!currentText.equals(""))
                {
                    saveTextToDatabase(currentText);
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,OutputTextActivity.class);
                startActivity(intent);

            }
        });
    }

    @TargetApi(21)
    private void setDate(Calendar cal) {
        mCurrentDate = (Calendar) cal.clone();
    }

    private void loadLastState() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String lastSavedText = sharedPref.getString(LAST_SAVED_TEXT,"");
        String lastSavedWordCount = sharedPref.getString(LAST_SAVED_WORD_COUNT,"");
        if(!lastSavedText.equals(""))
        {
            tvNovelText.setHtml(lastSavedText);
        }
        if(!lastSavedWordCount.equals(""))
        {
            tvWordCount.setText(lastSavedWordCount);
        }
    }

    private void saveTextToDatabase(String text) {

        Log.d(TAG,"The String is "+text);
        String htmlString = Html.toHtml(new SpannableString(text));
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        SimpleDateFormat sdf = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.US);

        ContentValues values = new ContentValues();
        values.put(TextEditContract.TextEntry.COLUMN_NAME_TITLE, htmlString);
        values.put(TextEditContract.TextEntry.DATE_CREATED,sdf.format(mCurrentDate.getTime()));

        long newRowId = db.insert(TABLE_NAME, null, values);
        if(newRowId>0)
        {
            Toast.makeText(this,"Data saved to Database",Toast.LENGTH_SHORT).show();
        }
        Log.d(TAG,"The new row inserted "+newRowId);
    }

    private void setUpViewObjects() {

        tvNovelText = (RichEditor) findViewById(R.id.tvNovelText);
        tvWordCount = (TextView) findViewById(R.id.tvWordCount);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

    }

    private void setUpWordCountObserver() {

        tvNovelText.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
                currentText = text;
                String totalWordCount = String.valueOf(text.length());
                mWordCount = totalWordCount;
                tvWordCount.setText(totalWordCount);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.text_format_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_italic_format:
                italicSwitch = (!italicSwitch) ? true : false;
                tvNovelText.setItalic();
                return true;

            case R.id.action_bullet_format:
                bulletSwitch = (!bulletSwitch) ? true : false;
                tvNovelText.setBullets();
                return true;

            case R.id.action_quotes_format:
                tvNovelText.setBlockquote();
                return true;
            case R.id.action_left_align:
                tvNovelText.setAlignLeft();
                return true;
            case R.id.action_right_align:
                tvNovelText.setAlignRight();
                return true;

            case R.id.action_center_align:
                tvNovelText.setAlignCenter();
                return true;

            case R.id.action_underline_format:
                tvNovelText.setUnderline();
                return true;

            case R.id.action_bold_format:
                tvNovelText.setBold();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(LAST_SAVED_TEXT, currentText);
        editor.putString(LAST_SAVED_WORD_COUNT,mWordCount);
        saveTextToDatabase(currentText);
        editor.commit();

    }
    @Override
   protected void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState.putString(LAST_SAVED_TEXT,currentText);
        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        Log.d(TAG,savedInstanceState.getString(LAST_SAVED_TEXT));
        super.onRestoreInstanceState(savedInstanceState);

    }

}

package com.kaidoh.mayuukhvarshney.textedit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
/**
 * Created by mayuukhvarshney on 11/09/17.
 */

public class OutputTextActivity extends AppCompatActivity  {

    public static final String TAG = OutputTextActivity.class.getSimpleName();
    private WebView wbOutputText;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.output_activity_layout);

        wbOutputText = (WebView) findViewById(R.id.tvNovelOutput);

        Log.d(TAG,"The text to be displayed is "+Utils.getLatestPost(this));
        String temp = Utils.getLatestPost(this);
        temp = temp.replace("&nbsp;","");
        temp = temp.replace("<br>","\n -");
        wbOutputText.getSettings().setJavaScriptEnabled(true);
        wbOutputText.loadData(temp, "text/html", null);
        Log.d(TAG,"The text is "+temp);
    }

}


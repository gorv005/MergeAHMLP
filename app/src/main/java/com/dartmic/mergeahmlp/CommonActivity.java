package com.dartmic.mergeahmlp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.dartmic.mergeahmlp.Constants.FixedData;

public class CommonActivity extends AppCompatActivity {

    Context context;
    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;

        wv = (WebView) findViewById(R.id.webView);
        WebSettings mWebSettings = wv.getSettings();
        mWebSettings.setBuiltInZoomControls(true);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setDefaultTextEncodingName("utf-8");

        if (FixedData.fixedIntent == 7) {
            getSupportActionBar().setTitle("About Us");
            wv.loadUrl("file:///android_asset/html/data07.html");
        }
        if (FixedData.fixedIntent == 8) {
            getSupportActionBar().setTitle("Design Capabilities");
            wv.loadUrl("file:///android_asset/html/data08.html");
        }
        if (FixedData.fixedIntent == 9) {
            getSupportActionBar().setTitle("Achievements");
            wv.loadUrl("file:///android_asset/html/data09.html");
        }
        if (FixedData.fixedIntent == 10) {
            getSupportActionBar().setTitle("Plants and Distribution");
            wv.loadUrl("file:///android_asset/html/data10.html");
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(context, About_us.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }
}

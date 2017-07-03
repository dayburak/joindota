package com.unofficial.joindota;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

/**
 * Created by burak on 27.06.2017.
 */

public class Single_Live_Stream_Activity extends Activity {
    WebView webView;
    String stream_link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        START_RECEIVER();
        setContentView(R.layout.single_live_stream_layout);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            /*onBackPressed();
            return true;*/
        }
        return super.onKeyDown(keyCode, event);
    }

    /*@Override
    public void onBackPressed() {
    }*/

    @Override
    public void onConfigurationChanged(Configuration config)
    {
        super.onConfigurationChanged(config);
    }

    @Override
    public void onStop ()
    {
        super.onStop();
    }

    @Override
    public void onDestroy()
    {
        STOP_RECEIVER();
        super.onDestroy();
    }

    public void START_RECEIVER ()
    {
        registerReceiver(receiver, new IntentFilter("stream_link"));
    }

    public void STOP_RECEIVER ()
    {
        unregisterReceiver(receiver);
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            stream_link = intent.getStringExtra("stream_link");
            Parser.LOG(stream_link);
            webView = (WebView) findViewById(R.id.webView);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(stream_link);
        }
    };
}

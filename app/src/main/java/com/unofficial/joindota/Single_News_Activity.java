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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class Single_News_Activity extends Activity {

    WebView webView2;
    String news_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        news_link = getIntent().getStringExtra("news_link");
        setContentView(R.layout.single_news_layout);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Element element       = Jsoup.connect(news_link).get().select("div#content").last().child(0);
                    String content        =
                            "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"+
                                    "<html><head>"+
                                    "<style type=\"text/css\">body{color: #fff; background-color: #000000;}"
                                    + "</style>" +
                                    "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" />"+
                                    "<head><body>";
                    final String html     = content + element.toString() + "</body></html>";
                    final String mime     = "text/html; charset=utf-8";
                    final String encoding = "UTF-8";

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            webView2 = findViewById(R.id.webView2);
                            webView2.getSettings().setJavaScriptEnabled(true);
                            webView2.getSettings().setLoadWithOverviewMode(true);
//                            webView2.getSettings().setUseWideViewPort(true);
                            webView2.getSettings().setBuiltInZoomControls(true);
                            webView2.loadDataWithBaseURL(news_link, html, mime, encoding, "");
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        /*if (keyCode == KeyEvent.KEYCODE_BACK) {
            *//*onBackPressed();
            return true;*//*
        }*/
        return super.onKeyDown(keyCode, event);
    }

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
        super.onDestroy();
    }
}


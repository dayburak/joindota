package com.unofficial.joindota;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    public static Activity main_activity;
    Parser                      parser;
    android.app.FragmentManager fragment_manager;
    FloatingActionButton        refresher;
    Animation                   refresher_on_cooldown;
    Animation                   rotate_forward;
    Animation                   rotate_backward;
    BottomNavigationView        navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_news:
                    fragment_manager.beginTransaction().replace(R.id.inner_content, new News_Fragment()).commit();
                    return true;
                case R.id.navigation_live_streams:
                    fragment_manager.beginTransaction().replace(R.id.inner_content, new Live_Streams_Fragment()).commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_activity         = this;
        refresher             = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fragment_manager      = getFragmentManager();
        refresher_on_cooldown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.refresher_on_cooldown);
        rotate_forward        = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward       = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        parser                = new Parser();
        parser.PARSE_FROM_SCRATCH();

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragment_manager.beginTransaction().replace(R.id.inner_content, new News_Fragment()).commit();
        refresher.startAnimation(refresher_on_cooldown);

        refresher.setOnClickListener(this);
    }

    public void ANIMATE_REFRESHER ()
    {
        refresher.startAnimation(rotate_forward);
        refresher.setClickable(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                WAIT_FOR_ANIMATION(300);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refresher.startAnimation(rotate_backward);
                    }
                });
                WAIT_FOR_ANIMATION(300);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refresher.startAnimation(refresher_on_cooldown);
                    }
                });
                WAIT_FOR_ANIMATION(5001);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refresher.setClickable(true);
                    }
                });
            }
        }).start();
    }

    public void WAIT_FOR_ANIMATION (long duration)
    {
        while (duration > 0) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            duration -=20;
        }
    }

    public void REFRESH ()
    {
        if (Parser.news_list.size() != 8 || Parser.streams_list.size() != 10
                || Parser.news_list.get(7).m_image_bitmap == null || Parser.streams_list.get(9).m_flag_bitmap == null) {
            return;
        }
        MediaPlayer sound_player = MediaPlayer.create(this, R.raw.refresher_orb_sound);
        sound_player.start();

        parser.PARSE_FROM_SCRATCH();
        switch(navigation.getSelectedItemId()) {
            case R.id.navigation_news:
                fragment_manager.beginTransaction().replace(R.id.inner_content, new News_Fragment()).commit();
                break;
            case R.id.navigation_live_streams:
                fragment_manager.beginTransaction().replace(R.id.inner_content, new Live_Streams_Fragment()).commit();
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.floatingActionButton:
                ANIMATE_REFRESHER();
                REFRESH();
                break;
        }
    }
}

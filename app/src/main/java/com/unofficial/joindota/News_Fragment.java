package com.unofficial.joindota;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by burak on 27.06.2017.
 */

public class News_Fragment extends Fragment {

    View news_view;

    ArrayList<TextView> titles;
    ArrayList<TextView> contents;
    ArrayList<ImageView> images;
    ArrayList<ProgressBar> bars;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        news_view = inflater.inflate(R.layout.news_layout, container, false);
        IDENTIFY_AND_GROUP_UI_ELEMENTS();
        INITIALIZE_UI_ELEMENTS();
        return news_view;
    }

    public void IDENTIFY_AND_GROUP_UI_ELEMENTS ()
    {
        Resources resources = getResources();
        ImageView header_image = (ImageView) news_view.findViewById(R.id.imageView);
        header_image.setImageDrawable(resources.getDrawable(R.mipmap.joindota1));

        ImageView news_image  = (ImageView) news_view.findViewById(R.id.imageView2);
        ImageView news_image1 = (ImageView) news_view.findViewById(R.id.imageView3);
        ImageView news_image2 = (ImageView) news_view.findViewById(R.id.imageView4);
        ImageView news_image3 = (ImageView) news_view.findViewById(R.id.imageView5);
        ImageView news_image4 = (ImageView) news_view.findViewById(R.id.imageView6);
        ImageView news_image5 = (ImageView) news_view.findViewById(R.id.imageView7);
        ImageView news_image6 = (ImageView) news_view.findViewById(R.id.imageView8);
        ImageView news_image7 = (ImageView) news_view.findViewById(R.id.imageView9);

        TextView title1 = (TextView) news_view.findViewById(R.id.textView);
        TextView title2 = (TextView) news_view.findViewById(R.id.textView3);
        TextView title3 = (TextView) news_view.findViewById(R.id.textView5);
        TextView title4 = (TextView) news_view.findViewById(R.id.textView7);
        TextView title5 = (TextView) news_view.findViewById(R.id.textView9);
        TextView title6 = (TextView) news_view.findViewById(R.id.textView11);
        TextView title7 = (TextView) news_view.findViewById(R.id.textView13);
        TextView title8 = (TextView) news_view.findViewById(R.id.textView15);

        TextView content1 = (TextView) news_view.findViewById(R.id.textView2);
        TextView content2 = (TextView) news_view.findViewById(R.id.textView4);
        TextView content3 = (TextView) news_view.findViewById(R.id.textView6);
        TextView content4 = (TextView) news_view.findViewById(R.id.textView8);
        TextView content5 = (TextView) news_view.findViewById(R.id.textView10);
        TextView content6 = (TextView) news_view.findViewById(R.id.textView12);
        TextView content7 = (TextView) news_view.findViewById(R.id.textView14);
        TextView content8 = (TextView) news_view.findViewById(R.id.textView16);

        ProgressBar bar1 = (ProgressBar) news_view.findViewById(R.id.progressBar2);
        ProgressBar bar2 = (ProgressBar) news_view.findViewById(R.id.progressBar3);
        ProgressBar bar3 = (ProgressBar) news_view.findViewById(R.id.progressBar4);
        ProgressBar bar4 = (ProgressBar) news_view.findViewById(R.id.progressBar5);
        ProgressBar bar5 = (ProgressBar) news_view.findViewById(R.id.progressBar6);
        ProgressBar bar6 = (ProgressBar) news_view.findViewById(R.id.progressBar7);
        ProgressBar bar7 = (ProgressBar) news_view.findViewById(R.id.progressBar8);
        ProgressBar bar8 = (ProgressBar) news_view.findViewById(R.id.progressBar9);

        bars = new ArrayList<ProgressBar>();
        bars.add(bar1);
        bars.add(bar2);
        bars.add(bar3);
        bars.add(bar4);
        bars.add(bar5);
        bars.add(bar6);
        bars.add(bar7);
        bars.add(bar8);

        if (titles != null || contents != null || images != null) {
            for (ProgressBar bar : bars) {
                bar.setVisibility(View.GONE);
            }
            return;
        }

        titles   = new ArrayList<TextView>();
        contents = new ArrayList<TextView>();
        images   = new ArrayList<ImageView>();

        images.add(news_image);
        images.add(news_image1);
        images.add(news_image2);
        images.add(news_image3);
        images.add(news_image4);
        images.add(news_image5);
        images.add(news_image6);
        images.add(news_image7);

        titles.add(title1);
        titles.add(title2);
        titles.add(title3);
        titles.add(title4);
        titles.add(title5);
        titles.add(title6);
        titles.add(title7);
        titles.add(title8);

        contents.add(content1);
        contents.add(content2);
        contents.add(content3);
        contents.add(content4);
        contents.add(content5);
        contents.add(content6);
        contents.add(content7);
        contents.add(content8);
    }

    public void INITIALIZE_UI_ELEMENTS ()
    {
        if (Parser.news_list == null) {
            return;
        }

        new Thread() {
            @Override
            public void run() {
                long timeout = 10000;
                while (Parser.news_list.size() != 8) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    timeout -= 50;
                    if (timeout <= 0) {
                        MainActivity.main_activity.finish();
                        break;
                    }
                }

                final View.OnClickListener listener = new View.OnClickListener() {
                    public void onClick(View v) {
                        if (v.getId() == R.id.imageView2 || v.getId() == R.id.textView) {
                            START_NEWS(Parser.news_list.get(0).m_page_link);
                        }
                        if (v.getId() == R.id.imageView3 || v.getId() == R.id.textView3) {
                            START_NEWS(Parser.news_list.get(1).m_page_link);
                        }
                        if (v.getId() == R.id.imageView4 || v.getId() == R.id.textView5) {
                            START_NEWS(Parser.news_list.get(2).m_page_link);
                        }
                        if (v.getId() == R.id.imageView5 || v.getId() == R.id.textView7) {
                            START_NEWS(Parser.news_list.get(3).m_page_link);
                        }
                        if (v.getId() == R.id.imageView6 || v.getId() == R.id.textView9) {
                            START_NEWS(Parser.news_list.get(4).m_page_link);
                        }
                        if (v.getId() == R.id.imageView7 || v.getId() == R.id.textView11) {
                            START_NEWS(Parser.news_list.get(5).m_page_link);
                        }
                        if (v.getId() == R.id.imageView8 || v.getId() == R.id.textView13) {
                            START_NEWS(Parser.news_list.get(6).m_page_link);
                        }
                        if (v.getId() == R.id.imageView9 || v.getId() == R.id.textView15) {
                            START_NEWS(Parser.news_list.get(7).m_page_link);
                        }
                    }
                };

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < images.size(); i++) {
                            while (Parser.news_list.get(i).m_image_bitmap == null) {
                                try {
                                    Thread.sleep(50);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            //new Image_Downloader(Parser.news_list.get(i).m_image_link).execute();
                            final int finalI = i;
                            MainActivity.main_activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    bars.get(finalI).setVisibility(View.GONE);
                                    images.get(finalI).setImageBitmap(Parser.news_list.get(finalI).m_image_bitmap);
                                }
                            });
                            images.get(i).setClickable(true);
                            images.get(i).setOnClickListener(listener);
                            //Parser.LOG(i + "images count");
                        }
                    }
                }).start();

                MainActivity.main_activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < titles.size(); i++) {
                            titles.get(i).setText(Parser.news_list.get(i).m_title);
                            titles.get(i).setTextColor(Color.rgb(255, 144, 18));
                            titles.get(i).setClickable(true);
                            titles.get(i).setOnClickListener(listener);
                        }

                        for (int i = 0; i < contents.size(); i ++) {
                            contents.get(i).setText(Parser.news_list.get(i).m_content);
                        }
                    }
                });
            }
        }.start();
    }

    public void START_NEWS (final String news_link)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.main_activity.getApplicationContext(), Single_News_Activity.class);
                startActivity(intent);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent2 = new Intent("news_link");
                intent2.putExtra("news_link", news_link);
                MainActivity.main_activity.sendBroadcast(intent2);
            }
        }).start();
    }
}
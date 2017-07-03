package com.unofficial.joindota;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by burak on 27.06.2017.
 */

public class Live_Streams_Fragment extends Fragment {

    View streams_view;
    WebView webView;
    GridLayout stream_grid;

    ArrayList<TextView> stream_names;
    ArrayList<ImageView> flags;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        streams_view = inflater.inflate(R.layout.live_streams_layout, container, false);
        IDENTIFY_AND_GROUP_UI_ELEMENTS();
        INITIALIZE_UI_ELEMENTS();
        return streams_view;
    }

    public void IDENTIFY_AND_GROUP_UI_ELEMENTS ()
    {
        stream_grid = (GridLayout) streams_view.findViewById(R.id.stream_grid);
        webView     = (WebView) MainActivity.main_activity.findViewById(R.id.webView);
        //recycler_view = (RecyclerView) streams_view.findViewById(R.id.recyclerview);

        TextView stream1  = (TextView) streams_view.findViewById(R.id.stream1);
        TextView stream2  = (TextView) streams_view.findViewById(R.id.stream2);
        TextView stream3  = (TextView) streams_view.findViewById(R.id.stream3);
        TextView stream4  = (TextView) streams_view.findViewById(R.id.stream4);
        TextView stream5  = (TextView) streams_view.findViewById(R.id.stream5);
        TextView stream6  = (TextView) streams_view.findViewById(R.id.stream6);
        TextView stream7  = (TextView) streams_view.findViewById(R.id.stream7);
        TextView stream8  = (TextView) streams_view.findViewById(R.id.stream8);
        TextView stream9  = (TextView) streams_view.findViewById(R.id.stream9);
        TextView stream10 = (TextView) streams_view.findViewById(R.id.stream10);

        ImageView flag1  = (ImageView) streams_view.findViewById(R.id.stream_img1);
        ImageView flag2  = (ImageView) streams_view.findViewById(R.id.stream_img2);
        ImageView flag3  = (ImageView) streams_view.findViewById(R.id.stream_img3);
        ImageView flag4  = (ImageView) streams_view.findViewById(R.id.stream_img4);
        ImageView flag5  = (ImageView) streams_view.findViewById(R.id.stream_img5);
        ImageView flag6  = (ImageView) streams_view.findViewById(R.id.stream_img6);
        ImageView flag7  = (ImageView) streams_view.findViewById(R.id.stream_img7);
        ImageView flag8  = (ImageView) streams_view.findViewById(R.id.stream_img8);
        ImageView flag9  = (ImageView) streams_view.findViewById(R.id.stream_img9);
        ImageView flag10 = (ImageView) streams_view.findViewById(R.id.stream_img10);

        if (stream_names != null || flags != null) {
            return;
        }

        stream_names = new ArrayList<TextView>();
        flags        = new ArrayList<ImageView>();

        stream_names.add(stream1);
        stream_names.add(stream2);
        stream_names.add(stream3);
        stream_names.add(stream4);
        stream_names.add(stream5);
        stream_names.add(stream6);
        stream_names.add(stream7);
        stream_names.add(stream8);
        stream_names.add(stream9);
        stream_names.add(stream10);

        flags.add(flag1);
        flags.add(flag2);
        flags.add(flag3);
        flags.add(flag4);
        flags.add(flag5);
        flags.add(flag6);
        flags.add(flag7);
        flags.add(flag8);
        flags.add(flag9);
        flags.add(flag10);

    }

    public void INITIALIZE_UI_ELEMENTS () {

        if (Parser.streams_list == null) {
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                long timeout = 10000;
                while (Parser.streams_list.size() == 0) {
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

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < flags.size(); i++) {
                            while (Parser.streams_list.get(i).m_flag_bitmap == null) {
                                try {
                                    Thread.sleep(50);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            final int finalI = i;
                            MainActivity.main_activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    flags.get(finalI).setImageBitmap(Parser.streams_list.get(finalI).m_flag_bitmap);
                                }
                            });
                        }
                    }
                }).start();


                final View.OnClickListener listener = new View.OnClickListener() {
                    public void onClick(View v) {
                        switch(v.getId()) {
                            case R.id.stream1:
                                START_STREAM(Parser.streams_list.get(0).m_page_link);
                                break;
                            case R.id.stream2:
                                START_STREAM(Parser.streams_list.get(1).m_page_link);
                                break;
                            case R.id.stream3:
                                START_STREAM(Parser.streams_list.get(2).m_page_link);
                                break;
                            case R.id.stream4:
                                START_STREAM(Parser.streams_list.get(3).m_page_link);
                                break;
                            case R.id.stream5:
                                START_STREAM(Parser.streams_list.get(4).m_page_link);
                                break;
                            case R.id.stream6:
                                START_STREAM(Parser.streams_list.get(5).m_page_link);
                                break;
                            case R.id.stream7:
                                START_STREAM(Parser.streams_list.get(6).m_page_link);
                                break;
                            case R.id.stream8:
                                START_STREAM(Parser.streams_list.get(7).m_page_link);
                                break;
                            case R.id.stream9:
                                START_STREAM(Parser.streams_list.get(8).m_page_link);
                                break;
                            case R.id.stream10:
                                START_STREAM(Parser.streams_list.get(9).m_page_link);
                                break;
                        }
                    }
                };

                MainActivity.main_activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < stream_names.size(); i++) {
                            stream_names.get(i).setText(Parser.streams_list.get(i).m_name);
                            stream_names.get(i).setClickable(true);
                            stream_names.get(i).setOnClickListener(listener);
                        }
                    }
                });
            }
        }).start();
    }

    public void START_STREAM (final String stream_link)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.main_activity.getApplicationContext(), Single_Live_Stream_Activity.class);
                startActivity(intent);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent2 = new Intent("stream_link");
                intent2.putExtra("stream_link", stream_link);
                MainActivity.main_activity.sendBroadcast(intent2);
            }
        }).start();
    }

    /*public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }
    }*/
}

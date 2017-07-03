package com.unofficial.joindota;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by burak on 03.07.2017.
 */

public class Image_Downloader extends AsyncTask<Void, Void, Bitmap>
{
    public static final int TYPE_NEWS_BITMAP   = 554;
    public static final int TYPE_STREAM_BITMAP = 555;


    private String m_url;
    Bitmap         m_bitmap;
    int            m_index;
    int            m_type;


    public Image_Downloader(String p_url, int p_index, int p_type) {
        m_url   = p_url;
        m_index = p_index;
        m_type  = p_type;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            URL urlConnection = new URL(m_url);
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            m_bitmap = BitmapFactory.decodeStream(input);
            return m_bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        switch (m_type) {
            case TYPE_NEWS_BITMAP:
                Parser.news_list.get(m_index).m_image_bitmap = m_bitmap;
                break;
            case TYPE_STREAM_BITMAP:
                Parser.streams_list.get(m_index).m_flag_bitmap = m_bitmap;
                break;
        }
    }
}
package com.unofficial.joindota;

import android.graphics.Bitmap;
import android.util.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.ArrayList;


public class Parser
{
    public static ArrayList<Parsed_News> news_list;
    public static ArrayList<Parsed_Stream> streams_list;

    public static void LOG (String string)
    {
        Log.w("TEST", string);
    }

    public void PARSE_FROM_SCRATCH ()
    {
        if (news_list != null && streams_list != null) {
            news_list.clear();
            streams_list.clear();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                PARSE_HTML_NEWS();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                PARSE_HTML_STREAMS();
            }
        }).start();
    }

    private void PARSE_HTML_NEWS ()
    {
        news_list                                 = new ArrayList<Parsed_News>();
        ArrayList<String> parsed_news_titles      = new ArrayList<String>();
        ArrayList<String> parsed_news_contents    = new ArrayList<String>();
        ArrayList<String> parsed_news_images      = new ArrayList<String>();
        ArrayList<String> parsed_news_page_links  = new ArrayList<String>();

        try {
            //ArrayList<String> news_contents = new ArrayList<String>();
            String url   = "http://www.joindota.com/en/start";
            Document doc = (Document) Jsoup.connect(url).get();


            for(Element h2 : doc.select("h2")) {
                if(h2.className().equals("news_title_new")) {
                    LOG(h2.text());
                    parsed_news_titles.add(h2.text());
                }
            }
            for(Element div : doc.select("div")){
                if(div.className().equals("news_teaser_text image")) {
                    LOG(div.text());
                    parsed_news_contents.add(div.text());
                }
            }

            for(Element news_title_img : doc.select("div")) {
                if(news_title_img.className().equals("news_teaser_img")) {
                    parsed_news_page_links.add(news_title_img.child(0).attr("href"));
                    Element img  = news_title_img.child(0).child(0);
                    parsed_news_images.add(img.attr("src").toString());
                }
            }

            for (int i = 0; i < parsed_news_titles.size(); i ++) {
                news_list.add(new Parsed_News(parsed_news_titles.get(i), parsed_news_contents.get(i), parsed_news_images.get(i), parsed_news_page_links.get(i)));
            }
            DOWNLOAD_NEWS_IMAGES();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void PARSE_HTML_STREAMS ()
    {
        streams_list                                = new ArrayList<Parsed_Stream>();
        ArrayList<String> stream_pages              = new ArrayList<String>();
        ArrayList<String> stream_links              = new ArrayList<String>();
        ArrayList<String> stream_flags              = new ArrayList<String>();
        ArrayList<String> stream_names              = new ArrayList<String>();
        try {

            String url   = "http://www.joindota.com/en/start";
            Document doc = (Document) Jsoup.connect(url).get();
            Document stream_doc;

            for(Element div : doc.select("div")) {
                if(div.id().equals("livestreams")) {
                    Element box = div.child(0);
                    for (Element stream_tag : box.children()) {
                        if (stream_tag.className().equals("title")) {
                            continue;
                        }
                        LOG(stream_tag.text());
                        stream_names.add(stream_tag.text());
                        LOG(stream_tag.attr("href"));
                        stream_pages.add(stream_tag.attr("href"));
                        stream_flags.add(stream_tag.child(0).child(0).attr("src"));
                    }
                    //LOG(h2.text());
                    //streams.add(h2.text());
                }
            }

            for (String stream_url : stream_pages) {
                stream_doc = (Document) Jsoup.connect(stream_url).get();
                for (Element iframe : stream_doc.select("iframe")) {
                    if (iframe.id().equals("live_stream_embed")) {
                        LOG(iframe.attr("src"));
                        stream_links.add(iframe.attr("src"));
                    }
                }
            }

            for (int i = 0; i < stream_names.size(); i++) {
                streams_list.add(new Parsed_Stream(stream_names.get(i), stream_flags.get(i), stream_links.get(i)));
            }
            DOWNLOAD_STREAM_IMAGES();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void DOWNLOAD_NEWS_IMAGES ()
    {
        for (int i = 0; i < news_list.size(); i++) {
            new Image_Downloader(news_list.get(i).m_image_link, i, Image_Downloader.TYPE_NEWS_BITMAP).execute();
        }
    }

    public void DOWNLOAD_STREAM_IMAGES ()
    {
        for (int i = 0; i < streams_list.size(); i++) {
            new Image_Downloader(streams_list.get(i).m_flag_link, i, Image_Downloader.TYPE_STREAM_BITMAP).execute();
        }
    }

    public class Parsed_News
    {
        String m_title;
        String m_content;
        String m_image_link;
        String m_page_link;
        Bitmap m_image_bitmap;

        public Parsed_News (String p_title, String p_content, String p_image_link, String p_page_link)
        {
            m_title        = p_title;
            m_content      = p_content;
            m_image_link   = p_image_link;
            m_page_link    = p_page_link;
//            m_image_bitmap = p_image_bitmap;
        }
    }

    public class Parsed_Stream
    {
        String m_name;
        String m_flag_link;
        String m_page_link;
        Bitmap m_flag_bitmap;

        public Parsed_Stream (String p_name, String p_flag_link, String p_page_link)
        {
            m_name        = p_name;
            m_flag_link   = p_flag_link;
            m_page_link   = p_page_link;
//            m_flag_bitmap = p_flag_bitmap;
        }

    }
}

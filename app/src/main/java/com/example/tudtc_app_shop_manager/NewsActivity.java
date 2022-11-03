package com.example.tudtc_app_shop_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;

import com.example.tudtc_app_shop_manager.adapter.NewsAdapter;
import com.example.tudtc_app_shop_manager.broadcast.CheckWifiReceiver;
import com.example.tudtc_app_shop_manager.model.News;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<News> newsList = new ArrayList<>();
    private NewsAdapter adapter;
    private CheckWifiReceiver broadcastReceiver;
    URL feedURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        recyclerView = findViewById(R.id.rv_tin_tuc);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        adapter = new NewsAdapter(newsList, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        String urlSite = ("https://thanhnien.vn/rss/doi-song/am-thuc-18.rss");
        new FeedService().execute(urlSite);
    }

    public ArrayList<News> parseFeed(URL url) throws IOException, XmlPullParserException {
        ArrayList<News> list = new ArrayList<>();
        InputStream stream = url.openConnection().getInputStream();
        XmlPullParser parser = Xml.newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(stream, "UTF-8");
        News item = new News();
        int eventType = parser.getEventType();
        String text = "";
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name = parser.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if(name.equalsIgnoreCase("item")) {
                        item = new News();
                    }
                    break;
                case XmlPullParser.TEXT:
                    text = parser.getText();
                    break;
                case XmlPullParser.END_TAG:
                    if(name.equalsIgnoreCase("item")) {
                        list.add(item);
                    }
                    if(name.equalsIgnoreCase("title")) {
                        if(text !=null) {
                            item.setTitle(text);
                        }
                    } else if (name.equalsIgnoreCase("image")){
                        if (text != null){
                            item.setImage(text);
                        }
                    } else if(name.equalsIgnoreCase("description")) {
                        if(text !=null) {
                            item.setDescription(text.replaceAll("<.*?>",""));
                        }
                    }else if(name.equalsIgnoreCase("link")) {
                        if(text !=null) {
                            item.setLink(text);
                        }
                    }
                    break;
            }
            eventType = parser.next();
        }
        return list;
    }

    public void closeNews(View view) {
        onBackPressed();
    }

    class FeedService extends AsyncTask<String, Integer, Integer> {

        @Override
        protected Integer doInBackground(String... strings) {
            try {
                feedURL = new URL(strings[0]);
                newsList = parseFeed(feedURL);
                Log.i("TEST", newsList.size()+" - "+feedURL);
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            System.out.println("SUCCESSS");
            adapter.setData(newsList);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        broadcastReceiver = new CheckWifiReceiver();
        registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }

}
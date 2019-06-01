 package com.example.shrijhajhra.careergo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

 public class Article_category extends AppCompatActivity {

     private ListView listView;
     private Handler handler;
     private ProgressDialog PD;
     ArrayAdapter<String> adapter;
     private ArrayList<String> news_heading = new ArrayList();
     ArrayList<String > links_site=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_category);


        init();
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.header_news,listView,false);
        listView.addHeaderView(header);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {

               // Toast.makeText(Article_category.this, "You Clicked at " + news_heading.get(position-1), Toast.LENGTH_SHORT).show();
              if(position!=0) {
                  if (news_heading.get(position - 1).equals("Careers")) {
                      Intent intent = new Intent(getApplicationContext(), Article_details.class);
                      intent.putExtra("url", links_site.get(position - 1));
                      startActivity(intent);
                  } else if (news_heading.get(position - 1).equals("Employment")) {
                      Intent intent = new Intent(getApplicationContext(), Article_details.class);
                      intent.putExtra("url", links_site.get(position - 1));
                      startActivity(intent);
                  } else {
                      Intent intent = new Intent(getApplicationContext(), Articles.class);
                      //file index as college type
                      intent.putExtra("news type", links_site.get(position - 1));
                      //   intent.putExtra("news category",links_site.get(position-1));
                      startActivity(intent);
                  }


                //  Toast.makeText(Article_category.this, "You Clicked at " + news_heading.get(position - 1), Toast.LENGTH_SHORT).show();
              }
            }
        });
    }
     private void init() {
         listView = (ListView)findViewById(R.id.news_listView);


         news_heading.add("Business");
         links_site.add("https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=44252ed70d314135bc36420125a1561c");
         news_heading.add("Careers");
         links_site.add("https://www.entrepreneur.com/topic/career-advice");
         news_heading.add("Google News");
         links_site.add("https://newsapi.org/v2/top-headlines?sources=google-news-in&apiKey=44252ed70d314135bc36420125a1561c");
         news_heading.add("Employment");
         links_site.add("https://www.govtjobs.allindiajobs.in/employment-news/");

         news_heading.add("Science");
         links_site.add("https://newsapi.org/v2/top-headlines?country=in&category=science&apiKey=44252ed70d314135bc36420125a1561c");
         news_heading.add("Technology");
         links_site.add("https://newsapi.org/v2/top-headlines?country=in&category=technology&apiKey=44252ed70d314135bc36420125a1561c");


         // adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, college_heading);
         adapter = new list_image_text(getApplicationContext(), android.R.layout.simple_list_item_1, news_heading);

     }
}

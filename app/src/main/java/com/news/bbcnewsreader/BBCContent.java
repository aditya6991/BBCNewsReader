package com.news.bbcnewsreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.logging.Logger;

public class BBCContent extends AppCompatActivity {
    private Logger log = Logger.getLogger(BBCContent.class.getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bbc_content);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String link = intent.getStringExtra("link");
        String puDate = intent.getStringExtra("pubDate");
        TextView titleView = findViewById(R.id.bbctitle);
        titleView.setText(title);
        TextView descriptionView =  findViewById(R.id.description);
        descriptionView.setText(description);
        TextView linkView = findViewById(R.id.link);
        linkView.setText(link);
        TextView pubDateView = findViewById(R.id.pubDate);
        pubDateView.setText(puDate);
    }
}

package com.news.bbcnewsreader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.news.bbcnewsreader.dto.BBCEntry;
import com.news.bbcnewsreader.service.BBCReaderDbHelper;

import java.util.ArrayList;
import java.util.logging.Logger;

public class FavListView extends AppCompatActivity {

    private BBCReaderDbHelper bbcReaderDbHelper = new BBCReaderDbHelper(this);
    private Logger log = Logger.getLogger(FavListView.class.getName());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fav_list);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String id = bbcReaderDbHelper.getFavIdByName(name);
        final ArrayList<BBCEntry> bbcEntries = bbcReaderDbHelper.readEntriesForFavouriteList(Integer.parseInt(id));
        BBCListAdaptor adaptor = new BBCListAdaptor(this,bbcEntries);
        ListView view = findViewById(R.id.bbcentries_list);
        view.setAdapter(adaptor);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BBCEntry entry = bbcEntries.get(position);
                Intent intent = new Intent(getApplicationContext(), BBCContent.class);
                intent.putExtra("title",entry.getTitle());
                intent.putExtra("description",entry.getDescription());
                intent.putExtra("pubDate",entry.getPubDate());
                intent.putExtra("link",entry.getLink());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });
    }
}

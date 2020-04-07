package com.news.bbcnewsreader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.news.bbcnewsreader.dto.BBCEntry;

import java.util.ArrayList;

public class BBCListAdaptor extends ArrayAdapter<BBCEntry> {

    private Context context;
    private ArrayList<BBCEntry> entries;

    public BBCListAdaptor(@NonNull Context context, ArrayList<BBCEntry> entries) {
        super(context, R.layout.list_item, entries);
        this.context = context;
        this.entries = entries;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View entryView = convertView;
        if (entryView == null) {
            entryView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }
        BBCEntry entry = this.entries.get(position);
        TextView title = (TextView) entryView.findViewById(R.id.bbctitle);
        title.setText(entry.getTitle());
        return entryView;
    }
}

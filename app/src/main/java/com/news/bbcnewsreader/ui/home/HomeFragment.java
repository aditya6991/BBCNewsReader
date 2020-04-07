package com.news.bbcnewsreader.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.news.bbcnewsreader.R;
import com.news.bbcnewsreader.service.HttpGetBBCData;

import java.util.logging.Logger;

public class HomeFragment extends Fragment{

    private Logger log = Logger.getLogger(HomeFragment.class.getName());
    private ListView listView = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        listView = view.findViewById(R.id.entries_list);
        HttpGetBBCData bbcData = new HttpGetBBCData(getContext(),listView);
        bbcData.execute("http://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml");
        return view;
    }

}

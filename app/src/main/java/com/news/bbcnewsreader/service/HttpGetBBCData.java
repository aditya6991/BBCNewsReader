package com.news.bbcnewsreader.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.news.bbcnewsreader.BBCContent;
import com.news.bbcnewsreader.BBCListAdaptor;
import com.news.bbcnewsreader.R;
import com.news.bbcnewsreader.dto.BBCEntry;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class HttpGetBBCData extends AsyncTask<String, Void, ArrayList<BBCEntry>> {


    private Logger log = Logger.getLogger(HttpGetBBCData.class.getName());
    private BBCDataParser parser = new BBCDataParser();
    private ArrayList<BBCEntry> bbcEntries = null;
    private ProgressBar progressBar = null;
    private Context context;
    private  ListView listView;

    public HttpGetBBCData(Context context,ListView listView){
        this.context = context;
        this.listView = listView;
    }

    @Override
    protected void onPreExecute(){
        progressBar = ((Activity)context).findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected ArrayList<BBCEntry> doInBackground(String... strings) {
        try{
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.connect();
            InputStream streamReader = new BufferedInputStream(connection.getInputStream());
            bbcEntries =  parser.parse(streamReader);
            streamReader.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return this.bbcEntries;
    }

    @Override
    protected void onPostExecute(ArrayList<BBCEntry> s) {
        progressBar.setVisibility(View.INVISIBLE);
        super.onPostExecute(s);
        final BBCListAdaptor bbcListAdaptor= new BBCListAdaptor(this.context,this.bbcEntries);
        this.listView.setAdapter(bbcListAdaptor);
        this.listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BBCEntry entry = bbcEntries.get(position);
                Intent intent = new Intent(context.getApplicationContext(), BBCContent.class);
                intent.putExtra("title",entry.getTitle());
                intent.putExtra("description",entry.getDescription());
                intent.putExtra("pubDate",entry.getPubDate());
                intent.putExtra("link",entry.getLink());
                context.startActivity(intent);
            }
        });
        final Set<String> lst = new HashSet<>();
        this.listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(!listView.isItemChecked(position)){
                    listView.setItemChecked(position,true);
                    listView.getChildAt(position).setBackgroundColor(Color.BLUE);
                    lst.add(listView.getAdapter().getItem(position).toString());
                    SharedPreferences sharedPreferences = context.getSharedPreferences("selectedObjs",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putStringSet("set",lst);
                    editor.commit();
                    log.info(""+lst);
                }else{
                    listView.setItemChecked(position,false);
                    listView.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
                    lst.remove(listView.getAdapter().getItem(position).toString());
                    SharedPreferences sharedPreferences = context.getSharedPreferences("selectedObjs",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putStringSet("set",lst);
                    editor.commit();
                    log.info(""+lst);
                }
                return true;
            }
        });
    }



    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        log.info(""+listView.getSelectedItemPosition());
    }
}

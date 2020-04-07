package com.news.bbcnewsreader.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.news.bbcnewsreader.dto.BBCEntry;

import java.util.ArrayList;
import java.util.logging.Logger;

public class BBCReaderDbHelper extends SQLiteOpenHelper {

    private Logger log = Logger.getLogger(BBCReaderDbHelper.class.getName());

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "BBCReader.db";
    private SQLiteDatabase sqLiteDatabase = null;

    private static final String SQL_CREATE_TABLE_LIST = "CREATE TABLE FAVLIST(FAV_ID INTEGER PRIMARY KEY NOT NULL, NAME TEXT)";

    private static final String SQL_CREATE_TABLE_ENTRY = "CREATE TABLE  BBCFEED (FEED_ID INTEGER PRIMARY KEY NOT NULL, " +
            "TITLE TEXT,DESCRIPTION TEXT,LINK TEXT,GUID TEXT,PUBDATE TEXT,FAV_ID INTEGER NOT NULL,FOREIGN KEY(FAV_ID) REFERENCES FAVLIST(FAV_ID))";

    public BBCReaderDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_LIST);
        db.execSQL(SQL_CREATE_TABLE_ENTRY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertIntoDatabase(BBCEntry entry, String listName){
        log.info(""+sqLiteDatabase);
        sqLiteDatabase = this.getWritableDatabase();
        log.info(""+sqLiteDatabase);
        ContentValues favContentValues = new ContentValues();
        favContentValues.put("NAME",listName);
        long rowId = sqLiteDatabase.insert("FAVLIST",null,favContentValues);
        log.info("rowID "+rowId);
        ContentValues bbcContentValues = new ContentValues();
        bbcContentValues.put("TITLE",entry.getTitle());
        bbcContentValues.put("DESCRIPTION",entry.getDescription());
        bbcContentValues.put("LINK",entry.getLink());
        bbcContentValues.put("GUID",entry.getGuid());
        bbcContentValues.put("PUBDATE",entry.getPubDate());
        bbcContentValues.put("FAV_ID",rowId);
        sqLiteDatabase.insert("BBCFEED",null,bbcContentValues);
        sqLiteDatabase.close();
    }

    public ArrayList<String> readFavourites(){
        sqLiteDatabase = this.getReadableDatabase();
        Cursor result = sqLiteDatabase.query("FAVLIST",null,null,null,null,null,null);
        result.moveToFirst();
        ArrayList<String> favourites = new ArrayList<>();
        while (result.moveToNext()){
            favourites.add(result.getString(result.getColumnIndex("NAME")));
        }
        sqLiteDatabase.close();
        return favourites;
    }

    public String getFavIdByName(String name){
        sqLiteDatabase = this.getReadableDatabase();
        String res = null;
        Cursor result = sqLiteDatabase.query("FAVLIST",new String[]{"FAV_ID"},"NAME=?",new String[]{name},null,null,null);
        while (result.moveToNext()){
            res = result.getString(result.getColumnIndex("FAV_ID"));
        }
        return res;
    }

    public ArrayList<BBCEntry> readEntriesForFavouriteList(Integer id){
        sqLiteDatabase = this.getReadableDatabase();
        Cursor result = sqLiteDatabase.query("BBCFEED",null,"FAV_ID=?",new String[]{Integer.toString(id)},null,null,null);
        ArrayList<BBCEntry> entries = new ArrayList<>();
        while (result.moveToNext()){
            BBCEntry entry = new BBCEntry();
            entry.setTitle(result.getString(result.getColumnIndex("TITLE")));
            entry.setDescription(result.getString(result.getColumnIndex("DESCRIPTION")));
            entry.setLink(result.getString(result.getColumnIndex("LINK")));
            entry.setGuid(result.getString(result.getColumnIndex("GUID")));
            entry.setPubDate(result.getString(result.getColumnIndex("PUBDATE")));
            entries.add(entry);
        }
        return entries;
    }

    public void deleteFavouritesList(Integer id){
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete("FAVLIST","FAV_ID=?",new String[]{Integer.toString(id)});
        sqLiteDatabase.delete("BBCFEED","FAV_ID=?",new String[]{Integer.toString(id)});
        sqLiteDatabase.close();
    }
}

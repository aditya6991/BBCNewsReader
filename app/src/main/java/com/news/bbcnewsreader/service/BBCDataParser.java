package com.news.bbcnewsreader.service;

import android.util.Xml;


import com.news.bbcnewsreader.dto.BBCChannel;
import com.news.bbcnewsreader.dto.BBCEntry;
import com.news.bbcnewsreader.dto.BBCImage;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Logger;

public class BBCDataParser {
    private Logger log = Logger.getLogger(BBCDataParser.class.getName());
    public ArrayList<BBCEntry> parse(InputStream stream) {
        BBCChannel channel = null;
        try{
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(stream, null);
            parser.nextTag();
            channel = readChannel(parser);
        }catch (XmlPullParserException ex){
            ex.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return channel.getEntries();
    }


    private BBCChannel readChannel(XmlPullParser parser) {
        BBCChannel channel = new BBCChannel();
        ArrayList<BBCEntry> entries = new ArrayList<>();
        try{
            parser.require(XmlPullParser.START_TAG, null, "rss");
            while (parser.next()!=XmlPullParser.END_TAG){
                if(parser.getEventType() != XmlPullParser.START_TAG){
                    continue;
                }else {
                    String name = parser.getName();
                    if(name.equals("channel")){
                        parser.require(XmlPullParser.START_TAG, null, "channel");
                        while(parser.next()!=XmlPullParser.END_TAG){
                            if(parser.getEventType()!=XmlPullParser.START_TAG){
                                continue;
                            }else {
                                name = parser.getName();
                                if (name.equals("title")) {
                                    channel.setTitle(readTitle(parser));
                                } else if (name.equals("description")) {
                                    channel.setDescription(readDescription(parser));
                                } else if (name.equals("link")) {
                                    channel.setLink(readLink(parser));
                                } else if (name.equals("image")) {
                                    channel.setImage(readBBCImage(parser));
                                } else if (name.equals("generator")) {
                                    channel.setGenerator(readGenerator(parser));
                                } else if (name.equals("lastBuildDate")) {
                                    channel.setLastBuildDate(readLastBuildDate(parser));
                                } else if (name.equals("copyright")) {
                                    channel.setCopyright(readCopyRight(parser));
                                } else if (name.equals("language")) {
                                    channel.setLanguage(readLanguage(parser));
                                } else if (name.equals("ttl")) {
                                    channel.setTtl(readTTL(parser));
                                } else if (name.equals("item")) {
                                    entries.add(readEntry(parser));
                                    channel.setEntries(entries);
                                }
                            }
                        }
                    }
                }
            }
        }catch (XmlPullParserException ex) {
            ex.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return channel;
    }

    private BBCImage readBBCImage(XmlPullParser parser) throws XmlPullParserException, IOException{
        parser.require(XmlPullParser.START_TAG,null,"image");
        BBCImage image = new BBCImage();
        while (parser.next()!=XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            } else {
                String name = parser.getName();
                if(name.equals("url")) {
                    image.setUrl(readUrl(parser));
                }else if(name.equals("title")) {
                    image.setTitle(readTitle(parser));
                }else if(name.equals("link")){
                    image.setLink(readLink(parser));
                }
            }
        }
        return image;
    }

    private String readUrl(XmlPullParser parser) throws XmlPullParserException, IOException{
        parser.require(XmlPullParser.START_TAG,null,"url");
        String url = readText(parser);
        parser.require(XmlPullParser.END_TAG,null,"url");
        return url;
    }

    private String readLastBuildDate(XmlPullParser parser) throws XmlPullParserException, IOException{
        parser.require(XmlPullParser.START_TAG,null,"lastBuildDate");
        String lastBuildDate = readText(parser);
        parser.require(XmlPullParser.END_TAG,null,"lastBuildDate");
        return lastBuildDate;
    }

    private String readGenerator(XmlPullParser parser) throws  XmlPullParserException, IOException{
        parser.require(XmlPullParser.START_TAG,null,"generator");
        String generator = readText(parser);
        parser.require(XmlPullParser.END_TAG,null,"generator");
        return generator;
    }

    private String readCopyRight(XmlPullParser parser) throws XmlPullParserException, IOException{
        parser.require(XmlPullParser.START_TAG,null,"copyright");
        String copyright = readText(parser);
        parser.require(XmlPullParser.END_TAG,null,"copyright");
        return copyright;
    }

    private String readLanguage(XmlPullParser parser) throws XmlPullParserException, IOException{
        parser.require(XmlPullParser.START_TAG,null,"language");
        String language = readText(parser);
        parser.require(XmlPullParser.END_TAG,null,"language");
        return language;
    }

    private String readTTL(XmlPullParser parser) throws XmlPullParserException, IOException{
        parser.require(XmlPullParser.START_TAG,null,"ttl");
        String ttl = readText(parser);
        parser.require(XmlPullParser.END_TAG,null,"ttl");
        return ttl;
    }

    private BBCEntry readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        BBCEntry entry = new BBCEntry();
        parser.require(XmlPullParser.START_TAG, null, "item");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("title")) {
                entry.setTitle(readTitle(parser));
            } else if (name.equals("description")) {
                entry.setDescription(readDescription(parser));
            } else if (name.equals("link")) {
                entry.setLink(readLink(parser));
            } else if (name.equals("guid")) {
                entry.setGuid(readGuid(parser));
            } else if (name.equals("pubDate")) {
                entry.setPubDate(readPub(parser));
            }
        }
        return entry;
    }

    private String readTitle(XmlPullParser parser) throws XmlPullParserException, IOException{
        parser.require(XmlPullParser.START_TAG,null,"title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG,null,"title");
        return title;
    }

    private String readDescription(XmlPullParser parser) throws XmlPullParserException, IOException{
        parser.require(XmlPullParser.START_TAG, null, "description");
        String description = readText(parser);
        parser.require(XmlPullParser.END_TAG,null,"description");
        return description;
    }

    private String readLink(XmlPullParser parser) throws XmlPullParserException, IOException{
        parser.require(XmlPullParser.START_TAG,null,"link");
        String link = readText(parser);
        parser.require(XmlPullParser.END_TAG,null,"link");
        return link;
    }

    private String readGuid(XmlPullParser parser) throws XmlPullParserException, IOException{
        parser.require(XmlPullParser.START_TAG, null,"guid");
        String guid = readText(parser);
        parser.require(XmlPullParser.END_TAG,null,"guid");
        return guid;
    }

    private String readPub(XmlPullParser parser) throws XmlPullParserException, IOException{
        parser.require(XmlPullParser.START_TAG,null,"pubDate");
        String pubDate = readText(parser);
        parser.require(XmlPullParser.END_TAG,null,"pubDate");
        return pubDate;
    }


    private String readText(XmlPullParser parser) throws XmlPullParserException, IOException{
        String res = "";
        if(parser.next()==XmlPullParser.TEXT){
            res = parser.getText();
            parser.nextTag();
        }
        return res;
    }


    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException{
        if(parser.getEventType()!=XmlPullParser.START_TAG){
            throw new IllegalStateException();
        }
        int dept = 1;
        while(dept!=0){
            switch (parser.next()){
                case XmlPullParser.END_TAG:
                    dept--;
                    break;
                case XmlPullParser.START_TAG:
                    dept++;
                    break;
            }
        }
    }
}

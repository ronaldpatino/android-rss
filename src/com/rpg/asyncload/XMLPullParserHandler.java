package com.rpg.asyncload;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class XMLPullParserHandler {

	List<FeedMessage> feedList;
	private FeedMessage feed;
	private String text;
	private String tagName;


	static final String TITLE = "title";
	static final String DESCRIPTION = "description";
	static final String CHANNEL = "channel";
	static final String LANGUAGE = "language";
	static final String COPYRIGHT = "copyright";
	static final String LINK = "link";
	static final String AUTHOR = "author";
	static final String ITEM = "item";
	static final String PUB_DATE = "pubDate";
	static final String GUID = "guid";
	static final String CATEGORY = "category";


	public XMLPullParserHandler() {
		feedList = new ArrayList<FeedMessage>();
	}

	public List<FeedMessage> getfeedList() {
		return feedList;
	}

	public List<FeedMessage> parse(InputStream is) {

		XmlPullParserFactory factory = null;
		XmlPullParser parser = null;
		
		try {
			Log.d("debug", "==========================");
			Log.d("debug", "Estoy en el parser");

			boolean isFeedHeader = true;
			// Set header values intial to the empty string
			String description = "";
			String title = "";
			String link = "";
			String language = "";
			String copyright = "";
			String author = "";
			String pubdate = "";
			String guid = "";

			boolean done = false;


			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);

			parser = factory.newPullParser();
			parser.setInput(is, null);

			 //rssFeed = new RSSFeed();
	          //rssFeedList = new ArrayList<RSSFeed>();
			feed = new FeedMessage();
			feedList = new ArrayList<FeedMessage>();
			
			int eventType = parser.getEventType();									

			while (eventType != XmlPullParser.END_DOCUMENT && !done) {
				tagName = parser.getName();
				switch (eventType) {
					case XmlPullParser.START_DOCUMENT:
						break;
					case XmlPullParser.START_TAG:
						if (tagName.equals(ITEM)) {
							feed = new FeedMessage();
							Log.d("debug", "Creamos nuevo item");

						}
						if (tagName.equals(TITLE)) {
							title = parser.nextText().toString();
							Log.d("debug", "Titulo " + title);
						}
						if (tagName.equals(LINK)) {
							link = parser.nextText().toString();
						}
						if (tagName.equals(DESCRIPTION)) {
							description = parser.nextText().toString();
						}
						if (tagName.equals(CATEGORY)) {
	
						}
						if (tagName.equals(PUB_DATE)) {
							pubdate = parser.nextText().toString();
						}
						if (tagName.equals(GUID)) {
							guid = parser.nextText().toString();
						}
	
						break;	
	
					case XmlPullParser.END_TAG:
						if (tagName.equals(CHANNEL)) {
							done = true;
						} else if (tagName.equals(ITEM)) {
							//rssFeed = new RSSFeed(title, link, description, category, pubDate,                               guid,  feedburner);
							//rssFeedList.add(rssFeed);
							feed.setTitle(title);
							feed.setDescription(description);
							feedList.add(feed);
						}
					break;                        
				}

				eventType = parser.next();			
			}

			Log.d("debug", "==========================");
			Log.d("debug", "ya sali del while");

		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return feedList;
	}

}

package com.rpg.asyncload;

import android.util.Log;

import com.rpg.asyncload.PostVo;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class XMLPullParserHandler {

	List<PostVo> posts;
	private PostVo post;
	private String text;

	public XMLPullParserHandler() {
		posts = new ArrayList<PostVo>();
	}

	public List<PostVo> getPosts() {
		return posts;
	}

	public List<PostVo> parse(InputStream is) {

		XmlPullParserFactory factory = null;
		XmlPullParser parser = null;

		try {

			Log.d("debug", "Estoy en el parser");
			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			parser = factory.newPullParser();

			parser.setInput(is, null);

			int eventType = parser.getEventType();

			while (eventType != XmlPullParser.END_DOCUMENT) {
				
				String tagname = parser.getName();				
				
				if (tagname != null)  {Log.d("debug", "El tag=> " + tagname.toString());}
				
				switch (eventType) {
				
						
					case XmlPullParser.START_TAG:					
				
						if (tagname.equalsIgnoreCase("channel")) {
							Log.d("debug", "channel me voy de aquí");
							break;
						}
						
						if (tagname.equalsIgnoreCase("item")) {
							// create a new instance of employee
							post = new PostVo();
							Log.d("debug", "Creando un item de " + tagname.toString());
						}
					break;	
					
					default:
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

		return posts;
	}

}

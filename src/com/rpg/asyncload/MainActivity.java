package com.rpg.asyncload;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView textoRss;
	private List<FeedMessage> feedList = null;
	List<FeedMessage> posts = null;
	ListView listView = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.generateDummyData();
        textoRss = (TextView) findViewById(R.id.texto_rss);
        listView = (ListView)this.findViewById(R.id.feedsListView);
        FeedItemAdapter itemAdapter = new FeedItemAdapter(this,	R.layout.feeditem, feedList);
		listView.setAdapter(itemAdapter);
		//listView.setVisibility(View.INVISIBLE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    

    public void onClickCargarRss(View view){
    
    	textoRss.setText("");
    	Log.d("debug", "Llamando a onClickCargarRss(View view)");
    	AsyncTaskRunner runner = new AsyncTaskRunner();		
		runner.execute();
		ListView listView = (ListView)this.findViewById(R.id.feedsListView);
		//listView.setVisibility(View.VISIBLE);
    	
    }
    
    private void generateDummyData() {
		FeedMessage data = null;
		feedList = new ArrayList<FeedMessage>();
		for (int i = 0; i < 5; i++) {
			data = new FeedMessage();						
			data.setDescription("Post " + (i + 1) + " Title: This is the Post Title from RSS Feed");
			data.setTitle("El titulo");						
			feedList.add(data);
		}				
	}
    	

	private class AsyncTaskRunner extends AsyncTask<String, String, List<FeedMessage>> {

		private String resp;
		

		@Override
		protected List<FeedMessage> doInBackground(String... params) {
			
			String urlStr = new String("http://www.elmercurio.com.ec/feed?cat=53");
			InputStream is = null;
			
			
	
			publishProgress("Cargando..."); // Calls onProgressUpdate()
			
			Log.d("debug", "A punto de llamar al url");
			try {
			
				URL url = new URL(urlStr);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setReadTimeout(10*1000);
				connection.setConnectTimeout(10*1000);
				connection.setRequestMethod("GET");
				connection.setDoInput(true);
				connection.connect();
				int response = connection.getResponseCode();
				Log.d("debug", "The response is: " + response);
				is = connection.getInputStream();
				resp = "hola";
				XMLPullParserHandler parser = new XMLPullParserHandler();
				posts = parser.parse(is);
								
											
			} 			
			catch(SocketException ex)
		    {
		         Log.e("Error : " , "Error on soapPrimitiveData() " + ex.getMessage());
		           ex.printStackTrace();
		    } 		
			catch (IOException e) {
	            e.printStackTrace();
	        }
			catch (Exception e) {
				e.printStackTrace();
				resp = e.getMessage();
			}			
			
			return posts;
		}

		
		@Override
		protected void onPostExecute(List<FeedMessage> result) {
			// execution of result of Long time consuming operation
			textoRss.setText("Hellou");		
			
			FeedMessage data = null;
									
			Iterator itr = result.iterator();
			int i = 1;
			feedList.clear();
			/*
			while(itr.hasNext()) {
				Object element = itr.next();
				data = new FeedMessage();						
				data.setDescription("Post " + (i++) + " Title: This is the Post Title from RSS Feed");
				data.setTitle("El titulo");						
				feedList.add(data);
				
			}	*/
			for (FeedMessage fm: result)
			{
				data = new FeedMessage();
				data.setDescription(fm.getDescription());
				data.setLink(fm.getTitle());
				feedList.add(data);
				Log.d("debug", "iterando" + i++);
			}
			
			listView.invalidateViews();
			
		}

		
		@Override
		protected void onPreExecute() {
			// Things to be done before execution of long running operation. For
			// example showing ProgessDialog
		}

	
		@Override
		protected void onProgressUpdate(String... text) {
			//finalResult.setText(text[0]);
			// Things to be done while execution of long running operation is in
			// progress. For example updating ProgessDialog
		}
	
		private  String getStringFromInputStream(InputStream is) {
			 
			BufferedReader br = null;
			StringBuilder sb = new StringBuilder();
	 
			String line;
			try {
	 
				br = new BufferedReader(new InputStreamReader(is));
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
	 
			} 
			
			catch (IOException e) {
				e.printStackTrace();
			} 
			
			finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
	 
			return sb.toString();
	 
		}
		
	}
	
}

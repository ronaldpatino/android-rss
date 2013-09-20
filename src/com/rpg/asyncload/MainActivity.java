package com.rpg.asyncload;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView textoRss;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textoRss = (TextView) findViewById(R.id.texto_rss);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    

    public void onClickCargarRss(View view){
    
    	textoRss.setText("");
    	Log.d(getDate(), "Llamando a onClickCargarRss(View view)");
    	AsyncTaskRunner runner = new AsyncTaskRunner();		
		runner.execute();
    	
    }
    
	private String getDate() {
		// TODO Auto-generated method stub
		return null;
	}

	private class AsyncTaskRunner extends AsyncTask<String, String, String> {

		private String resp;

		@Override
		protected String doInBackground(String... params) {
			
			String urlStr = new String("http://www.pcworld.com/index.rss");
			InputStream is = null;
	
			publishProgress("Cargando..."); // Calls onProgressUpdate()
			
			Log.d(getDate(), "A punto de llamar al url");
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
				resp = getStringFromInputStream(is);
											
			} 			
			catch(SocketException ex)
		    {
		         Log.e("Error : " , "Error on soapPrimitiveData() " + ex.getMessage());
		           ex.printStackTrace();
		    } 						
			catch (Exception e) {
				e.printStackTrace();
				resp = e.getMessage();
			}			
			
			return resp;
		}

		
		@Override
		protected void onPostExecute(String result) {
			// execution of result of Long time consuming operation
			textoRss.setText(result);
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

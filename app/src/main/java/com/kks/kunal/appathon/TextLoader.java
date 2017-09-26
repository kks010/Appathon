package com.kks.kunal.appathon;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Kunal on 26-09-2017.
 */

class TextLoader extends AsyncTask<String,Void,String>{

    public TextLoaderListener listener;
    private String URL="";

    public TextLoader(String url,TextLoaderListener listener) {
        this.URL=url;
        this.listener=listener;
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection urlConnection = null;
        String result = "";
        try {
            URL url = new URL(URL);
            urlConnection = (HttpURLConnection) url.openConnection();

            int code = urlConnection.getResponseCode();

            if(code==200){
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                if (in != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                    String line = "";

                    while ((line = bufferedReader.readLine()) != null)
                        result += line;
                }
                in.close();
            }

            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }

    @Override
    protected void onPostExecute(String result) {
        if ( result.length()==0) {

        } else {
            listener.getText(result);
        }
    }

    public interface TextLoaderListener{

        void getText(String result);

    }

}

package com.example.max.exercise1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;

public class URL_App extends AppCompatActivity {

    private TextView myText;
    private WebView web_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url__app);
    }

    public void buttonOnClick(View v) {

        myText = (TextView)findViewById(R.id.textView2);
        final EditText myInput = (EditText)findViewById(R.id.editText);

        //String url = myInput.toString();
        //String url = "http://192.168.0.12/files/Zutaten.txt";
        //String url = "http://192.168.0.12/files/halloween.jpg";
        String url = "http://www.google.com";

        if ((url.endsWith(".html"))||(url.endsWith(".jpg"))){
            web_view = (WebView) findViewById(R.id.webView);
            web_view.getSettings().setJavaScriptEnabled(true);
            try {
                URL webviewURL = new URL(url);
            } catch (MalformedURLException e){
                myText.setText("Webview doesn't work correct.\n"+e.toString());
            }
            web_view.loadUrl(url);
        } else {
            TaskA task = new TaskA();
            task.execute(new String[] { url });
        }

        //TaskA task = new TaskA();
        //task.execute(new String[] { url });
    }

    private class TaskA extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            for (String strURL : urls) {
                BufferedReader in = null;

                //
                if (!strURL.startsWith("http://")){
                    strURL = "http://"+strURL;
                }

                try {
                    URL url = new URL(strURL);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    urlConnection.disconnect();
                    String inputLine;

                    while ((inputLine = in.readLine()) != null) {
                        response += inputLine;
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            myText.setText(result);
        }
    }


}

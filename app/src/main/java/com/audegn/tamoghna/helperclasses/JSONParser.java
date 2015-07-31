package com.audegn.tamoghna.helperclasses;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by Tamoghna on 16-06-2015.
 */
public class JSONParser {
    public static Activity activit;
    static JSONArray jarr = null;
    static String json = "";

    public JSONParser() {
    }

    public JSONArray getJSONFromUrl(String url, HashMap<String,String> header, Activity activity) {
        activit = activity;
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        for(int i = 0; i<header.size();i++){
            httpGet.addHeader((String)(header.keySet().toArray()[i]), header.get(header.keySet().toArray()[i]));
        }
        for(int i = 0;i<httpGet.getAllHeaders().length;i++){
            Log.e(httpGet.getAllHeaders()[i].getName(), httpGet.getAllHeaders()[i].getValue());
        }
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200 || statusCode == 404) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } else {
                Log.e("==>", "Failed to download file");
                return jarr;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("builder", builder.toString());
        if(builder != null && builder.length() != 0) {
            Log.e("jarr", builder.toString());
            if (builder.charAt(0) == '\"') {
                builder.deleteCharAt(0);
                builder.deleteCharAt(builder.length() - 1);
            }
            if (builder.charAt(0) == '{') {
                builder.insert(0, '[');
                builder.append("]");
            }
        }
        if(builder.toString().contains("#@!error!@#$")){
            String buld = builder.toString();
            int index = buld.indexOf("$");
            final String subbuld = buld.substring(index+1);
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(JSONParser.this.activit,subbuld,Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        }
        // Parse String to JSON object
        try {
            jarr = new JSONArray(builder.toString());
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        // return JSON Object
        return jarr;
    }
    public byte[] getimagebitmapfromurl(String url, HashMap<String,String> header) {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        if(header != null){
            for (int i = 0; i < header.size(); i++) {
                httpGet.addHeader((String) (header.keySet().toArray()[i]), header.get(header.keySet().toArray()[i]));
            }
            for (int i = 0; i < httpGet.getAllHeaders().length; i++) {
                Log.e(httpGet.getAllHeaders()[i].getName(), httpGet.getAllHeaders()[i].getValue());
            }
        }
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(entity);
                InputStream instream = bufferedHttpEntity.getContent();
                byte[] buff = new byte[8000];

                int bytesRead = 0;

                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                while((bytesRead = instream.read(buff)) != -1) {
                    bao.write(buff, 0, bytesRead);
                }
                byte[] data = bao.toByteArray();
                return  data;
            } else {
                Log.e("==>", "Failed to download file");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
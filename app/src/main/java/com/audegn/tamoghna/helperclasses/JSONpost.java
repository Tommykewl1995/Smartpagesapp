package com.audegn.tamoghna.helperclasses;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.sun.mail.iap.ByteArray;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Tamoghna on 13-06-2015.
 */
public class JSONpost {
    int serverResponseCode;
    StringBuilder builder = new StringBuilder();
    JSONObject json;
    HttpClient httpClient = new DefaultHttpClient();
    HttpContext httpContext = new BasicHttpContext();
    public static HttpPost httpPost = new HttpPost();
    public static HttpPut httpPut = new HttpPut();
    static JSONArray jobj = null;
    public JSONArray postJSON(JSONObject array, String url, HashMap<String,String> header) {

        Log.e("JSON", array.toString());
        builder = new StringBuilder();
        json = array;
        httpClient = new DefaultHttpClient();
        httpContext = new BasicHttpContext();
        httpPost = new HttpPost(url); // this is the request url is the url req
        try {
            if(array != null) {
                StringEntity se = new StringEntity(json.toString(), "UTF-8");
                Log.e("stringentity", se.toString());
                Log.e("httppostmethod", httpPost.getMethod());
                httpPost.setEntity(se); // Hence the se[0] instead of se ok sir (Y)
            }
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json; charset=utf-8");
            if(header != null) {
                for (int i = 0; i < header.size(); i++) {
                    httpPost.addHeader((String) (header.keySet().toArray()[i]), header.get(header.keySet().toArray()[i]));
                }
                for (int i = 0; i < httpPost.getAllHeaders().length; i++) {
                    Log.e(httpPost.getAllHeaders()[i].getName(), httpPost.getAllHeaders()[i].getValue());
                }
            }
            HttpResponse response = httpClient.execute(httpPost); //execute your request and parse response
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            if(builder.charAt(0) == '{'){
                builder.insert(0,'[');
                builder.append("]");
            }
            jobj = new JSONArray( builder.toString()); //if response in JSON format
            Log.e("bangbang", builder.toString());
            return jobj;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Shit", e.toString());
        }
        return null;
    }
    public JSONArray postJSONimage(ArrayList<String> imagepath, String imageurl, HashMap<String,String> header, String contenttype) {

        httpClient = new DefaultHttpClient();
        httpContext = new BasicHttpContext();
        httpPut = new HttpPut(imageurl); // this is the request url is the url req

        try {
            for(int i = 0; i<header.size();i++){
                httpPut.addHeader((String)(header.keySet().toArray()[i]), header.get(header.keySet().toArray()[i]));
            }
            for(int i = 0;i<httpPut.getAllHeaders().length;i++){
                Log.e(httpPut.getAllHeaders()[i].getName(), httpPut.getAllHeaders()[i].getValue());
            }
            ArrayList<InputStream> is = new ArrayList<>();
            for (int i = 0; i < imagepath.size(); i++){
                try {
                    File file = new File(imagepath.get(i));
                    FileEntity fila = new FileEntity(file,contenttype);
                    httpPut.setEntity(fila);
                    HttpResponse response = httpClient.execute(httpPut); //execute your request and parse response
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                    if (builder.charAt(0) == '{') {
                        builder.insert(0, '[');
                        builder.append("]");
                    }
                    jobj = new JSONArray(builder.toString()); //if response in JSON format
                    Log.e("bangbang", builder.toString());
                }catch(Exception e){
                    e.printStackTrace();
                    Log.e("Shit", e.toString());
                }
            }
            return jobj;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Shit", e.toString());
        }
        return null;
    }
    public void clear(){
        httpPost = null;
    }
}

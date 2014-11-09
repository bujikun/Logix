package com.logix.parser;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by newton on 11/7/14.
 */
public class JSONParser {

    static InputStream inputStream=null;
    static JSONObject jsonObject = null;
    static  String jsonString ="";

    //constructor
    public JSONParser(){

    }

    public JSONObject getJSONString(String link,List<NameValuePair> params)
            throws Exception {


        /**
         * This method throws numerous exceptions
         * Be careful as we are only throwing a super Exception
         * Possible sub exceptions : UnsupportedEncodingException,ClientProtocolException,
         * IOException,JSONException
         */
        //make HTTP request

        //instantiate an http client that is going to execute our post request
        DefaultHttpClient httpClient = new DefaultHttpClient();

        //create the post object
        HttpPost httpPost = new HttpPost(link);
        //create a new url encoded form entity with the given parameters
        httpPost.setEntity(new UrlEncodedFormEntity(params));

        //execute post and get the response
        HttpResponse httpResponse = httpClient.execute(httpPost);

        //get http entity from response
        HttpEntity httpEntity = httpResponse.getEntity();

        //retrieve the content
        inputStream = httpEntity.getContent();

        //write the stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line ="";

        while ((line=reader.readLine())!=null){

            stringBuilder.append(line+"\n");



        }

        inputStream.close();
        //get the JSON string
        jsonString = stringBuilder.toString();

        jsonObject = new JSONObject(jsonString);
        Log.d("response",jsonString);
        //get
       return jsonObject;

    }






}

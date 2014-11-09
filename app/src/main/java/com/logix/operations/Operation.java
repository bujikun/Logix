package com.logix.operations;

import android.os.AsyncTask;
import android.util.Log;

import com.logix.parser.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by newton on 11/7/14.
 */
public class Operation {

    private JSONParser jsonParser;

    private static final String API_URL = "http://logix.herobo.com/logix/index.php";

    private static  final String REQUEST_LOGIN="login";

    private static  final String REQUEST_REGISTER = "register";
    JSONObject jsonObject;


    public Operation(){

        jsonParser = new JSONParser();

    }


    public JSONObject login(String email,String password) {

        //Building parameters to be POST-ed
        final  List<NameValuePair> parameters = new ArrayList<NameValuePair>();

        parameters.add(new BasicNameValuePair("request","login"));
        parameters.add(new BasicNameValuePair("email",email));
        parameters.add(new BasicNameValuePair("password",password));


        try {
            Operation.this.jsonObject = jsonParser.getJSONString(API_URL,parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject;


    }


    public JSONObject register (String name,String email,String password){

        JSONObject jsonObject=null;
        //Building parameters
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();

        parameters.add(new BasicNameValuePair("request","register"));
        parameters.add(new BasicNameValuePair("email",email));
        parameters.add(new BasicNameValuePair("name",name));
        parameters.add(new BasicNameValuePair("password",password));



        try {
            jsonObject = jsonParser.getJSONString(API_URL,parameters);
        }catch (Exception exception){

        }


        return jsonObject;

    }


    public boolean hasLoggedIn(){


        return true;
    }

    public boolean logout(){

        return true;
    }




}

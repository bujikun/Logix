package com.logix.logix;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.logix.util.Util;

import org.json.JSONException;
import org.json.JSONObject;


public class DashboardActivity extends ActionBarActivity {

    private TextView welcome,email,time;
    private Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        welcome = (TextView)findViewById(R.id.dashboard_welcome);
        email = (TextView) findViewById(R.id.dashboard_email);
        time = (TextView) findViewById(R.id.dashboard_created);
        logout = (Button) findViewById(R.id.dashboard_button_logout);

        JSONObject jsonObject=null;
        try {
            jsonObject=new JSONObject(getIntent().getStringExtra("JSON"));
            welcome.setText("Hello, "+jsonObject.getString(Util.KEY_NAME).toUpperCase()+".\nWelcome to Logix.We are " +
                    "so happy to have you in our system.");
            email.setText("E-mail : "+jsonObject.getString(Util.KEY_EMAIL));
            time.setText("Joined : "+jsonObject.getString(Util.KEY_CREATED_AT));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

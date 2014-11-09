package com.logix.logix;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.logix.operations.Operation;
import com.logix.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends ActionBarActivity {

    private Button signUp;//A button to handle sign up nevent
    private EditText signUpName,signUpPassword,signUpConfirmPassword,signUpEMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);//requesting no action bar
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
              //  ,WindowManager.LayoutParams.FLAG_FULLSCREEN);//flagging the activity to take the
        //whole screen
        setContentView(R.layout.activity_sign_up);//inflate the layout

        signUp = (Button) findViewById(R.id.sign_up_sign_up);//find the button by id
        signUpName =(EditText)findViewById(R.id.sign_up_first_name);
        signUpPassword = (EditText)findViewById(R.id.sign_up_password);
        signUpConfirmPassword = (EditText)findViewById(R.id.sign_up_confirm_password);
        signUpEMail = (EditText)findViewById(R.id.sign_up_email);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = signUpName.getText().toString();
                String email = signUpEMail.getText().toString();
                String password = signUpPassword.getText().toString();

                if(!password.equals(signUpConfirmPassword.getText().toString())){
                    Toast.makeText(
                            getApplicationContext(),
                            "Passwords don't match ",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }


                RegisterTask registerTask = new RegisterTask();

                registerTask.execute(name,email,password);




                //Toast.makeText(getApplicationContext(), "Pumbafu Wewe It's Still" +
                       // " Under Construction ", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sign_up, menu);
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


    private  class RegisterTask extends AsyncTask<String,Void,JSONObject>{

        private ProgressDialog progressDialog;

        @Override
        protected JSONObject doInBackground(String... params) {
            return new Operation().register(params[0],params[1],params[2]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(
                    SignUpActivity.this,
                    "Signing Up...",
                    "Please Wait....",
                    true);

        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);

            if(jsonObject!=null){

                try {
                    if(Integer.parseInt(jsonObject.getString(Util.KEY_SUCCESS))==1){

                        //User successfully registered


                        JSONObject jsonUser = jsonObject.getJSONObject("user");

                        //Clear previous infos and
                        //Store new credentials here

                        Intent intent = new Intent(getApplicationContext(),DashboardActivity.class);

                        intent.putExtra("JSON",jsonUser.toString());
                        //Close all views before showing the dashboard

                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        progressDialog.dismiss();
                        SignUpActivity.this.startActivity(intent);
                        SignUpActivity.this.finish();


                    }else{

                        progressDialog.dismiss();

                        Toast.makeText(
                                getApplicationContext(),
                                "An error occured during registration ",
                                Toast.LENGTH_SHORT
                        ).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                    progressDialog.dismiss();

                    Toast.makeText(
                            getApplicationContext(),
                            "An error occured during registration ",
                            Toast.LENGTH_SHORT
                    ).show();

                }


            }

        }
    }

}

package com.logix.logix;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.logix.operations.Operation;
import com.logix.util.Util;

import org.json.JSONException;
import org.json.JSONObject;


public class SignInActivity extends ActionBarActivity {

   private Button signUp,signIn;//Two buttons to handle sign up and sign in events
   private EditText signInEMail,signInPassword;
   private JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);//requesting no action bar
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
             //   ,WindowManager.LayoutParams.FLAG_FULLSCREEN);//flagging the activity to take the
        //whole screen
        setContentView(R.layout.activity_sign_in);//inflate the layout
        signUp = (Button) findViewById(R.id.sign_in_sign_up);//find the button by id
        signIn = (Button) findViewById(R.id.sign_in_sign_in);
        signInEMail = (EditText)findViewById(R.id.sign_in_email);
        signInPassword =(EditText)findViewById(R.id.sign_in_password);










        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //set on click event listener and show a Toast when the button is clicked
                //Toast.makeText(getApplicationContext(),"Pumbafu Wewe It's Still" +
                       // " Under Construction ",Toast.LENGTH_SHORT).show();;
                String email = signInEMail.getText().toString();
                String password = signInPassword.getText().toString();
                if(email==null || password==null||email.equals("")||password.equals("")){
                    Toast.makeText(
                            getApplicationContext(),
                            "Something went wrong with the values you entered..!",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }

                Log.d("email",email);
                Log.d("password",password);


                LoginTask task = new LoginTask();
                task.execute(email,password);





            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //start the SignUpActivity via Intent when this button is clicked
                startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sign_in, menu);
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

    private class LoginTask extends AsyncTask<String,Void,JSONObject>{


        ProgressDialog  progressDialog=null;


        @Override
        protected JSONObject doInBackground(String... params) {


            return new Operation().login(params[0],params[1]);//try to log in
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

                progressDialog = ProgressDialog.show(
                        SignInActivity.this,
                        "Logging In...",
                        "Please Wait....",
                        true);




        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            SignInActivity.this.jsonObject = jsonObject;

            //check for login response

            try {
                if(jsonObject!=null){

                    if(Integer.parseInt(jsonObject.getString(Util.KEY_SUCCESS))==1){

                        //user successfully logged in
                        //get the user object from the jsonObject using getString
                        //store the user credentials here


                        JSONObject jsonUser = jsonObject.getJSONObject("user");
                        //Launch DashboardActivity

                        Intent intent = new Intent(getApplicationContext(),DashboardActivity.class);

                        //close all other tasks

                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        intent.putExtra("JSON",jsonUser.toString());
                        progressDialog.dismiss();
                        SignInActivity.this.startActivity(intent);

                        //close login screen

                        SignInActivity.this.finish();



                    }else{

                        progressDialog.dismiss();

                        //Error in login
                        Toast.makeText(
                                getApplicationContext(),
                                "Login failed\n" +
                                        "Incorrect email or password",
                                Toast.LENGTH_SHORT
                        ).show();

                    }


               }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}

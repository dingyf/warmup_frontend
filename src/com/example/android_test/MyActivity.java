package com.example.android_test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.*;

public class MyActivity extends Activity {

    public final static String EXTRA_MESSAGE = "com.example.android_test.MESSAGE";

    TextView message;
    String hi;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        message = (TextView) findViewById(R.id.textView);
    }


    /** Called when the user clicks the Send button */
    public void login(View view) {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new GetResponse().execute("https://yifan-warmup.herokuapp.com/users/login");
        } else {
            message.setText("No network connection available.");
        }

    }


    public void addUser(View view) {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new GetResponse().execute("https://yifan-warmup.herokuapp.com/users/add");
        } else {
            message.setText("No network connection available.");
        }


    }




    private class GetResponse extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                EditText et_un = (EditText) findViewById(R.id.username);
                EditText et_pw = (EditText) findViewById(R.id.password);
                String un = et_un.getText().toString();
                String pw = et_pw.getText().toString();

                //instantiates httpclient to make request
                DefaultHttpClient httpclient = new DefaultHttpClient();

                //url with the post data
                HttpPost httpost = new HttpPost(urls[0]);

                //convert parameters into JSON object
                JSONObject holder = new JSONObject();
                holder.put("password", pw);
                holder.put("user", un);
                //passes the results to a string builder/entity
                StringEntity se = new StringEntity(holder.toString());

                //sets the post request as the resulting string
                httpost.setEntity(se);
                //sets a request header so the page receving the request
                //will know what to do with it
                httpost.setHeader("Accept", "application/json");
                httpost.setHeader("Content-type", "application/json");

                //Handles what is returned from the page
                ResponseHandler responseHandler = new BasicResponseHandler();

                String response = (String) httpclient.execute(httpost, responseHandler);
                Log.e("--------==========", response);
                return response;
            } catch (Exception e) {
                e.printStackTrace();
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            message.setText(result);
        }
    }
}

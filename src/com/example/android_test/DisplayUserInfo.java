package com.example.android_test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: dyf
 * Date: 2/18/14
 * Time: 9:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class DisplayUserInfo extends Activity {

    TextView tv_uname;
    TextView tv_count;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Get the message from the intent
        Intent intent = getIntent();
        String uname = intent.getStringExtra(MyActivity.UNAME);
        int count = intent.getIntExtra(MyActivity.COUNT, 1);

        // Set the text view as the activity layout
        setContentView(R.layout.userinfo);

        tv_uname = (TextView) findViewById(R.id.textView);
        tv_count = (TextView) findViewById(R.id.textView1);
        tv_uname.setText(uname);
        tv_count.setText("You have logged in " + Integer.toString(count) + " times");
    }


    public void logout(View view) {
        this.finish();

    }
}



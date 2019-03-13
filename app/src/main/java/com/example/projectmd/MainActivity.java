package com.example.projectmd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button ctl; //continue to login page

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void gotologinactivity(View view)
    {
        ctl = (Button)findViewById(R.id.ctlib);
        Intent i1 = new Intent(this, LoginActivity.class);
        startActivity(i1);
    }
}
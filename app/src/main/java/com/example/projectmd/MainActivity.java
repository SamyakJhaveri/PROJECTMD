package com.example.projectmd;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button ctl; //continue to login page

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String customFont = "GoogleSans-Regular.ttf";
        Typeface typeface = Typeface.createFromAsset(getAssets(), customFont);
        TextView caption = (TextView)findViewById(R.id.textView);
        TextView title = (TextView)findViewById(R.id.t1);
        caption.setTypeface(typeface);
        title.setTypeface(typeface);
    }

    public void gotologinactivity(View view)
    {
        ctl = (Button)findViewById(R.id.ctlib);
        Intent i1 = new Intent(this, LoginActivity.class);
        startActivity(i1);
    }
}
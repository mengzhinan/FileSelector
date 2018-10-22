package com.duke.fileselector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView useDefaultActivity;
    private TextView useDefaultMultiModeActivity;
    private TextView useView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        useDefaultActivity = findViewById(R.id.tv_use_default_activity);
        useDefaultMultiModeActivity = findViewById(R.id.tv_use_default_multiMode_activity);
        useView = findViewById(R.id.tv_use_view);
        useDefaultActivity.setOnClickListener(this);
        useDefaultMultiModeActivity.setOnClickListener(this);
        useView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == null) {
            return;
        }
        int id = v.getId();
        Class c = null;
        if (id == R.id.tv_use_default_activity) {
            c = Demo1Activity.class;
        } else if (id == R.id.tv_use_default_multiMode_activity) {
            c = Demo2Activity.class;
        } else if (id == R.id.tv_use_view) {
            c = Demo3Activity.class;
        }
        if (c != null) {
            startActivity(new Intent(this, c));
        }
    }


}

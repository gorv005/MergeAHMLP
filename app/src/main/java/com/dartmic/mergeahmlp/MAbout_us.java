package com.dartmic.mergeahmlp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dartmic.mergeahmlp.Constants.FixedData;

public class MAbout_us extends AppCompatActivity implements View.OnClickListener {

    TextView tv_About, tv_Design, tv_Plants, tv_Achieve;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mabout_us);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;

        tv_About = findViewById(R.id.tv_About);
        tv_Design = findViewById(R.id.tv_Design);
        tv_Plants = findViewById(R.id.tv_Plants);
        tv_Achieve = findViewById(R.id.tv_Achieve);

        tv_About.setOnClickListener(this);
        tv_Design.setOnClickListener(this);
        tv_Plants.setOnClickListener(this);
        tv_Achieve.setOnClickListener(this);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(context, MLPDashboard.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_About:
                FixedData.fixedIntent = 7;
                startActivity(new Intent(context, MCommon.class));
                finish();
                break;

            case R.id.tv_Design:
                FixedData.fixedIntent = 8;
                startActivity(new Intent(context, MCommon.class));
                finish();
                break;

            case R.id.tv_Achieve:
                FixedData.fixedIntent = 9;
                startActivity(new Intent(context, MCommon.class));
                finish();
                break;

            case R.id.tv_Plants:
                FixedData.fixedIntent = 10;
                startActivity(new Intent(context, MCommon.class));
                finish();
                break;

        }
    }

}
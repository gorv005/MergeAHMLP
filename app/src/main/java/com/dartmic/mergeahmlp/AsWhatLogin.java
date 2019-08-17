package com.dartmic.mergeahmlp;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dartmic.mergeahmlp.SharedPref.MyPref;

public class AsWhatLogin extends AppCompatActivity implements View.OnClickListener {

    LinearLayout LME, AH;
    Context context;
    static boolean doubleBackToExit = false;
    TextView tv_MLP, tv_AH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_as_what_login);
        context = this;
        LME = findViewById(R.id.LME);
        AH = findViewById(R.id.AH);
        AH.setOnClickListener(this);
        LME.setOnClickListener(this);

        tv_MLP = findViewById(R.id.tv_MLP);
        tv_AH = findViewById(R.id.tv_AH);
        tv_AH.setOnClickListener(this);
        tv_MLP.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.LME:
                MyPref.setRole(context, "L");
                Intent intent = new Intent(context, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            case R.id.AH:
                MyPref.setRole(context, "A");
                Intent intent1 = new Intent(context, AHLogin.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                finish();
                break;

            case R.id.tv_AH:
                MyPref.setRole(context, "A");
                Intent intent2 = new Intent(context, AHLogin.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                finish();
                break;
            case R.id.tv_MLP:
                MyPref.setRole(context, "L");
                Intent intent3 = new Intent(context, Login.class);
                intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent3);
                finish();
        }

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExit) {
            finish();
        } else {
            doubleBackToExit = true;
            Toast.makeText(context, "Press again to exit.", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExit = false;
                }
            }, 2000);
        }
    }
}

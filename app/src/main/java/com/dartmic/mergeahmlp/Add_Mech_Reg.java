package com.dartmic.mergeahmlp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Add_Mech_Reg extends AppCompatActivity {
    RelativeLayout otp, detail;
    Button next;
    EditText dist, mobile, psbk;
    TextView cancel, submit , resend;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__mech__reg);
        context = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        detail = findViewById(R.id.detail);
        otp = findViewById(R.id.otp);

        dist = findViewById(R.id.dist);
        mobile = findViewById(R.id.mobile);
        psbk = findViewById(R.id.psbk);


        cancel = findViewById(R.id.cancel);
        submit = findViewById(R.id.submit);

        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (psbk.getText().toString().isEmpty())
                    Toast.makeText(Add_Mech_Reg.this, "empty passbook number", Toast.LENGTH_SHORT).show();

                else if (psbk.getText().toString().length() < 4)
                    Toast.makeText(Add_Mech_Reg.this, "invalid passbook number", Toast.LENGTH_SHORT).show();

                else if (mobile.getText().toString().isEmpty())
                    Toast.makeText(Add_Mech_Reg.this, "empty mobile", Toast.LENGTH_SHORT).show();

                else if (mobile.getText().toString().length() < 10 || mobile.getText().length() > 10)
                    Toast.makeText(Add_Mech_Reg.this, "invalid mobile", Toast.LENGTH_SHORT).show();

                else if (dist.getText().toString().isEmpty())
                    Toast.makeText(Add_Mech_Reg.this, "empty distributor", Toast.LENGTH_SHORT).show();

                else {

                    otp.setVisibility(View.VISIBLE);
                    detail.setVisibility(View.GONE);

                }

            }
        });


        resend = findViewById(R.id.resend);
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "OTP sent", Toast.LENGTH_SHORT).show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                otp.setVisibility(View.GONE);
                detail.setVisibility(View.VISIBLE);

            }
        });
    }
}

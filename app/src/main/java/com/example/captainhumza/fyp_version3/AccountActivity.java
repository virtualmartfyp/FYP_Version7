package com.example.captainhumza.fyp_version3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class AccountActivity extends AppCompatActivity {


    RelativeLayout btnSignup, btnSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        btnSignup = (RelativeLayout) findViewById(R.id.account_sign_up);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AccountActivity.this,RegisterActivity.class);
                startActivity(i);

            }
        });
        btnSignin = (RelativeLayout) findViewById(R.id.account_sign_in);
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AccountActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });


    }

}

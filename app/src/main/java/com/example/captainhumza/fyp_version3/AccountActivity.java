package com.example.captainhumza.fyp_version3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.captainhumza.fyp_version3.Classes.Person;
import com.example.captainhumza.fyp_version3.Rider.RiderMenu;
import com.example.captainhumza.fyp_version3.Vender.VenderMenuActivity;

public class AccountActivity extends AppCompatActivity {


    private static AccountActivity inst;
    RelativeLayout btnSignup, btnSignin;
    public static  AccountActivity instance()
    {
        return  inst;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        inst = this;
        Person person = Person.GetInstance();
        if(person.PersonEmail != null && person.PersonTypeId == 3)
        {
            Intent intent1 = new Intent(this , MainActivity.class);
            startActivity(intent1);
        }else if(person.PersonEmail != null && person.PersonTypeId == 2)
        {
            Intent intent1 = new Intent(this , VenderMenuActivity.class);
            startActivity(intent1);
        }else if(person.PersonEmail != null && person.PersonTypeId == 1)
        {
            Intent intent1 = new Intent(this , RiderMenu.class);
            startActivity(intent1);
        }

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
    @Override
    public void onStart()
    {
        super.onStart();
    }
    @Override
    public void onRestart()
    {
        super.onRestart();
        if(Person.GetInstance().PersonEmail != null)
            finish();


    }
    @Override
    public void onResume()
    {
        super.onResume();
    }
    @Override
    public void onPause()
    {
        super.onPause();
    }
    @Override
    public void onStop()
    {
        super.onStop();
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }


}

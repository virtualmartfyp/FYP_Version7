package com.example.captainhumza.fyp_version3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.captainhumza.fyp_version3.Classes.ConstantClass;
import com.example.captainhumza.fyp_version3.Classes.Person;
import com.example.captainhumza.fyp_version3.Classes.RegisterWebServiceHandler;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;
import org.honorato.multistatetogglebutton.ToggleButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements RegisterWebServiceHandler.AsyncResponse {

    private EditText Name, Email , Number , Password , UserName , confirmpass , contact;
    private  String PersonName ="";
    private String PersonContact ="";
    private String PersonEmail ="";
    private String PersonPass ="";
    private String PersonUser ="";
    private  int type = 0;
    private  boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Password = (EditText)findViewById(R.id.register_password);
        confirmpass =(EditText)findViewById(R.id.register_confirm_password);
        Email = (EditText)findViewById(R.id.register_email);
        contact = (EditText)findViewById(R.id.register_contact);

        contact.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    check = ValidateContact(contact.getText().toString());
                    if (check == false)
                    {
                        contact.setError("Invalid Contact");
                    }
                }
            }
        });


        confirmpass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    check =   ValidatePassword(Password.getText().toString() , confirmpass.getText().toString() );
                    if (check ==false)
                    {
                        confirmpass.setError("Password Does'nt  Match");
                    }
                }
            }
        });
        Email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                check = ValidateEmail(Email.getText().toString());
                if(!hasFocus) {
                    if (check == false) {
                        Email.setError("Invalid Email");
                    }
                }
            }
        });

        MultiStateToggleButton button = (MultiStateToggleButton) this.findViewById(R.id.mstb_multi_id);
        button.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int position) {
                if(position ==0 )
                    type =3;//means customer
                else  if(position ==1)
                    type = 2;//means Vender
                else if(position ==2)
                    type = 1;//means Rider
            }
        });
    }

    private  boolean ValidatePassword(String pass , String confirmpass)
    {
        if (pass.toString().equals(confirmpass.toString()) )
            return true;
        else
            return  false;
    }

    private  boolean ValidateEmail(String email )
    {
        String emaill = Email.getText().toString().trim();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(emaill.matches(emailPattern)  )
            return  true;
        else
            return  false;
    }
    private boolean ValidateContact(String contact)
    {
        if(contact.length() >11)
            return false;
        else
            return  true;
    }

    public void CreateAccount(View view) {
        if(type < 1)
            Toast.makeText(this, "Please Select User Type", Toast.LENGTH_SHORT).show();
        else {
            Name = (EditText) findViewById(R.id.register_name);
            Email = (EditText) findViewById(R.id.register_email);
            UserName = (EditText) findViewById(R.id.register_username);
            Password = (EditText) findViewById(R.id.register_password);
            Number = (EditText) findViewById(R.id.register_contact);

            if (!Name.getText().equals("") && !Email.getText().equals("") && !Password.getText().equals("") && !Number.getText().equals("")) {
                PersonName = Name.getText().toString();
                PersonContact = Number.getText().toString();
                PersonEmail = Email.getText().toString();
                PersonPass = Password.getText().toString();
                PersonUser = UserName.getText().toString();
                Person person = Person.GetInstance();
                HashMap<String, String> map = person.AddPerson(PersonName, PersonContact, PersonEmail, PersonPass, PersonUser, type);
                new RegisterWebServiceHandler(this, ConstantClass.PersonRegister, map).execute();
                person.DestroyInstance();

            } else {
                Toast.makeText(this, "Please Fill", Toast.LENGTH_LONG).show();
            }
        }


    }
    @Override
    public void onRestart()
    {
        super.onRestart();
        if(Person.GetInstance() != null)
            finish();
    }

    @Override
    public void processFinish(String output) {

        Toast.makeText(RegisterActivity.this ,output , Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this , LoginActivity.class);
        startActivity(intent);
    }


}

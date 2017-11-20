package com.example.captainhumza.fyp_version3;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.captainhumza.fyp_version3.Classes.ConstantClass;
import com.example.captainhumza.fyp_version3.Classes.DatabaeHelper;
import com.example.captainhumza.fyp_version3.Classes.DatabaeHelper;
import com.example.captainhumza.fyp_version3.Classes.GetSync;
import com.example.captainhumza.fyp_version3.Classes.Person;
import com.example.captainhumza.fyp_version3.Classes.WebServiceHandler;
import com.example.captainhumza.fyp_version3.Rider.RiderMenu;
import com.example.captainhumza.fyp_version3.Vender.VenderMenuActivity;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;
import org.honorato.multistatetogglebutton.ToggleButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements WebServiceHandler.AsyncResponse {


    String UserName , password = "";
    EditText editUserName , editpassword ;
    TextView forgotpassword ;
    private Button button;
    private TextView resultText;
    private Person peron = Person.GetInstance();
    boolean check = false;
    int type = 0;
    static LoginActivity inst = null;
    public static LoginActivity instance()
    {
        return inst;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

        editUserName = (EditText)findViewById(R.id.sign_in_email);
        forgotpassword = (TextView)findViewById(R.id.forgotpass);
        forgotpassword.setText("Forgotten Account");
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(forgotpassword.getText().toString().equals("Forgotten Account") )
                {
                    showDialogforAccount();
                }
                else
                {
                    showDialogforPassword();
                }

            }
        });

        editUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    check = ValidateEmail(editUserName.getText().toString());
                    if(check == false)
                    {
                        editUserName.setError("Invalid Email");
                    }
                    else
                    {
                        forgotpassword.setText("Forgotten Password");
                    }
                }
            }
        });

    }


    public void LoginButton(View view) {
        editUserName = (EditText)findViewById(R.id.sign_in_email);
        editpassword = (EditText)findViewById(R.id.sign_in_password);

        if(editUserName.getText().length() > 2 && editpassword.getText().length() > 2)
        {
            UserName = editUserName.getText().toString();
            password = editpassword.getText().toString();
            HashMap<String, String> User = peron.ValidatePerson(UserName , password);
            new WebServiceHandler(this, ConstantClass.LogIn , User ).execute();
        }else
            {
                Toast.makeText(this, "Please type\nEmail and Password", Toast.LENGTH_SHORT).show();
            }


    }

    @Override
    public void processFinish(Person output) {
        if(output != null)
        {
            if(output.PersonTypeId == 3)
            {
                Intent intent1 = new Intent(this , MainActivity.class);
                startActivity(intent1);
            }else if(output.PersonTypeId == 2)
            {
                Intent intent1 = new Intent(this , VenderMenuActivity.class);
                startActivity(intent1);
            }else if(output.PersonTypeId == 1)
            {
                Intent intent1 = new Intent(this , RiderMenu.class);
                startActivity(intent1);
            }else
                {
                    Toast.makeText(this, "Invalid User", Toast.LENGTH_SHORT).show();
                }
        }

    }
    private  boolean ValidateEmail(String email )
    {
        String emaill = editUserName.getText().toString().trim();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(emaill.matches(emailPattern)  )
        {
            return  true;
        }
        else
        {
            return  false;
        }
    }


    protected void showDialogforPassword() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(LoginActivity.this);
        View promptView = layoutInflater.inflate(R.layout.dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        editText.setText("abdulrehmanss32@gmail.com");
                        Toast.makeText(LoginActivity.this , "Code Send to Your Email"  , Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


    protected void showDialogforAccount() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(LoginActivity.this);
        View promptView = layoutInflater.inflate(R.layout.dialogforaccount, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        check= ValidateEmail(editText.getText().toString());

      editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
          @Override
          public void onFocusChange(View v, boolean hasFocus) {
              if(!hasFocus)
              {
                  if(check == false) {
                      editText.setError("Invalid Email");
                  }


              }
          }
      });
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        showDialogforPassword();


                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
            // setup a dialog window



        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    @Override
    public void onRestart()
    {
        super.onRestart();
        if(Person.GetInstance().PersonEmail == null)
            finish();
        else
            finish();
    }
}


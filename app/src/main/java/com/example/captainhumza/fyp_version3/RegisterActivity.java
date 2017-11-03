package com.example.captainhumza.fyp_version3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private Spinner roleSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        addItemsOnSpinner();
    }

    public void addItemsOnSpinner() {

        roleSpinner = (Spinner) findViewById(R.id.sign_up_select_spinner);
        List<String> list = new ArrayList<String>();
        list.add("Select User Role");
        list.add("Customer");
        list.add("Vendor");
        list.add("Rider");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(dataAdapter);
    }
}

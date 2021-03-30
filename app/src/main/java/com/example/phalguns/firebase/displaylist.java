package com.example.phalguns.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class displaylist extends AppCompatActivity {

    TextView from,to,vehicleName,Vehicleno;
    Intent i;
    ArrayList<String> array = new ArrayList<String>();
    ArrayList<String> namearray = new ArrayList<String>();
    ArrayList<String> fromarray = new ArrayList<String>();
    ArrayList<String> toarray = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaylist);

        Vehicleno = findViewById(R.id.vehicleno);
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        vehicleName = findViewById(R.id.vehicleName);

        i=getIntent();
        array=i.getStringArrayListExtra("array");
        namearray=i.getStringArrayListExtra("name");
        fromarray=i.getStringArrayListExtra("from");
        toarray=i.getStringArrayListExtra("to");
    }
}

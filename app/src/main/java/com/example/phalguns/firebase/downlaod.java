package com.example.phalguns.firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;

public class downlaod extends AppCompatActivity {

    ListView listview;
    ArrayList<String> array = new ArrayList<String>();
    ArrayList<String> namearray = new ArrayList<String>();
    ArrayList<String> fromarray = new ArrayList<String>();
    ArrayList<String> toarray = new ArrayList<String>();
    private adapter mAdapter;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downlaod);
        listview = findViewById(R.id.listview);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // String key=mDatabase.getKey();
        DatabaseReference mDatabase = database.getReference();

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                user VehicleNo = dataSnapshot.getValue(user.class);
                String Vehiclenumber = VehicleNo.getVehicleNo();
                String vehicleNAme = VehicleNo.getVehicleName();
                String from = VehicleNo.getPlace();
                String to = VehicleNo.getTo();
                array.add(Vehiclenumber);
                namearray.add(vehicleNAme);
                fromarray.add(from);
                toarray.add(to);
                mAdapter = new adapter(downlaod.this, array,namearray,fromarray,toarray);
                listview.setAdapter(mAdapter);


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        listview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(downlaod.this, displaylist.class);

                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable)array);
                i.putExtra("BUNDLE",args);
                i.putExtra("array",array);
                i.putExtra("name",namearray);
                i.putExtra("from",fromarray);
                i.putExtra("to",toarray);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

    }
}

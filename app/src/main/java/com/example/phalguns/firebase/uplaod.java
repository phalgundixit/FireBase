package com.example.phalguns.firebase;

import android.app.DownloadManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.textclassifier.TextClassification;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class uplaod extends AppCompatActivity {

    EditText vehicleNo,VehicleName,from,to;
    Button btnUpload;

    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private FirebaseDatabase mFireBaseDataBase;
    private DatabaseReference mRef;



    //URL derived from form URL
    public static final String URL="https://docs.google.com/forms/d/e/1FAIpQLScntMwwx3hsE6_LZoJwc01RbHIkm6oTf9rRvywfww6_Z9EgYw/formResponse";
    //input element ids found from the live form page
    public static final String VEHICLE_NO="entry.1669586910";
    public static final String VEHICLE_NAME="entry.1542308193";
    public static final String FROM="entry.593524800";
    public static final String TO="entry.2087730370";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uplaod);


        auth = FirebaseAuth.getInstance();
        //write data to firebase database
        mFireBaseDataBase = FirebaseDatabase.getInstance();
        mRef = mFireBaseDataBase.getReference().push();


        vehicleNo = findViewById(R.id.vehicleno);
        VehicleName = findViewById(R.id.vehicleName);
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        btnUpload = findViewById(R.id.btnUpload);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://docs.google.com/forms/d/")
                .build();
        final api spreadsheetWebService = retrofit.create(api.class);

        findViewById(R.id.btnUpload).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String vehicleno = vehicleNo.getText().toString();
                        String vehiclename = VehicleName.getText().toString();
                        String From = from.getText().toString();
                        String To = to.getText().toString();



                        mRef.child("VehicleNo").setValue(vehicleno);
                        mRef.child("VehicleName").setValue(vehiclename);
                        mRef.child("From").setValue(From);
                        mRef.child("To").setValue(To);

                        Call<Void> completeQuestionnaireCall = spreadsheetWebService.spreadsheet(vehicleno, vehiclename,From,To);
                        completeQuestionnaireCall.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Toast.makeText(uplaod.this, "success", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(uplaod.this, "fail", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }


                }
        );



    }


}

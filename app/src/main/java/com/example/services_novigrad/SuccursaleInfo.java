package com.example.services_novigrad;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.services_novigrad.Services.NewServicesActivity;
import com.example.services_novigrad.Services.ServiceList;
import com.example.services_novigrad.Services.ServicesAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SuccursaleInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succursale_info2);


        String address = getIntent().getStringExtra("mAddress");
        String phone = getIntent().getStringExtra("mPhone");
        String description  = getIntent().getStringExtra("mDescription");

        String monday = getIntent().getStringExtra("mMonday");
        String tuesday = getIntent().getStringExtra("mTuesday");
        String wednesday = getIntent().getStringExtra("mWednesday");
        String thursday = getIntent().getStringExtra("mThursday");
        String friday = getIntent().getStringExtra("mFriday");
        String saturday = getIntent().getStringExtra("mSaturday");
        String sunday = getIntent().getStringExtra("mSunday");


        TextView addressTextView = findViewById(R.id.address);
        TextView phoneTextView = findViewById(R.id.phone);
        TextView descriptionTextView = findViewById(R.id.description);

        TextView mondayTextView = findViewById(R.id.monday);
        TextView tuesdayTextView = findViewById(R.id.tuesday);
        TextView wednesdayTextView = findViewById(R.id.wednesday);
        TextView thursdayTextView = findViewById(R.id.thursday);
        TextView fridayTextView = findViewById(R.id.friday);
        TextView saturdayTextView = findViewById(R.id.saturday);
        TextView sundayTextView = findViewById(R.id.sunday);



        addressTextView.setText(address);
        phoneTextView.setText(phone);
        descriptionTextView.setText(description);

        mondayTextView.setText(monday);
        tuesdayTextView.setText(tuesday);
        wednesdayTextView.setText(wednesday);
        thursdayTextView.setText(thursday);
        fridayTextView.setText(friday);
        saturdayTextView.setText(saturday);
        sundayTextView.setText(sunday);




        Button btnRequestServices = findViewById(R.id.buttonRequest);
        btnRequestServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(SuccursaleInfo.this, RequestForm.class);
                startActivity(intent);
            }
        });
    }










}
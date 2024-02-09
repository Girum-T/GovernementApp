package com.example.services_novigrad.Services;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.services_novigrad.AddServicesPage;
import com.example.services_novigrad.DeleteServicesPage;
import com.example.services_novigrad.EditServicesPage;
import com.example.services_novigrad.Login;
import com.example.services_novigrad.R;
import com.google.firebase.auth.FirebaseAuth;

public class ServicesPage extends AppCompatActivity {

    private Button btnAddServices , btnEditServices , btnDeleteServices, logout;
    FirebaseAuth fAuth;

    public void openAddService(){

        startActivity(new Intent(ServicesPage.this, AddServicesPage.class));

    }

    public void openEditPage(){

        startActivity(new Intent(ServicesPage.this, EditServicesPage.class));

    }

    public void openDeletePage(){

        startActivity(new Intent(ServicesPage.this, DeleteServicesPage.class));

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_page);

        btnAddServices = findViewById(R.id.addServices);
        btnEditServices = findViewById(R.id.editServices);
        btnDeleteServices = findViewById(R.id.deleteServices);
        logout = findViewById(R.id.logout);

        fAuth = FirebaseAuth.getInstance();

        btnAddServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddService();
            }
        });


        btnEditServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditPage();
            }
        });

        btnDeleteServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDeletePage();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.signOut();
                Intent intent = new Intent(ServicesPage.this , Login.class);
                startActivity(intent);
                finish();
                Toast.makeText(ServicesPage.this , "Logout successful" , Toast.LENGTH_SHORT).show();
            }
        });
    }
}
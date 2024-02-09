package com.example.services_novigrad.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.services_novigrad.Login;
import com.example.services_novigrad.R;
import com.example.services_novigrad.Services.ServicesPage;
import com.google.firebase.auth.FirebaseAuth;

public class AdminPage extends AppCompatActivity {

    private Button services;
    private Button users;
    private Button logout;

    FirebaseAuth fAuth;

    public void openServicePage(){

        startActivity(new Intent(AdminPage.this, ServicesPage.class));

    }

    public void openUsersPage(){

        startActivity(new Intent(AdminPage.this, UsersPage.class));

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        services = findViewById(R.id.services);
        users = findViewById(R.id.user);
        logout = findViewById(R.id.logout);

        fAuth = FirebaseAuth.getInstance();

        services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openServicePage();
            }
        });

        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUsersPage();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.signOut();
                Intent intent = new Intent(AdminPage.this , Login.class);
                startActivity(intent);
                finish();
                Toast.makeText(AdminPage.this , "Logout successful" , Toast.LENGTH_SHORT).show();
            }
        });
    }
}
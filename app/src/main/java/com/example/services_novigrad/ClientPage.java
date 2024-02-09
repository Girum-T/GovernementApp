package com.example.services_novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ClientPage extends AppCompatActivity {

    private Button requestServices;
    private Button logout;
    FirebaseAuth fAuth;

    public void openSuccursalePage(){

        startActivity(new Intent(ClientPage.this, ClientViewSuccursalePage.class));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_page);

        requestServices = findViewById(R.id.requestServices);
        logout = findViewById(R.id.logout);
        fAuth = FirebaseAuth.getInstance();

        requestServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSuccursalePage();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.signOut();
                Intent intent = new Intent(ClientPage.this , Login.class);
                startActivity(intent);
                finish();
                Toast.makeText(ClientPage.this , "Logout successful" , Toast.LENGTH_SHORT).show();
            }
        });
    }
}
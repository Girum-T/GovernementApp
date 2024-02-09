package com.example.services_novigrad;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class WelcomePage extends AppCompatActivity {
    TextView userName , role, textView;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    Button btnLogout;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        userName = (TextView) findViewById(R.id.userName);
        role = (TextView) findViewById(R.id.role);
        textView = findViewById(R.id.textView);

        btnLogout = findViewById(R.id.buttonLogout);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userId);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                userName.setText(documentSnapshot.getString("mUserName"));
                role.setText(documentSnapshot.getString("madmin"));

            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.signOut();
                Intent intent = new Intent(WelcomePage.this , Login.class);
                startActivity(intent);
                finish();
                Toast.makeText(WelcomePage.this , "Logout successful" , Toast.LENGTH_SHORT).show();
            }
        });


    }

}
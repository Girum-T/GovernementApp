package com.example.services_novigrad;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    EditText mEmail, mUserName, mPassword;
    RadioButton admin, employee, client;
    Button confirmBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mEmail = findViewById(R.id.email);
        mUserName = findViewById(R.id.userName);
        mPassword = findViewById(R.id.password);

        admin = findViewById(R.id.checkBoxAdmin);
        employee = findViewById(R.id.checkBoxEmployee);
        client = findViewById(R.id.checkBoxClient);

        confirmBtn=findViewById(R.id.buttonConfirm);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String userName = mUserName.getText().toString();
                String madmin = admin.getText().toString();
                String memployee = employee.getText().toString();
                String mclient = client.getText().toString();

                if(mclient.isEmpty()){
                    admin.setError("Email is required");
                    Toast.makeText(v.getContext(), "ERREUR",Toast.LENGTH_SHORT);
                    return;
                }


                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required");
                    return;
                }


                if (TextUtils.isEmpty(userName)){
                    mUserName.setError("Username is required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is required");
                    return;
                }

                if (password.length() < 6) {
                    mPassword.setError("password must be >= 6 digits");
                    return;
                }
                if(!admin.isChecked()&& !employee.isChecked()&& !client.isChecked()){
                    admin.setError("Selectionner un type de Compte");
                    return;
                }




                //Register the user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){




                            //problem ligne startActivity(new Intent(getApplicationContext(), WelcomePage.class));. Le lien de verification est envoyé pour verifier si le email existe bel et bien , mais User est automatiquement connecté à la page d acceuil

                            Toast.makeText(SignUpActivity.this , "Account created   " , Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();

                            //create collection and document to store in firestore
                            // My collection is called users and inside we're gonna have documents
                            DocumentReference documentReference = fStore.collection("users").document(userID);

                            if (admin.isChecked()){

                                //create document to store data . Hashmap is ususally used
                                Map<String,Object> user = new HashMap<>();
                                user.put("mEmail" , email);
                                user.put("mPassword" , password);
                                user.put("mUserName", userName);
                                user.put ("madmin" , madmin);
                                user.put("mid", userID);


                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d(TAG, "onSuccess: user profile created for" + userID);
                                    }
                                });

                                startActivity(new Intent(getApplicationContext(), Login.class));

                            } else if (employee.isChecked()) {
                                Map<String,Object> user = new HashMap<>();
                                user.put("mEmail" , email);
                                user.put("mPassword" , password);
                                user.put("mUserName", userName);
                                user.put ("madmin" , memployee);
                                user.put("mid", userID);
                                user.put("msuccursale", null);

                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d(TAG, "onSuccess: user profile created for" + userID);
                                    }
                                });

                                startActivity(new Intent(getApplicationContext(), Login.class));


                            } else {
                                Map<String,Object> user = new HashMap<>();
                                user.put("mEmail" , email);
                                user.put("mPassword" , password);
                                user.put("mUserName", userName);
                                user.put ("madmin" , mclient);
                                user.put("mid", userID);

                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d(TAG, "onSuccess: user profile created for" + userID);
                                    }
                                });

                                startActivity(new Intent(getApplicationContext(), Login.class));

                            }}

                        else {
                            Toast.makeText(SignUpActivity.this , "Error !" + task.getException().getMessage() , Toast.LENGTH_SHORT).show();

                        }


                    };


                });


            };


        });
    }}

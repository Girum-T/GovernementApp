package com.example.services_novigrad;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.services_novigrad.Admin.AdminPage;
import com.example.services_novigrad.Employee.MainBranchPage;
import com.example.services_novigrad.Employee.NewEmployeePageError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    EditText mEmail, mPassword;

    TextView role;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;


    private Button buttonSignUp,mSignIn ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);

        fAuth = FirebaseAuth.getInstance();

        mSignIn = findViewById(R.id.buttonSignIn);

        buttonSignUp = findViewById(R.id.buttonCreateAccount);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUpActivity();
            }


        });




        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required");
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



                //String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                //CollectionReference usersRef = null;


                //Authenticate the user
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser currentUser = fAuth.getCurrentUser();
                            String uid = currentUser.getUid();
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            DocumentReference userRef = db.collection("users").document(uid);
                            userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot document = task.getResult();
                                    String mAdmin = document.getString("madmin");
                                    Toast.makeText(Login.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                                    if(mAdmin.equals("Administrator")){
                                        openAdminPage();
                                    }
                                    else if(mAdmin.equals("Employee")){
                                        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                String Succursale = task.getResult().getString("msuccursale");
                                                if(Succursale==null){
                                                    openNewEmployeePageActivity(uid);

                                                }else{
                                                    openMainBranchPage(uid);
                                                }
                                            }
                                        });
                                        }
                                    else{
                                        openClientPage();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(Login.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }

                });

            }
        });




    }



    public void openSignUpActivity(){
        Intent intent=new Intent(this, SignUpActivity.class);
        startActivity(intent);

    }
    public void openWelcomeActivity(){
        Intent intent=new Intent(this, WelcomePage.class);
        startActivity(intent);

    }
    public void openNewEmployeePageActivity(String uid){
        Intent intent=new Intent(this, NewEmployeePageError.class);
        intent.putExtra("ID", uid);
        startActivity(intent);
    }
    public void openMainBranchPage(String uid){
        Intent intent=new Intent(this, MainBranchPage.class);
        intent.putExtra("ID", uid);
        startActivity(intent);
    }

    public void openAdminPage(){
        Intent intent = new Intent(Login.this, AdminPage.class);
        startActivity(intent);
        finish();
    }

    public void openClientPage(){
        Intent intent = new Intent(Login.this, ClientPage.class);
        startActivity(intent);
        finish();
    }


}

package com.example.services_novigrad.Employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.services_novigrad.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BranchEditPage extends AppCompatActivity {
    EditText mName, mAddress, mPhone, mDescription;
    FirebaseFirestore fStore;
    String id;
    Button enregistrer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        setContentView(R.layout.activity_edit_succursale);
        mName = findViewById(R.id.branch_name);
        mAddress = findViewById(R.id.address);
        mPhone = findViewById(R.id.phone_num);
        mDescription = findViewById(R.id.description);

        enregistrer = findViewById(R.id.button_enregistrer);
        fStore = FirebaseFirestore.getInstance();
        enregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString().trim();
                String address = mAddress.getText().toString().trim();
                String phone = mPhone.getText().toString().trim();
                String description = mDescription.getText().toString().trim();
                List<String> services = new ArrayList<>();
                services.add("");
                if(name.isEmpty()|| address.isEmpty()|| phone.isEmpty()) {
                    if (name.isEmpty()) {
                        mName.setError("A Remplir");
                        Toast.makeText(v.getContext(), "Veuillez tout remplir", Toast.LENGTH_SHORT);
                    }
                    if (address.isEmpty()) {
                        mAddress.setError("A Remplir");
                        Toast.makeText(v.getContext(), "Veuillez tout remplir", Toast.LENGTH_SHORT);
                    }
                    if (phone.isEmpty()) {
                        mPhone.setError("A Remplir");
                        Toast.makeText(v.getContext(), "Veuillez tout remplir", Toast.LENGTH_SHORT);
                    }

                }else if(!phone.matches("\\d{10}")){
                    mPhone.setError("Format de numéro incorrect");
                    Toast.makeText(v.getContext(), "Utiliser un bon format de numéro de téléphone", Toast.LENGTH_SHORT).show();

                }else{
                    fStore.collection("users").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    // Retrieve the user's name
                                    String suc = document.getString("msuccursale");
                                    if(suc == null){
                                        Map<String, Object> succursaledata = new HashMap<>();

                                        succursaledata.put("mName", name);
                                        succursaledata.put("mAddress", address);
                                        succursaledata.put("mPhone", phone);
                                        succursaledata.put("mDescription", description);
                                        succursaledata.put("mService", services);
                                        fStore.collection("Succursale").add(succursaledata).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                String documentId = task.getResult().getId();
                                                succursaledata.put("mId", documentId);
                                                fStore.collection("Succursale").document(documentId).set(succursaledata);
                                                Map<String, Object> update = new HashMap<>();
                                                update.put("msuccursale", documentId);
                                                fStore.collection("users").document(id).update(update);
                                                Toast.makeText(v.getContext(), "Branch saved successfully", Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(getApplicationContext(), MainBranchPage.class);
                                                intent.putExtra("ID", id);
                                                startActivity(intent);

                                        }
                                    });

                                    }else{
                                        Map<String, Object> succursaledata = new HashMap<>();

                                        succursaledata.put("mName", name);
                                        succursaledata.put("mAddress", address);
                                        succursaledata.put("mPhone", phone);
                                        succursaledata.put("mDescription", description);
                                        fStore.collection("Succursale").document(suc).update(succursaledata).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(v.getContext(), "Branch updated successfully", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getApplicationContext(), MainBranchPage.class);
                                                intent.putExtra("ID", id);
                                                startActivity(intent);
                                            }
                                        });
                                    }

                                }
                            }

                        }
                    });





                }

            }
        });

    }
}

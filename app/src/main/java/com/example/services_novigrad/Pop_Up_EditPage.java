package com.example.services_novigrad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Pop_Up_EditPage extends AppCompatActivity {
    EditText existingServiceName,  newServiceName;
    EditText existingForm, newForm;

    EditText existingDoc, newDoc;

    Button btnUpdate ;


    FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_edit_page);

        existingServiceName = findViewById(R.id.inputServiceName);
        newServiceName=findViewById(R.id.NewInputServiceName);

        existingForm =  findViewById(R.id.inputForm);
        newForm = findViewById(R.id.NewInputForm);

        existingDoc=  findViewById(R.id.inputDoc);
        newDoc = findViewById(R.id.NewInputDoc);

        btnUpdate =findViewById(R.id.btnUpdateData);

        db= FirebaseFirestore.getInstance();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String existingservicename = existingServiceName.getText().toString();
                String newservicename = newServiceName.getText().toString();
                existingServiceName.setText("");
                newServiceName.setText("");

                String existingform = existingForm.getText().toString();
                String newform = newForm.getText().toString();
                existingForm.setText("");
                newForm.setText("");

                String existingdoc = existingDoc.getText().toString();
                String newdoc = newDoc.getText().toString();
                existingDoc.setText("");
                newDoc.setText("");

                updateServices(existingservicename,newservicename , existingform, newform, existingdoc , newdoc);
            }
        });

    }

    private void updateServices(String existingservicename, String newservicename, String existingform, String newform, String existingdoc, String newdoc) {


        Map<String ,Object> serviceDetail = new HashMap<>();
        serviceDetail.put( "servicename", newservicename);
        serviceDetail.put( "formulaires", newform);
        serviceDetail.put( "documents", newdoc);



        db.collection("ServicesBookRef").whereEqualTo("servicename", existingServiceName)
                .whereEqualTo("formulaires" ,existingform).whereEqualTo("documents" ,existingdoc).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()){
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String documentID =documentSnapshot.getId();
                            db.collection("ServicesBookRef").document(documentID).update(serviceDetail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(Pop_Up_EditPage.this,"success",Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Pop_Up_EditPage.this,"error",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }else{
                            Toast.makeText(Pop_Up_EditPage.this,"failed",Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


        Intent intent = new Intent(Pop_Up_EditPage.this , AddServicesPage.class);
        startActivity(intent);


        //}
    };

}







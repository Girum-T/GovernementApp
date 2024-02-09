package com.example.services_novigrad.Services;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.services_novigrad.AddServicesPage;
import com.example.services_novigrad.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NewServicesActivity extends AppCompatActivity {

    private EditText addServiceName;
    private EditText addServiceForm;
    private EditText addServiceDoc;

    FirebaseFirestore db;

    private EditText addId;
    private Button buttonAddService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_services);

        addServiceName = findViewById(R.id.inputServiceName);
        addServiceForm = findViewById(R.id.inputForm);
        addServiceDoc = findViewById(R.id.inputDoc);
        addId = findViewById(R.id.inputId);
        buttonAddService = (Button) findViewById(R.id.btnConfirm);

        db = FirebaseFirestore.getInstance();

        buttonAddService.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                saveServices();
            }
        });
    }


    private void saveServices(){

        String serviceName =addServiceName.getText().toString();
        String formulaires = addServiceForm.getText().toString();
        String documents = addServiceDoc.getText().toString();
        String id = addId.getText().toString();

        Map<String,Object> user = new HashMap<>();
        user.put("servicename" , serviceName);
        user.put("formulaires" , formulaires);
        user.put("documents",documents);





        if (serviceName.trim().isEmpty() || formulaires.trim().isEmpty() || documents.trim().isEmpty() || id.trim().isEmpty()){
            Toast.makeText(NewServicesActivity.this , "Please, insert service name, form , documents, id" ,  Toast.LENGTH_SHORT).show();
            return;
        }
        db.collection("ServicesBookRef").add(user).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                String documentId = task.getResult().getId();
                user.put("id", documentId);
                db.collection("ServicesBookRef").document(documentId).set(user);
                //Toast.makeText(NewServicesActivity.this, "Services added", Toast.LENGTH_SHORT).show();

                //CollectionReference servicesBookRef = FirebaseFirestore.getInstance().collection("ServicesBookRef");
                //servicesBookRef.add(new ServiceList(serviceName,formulaires, documents ));
                //Toast.makeText(NewServicesActivity.this , "Services added" ,  Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(NewServicesActivity.this, AddServicesPage.class);
                startActivity(intent);
            }





        });


    }

}
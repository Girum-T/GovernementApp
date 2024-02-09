package com.example.services_novigrad.Employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.services_novigrad.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainBranchPage extends AppCompatActivity {
    Button Edit_info, Edit_hours, Edit_services;
    FirebaseFirestore fStore;
    String id, sucID;
    ListView servicelistview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        setContentView(R.layout.activity_branch_page);
        Edit_info = findViewById(R.id.edit_branch_info);
        Edit_hours = findViewById(R.id.edit_working_hours);
        Edit_services = findViewById(R.id.edit_branch_services);
        fStore = FirebaseFirestore.getInstance();
        servicelistview = findViewById(R.id.listbranchservice);
        fStore.collection("users").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            sucID = task.getResult().getString("msuccursale");
                fStore.collection("Succursale").document(sucID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {

                            ArrayList<String> list = (ArrayList<String>) document.get("mServices");


                            ArrayList<String> serviceList = new ArrayList<>();


                            ArrayList<Task<DocumentSnapshot>> tasks = new ArrayList<>();
                            if(list!=null){
                            for (String e : list) {
                                Task<DocumentSnapshot> taskDoc = fStore.collection("ServicesBookRef").document(e).get();
                                tasks.add(taskDoc);
                            }


                            Tasks.whenAllComplete(tasks).addOnCompleteListener(new OnCompleteListener<List<Task<?>>>() {
                                @Override
                                public void onComplete(@NonNull Task<List<Task<?>>> allTasks) {
                                    for (Task<?> task : allTasks.getResult()) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot doc = (DocumentSnapshot) task.getResult();
                                            if (doc.exists()) {
                                                serviceList.add(doc.getString("servicename"));
                                            }
                                        }
                                    }
                                    // Update the ListView after all data has been fetched
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(MainBranchPage.this, android.R.layout.simple_list_item_1, serviceList);
                                    servicelistview.setAdapter(adapter);
                                }
                            });}
                        }
                    }
                });

            }
        });





        Edit_hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WorkingHours.class);
                intent.putExtra("sID", sucID);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });
        Edit_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BranchEditPage.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });
        Edit_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddServicePage.class);
                intent.putExtra("sID", sucID);
                intent.putExtra("ID", id);
                startActivity(intent);

            }
        });
    }
}

package com.example.services_novigrad.Employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.services_novigrad.R;
import com.example.services_novigrad.Services.ServiceList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddServicePage extends AppCompatActivity implements CheckList{
    RecyclerView recview;
    ArrayList<ServiceList> datalist;
    FirebaseFirestore db;
    ServiceAdapterEmployee adapterEmployee;
    String sID, id;
    Button add_service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        sID= intent.getStringExtra("sID");
        id= intent.getStringExtra("ID");
        setContentView(R.layout.activity_add_branch_service);
        datalist = new ArrayList<ServiceList>();
        recview = (RecyclerView)findViewById(R.id.listservice);

        add_service = findViewById(R.id.button_add_services_branch);

        db = FirebaseFirestore.getInstance();
        db.collection("ServicesBookRef").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot d:list){
                    ServiceList obj = d.toObject(ServiceList.class);
                    datalist.add(obj);
                }
                recview.setHasFixedSize(true);
                recview.setLayoutManager(new LinearLayoutManager(AddServicePage.this));

                adapterEmployee =new ServiceAdapterEmployee(datalist, db, AddServicePage.this);
                recview.setAdapter(adapterEmployee);
                adapterEmployee.notifyDataSetChanged();
                add_service.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<String> selectionner = adapterEmployee.getSelected();
                        Map<String, Object> succursaledata = new HashMap<>();
                        succursaledata.put("mServices", selectionner);
                        db.collection("Succursale").document(sID).update(succursaledata).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getApplicationContext(), "Services saved successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainBranchPage.class);
                                intent.putExtra("ID", id);
                                startActivity(intent);
                            }
                        });
                    }
                });

            }
        });





    }

    @Override
    public void onQuantityChange(ArrayList<String> arrayList) {

    }
}

package com.example.services_novigrad.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.services_novigrad.R;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.database.DatabaseReference;


import java.util.ArrayList;
import java.util.List;

public class UsersPage extends AppCompatActivity {

    RecyclerView recview;
    ArrayList<model> datalist;
    DatabaseReference mRef;
    FirebaseFirestore db;
    myadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_page);

        recview = (RecyclerView)findViewById(R.id.listuser);
        recview.setLayoutManager(new LinearLayoutManager(this));
        datalist = new ArrayList<>();
        adapter=new myadapter(datalist);
        recview.setAdapter(adapter);



        db = FirebaseFirestore.getInstance();
        db.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot d:list){
                    model obj = d.toObject(model.class);
                    datalist.add(obj);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}
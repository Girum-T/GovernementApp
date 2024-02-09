package com.example.services_novigrad;



import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.services_novigrad.Services.NewServicesActivity;
import com.example.services_novigrad.Services.ServiceList;
import com.example.services_novigrad.Services.ServicesAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class AddServicesPage extends AppCompatActivity implements RecyclerViewInterface{

    RecyclerView recyclerView;
    ArrayList<ServiceList> userArrayList;
    ServicesAdapter servicesAdapter;
    ProgressDialog progressDialog;
    private ServicesAdapter adapter;
    private FirebaseFirestore db;

    //private FirebaseFirestore db= FirebaseFirestore.getInstance();
    //private CollectionReference servicesBookRef = db.collection("ServicesBookRef");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_services_page);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data....");
        progressDialog.show();



        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        db= FirebaseFirestore.getInstance();
        userArrayList=new ArrayList<ServiceList>();
        servicesAdapter = new ServicesAdapter((Context) AddServicesPage.this,userArrayList,this);
        recyclerView.setAdapter(servicesAdapter);

        EventChangeListener();
        progressDialog.dismiss();



        FloatingActionButton btnAddServices = findViewById(R.id.button_add_services);
        btnAddServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AddServicesPage.this, NewServicesActivity.class);
                startActivity(intent);
            }
        });


    }



    private void EventChangeListener() {

        CollectionReference servicesBookRef = db.collection("ServicesBookRef").document().getParent();

        //Ajouter quelque chose pour ordonner les services ajoutés du plus recent au plus ancien


        servicesBookRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error!= null){

                    if(progressDialog.isShowing())
                        progressDialog.dismiss();

                    Log.e("firestore error",error.getMessage());
                    return;
                }

                for(DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType()==DocumentChange.Type.ADDED){
                        userArrayList.add(dc.getDocument().toObject(ServiceList.class));
                    }

                    servicesAdapter.notifyDataSetChanged();
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();

                }



            }
        });
    }


    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemLongClick(int position) {

    }

    @Override
    public void onItemClick(String deleteId) {

    }
}

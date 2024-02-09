package com.example.services_novigrad;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.services_novigrad.Services.ServiceList;
import com.example.services_novigrad.Services.ServicesAdapter;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class EditServicesPage extends AppCompatActivity implements RecyclerViewInterface {

    RecyclerView recyclerView;
    ArrayList<ServiceList> userArrayList;
    ServicesAdapter servicesAdapter;

    private ServicesAdapter adapter;
    private FirebaseFirestore db;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_services_page);

        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        db= FirebaseFirestore.getInstance();
        userArrayList=new ArrayList<ServiceList>();
        servicesAdapter = new ServicesAdapter(EditServicesPage.this,userArrayList,this);
        recyclerView.setAdapter(servicesAdapter);

        EventChangeListener();
    }

    private void EventChangeListener() {

        CollectionReference servicesBookRef = db.collection("ServicesBookRef").document().getParent();

        //Ajouter quelque chose pour ordonner les services ajoutés du plus recent au plus ancien


        servicesBookRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error!= null){



                    Log.e("firestore error",error.getMessage());
                    return;
                }

                for(DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType()==DocumentChange.Type.ADDED){
                        userArrayList.add(dc.getDocument().toObject(ServiceList.class));
                    }

                    servicesAdapter.notifyDataSetChanged();


                }



            }
        });
    }

    @Override
    public void onItemClick(int position) {


    }

    @Override
    public void onItemLongClick(int position) {
        userArrayList.get(position);
        servicesAdapter.notifyItemChanged(position);
        Intent intent = new Intent(EditServicesPage.this, Pop_Up_EditPage.class);
        startActivity(intent);



    }

    @Override
    public void onItemClick(String deleteId) {

    }
}
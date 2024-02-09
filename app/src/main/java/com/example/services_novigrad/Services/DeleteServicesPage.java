package com.example.services_novigrad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.services_novigrad.Services.ServiceList;
import com.example.services_novigrad.Services.ServicesAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

//To delete, press long on the service name (the first line of every service that appears)
public class DeleteServicesPage extends AppCompatActivity implements RecyclerViewInterface{

    RecyclerView recyclerView;
    ArrayList<ServiceList> userArrayList;
    ServicesAdapter servicesAdapter;
    private ServicesAdapter adapter;
    FirebaseFirestore db;
    String id ;;



    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_services_page);

        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        db= FirebaseFirestore.getInstance();
        userArrayList=new ArrayList<ServiceList>();
        servicesAdapter = new ServicesAdapter(DeleteServicesPage.this,userArrayList,this);
        recyclerView.setAdapter(servicesAdapter);




        EventChangeListener();
    }



    private void EventChangeListener() {


        CollectionReference servicesBookRef = db.collection("ServicesBookRef").document().getParent();

        servicesBookRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error!= null){



                    Log.e("firestore error",error.getMessage());
                    return;
                }

                for(DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType()==DocumentChange.Type.ADDED){
                        userArrayList.add(dc.getDocument().toObject(ServiceList.class)); //essaye de comprendre cette ligne
                    }

                    servicesAdapter.notifyDataSetChanged();

                }

            }
        });
    }

    private void DataChanged() {
        userArrayList.clear();
        db.collection("ServicesBookRef").document().getParent().addSnapshotListener(new EventListener<QuerySnapshot>() {
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
//                           if(progressDialog.isShowing())
//                               progressDialog.dismiss();
                }
            }

        });
    }


    @Override
    public void onItemClick(int position) {

        db= FirebaseFirestore.getInstance();
        String id = userArrayList.get(position).getId();


        AlertDialog.Builder dialog = new AlertDialog.Builder(DeleteServicesPage.this);
        dialog.setTitle("Do you really want to delete?");
        dialog.setMessage("Deletion is permanent");

        dialog.setPositiveButton(" yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.collection("ServicesBookRef").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(DeleteServicesPage.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                        DataChanged();
                        servicesAdapter.notifyItemRemoved(position);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DeleteServicesPage.this, "Data not Deleted", Toast.LENGTH_SHORT).show();

                    }
                });

                dialog.dismiss();
            }
        });


        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    @Override
    public void onItemLongClick(int position) {}
    @Override
    public void onItemClick(String id) {}


}



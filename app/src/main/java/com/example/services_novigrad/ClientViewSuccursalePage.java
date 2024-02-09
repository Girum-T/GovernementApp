package com.example.services_novigrad;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ClientViewSuccursalePage extends AppCompatActivity implements RecyclerViewInterface {

    RecyclerView recyclerView;
    ArrayList<SuccursaleList> ArrayList;
    SuccursalesAdapter succursaleAdapter;
    ProgressDialog progressDialog;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succursale_page);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data....");
        progressDialog.show();


        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        db= FirebaseFirestore.getInstance();
        ArrayList=new ArrayList<SuccursaleList>();
        succursaleAdapter = new SuccursalesAdapter((Context) ClientViewSuccursalePage.this,ArrayList,this);
        recyclerView.setAdapter(succursaleAdapter);

        EventChangeListener();
        progressDialog.dismiss();
    }

    private void EventChangeListener() {
        db.collection("Succursale").document().getParent().addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {

                    if(progressDialog.isShowing())
                        progressDialog.dismiss();

                    Log.e("firestore error", error.getMessage());
                    return;
                }

                for (DocumentChange dc : value.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        ArrayList.add(dc.getDocument().toObject(SuccursaleList.class));
                    }

                    succursaleAdapter.notifyDataSetChanged();
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();

                }
            }
        });

    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(ClientViewSuccursalePage.this , SuccursaleInfo.class);
        intent.putExtra("mAddress", ArrayList.get(position).getmAddress());
        intent.putExtra("mPhone", ArrayList.get(position).getmPhone());
        intent.putExtra("mDescription", ArrayList.get(position).getmDescription());
        intent.putExtra("mMonday", ArrayList.get(position).getmMonday());
        intent.putExtra("mTuesday", ArrayList.get(position).getmTuesday());
        intent.putExtra("mWednesday", ArrayList.get(position).getmWednesday());
        intent.putExtra("mThursday", ArrayList.get(position).getmThursday());
        intent.putExtra("mFriday", ArrayList.get(position).getmFriday());
        intent.putExtra("mSaturday", ArrayList.get(position).getmSaturday());
        intent.putExtra("mSunday", ArrayList.get(position).getmSunday());



        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position) {

    }

    @Override
    public void onItemClick(String id) {

    }
}
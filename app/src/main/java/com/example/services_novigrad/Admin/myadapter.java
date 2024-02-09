package com.example.services_novigrad.Admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.services_novigrad.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder> {
    ArrayList<model> datalist;
    FirebaseFirestore firestore;
    FirebaseAuth auth;


    public myadapter(ArrayList<model> datalist) {
        this.datalist = datalist;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

    }


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_users, parent, false);
        return new myviewholder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.t1.setText(datalist.get(position).getmUserName());
        holder.t2.setText(datalist.get(position).getmadmin());
        holder.deleteitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String username = datalist.get(position).getmUserName();
                //String userID = auth.getCurrentUser().getUid();
                String userID = datalist.get(position).getmId();
                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
                dialog.setTitle("Do you really want to delete "+ username + "'s account?");
                dialog.setMessage("Deletion is permanent");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                        firestore.collection("users").document(userID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                datalist.remove(position); // Remove the item from the list
                                notifyItemRemoved(position); // Notify the adapter
                                notifyItemRangeChanged(position, datalist.size());
                                Toast.makeText(v.getContext(), "Success!" + userID, Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(v.getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                            }
                        });}
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });



    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myviewholder extends RecyclerView.ViewHolder{

        TextView t1,t2;
        ImageView deleteitem;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.t1);
            t2=itemView.findViewById(R.id.t2);
            deleteitem = itemView.findViewById(R.id.delete);

        }
    }
}

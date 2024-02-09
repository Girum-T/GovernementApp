package com.example.services_novigrad.Employee;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.services_novigrad.R;
import com.example.services_novigrad.Services.ServiceList;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ServiceAdapterEmployee extends RecyclerView.Adapter<ServiceAdapterEmployee.myviewholder> {
    ArrayList<ServiceList> datalist;
    FirebaseFirestore firestore;
    CheckList quantityLitsener;
    ArrayList<String> selected = new ArrayList<String>();


    public ServiceAdapterEmployee(ArrayList<ServiceList> datalist, FirebaseFirestore firestore, CheckList quantityLitsener) {
        this.datalist = datalist;
        firestore = FirebaseFirestore.getInstance();
        this.quantityLitsener = quantityLitsener;
    }

    @NonNull
    @Override
    public ServiceAdapterEmployee.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_services_employee, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceAdapterEmployee.myviewholder holder, int position) {
        holder.check_box.setText(datalist.get(position).getServicename());
        holder.check_box.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                if(holder.check_box.isChecked()){
                    selected.add(datalist.get(position).getId());
                }
                else{
                    selected.remove(datalist.get(position).getId());
                }

                quantityLitsener.onQuantityChange(selected);

            }
        });
    }

    public ArrayList<String> getSelected() {
        return selected;
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }


    class myviewholder extends RecyclerView.ViewHolder {

        CheckBox check_box;

        public myviewholder(@NonNull View itemView) {
            super(itemView );
            check_box = itemView.findViewById(R.id.checkbox_name_service);


        }

    }
}


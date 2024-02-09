package com.example.services_novigrad.Services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.services_novigrad.R;
import com.example.services_novigrad.RecyclerViewInterface;

import java.util.ArrayList;


public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServicesHolder> {

    Context context;
    static ArrayList<ServiceList> userArrayList;
    private final RecyclerViewInterface recyclerViewInterface;





    public ServicesAdapter(Context context, ArrayList<ServiceList> userArrayList, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.userArrayList = userArrayList;
        this.recyclerViewInterface=recyclerViewInterface;


    }

    @NonNull
    @Override
    public ServicesAdapter.ServicesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_service_list ,parent,false);
        return new ServicesHolder(v , recyclerViewInterface);
    }



    @Override
    public void onBindViewHolder(@NonNull ServicesHolder holder, int position) {
        ServiceList serviceList = userArrayList.get(position);


        holder.ServiceName.setText(serviceList.getServicename());
        holder.Formulaires.setText(serviceList.getFormulaires());
        holder.Documents.setText(serviceList.getDocuments());

    }



    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static  class ServicesHolder extends RecyclerView.ViewHolder{
        TextView ServiceName, Formulaires, Documents;


        public ServicesHolder(@NonNull View itemView,  RecyclerViewInterface recyclerViewInterface){
            super(itemView);
            ServiceName = itemView.findViewById(R.id.text_view_service_name);
            Formulaires = itemView.findViewById(R.id.text_view_formulaires);
            Documents= itemView.findViewById(R.id.text_view_documents);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface!=null){
                        int pos = getAdapterPosition();

                        if (pos!= RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                            String id=userArrayList.get(pos).getId();
                            recyclerViewInterface.onItemClick(id);
                        }





                    }

                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (recyclerViewInterface!=null){
                        int pos = getAdapterPosition();

                        if (pos!= RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemLongClick(pos);
                        }

                    }
                    return true;
                }
            });







        }
    }
}


package com.example.services_novigrad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewRequestAdapter extends RecyclerView.Adapter<ViewRequestAdapter.ViewRequestHolder>{

    Context context;
    static ArrayList<SuccursaleList> ArrayList;
    private final RecyclerViewInterface recyclerViewInterface;

    public ViewRequestAdapter(Context context, java.util.ArrayList<SuccursaleList> ArrayList, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.ArrayList =ArrayList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ViewRequestAdapter.ViewRequestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_view_request ,parent,false);
        return new  ViewRequestHolder(v , recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewRequestAdapter.ViewRequestHolder holder, int position) {
        SuccursaleList succursaleList = ArrayList.get(position);
        holder.SuccursaleName.setText(succursaleList.getmName());
        holder.Address.setText(succursaleList.getmAddress());

        //ajouter type de service, date et huere. Pour cela, cree une liste view request list afin de retourner getservice, getdate, get time

    }

    @Override
    public int getItemCount() {
        return ArrayList.size();
    }

    public static class ViewRequestHolder extends RecyclerView.ViewHolder{
        TextView SuccursaleName;
        TextView Address;

        public ViewRequestHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            SuccursaleName = itemView.findViewById(R.id.text_view_succursale_name);
           Address = itemView.findViewById(R.id.text_view_succursale_address);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface!=null){
                        int pos = getAdapterPosition();

                        if (pos!= RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                            String id=ArrayList.get(pos).getId();
                            recyclerViewInterface.onItemClick(id);
                        }

                    }

                }
                });
            }
        }
    }


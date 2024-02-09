package com.example.services_novigrad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.services_novigrad.Services.ServiceList;
import com.example.services_novigrad.Services.ServicesAdapter;

import java.util.ArrayList;

public class SuccursalesAdapter extends RecyclerView.Adapter<SuccursalesAdapter.SuccursalesHolder>{

    Context context;
    static ArrayList<SuccursaleList> ArrayList;
    private final RecyclerViewInterface recyclerViewInterface;

    public SuccursalesAdapter(Context context, ArrayList<SuccursaleList> ArrayList, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.ArrayList =ArrayList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public SuccursalesAdapter.SuccursalesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_succursale_list ,parent,false);
        return new SuccursalesHolder(v , recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull SuccursalesAdapter.SuccursalesHolder holder, int position) {
        SuccursaleList succursaleList = ArrayList.get(position);
        holder.SuccursaleName.setText(succursaleList.getmName());

    }

    @Override
    public int getItemCount() {
        return ArrayList.size();
    }

    public static class SuccursalesHolder extends RecyclerView.ViewHolder {
        TextView SuccursaleName;

        public SuccursalesHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            SuccursaleName = itemView.findViewById(R.id.text_view_succursale_name);

            itemView.setOnClickListener(new View.OnClickListener() {
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

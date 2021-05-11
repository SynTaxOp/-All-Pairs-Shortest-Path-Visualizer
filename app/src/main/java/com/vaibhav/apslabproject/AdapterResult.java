package com.vaibhav.apslabproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterResult extends RecyclerView.Adapter<AdapterResult. Holder> {

    Context context;
    int resource;
    java.util.ArrayList<Model> ArrayList;

    public AdapterResult(Context context, int resource, ArrayList<Model>  ArrayList) {
        this.context = context;
        this.resource = resource;
        this.ArrayList = ArrayList;
    }

    @NonNull
    @Override
    public AdapterResult. Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterResult. Holder(LayoutInflater.from(context).inflate(resource,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterResult. Holder holder, int position) {
        Model model = ArrayList.get(position);
        holder.first.setText(model.getPt1() + "");
        holder.second.setText(model.getPt2() + "");
        if(!model.getDist().equals("∞")) {
            holder.value.setText(model.getDist());
        }
        StringBuilder s = new StringBuilder();
        if(model.getPath() != null) {
            if(model.getPath().size() == 0) {
                s.append("Self Loop");
            } else {
                for (int i = 0; i < model.getPath().size(); i++) {
                    if (i < model.getPath().size() - 1) {
                        s.append(model.getPath().get(i) + 1).append(" --> ");
                    } else {
                        s.append(model.getPath().get(i) + 1);
                    }
                }
            }
        } else {
            s.append("Unreachable");
        }
        //Log.d("Paths", model.getPath().toString());
        holder.path.setText(s);
        final boolean[] touched = {false};
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("Paths", "hereCard"+touched[0]);
                if(!touched[0]) {
                    holder.pathFollow.setVisibility(View.VISIBLE);
                    touched[0] = true;
                } else {
                    holder.pathFollow.setVisibility(View.GONE);
                    touched[0] = false;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return ArrayList.size();
    }


    class  Holder extends RecyclerView.ViewHolder {

        TextView first, second, value, path;
        LinearLayout pathFollow;
        CardView card;
        public  Holder(@NonNull View itemView) {
            super(itemView);
            first = itemView.findViewById(R.id.first);
            second = itemView.findViewById(R.id.second);
            value = itemView.findViewById(R.id.value);
            card = itemView.findViewById(R.id.result);
            path = itemView.findViewById(R.id.path);
            pathFollow = itemView.findViewById(R.id.pathFollow);
        }
    }
}

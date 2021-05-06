package com.vaibhav.apslabproject;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter. Holder> {

    Context context;
    int resource;
    java.util.ArrayList<Model> ArrayList;

    public Adapter(Context context, int resource, ArrayList<Model>  ArrayList) {
        this.context = context;
        this.resource = resource;
        this.ArrayList = ArrayList;
    }

    @NonNull
    @Override
    public Adapter. Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Adapter. Holder(LayoutInflater.from(context).inflate(resource,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull Adapter. Holder holder, int position) {
        Model model = ArrayList.get(position);
        holder.first.setText(model.getPt1());
        holder.second.setText(model.getPt2());
        holder.value.setText(model.getDist());
    }

    @Override
    public int getItemCount() {
        return ArrayList.size();
    }


    class  Holder extends RecyclerView.ViewHolder {

        TextView first, second, value;
        public  Holder(@NonNull View itemView) {
            super(itemView);
            first = itemView.findViewById(R.id.first);
            second = itemView.findViewById(R.id.second);
            value = itemView.findViewById(R.id.value);
        }
    }
}

package com.example.task;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    Context context;
    ArrayList<Task> task;

    public TaskAdapter(Context c,ArrayList<Task> p){
        context=c;
        task=p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_does,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.titledoes.setText(task.get(position).getTitledoes());
        holder.descdoes.setText(task.get(position).getDescdoes());
        holder.adddate2.setText(task.get(position).getAdddate2());


        final String getTitleDoes=task.get(position).getTitledoes();
        final String getDescDoes=task.get(position).getDescdoes();
        final String getAddDate2=task.get(position).getAdddate2();
        final String getKeyDoes=task.get(position).getKeydoes();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aa=new Intent(context,EditTaskDesk.class);
                aa.putExtra("titledoes",getTitleDoes);
                aa.putExtra("descdoes",getDescDoes);
                aa.putExtra("adddate2",getAddDate2);
                aa.putExtra("keydoes", getKeyDoes);
                context.startActivity(aa);
            }
        });

    }

    @Override
    public int getItemCount() {
        return task.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titledoes,descdoes,adddate2,keydoes;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            titledoes=(TextView) itemView.findViewById(R.id.titledoes);
            descdoes=(TextView) itemView.findViewById(R.id.descdoes);
            adddate2=(TextView) itemView.findViewById(R.id.adddate2);
        }
    }
}

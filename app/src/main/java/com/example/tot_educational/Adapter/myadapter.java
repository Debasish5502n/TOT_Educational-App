package com.example.tot_educational.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tot_educational.Activity.viewpdf;
import com.example.tot_educational.Model.model;
import com.example.tot_educational.R;

import java.util.ArrayList;


public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder>
{
    Context context;
    ArrayList<model> model;

    public myadapter(Context context, ArrayList<model> model) {
        this.context = context;
        this.model = model;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.singelrowdesign,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        model model1=model.get(position);
        holder.header.setText(model1.getFilename());
        holder.dateText.setText("Date :"+model1.getDate()+ " "+"Time :"+model1.getTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(holder.img1.getContext(),viewpdf.class);
                intent.putExtra("filename",model1.getFilename());
                intent.putExtra("fileurl",model1.getFileurl());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.img1.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return model.size();
    }


    public class myviewholder extends RecyclerView.ViewHolder {
        ImageView img1;
        TextView header;
        TextView dateText;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            img1=itemView.findViewById(R.id.img1);
            header=itemView.findViewById(R.id.header);
            dateText=itemView.findViewById(R.id.date_text);

        }
    }
}

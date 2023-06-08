package com.example.tot_educational.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tot_educational.Model.CommunityChatModel;
import com.example.tot_educational.R;

import java.util.List;

public class CommunityChatAdapter extends RecyclerView.Adapter<CommunityChatAdapter.myViewHolder> {

    Context context;
    List<CommunityChatModel> models;

    public CommunityChatAdapter(Context context, List<CommunityChatModel> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_community_chat,parent,false);
        return new CommunityChatAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        holder.chat.setText(models.get(position).getChat());
        holder.pdfTitle.setText(models.get(position).getPdfTitle());
        Glide.with(context).load(models.get(position).getImage()).placeholder(R.drawable.image_background).into(holder.image);

        if (models.get(position).getPdf().equals("")){
            holder.pdfBackground.setVisibility(View.GONE);
            holder.image.setVisibility(View.VISIBLE);
        }
        if (models.get(position).getImage().equals("")){
            holder.image.setVisibility(View.GONE);
            holder.pdfBackground.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView chat,pdfTitle;
        ImageView image;
        ConstraintLayout pdfBackground;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            chat = itemView.findViewById(R.id.chat);
            pdfTitle= itemView.findViewById(R.id.pdfTitle);
            image = itemView.findViewById(R.id.chat_image);
            pdfBackground = itemView.findViewById(R.id.pdf_background);
        }
    }
}

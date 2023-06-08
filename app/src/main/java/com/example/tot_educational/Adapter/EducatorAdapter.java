package com.example.tot_educational.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tot_educational.Activity.CommunityChatActivity;
import com.example.tot_educational.Model.EducatorModel;
import com.example.tot_educational.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EducatorAdapter extends RecyclerView.Adapter<EducatorAdapter.myViewHolder> {

    Context context;
    List<EducatorModel> models;

    public EducatorAdapter(Context context, List<EducatorModel> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public EducatorAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_ed_item,parent,false);
        return new EducatorAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EducatorAdapter.myViewHolder holder, int position) {

        holder.edName.setText(models.get(position).getEd_name());
        holder.edBio.setText(models.get(position).getEd_bio());
        Glide.with(context).load(models.get(position).getEd_image()).placeholder(R.drawable.avatar).into(holder.edImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommunityChatActivity.class);
                intent.putExtra("edName", models.get(position).getEd_name());
                intent.putExtra("edImage", models.get(position).getEd_image());
                intent.putExtra("edBio", models.get(position).getEd_bio());
                intent.putExtra("edId", models.get(position).getEd_id());
                intent.putExtra("edFollower", models.get(position).getEd_follower());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        CircleImageView edImage;
        TextView edName,edBio;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            edImage = itemView.findViewById(R.id.ed_image);
            edName = itemView.findViewById(R.id.ed_name);
            edBio = itemView.findViewById(R.id.ed_bio);
        }
    }
}

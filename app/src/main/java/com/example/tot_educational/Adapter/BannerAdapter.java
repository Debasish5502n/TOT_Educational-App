package com.example.tot_educational.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tot_educational.Model.BannerModel;
import com.example.tot_educational.R;

import java.util.ArrayList;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.myViewHolder> {

    Context context;
    ArrayList<BannerModel> models;

    public BannerAdapter(Context context, ArrayList<BannerModel> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public BannerAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_banner_item,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerAdapter.myViewHolder holder, int position) {
            Glide.with(context).load(models.get(position).getBanner_image()).into(holder.bannerImage);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        ImageView bannerImage;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            bannerImage = itemView.findViewById(R.id.banner_image);
        }
    }
}

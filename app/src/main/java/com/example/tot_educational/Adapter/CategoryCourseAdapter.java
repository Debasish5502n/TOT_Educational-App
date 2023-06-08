package com.example.tot_educational.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tot_educational.Activity.SetsActivity;
import com.example.tot_educational.Model.CategoryCourseModel;
import com.example.tot_educational.R;

import java.util.ArrayList;

public class CategoryCourseAdapter extends RecyclerView.Adapter<CategoryCourseAdapter.ViewHolder> {

    ArrayList<CategoryCourseModel> models;

    public CategoryCourseAdapter(ArrayList<CategoryCourseModel> models) {
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(models.get(position).getCategoryTitle(), models.get(position).getCategoryCourse());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.background1.getContext(), SetsActivity.class);
                intent.putExtra("setsTitle",models.get(position).getCategoryTitle());
                holder.background1.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title1, course1;
        LinearLayout background1;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.category_image);
            title1 = itemView.findViewById(R.id.category_title);
            background1 = itemView.findViewById(R.id.background);
            course1 = itemView.findViewById(R.id.category_course);
        }

        private void setData(final String title, String course) {
            title1.setText(title);

            if (title.equals("Mathematics")) {
                background1.setBackground(imageView.getResources().getDrawable(R.drawable.bg_math));
                Glide.with(itemView.getContext()).load(R.drawable.calculator).into(imageView);
            }else if (title.equals("History")){
                background1.setBackground(imageView.getResources().getDrawable(R.drawable.bg_history));
                Glide.with(itemView.getContext()).load(R.drawable.history1).into(imageView);
            }else if (title.equals("Geography")){
                background1.setBackground(imageView.getResources().getDrawable(R.drawable.bg_geo));
                Glide.with(itemView.getContext()).load(R.drawable.geo_icon).into(imageView);
            }else if (title.equals("Polity")){
                background1.setBackground(imageView.getResources().getDrawable(R.drawable.bg_polity));
                Glide.with(itemView.getContext()).load(R.drawable.polity).into(imageView);
            }else if (title.equals("Science")){
                background1.setBackground(imageView.getResources().getDrawable(R.drawable.bg_science));
                Glide.with(itemView.getContext()).load(R.drawable.science_icon).into(imageView);
            }else if (title.equals("Current Affairs")){
                background1.setBackground(imageView.getResources().getDrawable(R.drawable.bg_current));
                Glide.with(itemView.getContext()).load(R.drawable.current_affairs_icon).into(imageView);
            }else if (title.equals("Environment & Ecology")){
                background1.setBackground(imageView.getResources().getDrawable(R.drawable.bg_environment));
                Glide.with(itemView.getContext()).load(R.drawable.environment).into(imageView);
            }else {
                background1.setBackground(imageView.getResources().getDrawable(R.drawable.bg_history));
                Glide.with(itemView.getContext()).load(R.drawable.history1).into(imageView);
            }
            course1.setText(course + " Course");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
    }
}

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

import com.bumptech.glide.Glide;
import com.example.tot_educational.Activity.VideoPlayerActivity;
import com.example.tot_educational.Model.LessonModel;
import com.example.tot_educational.R;

import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.myViewHolder> {

    Context context;
    List<LessonModel> models;

    public LessonAdapter(Context context, List<LessonModel> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public LessonAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_course_item,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonAdapter.myViewHolder holder, int position) {

        holder.lessonTitle.setText(models.get(position).getLessonTitle());
        Glide.with(context).load(models.get(position).getLessonImage()).into(holder.lessonImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoPlayerActivity.class);
                intent.putExtra("lessonTitle",models.get(position).getLessonTitle());
                intent.putExtra("lessonVideo",models.get(position).getLessonVideo());
                intent.putExtra("lessonYoutubeVideo",models.get(position).getLessonYoutubeVideo());
                intent.putExtra("lessonImage",models.get(position).getLessonImage());
                intent.putExtra("id",models.get(position).getLessonId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView lessonTitle,lessonLikes,lessonComments;
        ImageView lessonImage;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            lessonTitle = itemView.findViewById(R.id.lesson_title);
            lessonImage = itemView.findViewById(R.id.lesson_image);
            lessonLikes = itemView.findViewById(R.id.lesson_like);
            lessonComments = itemView.findViewById(R.id.lesson_comment);
        }
    }
}

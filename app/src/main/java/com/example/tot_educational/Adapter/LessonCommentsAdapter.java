package com.example.tot_educational.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tot_educational.Model.LessonCommentsModel;
import com.example.tot_educational.R;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class LessonCommentsAdapter extends RecyclerView.Adapter<LessonCommentsAdapter.myViewHolder> {

    Context context;
    ArrayList<LessonCommentsModel> models;

    public LessonCommentsAdapter(Context context, ArrayList<LessonCommentsModel> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public LessonCommentsAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_lesson_comments,parent,false);
        return new LessonCommentsAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonCommentsAdapter.myViewHolder holder, int position) {
        if (models.get(position).getLessonCommentImage().equals("")){
            Glide.with(context).load(R.drawable.avatar).placeholder(R.drawable.avatar).into(holder.circleImageView);
        }else {
            Glide.with(context).load(models.get(position).getLessonCommentImage()).placeholder(R.drawable.avatar).into(holder.circleImageView);
        }
        holder.lessonName.setText(models.get(position).getLessonCommentName());
        holder.lessonComments.setText(models.get(position).getLessonComments());

        DateFormat obj = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        // we create instance of the Date and pass milliseconds to the constructor
        Date res = new Date(Long.parseLong(models.get(position).getTimeStamp()));
        holder.lessonTimeStamp.setText(obj.format(res));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView lessonName,lessonComments,lessonTimeStamp;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.lesson_comment_image);
            lessonName = itemView.findViewById(R.id.lesson_comment_name);
            lessonComments = itemView.findViewById(R.id.lesson_comment);
            lessonTimeStamp = itemView.findViewById(R.id.timeStamp);
        }
    }
}

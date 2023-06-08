package com.example.tot_educational.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tot_educational.Model.LessonScoreModel;
import com.example.tot_educational.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LessonScoreAdapter extends RecyclerView.Adapter<LessonScoreAdapter.myViewHolder> {

    List<LessonScoreModel> lessonScoreModels;
    Context context;

    public LessonScoreAdapter(List<LessonScoreModel> lessonScoreModels, Context context) {
        this.lessonScoreModels = lessonScoreModels;
        this.context = context;
    }

    @NonNull
    @Override
    public LessonScoreAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_lessons_score,parent,false);
        return new LessonScoreAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonScoreAdapter.myViewHolder holder, int position) {
        String totalAnswer = String.valueOf(Long.parseLong(lessonScoreModels.get(position).getTotal_poll()) - Long.parseLong(lessonScoreModels.get(position).getWrong_poll()));

        if (lessonScoreModels.get(position).getImage().equals("")){
            Glide.with(context).load(R.drawable.avatar).placeholder(R.drawable.avatar).into(holder.circleImageView);
        }else {
            Glide.with(context).load(lessonScoreModels.get(position).getImage()).placeholder(R.drawable.avatar).into(holder.circleImageView);
        }
        holder.name.setText(lessonScoreModels.get(position).getName());
        holder.totalPoll.setText(lessonScoreModels.get(position).getTotal_poll());
        holder.wrongPoll.setText(lessonScoreModels.get(position).getWrong_poll());
        holder.correctPoll.setText(lessonScoreModels.get(position).getCorrect_poll());
        holder.totalAnswer.setText(totalAnswer);
        if (Long.parseLong(totalAnswer) < Long.parseLong(lessonScoreModels.get(position).getWrong_poll())){
            holder.totalAnswer.setTextColor(Color.parseColor("#EF0000"));
        }else  if (Long.parseLong(totalAnswer) > Long.parseLong(lessonScoreModels.get(position).getWrong_poll())){
            holder.totalAnswer.setTextColor(Color.parseColor("#00EF3C"));
        }
    }

    @Override
    public int getItemCount() {
        return lessonScoreModels.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        CircleImageView circleImageView;
        TextView name,totalPoll,wrongPoll,correctPoll,totalAnswer;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            totalPoll = itemView.findViewById(R.id.total_poll);
            wrongPoll = itemView.findViewById(R.id.wrong_poll);
            correctPoll = itemView.findViewById(R.id.correct_poll);
            totalAnswer = itemView.findViewById(R.id.total_answer);

        }
    }
}

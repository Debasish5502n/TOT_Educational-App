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
import com.example.tot_educational.Activity.CourseActivity;
import com.example.tot_educational.Model.UpcomingCourseModel;
import com.example.tot_educational.R;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpcomingCourseAdapter extends RecyclerView.Adapter<UpcomingCourseAdapter.myViewHolder> {

    Context context;
    List<UpcomingCourseModel> models;

    public UpcomingCourseAdapter(Context context, List<UpcomingCourseModel> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_upcoming_classes, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        holder.courseTitle.setText(models.get(position).getCourseTitle());
        holder.courseCount.setText(models.get(position).getCourseCount()+" Lessons");
        holder.ed_name.setText(models.get(position).getEd_name());
        Glide.with(context).load(models.get(position).getCourseImage()).into(holder.courseImage);
        Glide.with(context).load(models.get(position).getEd_image()).into(holder.ed_image);

        Map<String, Object> map = new HashMap<>();
        map.put("courseUpdate", "Started");
        Long currentTime = System.currentTimeMillis();

        if (models.get(position).getCourseStartDate() < currentTime) {
            FirebaseDatabase.getInstance().getReference()
                    .child("Course")
                    .child(models.get(position).getCourseId())
                    .updateChildren(map);
        }

        DateFormat obj = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        // we create instance of the Date and pass milliseconds to the constructor
        Date res = new Date(models.get(position).getCourseStartDate());

        holder.courseDate.setText("Start on- "+obj.format(res));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseActivity.class);
                intent.putExtra("courseTitle",models.get(position).getCourseTitle());
                intent.putExtra("courseImage",models.get(position).getCourseImage());
                intent.putExtra("courseCount",models.get(position).getCourseCount());
                intent.putExtra("ed_name",models.get(position).getEd_name());
                intent.putExtra("ed_image",models.get(position).getEd_image());
                intent.putExtra("ed_id",models.get(position).getEd_id());
                intent.putExtra("id",models.get(position).getCourseId());
                intent.putExtra("previewVideo",models.get(position).getPreviewVideo());
                intent.putExtra("language",models.get(position).getLanguage());
                intent.putExtra("courseDetails",models.get(position).getCourseDetails());
                intent.putExtra("subject",models.get(position).getSubject());
                intent.putExtra("courseStartDate",models.get(position).getCourseStartDate());
                intent.putExtra("courseEndDate",models.get(position).getCourseEndDate());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView courseTitle, courseDate, courseCount, ed_name;
        ImageView courseImage;
        CircleImageView ed_image;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            courseTitle = itemView.findViewById(R.id.class_title);
            courseCount = itemView.findViewById(R.id.courseCount);
            courseDate = itemView.findViewById(R.id.date);
            ed_name = itemView.findViewById(R.id.ed_name);
            courseImage = itemView.findViewById(R.id.class_image);
            ed_image = itemView.findViewById(R.id.ed_image);

        }
    }
}

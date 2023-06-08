package com.example.tot_educational.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.tot_educational.Adapter.LessonAdapter;
import com.example.tot_educational.Model.LessonModel;
import com.example.tot_educational.R;
import com.example.tot_educational.databinding.ActivityCourseBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseActivity extends AppCompatActivity {

    private ActivityCourseBinding binding;
    RecyclerView lessonRecyclerView;
    LessonAdapter lessonAdapter;
    String courseTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        lessonRecyclerView = findViewById(R.id.lesson_recyclerView);

        String courseImage = getIntent().getStringExtra("courseImage");
        String courseCount = getIntent().getStringExtra("courseCount");
        courseTitle = getIntent().getStringExtra("courseTitle");
        String ed_name = getIntent().getStringExtra("ed_name");
        String ed_image = getIntent().getStringExtra("ed_image");
        String courseUpdate = getIntent().getStringExtra("courseUpdate");
        String ed_id = getIntent().getStringExtra("ed_id");
        String id = getIntent().getStringExtra("id");
        String previewVideo = getIntent().getStringExtra("previewVideo");
        String language = getIntent().getStringExtra("language");
        String courseDetails = getIntent().getStringExtra("courseDetails");
        String subject = getIntent().getStringExtra("subject");
        long courseStartDate = getIntent().getLongExtra("courseStartDate",1);
        long courseEndDate = getIntent().getLongExtra("courseEndDate",1);

        lessons();
        DateFormat obj = new SimpleDateFormat("dd MMM yyyy HH:mm");
        // we create instance of the Date and pass milliseconds to the constructor
        Date startDate = new Date(courseStartDate);
        Date endDate = new Date(courseEndDate);

        binding.date.setText(obj.format(startDate) +" To "+ obj.format(endDate));
        Glide.with(CourseActivity.this).load(ed_image).into(binding.edImage);
        Glide.with(CourseActivity.this).load(courseImage).into(binding.previewImage);
        binding.lessons.setText(courseCount+" Lessons");
        binding.edName.setText(ed_name);
        binding.language.setText(language);
        binding.subject.setText(subject);
        binding.lessonDetails.setText(courseDetails);
        binding.courseTitle.setText(courseTitle);

    }

    private void lessons(){
        List<LessonModel> lessonModels = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(CourseActivity.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        lessonRecyclerView.setLayoutManager(layoutManager);
        lessonAdapter = new LessonAdapter(CourseActivity.this,lessonModels);
        lessonRecyclerView.setAdapter(lessonAdapter);

        FirebaseDatabase.getInstance().getReference().child("Lessons")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                            LessonModel lessonModel = dataSnapshot1.getValue(LessonModel.class);

                            if (lessonModel.getCourseTitle().equals(courseTitle)) {
                                lessonModels.add(lessonModel);
                            }
                        }
                        lessonAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Map<String,Object> map = new HashMap<>();
        map.put("courseTitle",courseTitle);

        FirebaseDatabase.getInstance().getReference().child("users")
                .child(FirebaseAuth.getInstance().getUid())
                .updateChildren(map);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Map<String,Object> map = new HashMap<>();
        map.put("courseTitle","");

        FirebaseDatabase.getInstance().getReference().child("users")
                .child(FirebaseAuth.getInstance().getUid())
                .updateChildren(map);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Map<String,Object> map = new HashMap<>();
        map.put("courseTitle","");

        FirebaseDatabase.getInstance().getReference().child("users")
                .child(FirebaseAuth.getInstance().getUid())
                .updateChildren(map);
    }
}
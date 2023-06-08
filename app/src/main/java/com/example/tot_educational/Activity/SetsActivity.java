package com.example.tot_educational.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.tot_educational.Adapter.SetsAdapter;
import com.example.tot_educational.Model.SetsModel;
import com.example.tot_educational.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SetsActivity extends AppCompatActivity {

    RecyclerView setsRecyclerView;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);
        setsRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        title = findViewById(R.id.title);
        String setsTitle = getIntent().getStringExtra("setsTitle");

        List<SetsModel> setsModels = new ArrayList<>();
        GridLayoutManager layoutManager = new GridLayoutManager(SetsActivity.this, 1);
        setsRecyclerView.setLayoutManager(layoutManager);
        setsRecyclerView.setLayoutManager(layoutManager);
        SetsAdapter setsAdapter = new SetsAdapter(SetsActivity.this, setsModels);
        setsRecyclerView.setAdapter(setsAdapter);

        FirebaseDatabase.getInstance().getReference().child("Sets").child(setsTitle)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                            SetsModel setsModel = dataSnapshot1.getValue(SetsModel.class);

                            setsModels.add(setsModel);
                        }
                        setsAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}
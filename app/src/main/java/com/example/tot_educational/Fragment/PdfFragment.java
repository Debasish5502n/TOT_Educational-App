package com.example.tot_educational.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.example.tot_educational.Activity.uploadfile;
import com.example.tot_educational.Adapter.myadapter;
import com.example.tot_educational.Model.model;
import com.example.tot_educational.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class PdfFragment extends Fragment {

    ShimmerRecyclerView recview;
    myadapter adapter;
    FloatingActionButton floatingActionButton;
    ArrayList<model> models;
    FirebaseDatabase database;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_pdf, container, false);
        floatingActionButton=view.findViewById(R.id.floatingActionButton);
        recview=view.findViewById(R.id.recyclerView);
        models=new ArrayList<>();
        database=FirebaseDatabase.getInstance();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), uploadfile.class));
            }
        });

        adapter=new myadapter(getContext(),models);
        recview.setAdapter(adapter);
        recview.showShimmerAdapter();

        database.getReference().child("mypdf").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1 :snapshot.getChildren()){
                    models.size();
                    model user=snapshot1.getValue(model.class);
                    recview.hideShimmerAdapter();
                        models.add(user);

                }
                Collections.sort(models,model.nameAtoZ);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }
}
package com.example.tot_educational.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tot_educational.Adapter.EducatorAdapter;
import com.example.tot_educational.Model.EducatorModel;
import com.example.tot_educational.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CommunityFragment extends Fragment {

    RecyclerView edRecyclerView;
    EducatorAdapter educatorAdapter;
    List<EducatorModel> educatorModels;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);
        edRecyclerView = view.findViewById(R.id.ed_recyclerView);
        edRecyclerView();

        return view;
    }

    private void edRecyclerView(){
        educatorModels = new ArrayList<>();
        educatorAdapter = new EducatorAdapter(getActivity(),educatorModels);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        edRecyclerView.setLayoutManager(layoutManager);
        edRecyclerView.setAdapter(educatorAdapter);

        FirebaseDatabase.getInstance().getReference().child("Educators")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                            EducatorModel educatorModel = dataSnapshot1.getValue(EducatorModel.class);

                            educatorModels.add(educatorModel);
                        }
                        educatorAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}
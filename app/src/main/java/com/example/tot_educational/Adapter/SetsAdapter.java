package com.example.tot_educational.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tot_educational.Activity.QuestionsActivity;
import com.example.tot_educational.Model.SetsModel;
import com.example.tot_educational.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetsAdapter extends RecyclerView.Adapter<SetsAdapter.myViewHolder> {

    Context context;
    List<SetsModel> models;

    public SetsAdapter(Context context, List<SetsModel> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public SetsAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sets_item, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SetsAdapter.myViewHolder holder, int position) {
        holder.sets_count.setText(String.valueOf(models.get(position).getSetsId()));
        holder.sets_title.setText(models.get(position).getSets_title());

        String subject = models.get(position).getSubject();
        int setsNo = models.get(position).getSetsId();
        String uid = FirebaseAuth.getInstance().getUid();

        FirebaseDatabase.getInstance().getReference("setsClicked")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.child(subject).child(String.valueOf(setsNo)).hasChild(uid)){
                            FirebaseDatabase.getInstance().getReference().child("setsClicked")
                                    .child(subject)
                                    .child(String.valueOf(setsNo))
                                    .child(uid)
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            String percentage = snapshot.child("percentage").getValue(String.class);
                                            if (percentage.equals("Failed")){
                                                holder.background.setBackgroundColor(Color.parseColor("#EBF3F3"));
                                                holder.percentage.setText("F");
                                            }else if (percentage.equals("")){
                                                holder.background.setBackgroundColor(Color.parseColor("#ffffff"));
                                                holder.percentage.setText("");
                                            }else {
                                                holder.background.setBackgroundColor(Color.parseColor("#EBF3F3"));
                                                holder.percentage.setText(percentage);
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                        } else {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, QuestionsActivity.class);
                    intent.putExtra("id", models.get(position).getSetsId());
                    intent.putExtra("subject", models.get(position).getSubject());
                    context.startActivity(intent);

                    Map<String, Object> map = new HashMap<>();
                    map.put("percentage", "");
                    map.put("uid", FirebaseAuth.getInstance().getUid());
                    FirebaseDatabase.getInstance().getReference().child("setsClicked")
                            .child(subject)
                            .child(String.valueOf(setsNo))
                            .child(uid)
                            .setValue(map);

                }
            });

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView sets_count, sets_title, percentage;
        ConstraintLayout background;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            sets_count = itemView.findViewById(R.id.sets_count);
            sets_title = itemView.findViewById(R.id.sets_title);
            percentage = itemView.findViewById(R.id.percentage);
            background = itemView.findViewById(R.id.background);
        }
    }
}

package com.example.tot_educational.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tot_educational.Adapter.CommunityChatAdapter;
import com.example.tot_educational.Model.CommunityChatModel;
import com.example.tot_educational.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommunityChatActivity extends AppCompatActivity {

    CircleImageView educatorImage;
    TextView educatorName, educatorFollowers;
    ImageView backArrow, more;
    String edName, edImage, edId, edBio;
    Long edFollower;

    RecyclerView communityChatRecyclerView;
    CommunityChatAdapter communityChatAdapter;
    List<CommunityChatModel> communityChatModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_chat);
        educatorImage = findViewById(R.id.ed_image);
        educatorName = findViewById(R.id.ed_name);
        educatorFollowers = findViewById(R.id.ed_followers);
        backArrow = findViewById(R.id.back_arrow);
        more = findViewById(R.id.more);
        communityChatRecyclerView = findViewById(R.id.community_chat_recyclerView);

        edName = getIntent().getStringExtra("edName");
        edImage = getIntent().getStringExtra("edImage");
        edId = getIntent().getStringExtra("edId");
        edBio = getIntent().getStringExtra("edBio");
        edFollower = getIntent().getLongExtra("edFollower",0);

        educatorFollowers.setText(edFollower + " Active followers");
        educatorName.setText(edName);
        Glide.with(this).load(edImage).placeholder(R.drawable.avatar).into(educatorImage);
        communityChat();
    }

    private void  communityChat(){
        communityChatModels = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        communityChatRecyclerView.setLayoutManager(layoutManager);
        communityChatAdapter = new CommunityChatAdapter(this, communityChatModels);
        communityChatRecyclerView.setAdapter(communityChatAdapter);

        FirebaseDatabase.getInstance().getReference().child("CommunityChats")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                            CommunityChatModel communityChatModel = dataSnapshot1.getValue(CommunityChatModel.class);

                            if (communityChatModel.getEd_id().equals(edId)) {
                                communityChatModels.add(communityChatModel);
                            }
                        }
                        communityChatAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}